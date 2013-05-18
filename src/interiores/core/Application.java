package interiores.core;

import interiores.core.business.BusinessController;
import interiores.core.data.JAXBDataController;
import interiores.core.presentation.PresentationController;
import java.util.ArrayList;
import java.util.List;

/**
 * Application represents a Java application using the 3-tier design pattern.
 * @author hector
 */
public class Application
{
    /**
     * The controller of the application data layer
     */
    private JAXBDataController data;
    
    /**
     * The controllers of the application presentation layer
     */
    private List<PresentationController> presentations;
    
    /**
     * Application constructor.
     * @param appPkg Application package used to find the business controllers
     */
    public Application()
    {
        presentations = new ArrayList();
    }
    
    /**
     * Initializes the application.
     * This method basically initializes the presentation layer which can start making requests to the
     * business.
     */
    public void init()
    {
        try {
            for(PresentationController presentation : presentations)
                presentation.init();
        }
        catch(Exception e) {
            if(Debug.isEnabled())
                e.printStackTrace();
        }
    }
    
    /**
     * Sets the controller of the data layer.
     * This data controller is injected to the business controllers on creation.
     * @param data The controller of the data layer
     */
    public void setDataController(JAXBDataController data) {
        this.data = data;
    }
    
    /**
     * Adds a presentation controller to the presentation layer.
     * @param presentation The presentation controller
     */
    public void addPresentation(PresentationController presentation)
    {
        presentations.add(presentation);
    }
    
    /**
     * Uses reflection to add business controllers to the business layer dinamically!
     * Every controller is injected with the data controller when constructed.
     * @param name Name of the business controller (camel cased)
     */
    public void addBusiness(Class<? extends BusinessController> controllerClass)
    {
        try
        {
            BusinessController controller = (BusinessController) controllerClass.getConstructor(
                    JAXBDataController.class).newInstance(data);
            
            addBusiness(controller);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Assigns a name to a controller in the business layer without reflection.
     * @param name Name to identify the controller
     * @param controller The business controller
     */
    public void addBusiness(BusinessController controller)
    {
        for(PresentationController presentation : presentations)
            presentation.addBusinessController(controller);
    }
}
