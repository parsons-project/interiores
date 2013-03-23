package interiores.presentation.swing;

import interiores.core.presentation.ViewLoader;
import interiores.core.presentation.View;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hector
 */
public class SwingLoader implements ViewLoader
{
    private final static String VIEWS_PKG = "interiores.presentation.swing.views";
    private Map<String, View> views;
    
    public SwingLoader()
    {
        views = new HashMap<String, View>();
    }
    
    @Override
    public void init()
    {
        
    }
    
    @Override
    public boolean isLoaded(String name)
    {
        return views.containsKey(name);
    }
    
    @Override
    public void load(String name) throws Exception
    {
        Class viewClass = Class.forName(VIEWS_PKG + "." + name);
        
        views.put(name, (View)viewClass.newInstance());
    }
    
    @Override
    public void unload(String name)
    {
        views.remove(name);
    }
    
    @Override
    public View get(String name) throws Exception
    {
        if(! isLoaded(name))
            throw new Exception("The view: " + name + " has not been loaded.");
        
        return views.get(name);
    }
}
