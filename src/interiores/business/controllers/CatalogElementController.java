package interiores.business.controllers;

import interiores.business.exceptions.DefaultCatalogOverwriteException;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.business.models.catalogs.PersistentIdObject;
import interiores.core.business.BusinessController;
import interiores.core.business.BusinessException;
import interiores.core.data.JAXBDataController;
import java.util.Collection;

/**
 *
 * @author hector
 */
abstract public class CatalogElementController<I extends PersistentIdObject>
    extends BusinessController
{
    private String name;
    
    public CatalogElementController(JAXBDataController data, String name) {
        super(data);
        
        this.name = name;
    }
    
    protected void add(I element)
            throws DefaultCatalogOverwriteException
    {
        NamedCatalog<I> activeCatalog = getActiveCatalog();
        
        if(activeCatalog.isDefault())
            throw new DefaultCatalogOverwriteException();
        
        activeCatalog.add(element);
    }
    
    public void rm(String elementName)
            throws BusinessException
    {
        NamedCatalog<I> activeCatalog = getActiveCatalog();
        
        if(activeCatalog.isDefault())
            throw new DefaultCatalogOverwriteException();
        
        activeCatalog.delete(elementName);
    }
        
    public Collection<I> getCatalogObjects() {
        return getActiveCatalog().getObjects();
    }
    
    public String getNameActiveCatalog() {
        return getActiveCatalog().getName();
    }
    
    protected NamedCatalog<I> getActiveCatalog() {
        return (NamedCatalog<I>) data.get(name);
    }
}
