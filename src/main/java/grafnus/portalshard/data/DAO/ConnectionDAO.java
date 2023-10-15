package grafnus.portalshard.data.DAO;

import grafnus.portalshard.data.HibernateDO.HibernateConnection;
import grafnus.portalshard.data.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.UUID;

public class ConnectionDAO {
    public static void saveConnection(HibernateConnection connection) {
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

    public static HibernateConnection getConnectionById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(HibernateConnection.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HibernateConnection getConnectionByUuid(UUID uuid) {
        String uniqueID = uuid.toString();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<HibernateConnection> query = session.createQuery("FROM HibernateConnection WHERE uuid = :uuid", HibernateConnection.class);
            query.setParameter("uuid", uniqueID);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void removeConnection(HibernateConnection connection) {
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
