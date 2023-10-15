package grafnus.portalshard.data.HibernateDO;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Hero is the main entity we'll be using to . . .
 *
 * Please see the  class for true identity
 * @author Grafnus
 *
 */
@Entity
@Table(name = "connection")
public class HibernateConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "charges")
    private int charges;

    @Column(name = "level")
    private int level;

    @Column(name = "ffa")
    private boolean ffa;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;

    public HibernateConnection() {
    }

    public HibernateConnection(UUID uuid, int charges, int level, boolean ffa, Player creator) {
        this.uuid = uuid.toString();
        this.charges = charges;
        this.level = level;
        this.ffa = ffa;
        this.createdBy = creator.getUniqueId().toString();
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
    }

    public HibernateConnection(Player creator) {
        this(UUID.randomUUID(), 20, 1, false, creator);
    }

    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public UUID getUUID() {return UUID.fromString(this.uuid);}

    public int getCharges() {
        return charges;
    }

    public void setCharges(int charges) {
        this.charges = charges;
        this.updated = LocalDateTime.now();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        this.updated = LocalDateTime.now();
    }

    public boolean isFfa() {
        return ffa;
    }

    public void setFfa(boolean ffa) {
        this.ffa = ffa;
        this.updated = LocalDateTime.now();
    }

    public String getCreator() {
        return createdBy;
    }

    public UUID getCreatorUUID() {
        return UUID.fromString(this.getCreator());
    }

    public OfflinePlayer getCreatorPlayer() {return Bukkit.getOfflinePlayer(UUID.fromString(this.getCreator()));}

    public void setCreator(String creator) {
        this.createdBy = createdBy;
        this.updated = LocalDateTime.now();
    }

    public void setCreator(UUID creator) {
        this.setCreator(creator.toString());
    }

    public void setCreator(OfflinePlayer creator) {
        this.setCreator(creator.getUniqueId());
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }
}
