package interiores.core;

import java.util.Map;

/**
 * All the instances that implement this interface can recieve notifications from Observable instances.
 * The Observer interface is part of the observer pattern.
 * @author hector
 */
public interface Observer
{
    /**
     * Notifies the Observer instance that some event has occurred.
     * @param name The event name
     * @param data Data related with the event
     */
    public void notify(String name, Map<String, ?> data);
}
