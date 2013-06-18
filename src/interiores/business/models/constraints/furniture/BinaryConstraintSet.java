package interiores.business.models.constraints.furniture;

import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.backtracking.InterioresVariable;
import interiores.business.models.room.elements.WantedFurniture;
import interiores.core.business.BusinessException;
import interiores.shared.backtracking.Variable;
import interiores.utils.BinaryConstraintAssociation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hector
 */
@XmlRootElement
public class BinaryConstraintSet
{
    private static final String KEY_FORMAT = "%s-%s-";
    private static final String KEY_TOKEN_FORMAT = "-%s-";
    
    @XmlElementWrapper
    private TreeMap<String, BinaryConstraintEnd> binaryConstraints;

    public BinaryConstraintSet() {
        binaryConstraints = new TreeMap();
    }
    
    public void bound(FurnitureVariable variable, BinaryConstraintEnd bc)
    {
        InterioresVariable other = bc.getOtherVariable();
        add(bc, other.getName());
        
        if(! bc.hasCounterPart())
            return;
        
        BinaryConstraintEnd counterPart = bc.getCounterPart(variable);
        
        WantedFurniture end = (WantedFurniture) other;
        end.addBinaryConstraint(counterPart, variable.getName());
    }
    
    public void unbound(String furnitureName, Class<? extends BinaryConstraintEnd> binaryConstraintClass,
            String elementId)
    {
        String key = getKey(binaryConstraintClass, elementId);
        
        if(! binaryConstraints.containsKey(key))
            return;

        BinaryConstraintEnd bc = binaryConstraints.get(key);
        
        remove(binaryConstraintClass, elementId);
        
        if(! bc.hasCounterPart())
            return;
        
        WantedFurniture end = (WantedFurniture) bc.getOtherVariable();
        end.removeBinaryConstraint(binaryConstraintClass, furnitureName);
    }
    
    public void clear(String furnitureName)
    {
        for(BinaryConstraintEnd bc : binaryConstraints.values()) {
            if(! bc.hasCounterPart())
                return;
        
            WantedFurniture end = (WantedFurniture) bc.getOtherVariable();
            end.removeBinaryConstraint(bc.getClass(), furnitureName);
        }
    }
    
    public void add(BinaryConstraintEnd bc, String elementId) {
        binaryConstraints.put(getKey(bc.getClass(), elementId), bc);
    }
    
    public void remove(Class<? extends BinaryConstraintEnd> binaryConstraintClass, String elementId) {
        String key = getKey(binaryConstraintClass, elementId);
        
        binaryConstraints.remove(key);
    }
    
    public Collection<BinaryConstraintEnd> getConstraints(String furnitureId) {
        Set<BinaryConstraintEnd> bcaSet = new HashSet();
        
        String keyToken = String.format(KEY_TOKEN_FORMAT, furnitureId);
        
        for(String key : binaryConstraints.keySet()) {
            if(key.contains(keyToken))
                bcaSet.add(binaryConstraints.get(key));
        }
        
        return bcaSet;
    }
    
    public void removeConstraints(String furnitureId) {
        String keyToken = String.format(KEY_TOKEN_FORMAT, furnitureId);
        
        for(String key : binaryConstraints.keySet()) {
            if(key.contains(keyToken))
                binaryConstraints.remove(key);
        }
    }
    
    public Collection<BinaryConstraintEnd> getAll() {
        return binaryConstraints.values();
    }
    
    private String getKey(Class binaryConstraintClass, String elementId) {
        String className = binaryConstraintClass.getSimpleName();
        String key = String.format(KEY_FORMAT, className, elementId);
        
        return key;
    }
}
