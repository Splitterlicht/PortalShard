package grafnus.portalshard.engine.task;

import grafnus.portalshard.engine.PortalEngine;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskFactory {

    public static void createTask(TaskBlueprint blueprint) {
        PortalEngine.getInstance().getTaskRunner().queueTask(blueprint.build());
    }

}
