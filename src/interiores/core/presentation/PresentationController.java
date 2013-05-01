package interiores.core.presentation;

import interiores.core.Observer;
import interiores.core.business.BusinessController;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a controller of the application presentation layer.
 * @author hector
 */
abstract public class PresentationController implements Observer
{
    /**
     * Map of the business controllers available on top of the presentation layer
     */
    private Map<String, BusinessController> controllers;
    
    /**
     * Default constructor.
     */
    public PresentationController()
    {
        controllers = new HashMap();
    }
    
    /**
     * Initializes and runs the presentation controller.
     * @throws Exception 
     */
    abstract public void init() throws Exception;
    
    /**
     * Adds a business controller on top of this presentation controller.
     * @param name Name to identify the business controller
     * @param controller The business controller to add
     */
    public void addBusinessController(String name, BusinessController controller)
    {
        controller.addListener(this);
        controllers.put(name, controller);
    }
    
    /**
     * Obtains a business controller that is on top of this presentation controller.
     * @param name The identifier of the business controller to obtain
     * @return The business controller
     * @throws Exception
     */
    public BusinessController getBusinessController(String name)
            throws Exception
    {
        if(! controllers.containsKey(name))
            throw new Exception("There is no business controller known as " + name);
        
        return controllers.get(name);
    }
}
