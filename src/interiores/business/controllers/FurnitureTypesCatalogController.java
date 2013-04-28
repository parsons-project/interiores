package interiores.business.controllers;

import horarios.shared.Catalog;
import interiores.business.models.FurnitureType;
import interiores.core.business.BusinessController;
import interiores.core.data.JAXBDataController;
import java.util.HashMap;
import javax.xml.bind.JAXBException;

/**
 *
 * @author hector
 */
public class FurnitureTypesCatalogController
    extends BusinessController
{
    private HashMap<String, Catalog<FurnitureType>> loadedTypeCatalogs;
    
    public FurnitureTypesCatalogController(JAXBDataController data) {
        super(data);
        
        Catalog<FurnitureType> typesCatalog = new Catalog<FurnitureType>();
        
        loadedTypeCatalogs = new HashMap();
        loadedTypeCatalogs.put("default", typesCatalog);
        
        data.set("typesCatalog", typesCatalog);
    }
    
    public void load(String catalogName, String path) throws JAXBException {
        loadedTypeCatalogs.put(catalogName, (Catalog<FurnitureType>) data.load(Catalog.class, path));
    }
    
    public void save(String catalogName, String path) throws JAXBException {
        data.save(loadedTypeCatalogs.get(catalogName), path);
    }
}
