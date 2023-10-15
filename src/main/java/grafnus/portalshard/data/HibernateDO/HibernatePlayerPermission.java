package grafnus.portalshard.data.HibernateDO;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "player_perms")
public class HibernatePlayerPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "connection_id")
    private Long connectionId;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "`use`")
    private boolean use;

    @Column(name = "charge")
    private boolean charge;

    @Column(name = "upgrade")
    private boolean upgrade;

    @Column(name = "destroy")
    private boolean destroy;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;

    public HibernatePlayerPermission() {}

    public HibernatePlayerPermission(Long connectionId, UUID uuid) {
        this.connectionId = connectionId;
        this.uuid = uuid.toString();
        this.use = false;
        this.charge = true;
        this.upgrade = false;
        this.destroy = false;
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getConnectionId() {
        return connectionId;
    }

    public String getUuid() {
        return uuid;
    }

    public UUID getUUID() {
        return UUID.fromString(this.uuid);
    }

    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(getUUID());
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
        this.updated = LocalDateTime.now();
    }

    public boolean isUse() {
        return use;
    }

    public void setUse(boolean use) {
        this.use = use;
        this.updated = LocalDateTime.now();
    }

    public boolean isCharge() {
        return charge;
    }

    public void setCharge(boolean charge) {
        this.charge = charge;
        this.updated = LocalDateTime.now();
    }

    public boolean isUpgrade() {
        return upgrade;
    }

    public void setUpgrade(boolean upgrade) {
        this.upgrade = upgrade;
        this.updated = LocalDateTime.now();
    }

    public boolean isDestroy() {
        return destroy;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
        this.updated = LocalDateTime.now();
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }
}
