package grafnus.portalshard.engine.task;

import grafnus.portalshard.engine.PortalEngine;

public class TaskFactory {

    public static void createTask(ITaskBlueprint blueprint) {
        PortalEngine.getInstance().getTaskRunner().queueTask(blueprint.build());
    }

}
