package interiores.presentation.terminal;

import interiores.business.controllers.FurnitureTypesCatalogController;


/**
 *
 * @author hector
 */
public class FurnitureTypesCatalogCommands
    extends CatalogCommands
{
    private static final String CATALOG_TYPE_NAME = "furniture types";
    
    public FurnitureTypesCatalogCommands(FurnitureTypesCatalogController ftCatalogController) {
        super(ftCatalogController, CATALOG_TYPE_NAME);
    }
}
