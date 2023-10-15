package grafnus.portalshard.data.HibernateDO;

import grafnus.portalshard.data.DAO.ConnectionDAO;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "portal")
public class HibernatePortal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "connection_id")
    private Long connectionId;

    @Column(name = "world")
    private String world;

    @Column(name = "x", columnDefinition = "REAL")
    private int x;

    @Column(name = "y", columnDefinition = "REAL")
    private int y;

    @Column(name = "z", columnDefinition = "REAL")
    private int z;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;

    public HibernatePortal() {
    }

    public HibernatePortal(Long connectionId, String world , int x, int y, int z) {
        this.connectionId = connectionId;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
    }

    public HibernatePortal(Long connectionId, Location location) {
        this(connectionId, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public Long getId() {
        return id;
    }

    public Long getConnectionId() {
        return connectionId;
    }

    public HibernateConnection getConnection() {
        return ConnectionDAO.getConnectionById(this.connectionId);
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(getWorld()), getX(), getY(), getZ());
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }
}
