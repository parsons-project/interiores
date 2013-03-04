package interiores.core.mvc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 *
 * @author hector
 */
public class Intermediary implements PropertyChangeListener
{
    private ArrayList<PropertyChangeListener> listeners;
    
    public Intermediary()
    {
        listeners = new ArrayList();
    }
    
    public void addListener(PropertyChangeListener listener)
    {
        listeners.add(listener);
    }
    
    public void removeListener(PropertyChangeListener listener)
    {
        listeners.remove(listener);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        for(PropertyChangeListener listener : listeners)
            listener.propertyChange(evt);
    }
}
