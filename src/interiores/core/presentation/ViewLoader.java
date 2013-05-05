package interiores.core.presentation;

import interiores.core.business.BusinessController;
import interiores.core.presentation.annotation.Business;
import java.util.HashMap;
import java.util.Map;

/**
 * A ViewLoader has the responsability of loading and managing views on memory.
 * @author hector
 */
public class ViewLoader
{ 
    /**
     * The controllers available for the views
     */
    private Map<Class, BusinessController> controllers;
    
    /**
     * The views loaded identified by name
     */
    private Map<Class, View> views;
    
    public ViewLoader()
    {
        views = new HashMap();
        controllers = new HashMap();
    }
    
    public void addBusinessController(BusinessController controller)
    {
        controllers.put(controller.getClass(), controller);
    }
    
    /**
     * Tells whether a view is loaded or not.
     * @param viewClass The view class
     * @return True if the view is loaded, false otherwise
     */
    public boolean isLoaded(Class<? extends View> viewClass)
    {
        return views.containsKey(viewClass);
    }
    
    /**
     * Loads a view.
     * @param name The name of the view to load
     * @throws Exception 
     */
    public void load(Class<? extends View> viewClass) throws Exception
    {
        View view;
        
        if(viewClass.isAnnotationPresent(Business.class))
        {
            Business viewBusiness = (Business) viewClass.getAnnotation(Business.class);
            Class controllerClass = viewBusiness.value();
            
            if(! controllers.containsKey(controllerClass))
                throw new Exception("There is no controller loaded as " + controllerClass.getName() + 
                        " for the view " + viewClass.getName());
            
            BusinessController controller = controllers.get(controllerClass);
            view = (View) viewClass.getConstructor(controllerClass).newInstance(controller);
        }
        else
            view = (View) viewClass.newInstance();
        
        views.put(viewClass, view);
    }
    
    /**
     * Unloads a view.
     * @param viewClass The class of the view to unload
     */
    public void unload(Class<? extends View> viewClass)
    {
        views.remove(viewClass);
    }
    
    /**
     * Gets a loaded view.
     * @param viewClass Class of the view to get
     * @return If the view is loaded returns the loaded view. If not, returns null.
     */
    public <T extends View> T get(Class<T> viewClass)
    {
        return (T) views.get(viewClass);
    }
}
