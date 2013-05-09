package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogElementController;
import interiores.business.exceptions.DefaultCatalogOverwriteException;
import interiores.business.exceptions.ElementNotFoundBusinessException;
import interiores.business.exceptions.ForbiddenFurniture;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.business.models.FurnitureType;
import interiores.business.models.RoomType;
import interiores.business.models.WantedFurniture;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.core.Debug;
import interiores.core.business.BusinessException;
import interiores.core.data.JAXBDataController;
import interiores.utils.Range;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Business controller covering the operations performed over a type of furniture
 * @author hector
 */
public class FurnitureTypeController
    extends CatalogElementController<FurnitureType>
{
    /**
     * Creates a particular instance of the furniture type controller
     * @param data The data controller that will give access to the objects this controller will use
     */
    public FurnitureTypeController(JAXBDataController data) {
        super(data, AvailableCatalog.FURNITURE_TYPES);
    }
    
    /**
     * Adds a furniture type to the current catalog
     * @param name The name of the type
     * @param minWidth The minimum width of the type
     * @param maxWidth The maximum width of the type
     * @param minDepth The minimum depth of the type
     * @param maxDepth The maximum depth of the type
     * @throws DefaultCatalogOverwriteException 
     */
    public void add(String name, int minWidth, int maxWidth, int minDepth, int maxDepth)
            throws DefaultCatalogOverwriteException
    {
        Range widthRange = new Range(minWidth, maxWidth);
        Range depthRange = new Range(minDepth, maxDepth);
        
        FurnitureType toAdd = new FurnitureType(name, widthRange, depthRange);
        
        super.add(toAdd);
    }
    
    /**
     * Selects a type and includes it in the wish list (the list of wanted furniture)
     * @param name The name of the type
     * @throws ElementNotFoundBusinessException
     * @throws NoRoomCreatedException 
     */
    public void select(String name)
            throws ElementNotFoundBusinessException, NoRoomCreatedException, ForbiddenFurniture
    {
        FurnitureType ft = get(name);
        RoomType rt = getRoom().getType();
        if ( rt.isForbidden(ft) )
            throw new ForbiddenFurniture(name,rt.getName());
        
        WantedFurniture wf = new WantedFurniture(ft);
        getWishList().addWantedFurniture(wf);
    }
    
    /**
     * Removes an element from the list of wanted furniture
     * @param name The identifier of the element to remove
     * @throws BusinessException 
     */
    public void unselect(String name)
            throws BusinessException
    {
        getWishList().removeWantedFurniture(name);
    }
    
    /**
     * Gets all the furniture in the wish list, that is, all the wanted furniture for the room being designed
     * @return A collection containing all the pieces of furniture in the wish list
     * @throws NoRoomCreatedException 
     */
    public Collection getRoomFurniture()
            throws NoRoomCreatedException
    {
        return getWishList().getFurnitureNames();
    }

    /**
     * Gets all the furniture types that can be placed within the current room.
     * That is, the set of all furniture types in the catalog save for those marked
     * as forbidden for the current room type
     * @return A collection of strings containing the names of the mentioned furniture types
     * @throws NoRoomCreatedException 
     */
    public Collection getSelectableFurniture() throws NoRoomCreatedException {
        Collection<String> selectable = new ArrayList();
        for (FurnitureType ft : getCatalogObjects()) selectable.add(ft.getName());
        
        Collection<String> forbidden = getRoom().getType().getForbidden();
        selectable.removeAll(forbidden);
        return selectable;
    }
}
