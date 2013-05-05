package interiores.core;

/**
 * Interface that Observers can use to start listening the Observable object.
 * The Observable interface is part of the observer pattern.
 * @author hector
 */
public interface Observable
{
    /**
     * Adds an Observer as a listener of the instance.
     * @param obs The Observer to add as a listener
     */
    public void addListener(Observer obs);
    
    /**
     * Notifies all the listeners that an event has occurred.
     * @param name The event to be notified
     */
    public void notify(Event event);
}
