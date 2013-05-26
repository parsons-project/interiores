package interiores.presentation.terminal.commands;

import interiores.business.controllers.RoomTypesCatalogController;
import interiores.core.presentation.terminal.annotation.CommandSubject;
import interiores.presentation.terminal.commands.abstracted.CatalogCommands;

/**
 *
 * @author hector
 */
@CommandSubject(name = "rtc", description = "Room types catalog related commands")
public class RoomTypesCatalogCommands
    extends CatalogCommands
{
    private static final String CATALOG_TYPE_NAME = "room types";
    
    public RoomTypesCatalogCommands(RoomTypesCatalogController rtCatalogController) {
        super(rtCatalogController, CATALOG_TYPE_NAME);
    }
}
