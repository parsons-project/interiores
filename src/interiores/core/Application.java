package interiores.core;

import interiores.core.business.BusinessController;
import interiores.core.data.JAXBDataController;
import interiores.core.presentation.PresentationController;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 *
 * @author hector
 */
public class Application
{
    private String appPkg;
    private JAXBDataController data;
    private List<PresentationController> presentations;
    
    public Application(String appPkg) throws JAXBException
    {
        this.appPkg = appPkg;
        presentations = new ArrayList();
    }
    
    public void init() throws Exception
    {   
        for(PresentationController presentation : presentations)
            presentation.init();
    }
    
    public void setDataController(JAXBDataController data) {
        this.data = data;
    }
    
    public void addBusiness(String name)
    {
        try
        {
            Class controllerClass = Class.forName(appPkg + ".business.controllers." + Utils.capitalize(name) +
                    "Controller");
            
            addBusiness(name,
                    (BusinessController) controllerClass.getConstructor(JAXBDataController.class).newInstance(data));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void addBusiness(String name, BusinessController controller)
    {
        for(PresentationController presentation : presentations)
            presentation.addBusinessController(name, controller);
    }
    
    public void addPresentation(PresentationController presentation)
    {
        presentations.add(presentation);
    }
}
