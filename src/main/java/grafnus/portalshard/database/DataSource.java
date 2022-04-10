package grafnus.portalshard.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import grafnus.portalshard.database.tables.ConnectionTable;
import grafnus.portalshard.database.tables.PortalTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSource {

    private static DataSource dh ;

    private HikariConfig config = new HikariConfig();
    private HikariDataSource ds;

    private DataSource() {
        config.setJdbcUrl("jdbc:mysql://localhost:3306/PortalShard");
        config.setUsername("PortalShard");
        config.setPassword("1234QWer!!");
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
        Logger logger = Logger.getLogger(HikariDataSource.class.getName());
        logger.setLevel(Level.WARNING);
    }

    public void initTables() {
        PortalTable.createTable();
        ConnectionTable.createTable();
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
