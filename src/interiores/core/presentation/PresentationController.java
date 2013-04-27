package interiores.core.presentation;

import interiores.core.Observer;
import interiores.core.business.BusinessController;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hector
 */
abstract public class PresentationController implements Observer
{
    private Map<String, BusinessController> controllers;
    
    public PresentationController()
    {
        controllers = new HashMap();
    }
    
    abstract public void init() throws Exception;
    
    public void addBusinessController(String name, BusinessController controller)
    {
        controller.addListener(this);
        controllers.put(name, controller);
    }
    
    public BusinessController getBusinessController(String name)
    {
        return controllers.get(name);
    }
}
