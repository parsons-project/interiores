package interiores.core.presentation;

import interiores.core.business.BusinessController;
import interiores.core.presentation.PresentationController;
import interiores.core.presentation.View;
import interiores.core.presentation.ViewLoader;
import java.util.Map;

/**
 *
 * @author hector
 */
public class SwingController extends PresentationController
{
    private ViewLoader vloader;
    
    public SwingController(String viewsPath)
    {
        super();
        vloader = new ViewLoader(viewsPath);
    }
    
    @Override
    public void init()
    {
        showView("MainApp");
    }
    
    @Override
    public void notify(String name, Map<String, Object> data)
    {
        
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
    
    public void closeView(String name)
    {
        vloader.unload(name);
    }
}
