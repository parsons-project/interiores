package interiores.core.presentation;

import java.util.HashMap;
import java.util.Map;

/**
 * A ViewLoader has the responsability of loading and managing views on memory.
 * @author hector
 */
public class ViewLoader
{
    /**
     * The package where the views are located
     */
    private String viewsPackage;
    
    /**
     * The views loaded identified by name
     */
    private Map<String, View> views;
    
    public ViewLoader(String viewsPkg)
    {
        this.viewsPackage = viewsPkg;
        views = new HashMap<String, View>();
    }
    
    /**
     * Tells whether a view is loaded or not.
     * @param name The view name
     * @return True if the view is loaded, false otherwise
     */
    public boolean isLoaded(String name)
    {
        return views.containsKey(name);
    }
    
    /**
     * Loads a view.
     * @param name The name of the view to load
     * @throws Exception 
     */
    public void load(String name) throws Exception
    {
        Class viewClass = Class.forName(viewsPackage + "." + name);
        
        views.put(name, (View)viewClass.newInstance());
    }
    
    /**
     * Unloads a view.
     * @param name The name of the view to unload
     */
    public void unload(String name)
    {
        views.remove(name);
    }
    
    /**
     * Gets a loaded view.
     * @param name Name of the view
     * @return If the view is loaded returns the loaded view. If not, returns null.
     */
    public View get(String name)
    {
        return views.get(name);
    }
}
