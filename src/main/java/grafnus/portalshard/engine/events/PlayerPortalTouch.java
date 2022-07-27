package grafnus.portalshard.engine.events;

import org.bukkit.event.Event;

public class PlayerPortalTouch implements IEvent {
    @Override
    public boolean isEvent(EEvents event) {
        return false;
    }

    @Override
    public void listen(Event event) {

    }
}
