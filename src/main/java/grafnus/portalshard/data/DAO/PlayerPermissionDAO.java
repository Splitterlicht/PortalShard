package grafnus.portalshard.data.DAO;

import grafnus.portalshard.data.HibernateDO.HibernatePlayerPermission;
import grafnus.portalshard.data.HibernateUtil;
import org.bukkit.OfflinePlayer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PlayerPermissionDAO {
    public static void savePlayerPermission(HibernatePlayerPermission playerPermission) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(playerPermission);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }

    public static HibernatePlayerPermission getPlayerPermissionById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(HibernatePlayerPermission.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HibernatePlayerPermission getPlayerPermissionByConnectionIdAndPlayer(Long connectionId, OfflinePlayer player) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<HibernatePlayerPermission> query = session.createQuery("FROM HibernatePlayerPermission WHERE connection_id = :connection_id AND uuid = :uuid", HibernatePlayerPermission.class);
            query.setParameter("connection_id", connectionId);
            query.setParameter("uuid", player.getUniqueId().toString());
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<HibernatePlayerPermission> getPlayerPermissionsByConnectionId(Long connectionId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<HibernatePlayerPermission> query = session.createQuery("FROM HibernatePlayerPermission WHERE connection_id >= :connection_id", HibernatePlayerPermission.class);
            query.setParameter("connection_id", connectionId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void removePlayerPermission(HibernatePlayerPermission playerPermission) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.remove(playerPermission);
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
