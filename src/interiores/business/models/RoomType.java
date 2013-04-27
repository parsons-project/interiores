package interiores.business.models;

import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author alvaro
 */
public class RoomType {
    
    private String name;
    
    private HashSet<FurnitureType> mustHave;
    private HashSet<FurnitureType> cantHave;
    
    public RoomType(String name, Collection<FurnitureType> mustHave, Collection<FurnitureType> cantHave) {
        this.name = name;
        this.mustHave = new HashSet<FurnitureType>(mustHave);
        this.cantHave = new HashSet<FurnitureType>(cantHave);
    }
    
    public String getName() {
        return name;        
    }
    
    public HashSet<FurnitureType> getObligatory() {
        return mustHave;
    }
    
    public HashSet<FurnitureType> getForbidden() {
        return cantHave;
    }
    
    
}
