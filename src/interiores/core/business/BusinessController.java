package interiores.core.business;

import interiores.core.Event;
import interiores.core.Observable;
import interiores.core.Observer;
import interiores.core.data.JAXBDataController;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a controller of the application business layer.
 * Implements some handy methods that all the business controllers can use.
 * @author hector
 */
abstract public class BusinessController implements Observable
{
    /**
     * Controller of the application data layer
     */
    protected JAXBDataController data;
    
    /**
     * List of the current business controller listeners.
     * These are normally controllers of the application presentation layer.
     */
    private List<Observer> listeners;
    
    /**
     * Creates a new business controllers with the given data controller.
     * @param data A controller of the application data layer
     */
    public BusinessController(JAXBDataController data)
    {
        this.data = data;
        listeners = new ArrayList();
    }
    
    /**
     * Adds a listener to the current controller listeners
     * @param obs The Observer that wants to listen to the business controller
     */
    @Override
    public void addListener(Observer obs)
    {
        listeners.add(obs);
    }
    
    /**
     * Notifies an event to all the business controller listeners a related data.
     * @param event Event to be notified
     */
    @Override
    public void notify(Event event)
    {
        for(Observer o : listeners)
            o.notify(event);
    }
}
