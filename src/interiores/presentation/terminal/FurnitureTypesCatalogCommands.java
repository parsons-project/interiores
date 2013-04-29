package interiores.presentation.terminal;

import interiores.business.controllers.FurnitureTypesCatalogController;
import interiores.core.business.BusinessException;
import interiores.core.presentation.terminal.CommandGroup;
import java.util.Collection;
import javax.xml.bind.JAXBException;

/**
 *
 * @author hector
 */
public class FurnitureTypesCatalogCommands
    extends CommandGroup
{
    private FurnitureTypesCatalogController ftCatalogController;
    
    public FurnitureTypesCatalogCommands(FurnitureTypesCatalogController ftCatalogController) {
        this.ftCatalogController = ftCatalogController;
    }
    
    public void list() {
        Collection<String> catalogNames = ftCatalogController.getNamesLoadedTypesCatalogs();
        String activeCatalog = ftCatalogController.getNameActiveTypesCatalog();
        
        println("Listing names of available furniture types catalogs:");
        
        for(String name : catalogNames) {
            if(name.equals(activeCatalog)) name = "*" + name;
            
            println(name);
        }
    }
    
    public void _new() throws BusinessException {
        String catalogName = readString("Enter the name of the catalog you want to create");
        
        ftCatalogController.create(catalogName);
    }
    
    public void checkout() throws BusinessException {
        String catalogName = readString("Enter the name of the catalog you want to set as active");
        
        ftCatalogController.checkout(catalogName);
    }
    
    public void load() throws JAXBException, BusinessException {
        String path = readString("Enter the path where to load a catalog");
        
        ftCatalogController.load(path);
    }
    
    public void save() throws JAXBException {
        String catalogName = readString("Enter the name of the catalog you want to save");
        String path = readString("Enter the path where to save the " + catalogName + " catalog");
        
        ftCatalogController.save(catalogName, path);
    }
}
