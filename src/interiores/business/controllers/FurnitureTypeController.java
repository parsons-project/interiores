package interiores.business.controllers;

import horarios.shared.Catalog;
import interiores.business.models.FurnitureType;
import interiores.core.business.BusinessController;
import interiores.core.data.JAXBDataController;
import interiores.utils.Dimension;
import java.util.Collection;

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
}
