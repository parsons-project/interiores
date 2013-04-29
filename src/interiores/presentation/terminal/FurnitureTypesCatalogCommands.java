package interiores.presentation.terminal;

import interiores.business.controllers.FurnitureTypesCatalogController;
import interiores.core.Debug;
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
        Collection<String> catalogNames = ftCatalogController.getNamesLoadedTypeCatalogs();
        
        print(catalogNames);
    }
    
    public void checkout() {
        String catalogName = readString("Enter the name of the catalog you want to set as active");
        
        ftCatalogController.checkout(catalogName);
    }
    
    public void load() throws JAXBException {
        String catalogName = readString("Enter the name of the catalog you want to load");
        String path = readString("Enter the path where to load the " + catalogName + " catalog");
        
        ftCatalogController.load(catalogName, path);
    }
    
    public void save() throws JAXBException {
        String catalogName = readString("Enter the name of the catalog you want to save");
        String path = readString("Enter the path where to save the " + catalogName + " catalog");
        
        ftCatalogController.save(catalogName, path);
    }
}
