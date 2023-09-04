package grafnus.portalshard.data;

import grafnus.portalshard.PortalShard;
import grafnus.portalshard.config.Config;
import grafnus.portalshard.data.DO.Connection;
import grafnus.portalshard.data.DO.PlayerPermission;
import grafnus.portalshard.data.DO.Portal;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.sqlite.hibernate.dialect.SQLiteDialect;

import java.io.File;
import java.util.logging.Level;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();

            if (Config.getInstance().isMySQL()) {
                configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
                configuration.setProperty("hibernate.connection.url", "jdbc:mysql://" + Config.getInstance().getMySQLHost() + ":" + Config.getInstance().getMySQLPort() + "/" + Config.getInstance().getMySQLDatabase());
                configuration.setProperty("hibernate.connection.username", Config.getInstance().getMySQLUsername());
                configuration.setProperty("hibernate.connection.password", Config.getInstance().getMySQLPassword());
            } else {
                configuration.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
                configuration.setProperty("hibernate.connection.url", "jdbc:sqlite:" + PortalShard.getInstance().getDataFolder().getPath() + File.separator + "PortalShard.db");
                configuration.setProperty("hibernate.dialect", SQLiteDialect.class.getName());
            }
            // Set Hibernate properties programmatically

            configuration.setProperty("hibernate.hbm2ddl.auto", "update");

            // Add entity classes to configuration
            configuration.addAnnotatedClass(Connection.class);
            configuration.addAnnotatedClass(Portal.class);
            configuration.addAnnotatedClass(PlayerPermission.class);

            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
