package grafnus.portalshard.engine;

import grafnus.portalshard.engine.events.*;
import org.bukkit.event.Event;

import java.util.ArrayList;

public class Listener {

    private ArrayList<IEvent> listeners = new ArrayList<IEvent>();

    public Listener() {
    }

    public void startListening() {
        listeners.add(new PlayerInteractRespawnAnchor());
        listeners.add(new PlayerPortal());
        listeners.add(new CryingObsidianBreak());
    }

    public void stopListening() {
        listeners.clear();
    }

    public void listen(Event event, EEvents type) {
        for (IEvent e : listeners) {
            if (!e.isEvent(type))
                continue;
            e.listen(event);
        }
    }

}
