package interiores.core;

import interiores.core.business.BusinessController;
import interiores.core.presentation.PresentationController;
import interiores.data.DataController;
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
    private DataController data;
    private List<PresentationController> presentations;
    
    public Application(String appPkg) throws JAXBException
    {
        this.appPkg = appPkg;
        data = new DataController();
        presentations = new ArrayList();
    }
    
    public void init() throws Exception
    {   
        for(PresentationController presentation : presentations)
            presentation.init();
    }
    
    public void addBusiness(String name)
    {
        try
        {
            Class controllerClass = Class.forName(appPkg + ".business.controllers." + Utils.capitalize(name) +
                    "Controller");
            
            addBusiness(name,
                    (BusinessController) controllerClass.getConstructor(DataController.class).newInstance(data));
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
