package interiores.business.controllers;

import interiores.business.exceptions.CatalogNotFoundException;
import interiores.business.exceptions.DefaultCatalogOverwriteException;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.business.models.catalogs.PersistentIdObject;
import interiores.core.business.BusinessController;
import interiores.core.business.BusinessException;
import interiores.core.data.JAXBDataController;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBException;

/**
 *
 * @author hector
 */
abstract public class CatalogController<I extends PersistentIdObject>
    extends BusinessController
{
    private String name;
    protected Map<String, NamedCatalog<I>> loadedCatalogs;
    
    public CatalogController(String name, JAXBDataController data) {
        super(data);
        
        this.name = name;
        loadedCatalogs = new HashMap();
        
        NamedCatalog<I> defaultCatalog = new NamedCatalog();
        
        loadedCatalogs.put(defaultCatalog.getName(), defaultCatalog);
        
        data.set(name + "CatalogName", defaultCatalog.getName());
        data.set(name + "Catalog", defaultCatalog);
    }
    
    public void create(String catalogName) throws BusinessException {
        if(catalogName.equals(NamedCatalog.getDefaultName()))
            throw new DefaultCatalogOverwriteException();
        
        loadedCatalogs.put(catalogName, new NamedCatalog(catalogName, getActiveCatalog()));
    }
    
    public void checkout(String catalogName) throws BusinessException {
        if(! loadedCatalogs.containsKey(catalogName))
            throw new CatalogNotFoundException(catalogName);
        
        data.set(name + "CatalogName", catalogName);
        data.set(name + "Catalog", loadedCatalogs.get(catalogName));
    }
    
    public void load(String path) throws JAXBException, BusinessException {
        NamedCatalog loadedCatalog = (NamedCatalog<I>) data.load(NamedCatalog.class, path);
        
        if(loadedCatalog.isDefault())
            throw new DefaultCatalogOverwriteException();
            
        loadedCatalogs.put(loadedCatalog.getName(), loadedCatalog);
    }
    
    public void save(String path) throws JAXBException {
        data.save(getActiveCatalog(), path);
    }
    
    public String getNameActiveCatalog() {
        return (String) data.get(name + "CatalogName");
    }
    
    public Collection<String> getNamesLoadedCatalogs() {
        return loadedCatalogs.keySet();
    }
    
    protected NamedCatalog<I> getActiveCatalog() {
        return (NamedCatalog) data.get(name + "Catalog");
    }
}
