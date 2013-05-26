package interiores.business.models.constraints.furniture;

import interiores.core.business.BusinessException;
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
    private static final String KEY_FORMAT = "-%s-%s-%s-";
    private static final String KEY_TOKEN_FORMAT = "-%s-";
    
    @XmlElementWrapper
    private TreeMap<String, BinaryConstraintAssociation> binaryConstraints;
    
    public BinaryConstraintSet() {
        binaryConstraints = new TreeMap();
    }
    
    public void add(BinaryConstraintEnd bc, String f1, String f2) {
        if (f1.equals(f2))
            throw new BusinessException("Can't apply binary constraint to itself");
        
        BinaryConstraintAssociation bca = new BinaryConstraintAssociation(bc, f1, f2);
        
        binaryConstraints.put(getKey(bc.getClass(), f1, f2), bca);
    }
    
    public void remove(Class<? extends BinaryConstraintEnd> binaryConstraintClass, String f1,
            String f2)
    {
        String key = getKey(binaryConstraintClass, f1, f2);
        BinaryConstraintEnd bc = binaryConstraints.get(key).getConstraint();
        
        binaryConstraints.remove(key);
    }
    
    public Collection<BinaryConstraintAssociation> getConstraints(String furnitureId) {
        Set<BinaryConstraintAssociation> bcaSet = new HashSet();
        
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
    
    public Collection<BinaryConstraintAssociation> getConstraints() {
        return binaryConstraints.values();
    }
    
        private String getKey(Class binaryConstraintClass, String f1, String f2)
    {
        String className = binaryConstraintClass.getSimpleName();
        String key = String.format(KEY_FORMAT, className, f1, f2);
        
        if(binaryConstraints.containsKey(key))
            return key;
        
        return String.format(KEY_FORMAT, className, f2, f1);
    }
}
