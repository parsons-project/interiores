package interiores.business.controllers;

import interiores.business.exceptions.DefaultCatalogOverwriteException;
import interiores.business.models.FurnitureType;
import interiores.business.models.catalogs.DefaultFurnitureTypesCatalog;
import interiores.business.models.catalogs.NamedCatalog;
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
    
    private HashMap<String, NamedCatalog<FurnitureType>> loadedTypesCatalogs;
    
    public FurnitureTypesCatalogController(JAXBDataController data) {
        super(data);
        
        NamedCatalog<FurnitureType> typesCatalog = new DefaultFurnitureTypesCatalog();
        
        loadedTypesCatalogs = new HashMap();
        loadedTypesCatalogs.put(typesCatalog.getName(), typesCatalog);
        
        data.set("typesCatalogName", typesCatalog.getName());
        data.set("typesCatalog", typesCatalog);
    }
    
    public void create(String catalogName) throws BusinessException {
        if(catalogName.equals(NamedCatalog.getDefaultName()))
            throw new DefaultCatalogOverwriteException();
        
        loadedTypesCatalogs.put(catalogName, new NamedCatalog(catalogName, getActiveCatalog()));
    }
    
    public void checkout(String catalogName) throws BusinessException {
        if(! loadedTypesCatalogs.containsKey(catalogName))
            throw new BusinessException("There is no catalog known as: " + catalogName);
        
        data.set("typesCatalogName", catalogName);
        data.set("typesCatalog", loadedTypesCatalogs.get(catalogName));
    }
    
    public void load(String path) throws JAXBException, BusinessException {
        NamedCatalog loadedCatalog = (NamedCatalog<FurnitureType>) data.load(NamedCatalog.class, path);
        
        if(loadedCatalog.getName().equals(NamedCatalog.getDefaultName()))
            throw new DefaultCatalogOverwriteException();
            
        loadedTypesCatalogs.put(loadedCatalog.getName(), loadedCatalog);
    }
    
    public void save(String catalogName, String path) throws JAXBException {
        data.save(loadedTypesCatalogs.get(catalogName), path);
    }
    
    public String getNameActiveTypesCatalog() {
        return (String) data.get("typesCatalogName");
    }
    
    public Collection<String> getNamesLoadedTypesCatalogs() {
        return loadedTypesCatalogs.keySet();
    }
    
    private NamedCatalog<FurnitureType> getActiveCatalog() {
        return (NamedCatalog) data.get("typesCatalog");
    }
}
