package interiores.presentation.terminal.commands;

import interiores.presentation.terminal.commands.abstracted.CatalogCommands;
import interiores.business.controllers.FurnitureTypesCatalogController;
import interiores.core.presentation.terminal.annotation.CommandSubject;


/**
 *
 * @author hector
 */
@CommandSubject(name = "ftc", description = "Furniture types catalog related commands")
public class FurnitureTypesCatalogCommands
    extends CatalogCommands
{
    private static final String CATALOG_TYPE_NAME = "furniture types";
    
    public FurnitureTypesCatalogCommands(FurnitureTypesCatalogController ftCatalogController) {
        super(ftCatalogController, CATALOG_TYPE_NAME);
    }
}
