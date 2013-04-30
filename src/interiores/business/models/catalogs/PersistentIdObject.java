package interiores.business.models.catalogs;

import horarios.shared.IdObject;
import interiores.business.models.FurnitureType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author hector
 */
@XmlRootElement
@XmlSeeAlso({FurnitureType.class})
public class PersistentIdObject
    extends IdObject
{
    public PersistentIdObject() {
        super();
    }
    
    public PersistentIdObject(String name) {
        super(name);
    }
}
