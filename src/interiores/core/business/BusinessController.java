package interiores.core.business;

import interiores.core.Observable;
import interiores.core.Observer;
import interiores.data.DataController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Base class for all the business controllers.
 * @author hector
 */
abstract public class BusinessController implements Observable
{
    protected DataController data;
    private List<Observer> listeners;
    
    public BusinessController(DataController data)
    {
        this.data = data;
        listeners = new ArrayList();
    }
    
    @Override
    public void addListener(Observer obs)
    {
        listeners.add(obs);
    }

    @Override
    public void notify(String name, Map<String, Object> data)
    {
        for(Observer o : listeners)
            o.notify(name, data);
    }
}
