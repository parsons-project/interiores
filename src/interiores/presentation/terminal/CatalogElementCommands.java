package interiores.presentation.terminal;

import interiores.business.controllers.CatalogElementController;
import interiores.core.business.BusinessException;
import interiores.core.presentation.terminal.CommandGroup;
import java.util.Collection;

/**
 *
 * @author hector
 */
abstract public class CatalogElementCommands
    extends CommandGroup {
    
    private CatalogElementController catalogElementController;
    private String catalogTypeName;
    
    public CatalogElementCommands(CatalogElementController catalogElementController, String catalogTypeName) {
        this.catalogElementController = catalogElementController;
        this.catalogTypeName = catalogTypeName;
    }
    
    public void rm() throws BusinessException {
        Collection<String> names = readStrings("Enter the names of the " + catalogTypeName  + " you want "
                + "to remove:");
        
        for(String name : names)
            catalogElementController.rm(name);
    }
    
    public void list() {
        Collection elements = catalogElementController.getCatalogObjects();
        
        println("Listing " + catalogTypeName + " of the catalog: "
                + catalogElementController.getNameActiveCatalog());
        
        print(elements);
        
        if(elements.isEmpty())
            println("The " + catalogTypeName + " catalog is empty.");
    }
}
