package interiores.core.presentation;

import interiores.core.Debug;
import interiores.core.Event;
import interiores.core.business.BusinessController;
import interiores.core.presentation.annotation.Listen;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.SwingUtilities;

/**
 * Represents a Swing graphical user interface presentation controller.
 * @author hector
 */
public class SwingController extends PresentationController
{
    /**
     * The class of the main view to load on initialization
     */
    private Class mainView;
    
    /**
     * The view loader used to load and manage views
     */
    private ViewLoader vloader;
    
    /**
     * Map of events that the loaded views are listening
     */
    private Map<Class, List< Entry<View, Method> >> events;
    
    /**
     * Constructs a SwingController given the package where the views are located
     * @param viewsPackage The package where the views of this controller are located 
     */
    public SwingController(Class<? extends View> mainView)
    {
        super();
        this.mainView = mainView;
        vloader = new ViewLoader();
        events = new HashMap();
    }
    
    /**
     * Initializes and runs the SwingController
     * @throws Exception 
     */
    @Override
    public void init() throws Exception
    {
        show(mainView);
    }
    
    /**
     * Recieves an event and notifies it to the views that are listening to that event.
     * @param name Name of the event
     * @param data Data related with the event
     */
    @Override
    public void notify(Event event)
    {
        Class eventClass = event.getClass();
        
        if(! events.containsKey(eventClass))
            return;
        
        for(Entry e : events.get(eventClass))
                invokeEvent(e, event);
    }
    
    /**
     * Given a view, one of its methods and an event, calls the method passing the event as
     * parameter using the AWT event queue to avoid thread race conditions.
     * @param entry A view-method pair
     * @param data Mapped data
     */
    private void invokeEvent(final Entry<View, Method> entry, final Event event) { 
        Runnable callEvent = new Runnable()
        {
            @Override
            public void run()
            {
                Method method = entry.getValue();

                try {
                    if(method.getParameterTypes().length == 0)
                        method.invoke(entry.getKey());
                    else
                        method.invoke(entry.getKey(), event);
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
        };

        SwingUtilities.invokeLater(callEvent);
    }
    
    @Override
    public void addBusinessController(String name, BusinessController controller) {
        vloader.addBusinessController(controller);
        
        super.addBusinessController(name, controller);
    }
    
    /**
     * Loads a view on memory given its name.
     * @param viewClass The class of the view to load
     * @throws Exception 
     */
    public void load(Class<? extends View> viewClass) throws Exception
    {            
        if(! vloader.isLoaded(viewClass))
        {   
            vloader.load(viewClass);
            
            View view = vloader.get(viewClass);
            
            view.setPresentation(this);
            
            try
            {
                view.onLoad();
                
                configureEvents(viewClass, view);
            }
            catch(Exception e)
            {
                vloader.unload(viewClass);
                throw e;
            }
        }
    }
    
        
    /**
     * Configures the events that listens a given view.
     * @param viewClass Class of the view
     * @param view Instance to which configure the events
     */
    private void configureEvents(Class viewClass, View view)
    {
        for(Method m : viewClass.getMethods()) {
            if(m.isAnnotationPresent(Listen.class)) {
                for(Class eventClass : m.getAnnotation(Listen.class).value())
                    addEvent(eventClass, new SimpleEntry(view, m));
            }
        }
    }
        
    /**
     * Adds view listener to the given event.
     * @param eventClass Class of the event
     * @param entry Entry that contains the view and the method to be called when the event occurs
     */
    private void addEvent(Class eventClass, Entry<View, Method> entry)
    {
        if(! events.containsKey(eventClass))
            events.put(eventClass, new ArrayList());
        
        events.get(eventClass).add(entry);
    }
    
    /**
     * Shows a loaded view.
     * @param viewClass The class of the view to show
     */
    public void show(Class<? extends View> viewClass)
    {
        try {
            if(! vloader.isLoaded(viewClass))
                load(viewClass);
        
            get(viewClass).showView();
        }
        catch(Exception e) {
            if(Debug.isEnabled())
                e.printStackTrace();
        }
    }
    
    /**
     * Obtains a loaded view.
     * @param viewClass The class of the view to obtain
     * @return The loaded view if the view was previously loaded, null otherwise
     */
    public <T extends View> T get(Class<T> viewClass)
    {
        return (T) vloader.get(viewClass);
    }
    
    /**
     * Unloads the view with the given name.
     * @param viewClass The name of the view to unload
     */
    public void close(Class<? extends View> viewClass)
    {
        if(! vloader.isLoaded(viewClass))
            return;
        
        View view = get(viewClass);
        
        // Remove assigned events
        for(Method method : viewClass.getMethods()) {
            if(method.isAnnotationPresent(Listen.class)) {
                List< Entry<View, Method> > entries = events.get(method.getName());
                
                for(int i = 0; i < entries.size(); ++i) {
                    if(entries.get(i).getKey().equals(view))
                        entries.remove(i);
                }
            }
        }
        
        vloader.unload(viewClass);
    }
}
