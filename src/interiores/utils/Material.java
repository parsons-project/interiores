package interiores.utils;

import interiores.core.business.BusinessException;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alvaro
 */
@XmlRootElement
public enum Material {
    
    
    AGGLOMERATE, PINE, PLASTIC,
    STEEL, BEECH, BIRCH,
    MARBLE, PORCELAIN, GLASS, BRICK, WOOD;
   
    public static Collection<String> getNames() {
        Collection<String> names = new ArrayList();
        for(Material material : values()) {
            names.add(material.name().toLowerCase());
        }
        return names;
    }
    
    public static Material getEnum(String name) throws BusinessException {
        for(Material material : values())
            if(material.name().equalsIgnoreCase(name))
                return material;
        
        throw new BusinessException("There is no material named as " + name);
    }
    
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
