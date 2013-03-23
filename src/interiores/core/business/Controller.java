package interiores.core.business;

import interiores.core.Observable;
import interiores.core.Observer;
import interiores.data.DataController;
import java.util.Map;

/**
 * Base class for all the business controllers.
 * @author hector
 */
abstract public class Controller implements Observable
{
    protected DataController data;
    private Observer observer;
    
    public Controller(DataController data)
    {
        this.data = data;
    }
    
    @Override
    public void registerObserver(Observer obs)
    {
        observer = obs;
    }

    @Override
    public void notify(String name, Map<String, Object> data)
    {
        observer.notify(name, data);
    }
}
