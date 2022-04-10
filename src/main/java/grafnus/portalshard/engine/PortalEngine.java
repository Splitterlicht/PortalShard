package grafnus.portalshard.engine;

import grafnus.portalshard.portals.PortalHandler;

public class PortalEngine {

    private static PortalEngine instance;

    private TaskRunner taskRunner;

    private PortalEngine() {
        this.taskRunner = new TaskRunner();
    }

    public static synchronized PortalEngine getInstance() {
        if (instance == null){
            instance = new PortalEngine();
        }
        return instance;
    }

    public void start() {
        taskRunner.start();
    }

    public void stop() {
        taskRunner.stop();
    }

    public TaskRunner getTaskRunner() {
        return taskRunner;
    }
}
