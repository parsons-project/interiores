package interiores.core.presentation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hector
 */
public class SwingController extends PresentationController
{
    private ViewLoader vloader;
    private Map<String, List<View>> events;
    
    public SwingController(String viewsPath)
    {
        super();
        vloader = new ViewLoader(viewsPath);
        events = new HashMap();
    }
    
    @Override
    public void init()
    {
        showView("MainApp");
    }
    
    @Override
    public void notify(String name, Map<String, Object> data)
    {
        if(! events.containsKey(name))
            return;
        
        try
        {
            for(View v : events.get(name))
            {
                Method m = v.getClass().getMethod(name, Map.class);
                m.invoke(v, data);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void showView(String viewName)
    {         
        try
        {
            View view;
            
            if(! vloader.isLoaded(viewName))
            {
                vloader.load(viewName);
                view = vloader.get(viewName);
            
                view.setPresentation(this);
                
                for(String event : view.getEvents())
                    addViewToEvent(event, view);
            }
            else
                view = vloader.get(viewName);
            
            view.showView();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } 
    }
    
    private void addViewToEvent(String event, View view)
    {
        if(! events.containsKey(event))
            events.put(event, new ArrayList());
        
        events.get(event).add(view);
    }
    
    public void closeView(String name)
    {
        vloader.unload(name);
    }
}
