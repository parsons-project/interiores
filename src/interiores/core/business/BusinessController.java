package interiores.core.business;

import interiores.core.Observable;
import interiores.core.Observer;
import interiores.core.data.JAXBDataController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Notifies an event to all the business controller listeners
     * @param name Name of the event
     */
    public void notify(String name) {
        notify(name, new HashMap());
    }
    
    /**
     * Notifies an event to all the business controller listeners with one entry of data.
     * @param name Name of the event
     * @param dataName Name of the entry of data
     * @param data Value of the entry of data
     */
    public void notify(String name, String dataName, Object data) {
        Map<String, Object> map = new HashMap();
        map.put(dataName, data);
        
        notify(name, map);
    }
    
    /**
     * Notifies an event to all the business controller listeners a related data.
     * @param name Name of the event
     * @param data Data related to the event
     */
    @Override
    public void notify(String name, Map<String, ?> data)
    {
        for(Observer o : listeners)
            o.notify(name, data);
    }
    
    /**
     * Notifies an event to all the business controller listeners with the domain model as related data.
     * @param name Name of the event
     * @param m Related domain model
     */
    public void notify(String name, Model m)
    {
        notify(name, m.toMap());
    }
}
