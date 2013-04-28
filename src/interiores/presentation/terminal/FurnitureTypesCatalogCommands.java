package interiores.presentation.terminal;

import interiores.business.controllers.FurnitureTypesCatalogController;
import interiores.core.presentation.terminal.CommandGroup;
import java.io.IOException;
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
    
    public void checkout() throws IOException {
        String catalogName = readString("Enter the name of the catalog you want to set as active");
        
        ftCatalogController.checkout(catalogName);
    }
    
    public void load() throws IOException {
        String catalogName = readString("Enter the name of the catalog you want to load");
        String path = readString("Enter the path where to load the " + catalogName + " catalog");
        
        try {
            ftCatalogController.load(catalogName, path);
        }
        catch(JAXBException e) {
            println("Some errors occurred when loading the " + catalogName + " catalog:");
            println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void save() throws IOException {
        String catalogName = readString("Enter the name of the catalog you want to save");
        String path = readString("Enter the path where to save the " + catalogName + " catalog");
        
        try {
            ftCatalogController.save(catalogName, path);
        }
        catch(JAXBException e) {
            println("Some errors occurred when saving the " + catalogName + " catalog:");
            println(e.getMessage());
            e.printStackTrace();
        }
    }
}
