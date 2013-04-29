package interiores.business.controllers;

import horarios.shared.Catalog;
import interiores.business.models.FurnitureType;
import interiores.core.business.BusinessController;
import interiores.core.business.BusinessException;
import interiores.core.data.JAXBDataController;
import java.util.Collection;
import java.util.HashMap;
import javax.xml.bind.JAXBException;

/**
 *
 * @author hector
 */
public class FurnitureTypesCatalogController
    extends BusinessController
{
    private HashMap<String, Catalog<FurnitureType>> loadedTypesCatalogs;
    
    public FurnitureTypesCatalogController(JAXBDataController data) {
        super(data);
        
        Catalog<FurnitureType> typesCatalog = new Catalog<FurnitureType>();
        
        loadedTypesCatalogs = new HashMap();
        loadedTypesCatalogs.put("default", typesCatalog);
        
        data.set("typesCatalogName", "default");
        data.set("typesCatalog", typesCatalog);
    }
    
    public String getNameActiveTypesCatalog() {
        return (String) data.get("typesCatalogName");
    }
    
    public Collection<String> getNamesLoadedTypesCatalogs() {
        return loadedTypesCatalogs.keySet();
    }
    
    public void checkout(String catalogName) throws BusinessException {
        if(! loadedTypesCatalogs.containsKey(catalogName))
            throw new BusinessException("There is no catalog known as: " + catalogName);
        
        data.set("typesCatalog", loadedTypesCatalogs.get(catalogName));
    }
    
    public void load(String catalogName, String path) throws JAXBException {
        loadedTypesCatalogs.put(catalogName, (Catalog<FurnitureType>) data.load(Catalog.class, path));
    }
    
    public void save(String catalogName, String path) throws JAXBException {
        data.save(loadedTypesCatalogs.get(catalogName), path);
    }
}
