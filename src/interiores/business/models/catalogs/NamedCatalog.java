package interiores.business.models.catalogs;

import horarios.shared.Catalog;
import horarios.shared.ElementNotFoundException;
import interiores.business.exceptions.DefaultCatalogOverwriteException;
import interiores.business.exceptions.ElementNotFoundBusinessException;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hector
 */
@XmlRootElement
public class NamedCatalog<X extends PersistentIdObject>
    extends Catalog<X>
{
    private static final String DEFAULT_NAME = "default";
    
    @XmlAttribute
    private String name;
    
    public static String getDefaultName() {
        return DEFAULT_NAME;
    }
    
    public NamedCatalog() {
        this(DEFAULT_NAME);
    }
    
    public NamedCatalog(String name) {
        super();
        
        this.name = name;
    }
    
    public NamedCatalog(String name, NamedCatalog catalog) {
        this(name);
        
        Collection<X> objects = catalog.getCopyObjects();
        
        for(X object : objects)
            add(object);
    }
    
    public boolean isDefault() {
        return name.equals(DEFAULT_NAME);
    }
    
    public String getName() {
        return name;
    }
    
    public X get(String id)
            throws ElementNotFoundBusinessException
    {
        try {
            return super.getObject(id);
        }
        catch(ElementNotFoundException e) {
            throw new ElementNotFoundBusinessException(name, id, e);
        }
    }
    
    public X getForWrite(String id)
            throws ElementNotFoundBusinessException, DefaultCatalogOverwriteException
    {
        if(isDefault())
            throw new DefaultCatalogOverwriteException();
        
        return get(id);
    }
    
    @Override
    public X getObject(String id) {
        throw new UnsupportedOperationException("getObject method in NamedCatalog is deprecated.");
    }
}
