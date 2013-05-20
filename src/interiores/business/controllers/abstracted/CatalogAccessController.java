package interiores.business.controllers.abstracted;

import interiores.business.exceptions.DefaultCatalogOverwriteException;
import interiores.business.exceptions.ElementNotFoundBusinessException;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.business.models.catalogs.PersistentIdObject;
import interiores.core.data.JAXBDataController;

/**
 *
 * @author hector
 */
abstract public class CatalogAccessController<I extends PersistentIdObject>
    extends InterioresController
{
    private String keyData;
    
    public CatalogAccessController(JAXBDataController data, AvailableCatalog catalog) {
        super(data);
        
        this.keyData = getCatalogKeyData(catalog);
    }
    
    public void setActiveCatalog(NamedCatalog<I> catalog) {
        data.set(keyData, catalog);
    }
    
    public String getNameActiveCatalog() {
        return getActiveCatalog().getName();
    }
    
    protected NamedCatalog<I> getActiveCatalog() {
        return (NamedCatalog<I>) data.get(keyData);
    }
    
    protected I getForWrite(String id)
            throws ElementNotFoundBusinessException, DefaultCatalogOverwriteException
    {
        return getActiveCatalog().getForWrite(id);
    }
    
    protected I get(String id)
            throws ElementNotFoundBusinessException
    {
        return getActiveCatalog().get(id);
    }
    
    public boolean exists(String name) {
        return getActiveCatalog().hasObject(name);
    }
}
