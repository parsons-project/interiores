package interiores.presentation.terminal.commands;

import interiores.business.controllers.FurnitureTypesCatalogController;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.core.Options;
import interiores.core.presentation.terminal.annotation.Command;
import interiores.core.presentation.terminal.annotation.CommandOptions;
import interiores.core.presentation.terminal.annotation.CommandSubject;
import interiores.presentation.terminal.commands.abstracted.CatalogCommands;
import java.util.ArrayList;
import java.util.Collection;


/**
 *
 * @author hector
 */
@CommandSubject(name = "ftc", description = "Furniture types catalog related commands")
public class FurnitureTypesCatalogCommands
    extends CatalogCommands
{
    private static final String CATALOG_TYPE_NAME = "furniture types";
    
    private FurnitureTypesCatalogController ftCatalogController;
    
    public FurnitureTypesCatalogCommands(FurnitureTypesCatalogController ftCatalogController) {
        super(ftCatalogController, CATALOG_TYPE_NAME);
        
        this.ftCatalogController = ftCatalogController;
    }
    
    
    @Override
    @Command("Selects the catalog to be used")
    @CommandOptions("new")
    public void checkout(Options options) {
        super.checkout(options);

        Collection<String> fNames;
        
        try {
            fNames = ftCatalogController.getSelectedFurnitureTypes();
        }
        catch(NoRoomCreatedException e) {
            fNames = new ArrayList();
        }
        
        for(String fName : fNames) {
            if(! ftCatalogController.exists(fName))
                println("Warning: The selected furniture type: " + fName + " does not exist in this catalog.");
        }
    }
}
