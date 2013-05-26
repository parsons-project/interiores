package interiores.business.controllers.abstracted;

import interiores.business.exceptions.DefaultCatalogOverwriteException;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.business.models.catalogs.PersistentIdObject;
import interiores.core.data.JAXBDataController;
import java.util.Collection;

/**
 *
 * @author hector
 */
abstract public class CatalogElementController<I extends PersistentIdObject>
    extends CatalogAccessController<I>
{
    public CatalogElementController(JAXBDataController data, AvailableCatalog catalog) {
        super(data, catalog);
    }
    
    protected void add(I element) {
        NamedCatalog<I> activeCatalog = getActiveCatalog();
        
        if(activeCatalog.isDefault())
            throw new DefaultCatalogOverwriteException();
        
        activeCatalog.add(element);
    }
    
    public void rm(String elementName) {
        NamedCatalog<I> activeCatalog = getActiveCatalog();
        
        if(activeCatalog.isDefault())
            throw new DefaultCatalogOverwriteException();
        
        activeCatalog.delete(elementName);
    }
    
    public Collection<I> getCatalogObjects() {
        return getActiveCatalog().getObjects();
    }
}
