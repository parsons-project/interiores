package interiores.core.presentation;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hector
 */
public class ViewLoader
{
    private String viewsPkg;
    private Map<String, View> views;
    
    public ViewLoader(String viewsPkg)
    {
        this.viewsPkg = viewsPkg;
        views = new HashMap<String, View>();
    }
    
    public void init()
    {
        
    }
    
    public boolean isLoaded(String name)
    {
        return views.containsKey(name);
    }
    
    public void load(String name) throws Exception
    {
        Class viewClass = Class.forName(viewsPkg + "." + name);
        
        views.put(name, (View)viewClass.newInstance());
    }
    
    public void unload(String name)
    {
        views.remove(name);
    }

    public View get(String name) throws Exception
    {
        if(! isLoaded(name))
            throw new Exception("The view: " + name + " has not been loaded.");
        
        return views.get(name);
    }
}
