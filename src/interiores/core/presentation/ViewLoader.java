package interiores.core.presentation;

import interiores.core.Debug;
import interiores.core.Event;
import interiores.core.presentation.annotation.Listen;
import interiores.core.presentation.swing.SwingException;
import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.SwingUtilities;

/**
 * A ViewLoader has the responsibility of loading and managing views on memory.
 * @author hector
 */
public class ViewLoader
{
    private SwingController presentation;
    /**
     * The views loaded identified by name
     */
    private Map<Class, Component> views;
    
    /**
     * Map of events that the loaded views are listening
     */
    private Map<Class, List< Entry<Component, Method> >> events;
    
    public ViewLoader(SwingController presentation)
    {
        this.presentation = presentation;
        views = new HashMap();
        events = new HashMap();
    }
    
    /**
     * Tells whether a view is loaded or not.
     * @param viewClass The view class
     * @return True if the view is loaded, false otherwise
     */
    public boolean isLoaded(Class<? extends Component> viewClass)
    {
        return views.containsKey(viewClass);
    }
    
    /**
     * Loads a view.
     * @param name The name of the view to load
     * @throws Exception 
     */
    public void load(Class<? extends Component> viewClass) throws SwingException
    {
        try {
            Component view = (Component) viewClass.getConstructor(SwingController.class).newInstance(
                    presentation);
        
            views.put(viewClass, view);
            configureEvents(viewClass, view);
        }
        catch(Exception e) {
            throw new SwingException("Unable to load view " + viewClass.getSimpleName(), e);
        }
    }
    
    /**
     * Unloads a view.
     * @param viewClass The class of the view to unload
     */
    public void unload(Class<? extends Component> viewClass)
    {
       if(! isLoaded(viewClass))
            return;
        
        Component view = views.get(viewClass);
        
        // Remove assigned events
        for(Method method : viewClass.getMethods()) {
            if(method.isAnnotationPresent(Listen.class)) {
                Class[] listenedEvents = method.getAnnotation(Listen.class).value();
                
                for(Class listenedEvent : listenedEvents) {
                    List< Entry<Component, Method> > entries = events.get(listenedEvent);
                    
                    Iterator<Entry<Component, Method>> it = entries.iterator(); 
                    while(it.hasNext()) {
                        if(it.next().getKey().equals(view))
                            it.remove();
                    }
                }
            }
        }
        
        views.remove(viewClass);
    }
    
    /**
     * Gets a loaded view.
     * @param viewClass Class of the view to get
     * @return If the view is loaded returns the loaded view. If not, returns null.
     */
    public <T extends Component> T get(Class<T> viewClass) throws SwingException
    {
        if(! isLoaded(viewClass))
            load(viewClass);
        
        return (T) views.get(viewClass);
    }
    
    public void notify(Event event) {
        Class eventClass = event.getClass();
        
        if(! events.containsKey(eventClass))
            return;
        
        for(Entry e : events.get(eventClass))
                invokeEvent(e, event);
    }
    
            
    /**
     * Configures the events that listens a given view.
     * @param viewClass Class of the view
     * @param view Instance to which configure the events
     */
    private void configureEvents(Class viewClass, Component view)
    {
        for(Method m : viewClass.getMethods()) {
            if(m.isAnnotationPresent(Listen.class)) {
                for(Class eventClass : m.getAnnotation(Listen.class).value())
                    addEvent(eventClass, new AbstractMap.SimpleEntry(view, m));
            }
        }
    }
        
    /**
     * Adds view listener to the given event.
     * @param eventClass Class of the event
     * @param entry Entry that contains the view and the method to be called when the event occurs
     */
    private void addEvent(Class eventClass, Entry<Component, Method> entry)
    {
        if(! events.containsKey(eventClass))
            events.put(eventClass, new ArrayList());
        
        events.get(eventClass).add(entry);
    }
    
    /**
     * Given a view, one of its methods and an event, calls the method passing the event as
     * parameter using the AWT event queue to avoid thread race conditions.
     * @param entry A view-method pair
     * @param data Mapped data
     */
    private void invokeEvent(final Entry<Component, Method> entry, final Event event) { 
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
}
