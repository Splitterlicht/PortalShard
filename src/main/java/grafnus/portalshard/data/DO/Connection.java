package grafnus.portalshard.data.DO;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Interface describing how a Connection-Data-Object is supposed to look like.
 *
 * The Connection Data Objects holds the information. It is returned by the Connection-Data-Access-Object.
 * @author Grafnus
 *
 */
public interface Connection {

    /**
     * Returns the unique ID of the persistent data set for the connection.
     *
     * @return The unique persistent data ID for the connection
     * @since 1.0
     */
    public Long getId();

    /**
     * Returns the UUID of the Connection displayed on the Key in text format.
     *
     * @return The Connection UUID as text
     * @since 1.0
     */
    public String getUuid();

    /**
     * Returns the UUID of the Connection displayed on the Key.
     *
     * @return The Connection UUID
     * @since 1.0
     */
    public UUID getUUID();

    /**
     * Returns the charges a connection has left. Charges describe how often a portal can be used until a recharge is necessary.
     *
     * @return The amount of charges left
     * @since 1.0
     */
    public int getCharges();

    /**
     * Sets the charges a connection has left. Charges describe how often a portal can be used until a recharge is necessary.
     *
     * This method is used to recharge or discharge a connection
     *
     * @return The unique Persistent Data ID for the connection
     * @since 1.0
     */
    public void setCharges(int charges);

    /**
     * Returns the unique ID of the Persistent Data Set for the Connection.
     *
     * @return The unique Persistent Data ID for the connection
     * @since 1.0
     */
    public int getLevel();

    /**
     * Returns the unique ID of the Persistent Data Set for the Connection.
     *
     * @return The unique Persistent Data ID for the connection
     * @since 1.0
     */
    public void setLevel(int level);

    /**
     * Returns the unique ID of the Persistent Data Set for the Connection.
     *
     * @return The unique Persistent Data ID for the connection
     * @since 1.0
     */
    public boolean isFfa();

    /**
     * Returns the unique ID of the Persistent Data Set for the Connection.
     *
     * @return The unique Persistent Data ID for the connection
     * @since 1.0
     */
    public void setFfa(boolean ffa);

    /**
     * Returns the unique ID of the Persistent Data Set for the Connection.
     *
     * @return The unique Persistent Data ID for the connection
     * @since 1.0
     */
    public String getCreator();

    /**
     * Returns the unique ID of the Persistent Data Set for the Connection.
     *
     * @return The unique Persistent Data ID for the connection
     * @since 1.0
     */
    public UUID getCreatorUUID();

    /**
     * Returns the unique ID of the Persistent Data Set for the Connection.
     *
     * @return The unique Persistent Data ID for the connection
     * @since 1.0
     */
    public OfflinePlayer getCreatorPlayer();

    /**
     * Returns the unique ID of the Persistent Data Set for the Connection.
     *
     * @return The unique Persistent Data ID for the connection
     * @since 1.0
     */
    public void setCreator(String creator);

    /**
     * Returns the unique ID of the Persistent Data Set for the Connection.
     *
     * @return The unique Persistent Data ID for the connection
     * @since 1.0
     */
    public void setCreator(UUID creator);

    /**
     * Returns the unique ID of the Persistent Data Set for the Connection.
     *
     * @return The unique Persistent Data ID for the connection
     * @since 1.0
     */
    public void setCreator(OfflinePlayer creator);

    /**
     * Returns the unique ID of the Persistent Data Set for the Connection.
     *
     * @return The unique Persistent Data ID for the connection
     * @since 1.0
     */
    public LocalDateTime getCreated();

    /**
     * Returns the unique ID of the Persistent Data Set for the Connection.
     *
     * @return The unique Persistent Data ID for the connection
     * @since 1.0
     */
    public LocalDateTime getUpdated();

    /**
     * Returns the unique ID of the Persistent Data Set for the Connection.
     *
     * @return The unique Persistent Data ID for the connection
     * @since 1.0
     */
}
