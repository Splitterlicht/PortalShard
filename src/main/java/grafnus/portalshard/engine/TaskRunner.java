package grafnus.portalshard.engine;

import grafnus.portalshard.PortalShard;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.logging.Level;

public class TaskRunner {

    private ArrayList<BukkitRunnable> tasks = new ArrayList<BukkitRunnable>();

    private BukkitRunnable mainTask;

    public TaskRunner() {
        this.mainTask = new BukkitRunnable() {
            @Override
            public void run() {
                for (BukkitRunnable task : tasks) {
                    task.runTask(PortalShard.getInstance());
                }
                tasks.clear();
            }
        };
    }

    public void queueTask(BukkitRunnable task) {
        tasks.add(task);
    }

    public void start() {
        this.mainTask.runTaskTimer(PortalShard.getInstance(), 0L, 5L);
    }

    public void stop() {
        this.mainTask.cancel();
    }

}
