package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogElementController;
import interiores.business.events.catalogs.FTModifiedEvent;
import interiores.business.events.catalogs.FTSetModifiedEvent;
import interiores.business.exceptions.InvalidValueException;
import interiores.business.models.room.RoomType;
import interiores.business.models.SpaceAround;
import interiores.business.events.furniture.ElementSelectedEvent;
import interiores.business.events.furniture.ElementUnselectedEvent;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.business.models.constraints.furniture.BinaryConstraintEnd;
import interiores.business.models.constraints.furniture.binary.MaxDistanceConstraint;
import interiores.business.models.constraints.furniture.binary.MinDistanceConstraint;
import interiores.business.models.constraints.furniture.binary.PartialFacingConstraint;
import interiores.business.models.room.FurnitureType;
import interiores.core.business.BusinessException;
import interiores.core.data.JAXBDataController;
import interiores.utils.Range;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Business controller covering the operations performed over a type of furniture
 * @author hector
 */
public class FurnitureTypeController
    extends CatalogElementController<FurnitureType>
{
    // The default distance a furniture is put with respect to other if they should be separated
    private static int AWAY_DISTANCE = 200;
    
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
     */    
    public void add(String name, int minWidth, int maxWidth, int minDepth, int maxDepth, boolean clingToWalls)
    {
        Range widthRange = new Range(minWidth, maxWidth);
        Range depthRange = new Range(minDepth, maxDepth);
        
        FurnitureType toAdd = new FurnitureType(name, widthRange, depthRange);
        toAdd.setToWalls(clingToWalls);
        
        super.add(toAdd);
        notify(new FTSetModifiedEvent(toAdd.getFullName(),name, true));
    }
    
    @Override
    public void rm(String typeName) {
        String fullName = get(typeName).getFullName();
        super.rm(typeName);
        notify(new FTSetModifiedEvent(fullName,typeName,false));
    }
    
    /**
     * Selects a type and includes it in the wish list (the list of wanted furniture)
     * @param name The name of the type
     */
    public void select(String name)
    {        
        String id = getWishList().addWantedFurniture(name);
        
        notify(new ElementSelectedEvent(id));
    }
    
    /**
     * Removes an element from the list of wanted furniture
     * @param name The identifier of the element to remove
     */
    public void unselect(String name)
            throws BusinessException
    {
        getWishList().removeWantedFurniture(name);
        
        notify(new ElementUnselectedEvent(name));
    }
    
    /**
     * Gets all the furniture in the wish list, that is, all the wanted furniture for the room being designed
     * @return A collection containing all the pieces of furniture in the wish list
     */
    public Collection<String> getRoomFurniture()
    {
        return getWishList().getFurnitureNames();
    }
    
    public Map<String, String> getFullNamesMap() {
        Map<String, String> fullNames = new TreeMap();
        
        for(FurnitureType ft : getCatalogObjects())
            fullNames.put(ft.getFullName(), ft.getName());
        
        return fullNames;
    }
    
    public Range getWidthRange(String ftname) {
        return get(ftname).getWidthRange();
    }
    
    public Range getDepthRange(String ftname) {
        return get(ftname).getDepthRange();
    }
    
    public void setWidthRange(String ftname, int wmin, int wmax) {
        FurnitureType ft = getForWrite(ftname);
        ft.setWidthRange(new Range(wmin, wmax));
        notify(new FTModifiedEvent(ft.getFullName(),ftname));
    }
    
    public void setDepthRange(String ftname, int dmin, int dmax) {
        FurnitureType ft = getForWrite(ftname);
        ft.setWidthRange(new Range(dmin, dmax));
        notify(new FTModifiedEvent(ft.getFullName(),ftname));
    }
    
    public int[] getPassiveSpace(String ftname) {
        return get(ftname).getPassiveSpace().getOffsets();
    }
    
    public void setPassiveSpace(String ftname, int[] ps) {
        if (ps.length != 4)
            throw new InvalidValueException("Passive space needs four values");
        
        SpaceAround sa = new SpaceAround(ps[0],ps[1],ps[2],ps[3]);
        getForWrite(ftname).setPassiveSpace(sa);
        notify(new FTModifiedEvent(get(ftname).getFullName(),ftname));
    }

    /**
     * Gets all the furniture types that can be placed within the current room.
     * That is, the set of all furniture types in the catalog save for those marked
     * as forbidden for the current room type
     * @return A collection of strings containing the names of the mentioned furniture types
     */
    public Collection getSelectableFurniture() {
        Collection<String> selectable = new ArrayList();
        for (FurnitureType ft : getCatalogObjects()) selectable.add(ft.getName());
        
        Collection<String> forbidden = getRoom().getType().getForbidden();
        selectable.removeAll(forbidden);
        return selectable;
    }
    
    public Collection getUncategorizedFurniture(String rtype) {
        Collection<String> u = new ArrayList();
        for (FurnitureType ft : getCatalogObjects()) u.add(ft.getName());
        
        Collection<String> forbidden = getRoomType(rtype).getForbidden();
        u.removeAll(forbidden);
        Collection<String> mandatory = getRoomType(rtype).getMandatory();
        u.removeAll(mandatory);
        
        return u;
    }
    
    private RoomType getRoomType(String name) {
        NamedCatalog<RoomType> rtCatalog = (NamedCatalog) getCatalog(AvailableCatalog.ROOM_TYPES);
        return rtCatalog.get(name);
    }


    public void addBinaryConstraint(String bctype, String type1, String type2) {
        
        BinaryConstraintEnd bce = null;
        
        if (bctype.equals("next-to")) bce = new MaxDistanceConstraint(0);
        else if (bctype.equals("away-from")) bce = new MinDistanceConstraint(AWAY_DISTANCE);
        else if (bctype.equals("in-front-of")) bce = new PartialFacingConstraint(1000); // When facing constraints lose their distance, this attribute should be simply removed
        
        if (bce != null) {
            get(type1).addBinaryConstraint(get(type2), bce);
            notify(new FTModifiedEvent(get(type1).getFullName(),type1));
        }
    }

    public boolean getWallClinging(String name) {
        return get(name).shouldBeClungToWalls();
    }
    
    public void setWallClinging(String name, boolean clinging) {
        get(name).setToWalls(clinging);
        notify(new FTModifiedEvent(get(name).getFullName(),name));
    }
}
