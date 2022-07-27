package grafnus.portalshard.engine;

import org.bukkit.event.Event;

public class Converter<T> {

    public static <T> T convert(Event event, Class<T> clazz) {
        return tryConvert(event, clazz);
    }

    private static <T> T tryConvert(Event event, Class<T> clazz) {
        try {
            return clazz.cast(event);
        } catch(ClassCastException e) {
            return null;
        }
    }

}
