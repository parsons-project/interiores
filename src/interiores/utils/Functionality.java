/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.utils;

import interiores.core.business.BusinessException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author hector0193
 */
public enum Functionality
{
    SLEEP, BATH;
    
    public static Functionality getEnum(String name) throws BusinessException {
        for(Functionality functionality : values())
            if(functionality.name().equalsIgnoreCase(name))
                return functionality;
        
        throw new BusinessException("There is no functionality named as " + name);
    }
    
    public static Collection<String> getFunctionalitiesName() {
        Collection<String> names = new ArrayList();
        for(Functionality f : values()) names.add(f.toString());
        return names;
    }
    
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
