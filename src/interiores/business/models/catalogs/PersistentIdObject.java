package interiores.business.models.catalogs;

import horarios.shared.IdObject;
import interiores.business.models.room.FurnitureType;
import interiores.business.models.RoomType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author hector
 */
@XmlRootElement
@XmlSeeAlso({RoomType.class, FurnitureType.class})
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
