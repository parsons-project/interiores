package interiores.mvc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * Base class for all the controllers.
 * @author hector
 */
abstract public class Controller implements PropertyChangeListener {
    private ArrayList<View> views;
    
    public Controller()
    {
        views = new ArrayList();
    }
    
    public void addView(View view)
    {
        views.add(view);
    }
    
    public void removeView(View view)
    {
        views.remove(view);
    }
    
    public void setModel(Model model)
    {
        
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        for(View view : views)
            view.propertyChange(evt);
    }
}
