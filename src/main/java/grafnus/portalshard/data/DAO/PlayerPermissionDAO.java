package grafnus.portalshard.data.DAO;

import grafnus.portalshard.data.DO.Connection;
import grafnus.portalshard.data.DO.PlayerPermission;
import grafnus.portalshard.data.DO.Portal;
import grafnus.portalshard.data.HibernateUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.UUID;

public class PlayerPermissionDAO {
    public static void savePlayerPermission(PlayerPermission playerPermission) {
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

    public static PlayerPermission getPlayerPermissionById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(PlayerPermission.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PlayerPermission getPlayerPermissionByConnectionIdAndPlayer(Long connectionId, OfflinePlayer player) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<PlayerPermission> query = session.createQuery("FROM PlayerPermission WHERE connection_id = :connection_id AND uuid = :uuid", PlayerPermission.class);
            query.setParameter("connection_id", connectionId);
            query.setParameter("uuid", player.getUniqueId().toString());
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<PlayerPermission> getPlayerPermissionsByConnectionId(Long connectionId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<PlayerPermission> query = session.createQuery("FROM PlayerPermission WHERE connection_id >= :connection_id", PlayerPermission.class);
            query.setParameter("connection_id", connectionId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void removePlayerPermission(PlayerPermission playerPermission) {
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
