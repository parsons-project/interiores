package interiores.business.controllers;

import horarios.shared.Catalog;
import horarios.shared.ElementNotFoundException;
import interiores.business.models.FurnitureType;
import interiores.business.models.Room;
import interiores.core.business.BusinessController;
import interiores.core.business.BusinessException;
import interiores.core.data.JAXBDataController;
import interiores.utils.Dimension;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hector
 */
public class FurnitureTypeController
    extends BusinessController
{
    public FurnitureTypeController(JAXBDataController data) {
        super(data);
    }
    
    public void add(String name, int minWidth, int minDepth, int maxWidth, int maxDepth) {
        Dimension min = new Dimension(minWidth, minDepth);
        Dimension max = new Dimension(maxWidth, maxDepth);
        
        getTypesCatalog().add(new FurnitureType(name, min, max));
    }
    
    public Collection<FurnitureType> getCatalogObjects() {
        return getTypesCatalog().getObjects();
    }
    
    public String getCatalogName() {
        return (String) data.get("typesCatalogName");
    }
    
    private Catalog<FurnitureType> getTypesCatalog() {
        return (Catalog<FurnitureType>) data.get("typesCatalog");
    }
    
    public void select(String name) throws ElementNotFoundException {
        if (getTypesCatalog().hasObject(name))
            getRoom().addFurnitureType(getTypesCatalog().getObject(name));
    }
    
    public void unselect(String name) throws BusinessException, ElementNotFoundException {
        if (getTypesCatalog().hasObject(name))
            getRoom().removeFurnitureType(getTypesCatalog().getObject(name));
    }
    
    private Room getRoom() {
        return (Room) data.get("room");
    }

    public Collection<FurnitureType> getRoomTypes() {
        return getRoom().getWishList();
    }
    
}
