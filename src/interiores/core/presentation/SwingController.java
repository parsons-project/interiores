package interiores.core.presentation;

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
 *
 * @author hector
 */
public class SwingController extends PresentationController
{
    private ViewLoader vloader;
    private Map<String, List< Entry<View, Method> >> events;
    
    public SwingController(String viewsPath)
    {
        super();
        vloader = new ViewLoader(viewsPath);
        events = new HashMap();
    }
    
    @Override
    public void init() throws Exception
    {
        load("MainApp");
        show("MainApp");
    }
    
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
     * Here is where all the magic happens.
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
            e.printStackTrace();
        }
        catch(InvocationTargetException e) {
            e.printStackTrace();
        }
        
    }
    
    public void show(String viewName)
    {
        get(viewName).showView();
    }
    
    public View get(String viewName)
    {
        return vloader.get(viewName);
    }
    
    private void addEvent(String event, Entry<View, Method> entry)
    {
        if(! events.containsKey(event))
            events.put(event, new ArrayList());
        
        events.get(event).add(entry);
    }
    
    public void close(String name)
    {
        vloader.unload(name);
    }
}
