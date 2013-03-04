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
    private ViewLoader loader;
    
    public Controller()
    {
        views = new ArrayList();
    }
    
    public void setViewLoader(ViewLoader loader)
    {
        this.loader = loader;
    }
    
    public void loadView(String name)
    {
        loadView(name, null);
    }
    
    public void loadView(String name, Model model)
    {
        try
        {
            loader.loadView(name, model);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.exit(1);
        }
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
