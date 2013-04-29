package interiores.business.models.catalogs;

import horarios.shared.Catalog;
import horarios.shared.IdObject;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hector
 */
@XmlRootElement
public class NamedCatalog<X extends IdObject>
    extends Catalog<X>
{
    private static final String DEFAULT_NAME = "default";
    
    @XmlAttribute
    private String name;
    
    public NamedCatalog() {
        this(DEFAULT_NAME);
    }
    
    public NamedCatalog(String name) {
        super();
        
        this.name = name;
    }
    
    public static String getDefaultName() {
        return DEFAULT_NAME;
    }
    
    public String getName() {
        return name;
    }
}
