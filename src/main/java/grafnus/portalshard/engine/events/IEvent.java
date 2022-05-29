package grafnus.portalshard.engine.events;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

public interface IEvent {

    public boolean isEvent(EEvents event);

    public void listen(Event event);
}
