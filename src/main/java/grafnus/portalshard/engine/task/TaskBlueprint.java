package grafnus.portalshard.engine.task;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public interface TaskBlueprint {

    public BukkitRunnable build();

}
