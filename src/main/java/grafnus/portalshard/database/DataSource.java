package grafnus.portalshard.database;

import com.google.common.collect.ImmutableMap;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import grafnus.portalshard.database.tables.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSource {

    private static DataSource dh ;

    private static final AtomicInteger POOL_COUNTER = new AtomicInteger(0);

    // https://github.com/brettwooldridge/HikariCP/wiki/About-Pool-Sizing
    private static final int MAXIMUM_POOL_SIZE = (Runtime.getRuntime().availableProcessors() * 2) + 1;
    private static final int MINIMUM_IDLE = Math.min(MAXIMUM_POOL_SIZE, 10);

    private static final long MAX_LIFETIME = TimeUnit.MINUTES.toMillis(30);
    private static final long CONNECTION_TIMEOUT = TimeUnit.SECONDS.toMillis(10);
    private static final long LEAK_DETECTION_THRESHOLD = TimeUnit.SECONDS.toMillis(10);

    private HikariConfig config = new HikariConfig();
    private HikariDataSource ds;

    private DataSource() {
        config.setPoolName("PortalShard" + POOL_COUNTER.getAndIncrement());
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/PortalShard");
        config.setUsername("PortalShard");
        config.setPassword("1234QWer!!");
        config.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
        config.setMinimumIdle(MINIMUM_IDLE);

        config.setMaxLifetime(MAX_LIFETIME);
        config.setConnectionTimeout(CONNECTION_TIMEOUT);
        config.setLeakDetectionThreshold(LEAK_DETECTION_THRESHOLD);

        Map<String, String> properties = ImmutableMap.<String, String>builder()
                // Ensure we use utf8 encoding
                .put("useUnicode", "true")
                .put("characterEncoding", "utf8")

                // https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
                .put("cachePrepStmts", "true")
                .put("prepStmtCacheSize", "250")
                .put("prepStmtCacheSqlLimit", "2048")
                .put("useServerPrepStmts", "true")
                .put("useLocalSessionState", "true")
                .put("rewriteBatchedStatements", "true")
                .put("cacheResultSetMetadata", "true")
                .put("cacheServerConfiguration", "true")
                .put("elideSetAutoCommits", "true")
                .put("maintainTimeStats", "false")
                .put("alwaysSendSetIsolation", "false")
                .put("cacheCallableStmts", "true")

                // Set the driver level TCP socket timeout
                // See: https://github.com/brettwooldridge/HikariCP/wiki/Rapid-Recovery
                .put("socketTimeout", String.valueOf(TimeUnit.SECONDS.toMillis(30)))
                .build();

        for (Map.Entry<String, String> property : properties.entrySet()) {
            config.addDataSourceProperty(property.getKey(), property.getValue());
        }


        ds = new HikariDataSource( config );
        Logger logger = Logger.getLogger(HikariDataSource.class.getName());
        logger.setLevel(Level.WARNING);
    }

    public void initTables() {

        new DBConnection();
        new DBPortal();
        new DBPlayerPerms();
        //PortalTable.createTable();
        //ConnectionTable.createTable();
    }

    public <T> T execute(ConnectionCallback<T> callback) {
        try (Connection conn = ds.getConnection()) {
            return callback.doInConnection(conn);
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    public static interface ConnectionCallback<T> {
        public T doInConnection(Connection conn) throws SQLException;
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static synchronized DataSource getInstance(){
        if (dh == null){
            dh = new DataSource();
        }
        return dh;
    }


}
