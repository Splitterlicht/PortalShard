package grafnus.portalshard.data.DAO;

import grafnus.portalshard.data.DO.Connection;
import grafnus.portalshard.data.HibernateUtil;
import org.bukkit.Bukkit;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.UUID;
import java.util.logging.Level;

public class ConnectionDAO {
    public static void saveConnection(Connection connection) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.saveOrUpdate(connection);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnectionById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Connection.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Connection getConnectionByUuid(UUID uuid) {
        String uniqueID = uuid.toString();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Connection> query = session.createQuery("FROM Connection WHERE uuid = :uuid", Connection.class);
            query.setParameter("uuid", uniqueID);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void removeConnection(Connection connection) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.remove(connection);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }
}
