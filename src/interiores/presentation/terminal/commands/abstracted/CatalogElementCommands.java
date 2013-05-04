package interiores.presentation.terminal.commands.abstracted;

import interiores.business.controllers.abstracted.CatalogElementController;
import interiores.core.business.BusinessException;
import interiores.core.presentation.terminal.AdvancedCommandGroup;
import interiores.core.presentation.terminal.annotation.Command;
import java.util.Collection;

/**
 *
 * @author hector
 */
abstract public class CatalogElementCommands
    extends AdvancedCommandGroup {
    
    private CatalogElementController catalogElementController;
    private String catalogTypeName;
    
    public CatalogElementCommands(CatalogElementController catalogElementController, String catalogTypeName) {
        this.catalogElementController = catalogElementController;
        this.catalogTypeName = catalogTypeName;
    }
    
    @Command("Remove a type of furniture from the catalog")
    public void rm() throws BusinessException {
        Collection<String> names = readStrings("Enter the names of the " + catalogTypeName  + " you want "
                + "to remove:");
        
        for(String name : names)
            catalogElementController.rm(name);
    }
    
    @Command("Obtain a list containing all the available furniture types")
    public void list() {
        Collection elements = catalogElementController.getCatalogObjects();
        
        println("Listing " + catalogTypeName + " of the catalog: "
                + catalogElementController.getNameActiveCatalog());
        
        print(elements);
        
        if(elements.isEmpty())
            println("The " + catalogTypeName + " catalog is empty.");
    }
}
