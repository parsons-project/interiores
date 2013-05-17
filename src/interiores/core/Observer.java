package interiores.core;

/**
 * All the instances that implement this interface can recieve notifications from Observable instances.
 * The Observer interface is part of the observer pattern.
 * @author hector
 */
public interface Observer
{
    /**
     * Notifies the Observer instance that some event has occurred.
     * @param event The event occurred
     */
    public void notify(Event event);
}
