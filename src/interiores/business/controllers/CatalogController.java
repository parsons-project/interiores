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
    
    public void merge(String catalogName) throws BusinessException {
        if (! loadedCatalogs.containsKey(catalogName))
            throw new CatalogNotFoundException(catalogName);
        
        NamedCatalog<I> currentCatalog = getActiveCatalog();
        
        if(currentCatalog.isDefault())
            throw new DefaultCatalogOverwriteException();
        
        Collection<I> toMerge = loadedCatalogs.get(catalogName).getCopyObjects();
        
        for (I fType : toMerge) {
            if (! currentCatalog.hasObject(fType)) {
                currentCatalog.add(fType);
            }
        }
    }
    
    public void load(String path) throws JAXBException, BusinessException {
        // Bound PersistentIdObject to load all data
        Class[] classes = { NamedCatalog.class, PersistentIdObject.class };
        
        NamedCatalog loadedCatalog = (NamedCatalog<I>) data.load(classes, path);
        
        if(loadedCatalog.isDefault())
            throw new DefaultCatalogOverwriteException();
            
        loadedCatalogs.put(loadedCatalog.getName(), loadedCatalog);
    }
    
    public void save(String path) throws JAXBException {
        NamedCatalog activeCatalog = getActiveCatalog();
        
        // Bound PersistentIdObject to save all data
        Class[] classes = { activeCatalog.getClass(), PersistentIdObject.class };
        
        data.save(getActiveCatalog(), path, classes);
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
