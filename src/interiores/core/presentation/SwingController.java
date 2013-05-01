package interiores.core.presentation;

import interiores.core.Debug;
import interiores.core.presentation.annotation.Event;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Represents a Swing graphical user interface presentation controller.
 * @author hector
 */
public class SwingController extends PresentationController
{
    /**
     * The view loader used to load and manage views
     */
    private ViewLoader vloader;
    
    /**
     * Map of events that the loaded views are listening
     */
    private Map<String, List< Entry<View, Method> >> events;
    
    /**
     * Constructs a SwingController given the package where the views are located
     * @param viewsPackage The package where the views of this controller are located 
     */
    public SwingController(String viewsPackage)
    {
        super();
        vloader = new ViewLoader(viewsPackage);
        events = new HashMap();
    }
    
    /**
     * Initializes and runs the SwingController
     * @throws Exception 
     */
    @Override
    public void init() throws Exception
    {
        load("MainApp");
        show("MainApp");
    }
    
    /**
     * Recieves an event and notifies it to the views that are listening to that event.
     * @param name Name of the event
     * @param data Data related with the event
     */
    @Override
    public void notify(String name, Map<String, ?> data)
    {
        if(! events.containsKey(name))
            return;
        
        try
        {
            for(Entry e : events.get(name))
                invokeEvent(e, data);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Loads a view on memory given its name.
     * @param viewName The name of the view to load
     * @throws Exception 
     */
    public void load(String viewName) throws Exception
    {            
        if(! vloader.isLoaded(viewName))
        {   
            vloader.load(viewName);
            
            View view = vloader.get(viewName);
            
            view.setPresentation(this);
            
            try
            {
                view.onLoad();
                
                configureEvents(view);
            }
            catch(Exception e)
            {
                vloader.unload(viewName);
                throw e;
            }
        }
    }
    
    /**
     * Congirues the events that listens a given view.
     * @param view The view to which configure the events
     */
    private void configureEvents(View view) {
        Class viewClass = view.getClass();
        
        for(Method m : viewClass.getMethods()) {
            if(m.isAnnotationPresent(Event.class))
                addEvent(m.getName(), new SimpleEntry(view, m));
        }
    }
    
    /**
     * Given a view, one of its methods and some mapped data, calls the method using the mapped data as
     * parameters using the parameter names from the Event annotation as keys of the map.
     * Magic code! Be careful!
     * @param entry A view-method pair
     * @param data Mapped data
     */
    private void invokeEvent(Entry<View, Method> entry, Map<String, ?> data) { 
        View view = entry.getKey();
        Method method = entry.getValue();
        Event annotation = method.getAnnotation(Event.class);
        String[] paramNames = annotation.paramNames();
        
        try {
            switch(paramNames.length) {
                case 0:
                    method.invoke(view);
                    break;
                    
                case 1:
                    method.invoke(view, data.get(paramNames[0]));
                    break;
                    
                case 2:
                    method.invoke(view, data.get(paramNames[0]), data.get(paramNames[1]));
                    break;
                    
                case 3:
                    method.invoke(view, data.get(paramNames[0]), data.get(paramNames[1]),
                            data.get(paramNames[2]));
                    break;
                    
                case 4:
                    method.invoke(view, data.get(paramNames[0]), data.get(paramNames[1]),
                            data.get(paramNames[2]), data.get(paramNames[3]));
                    break;
                    
                case 5:
                    method.invoke(view, data.get(paramNames[0]), data.get(paramNames[1]),
                            data.get(paramNames[2]), data.get(paramNames[3]), data.get(paramNames[4]));
                    break;
                    
                case 6:
                    method.invoke(view, data.get(paramNames[0]), data.get(paramNames[1]),
                            data.get(paramNames[2]), data.get(paramNames[3]), data.get(paramNames[4]),
                            data.get(paramNames[5]));
                    break;
                    
                case 7:
                    method.invoke(view, data.get(paramNames[0]), data.get(paramNames[1]),
                            data.get(paramNames[2]), data.get(paramNames[3]), data.get(paramNames[4]),
                            data.get(paramNames[6]), data.get(paramNames[7]));
                    break;
                    
                default:
                    throw new IllegalArgumentException("Too much arguments needed for the event.");
            }
        }
        catch(IllegalAccessException e) {
            if(Debug.isEnabled())
                e.printStackTrace();
        }
        catch(InvocationTargetException e) {
            if(Debug.isEnabled())
                e.printStackTrace();
        }
        
    }
    
    /**
     * Shows a loaded view.
     * @param viewName The name of the view to show
     */
    public void show(String viewName)
    {
        try {
            if(! vloader.isLoaded(viewName))
                load(viewName);
        
            get(viewName).showView();
        }
        catch(Exception e) {
            if(Debug.isEnabled())
                e.printStackTrace();
        }
    }
    
    /**
     * Obtains a loaded view.
     * @param viewName The name of the view to obtain
     * @return The loaded view if the view was previously loaded, null otherwise
     */
    public View get(String viewName)
    {
        return vloader.get(viewName);
    }
    
    /**
     * Adds view listener to the given event.
     * @param event Name of the event
     * @param entry Entry that contains the view and the method to be called when the event occurs
     */
    private void addEvent(String event, Entry<View, Method> entry)
    {
        if(! events.containsKey(event))
            events.put(event, new ArrayList());
        
        events.get(event).add(entry);
    }
    
    /**
     * Unloads the view with the given name.
     * @param viewName The name of the view to unload
     */
    public void close(String viewName)
    {
        View view = get(viewName);
        
        if(view == null)
            return;
        
        Class viewClass = view.getClass();
        
        // Remove assigned events
        for(Method method : viewClass.getMethods()) {
            if(method.isAnnotationPresent(Event.class)) {
                List< Entry<View, Method> > entries = events.get(method.getName());
                
                for(int i = 0; i < entries.size(); ++i) {
                    if(entries.get(i).getKey().equals(view))
                        entries.remove(i);
                }
            }
        }
        
        vloader.unload(viewName);
    }
}
