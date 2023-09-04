package grafnus.portalshard.data.DAO;

import grafnus.portalshard.data.DO.Connection;
import grafnus.portalshard.data.DO.Portal;
import grafnus.portalshard.data.HibernateUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class PortalDAO {
    public static void savePortal(Portal portal) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(portal);
                transaction.commit();
                Bukkit.getLogger().log(Level.INFO, "Connection added to db!");
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                    Bukkit.getLogger().log(Level.INFO, "Connection could not be added to db!");
                }
                e.printStackTrace();
            }
        }
    }

    public static Portal getPortalById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Portal.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Portal getPortalByLocation(Location loc) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Portal> query = session.createQuery("FROM Portal WHERE world = :world AND x = :x AND y = :y AND z = :z", Portal.class);
            query.setParameter("world", loc.getWorld().getName());
            query.setParameter("x", loc.getBlockX());
            query.setParameter("y", loc.getBlockY());
            query.setParameter("z", loc.getBlockZ());
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Portal> getPortalsByConnectionId(Long connectionId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Portal> query = session.createQuery("FROM Portal WHERE connection_id >= :connection_id", Portal.class);
            query.setParameter("connection_id", connectionId);
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }

    public static void removePortal(Portal portal) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.remove(portal);
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
