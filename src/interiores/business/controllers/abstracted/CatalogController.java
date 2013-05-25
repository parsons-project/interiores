package interiores.business.controllers.abstracted;

import interiores.business.events.catalogs.ElementChangedEvent;
import interiores.business.exceptions.ActiveCatalogRemovalException;
import interiores.business.exceptions.CatalogAlreadyExistsException;
import interiores.business.exceptions.CatalogNotFoundException;
import interiores.business.exceptions.DefaultCatalogOverwriteException;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.business.models.catalogs.PersistentIdObject;
import interiores.core.Debug;
import interiores.core.data.JAXBDataController;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.bind.JAXBException;

/**
 *
 * @author hector
 */
abstract public class CatalogController<I extends PersistentIdObject>
    extends CatalogAccessController<I>
{
    protected Map<String, NamedCatalog<I>> loadedCatalogs;
    protected String lastLoaded = "";
    
    public CatalogController(JAXBDataController data, AvailableCatalog catalog) {
        super(data, catalog);
        
        loadedCatalogs = new TreeMap();
        
        // Default catalog (cannot be overwritten)
        NamedCatalog<I> defaultCatalog = new NamedCatalog();
        loadedCatalogs.put(defaultCatalog.getName(), defaultCatalog);
        
        setActiveCatalog(defaultCatalog);
    }
    
    public void create(String catalogName) {
        if (loadedCatalogs.containsKey(catalogName))
            throw new CatalogAlreadyExistsException(catalogName);
        else if (catalogName.equals(NamedCatalog.getDefaultName()))
            throw new DefaultCatalogOverwriteException();
        
        loadedCatalogs.put(catalogName, new NamedCatalog(catalogName, getActiveCatalog()));
    }
    
    public void checkout(String catalogName) {
        if(! loadedCatalogs.containsKey(catalogName))
            throw new CatalogNotFoundException(catalogName);
        
        setActiveCatalog(loadedCatalogs.get(catalogName));
    }
    
    public void merge(String catalogName) {
        NamedCatalog<I> currentCatalog = getActiveCatalog();
        
        if(currentCatalog.isDefault())
            throw new DefaultCatalogOverwriteException();
        
        if (! loadedCatalogs.containsKey(catalogName))
            throw new CatalogNotFoundException(catalogName);
        
        Collection<I> toMerge = loadedCatalogs.get(catalogName).getCopyObjects();
        
        for (I fType : toMerge) {
            if (! currentCatalog.hasObject(fType)) {
                    currentCatalog.add(fType);
            }
        }
    }
    
    public void load(String path) throws JAXBException {
        // Bound PersistentIdObject to load all data
        Class[] classes = { NamedCatalog.class, PersistentIdObject.class };
        
        if(!path.startsWith("/"))
            path = getAbsolutePath(path);
        
        
        Debug.println("Loading from " + path);
        
        NamedCatalog loadedCatalog = (NamedCatalog<I>) data.load(classes, path);
        String catalogName = loadedCatalog.getName();
        
        if(loadedCatalog.isDefault())
            throw new DefaultCatalogOverwriteException();
        else if (loadedCatalogs.containsKey(catalogName))
            throw new CatalogAlreadyExistsException(catalogName);
        
        lastLoaded = catalogName;
        loadedCatalogs.put(catalogName, loadedCatalog);
        
    }
    
    public void save(String path) throws JAXBException {
        NamedCatalog activeCatalog = getActiveCatalog();
        
        // Bound PersistentIdObject to save all data
        Class[] classes = { activeCatalog.getClass(), PersistentIdObject.class };
        
        if(!path.startsWith("/"))
            path = getAbsolutePath(path);
        
        Debug.println("Saving to " + path);
        
        data.save(getActiveCatalog(), path, classes);
    }
    
    public void remove(String catalogName) {
        if(! loadedCatalogs.containsKey(catalogName))
            throw new CatalogNotFoundException(catalogName);
        else if(catalogName.equals(NamedCatalog.getDefaultName()))
            throw new DefaultCatalogOverwriteException();
        else if (catalogName.equals(getActiveCatalog().getName()))
            throw new ActiveCatalogRemovalException();
        
        loadedCatalogs.remove(catalogName);
    }
    
    public Collection<String> getNamesLoadedCatalogs() {
        return loadedCatalogs.keySet();
    }
    
    public boolean catalogContains(String cname) {
        return loadedCatalogs.containsKey(cname);
    }
    
    private String getAbsolutePath(String path) {
        return System.getProperty("user.dir") + System.getProperty("file.separator") + path;
    }
   
}
