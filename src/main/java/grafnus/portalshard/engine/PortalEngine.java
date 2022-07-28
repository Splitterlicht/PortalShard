package grafnus.portalshard.engine;

import grafnus.portalshard.commands.postcommand.PostCommandHandler;
import grafnus.portalshard.craft.Crafter;
import grafnus.portalshard.engine.events.EEvents;
import grafnus.portalshard.portals.PortalHandler;
import org.bukkit.event.Event;

public class PortalEngine {

    private static PortalEngine instance;

    private TaskRunner taskRunner;
    private Listener listener;
    private Crafter crafter;
    private PermissionCheck perms;
    private PostCommandHandler postCommand;

    private PortalEngine() {
        this.taskRunner = new TaskRunner();
        this.listener = new Listener();
        this.crafter = new Crafter();
        this.perms = new PermissionCheck();
        this.postCommand = new PostCommandHandler();
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
        crafter.addRecipes();
    }

    public void stop() {
        taskRunner.stop();
        listener.stopListening();
    }

    public void createPortal() {

    }

    public TaskRunner getTaskRunner() {
        return taskRunner;
    }

    public PermissionCheck getPlayerPermissionCheck() {
        return this.perms;
    }

    public void listenToEvent(Event event, EEvents type) {
        listener.listen(event, type);
    }

    public PostCommandHandler getPostCommandHandler() {
        return postCommand;
    }
}
