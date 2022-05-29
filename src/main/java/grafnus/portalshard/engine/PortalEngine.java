package grafnus.portalshard.engine;

import grafnus.portalshard.engine.events.EEvents;
import grafnus.portalshard.portals.PortalHandler;
import org.bukkit.event.Event;

public class PortalEngine {

    private static PortalEngine instance;

    private TaskRunner taskRunner;
    private Listener listener;

    private PortalEngine() {
        this.taskRunner = new TaskRunner();
        this.listener = new Listener();
    }

    public static synchronized PortalEngine getInstance() {
        if (instance == null){
            instance = new PortalEngine();
        }
        return instance;
    }

    public void start() {
        taskRunner.start();
        listener.startListening();
    }

    public void stop() {
        taskRunner.stop();
        listener.stopListening();
    }

    public TaskRunner getTaskRunner() {
        return taskRunner;
    }

    public void listenToEvent(Event event, EEvents type) {
        listener.listen(event, type);
    }
}
