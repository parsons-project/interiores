package interiores.core.presentation;

import interiores.core.Observer;
import interiores.core.business.BusinessController;

/**
 * Represents a controller of the application presentation layer.
 * @author hector
 */
abstract public class PresentationController implements Observer
{
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
    }
}
