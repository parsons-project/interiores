package interiores.business.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * This class represents a type of room, defined by a name, the type of furniture it should have,
 * and the type furniture it can't have
 * @author alvaro
 */
public class RoomType {
    
    private String name; // name of the type
    
    private HashSet<FurnitureType> mustHave; // Set containing all the FurnitureTypes that must be in this type of room
    private HashSet<FurnitureType> cantHave; // Set containing all the FurnitureTypes that can't be in this type
    
    public RoomType(String name) {
        this(name, new ArrayList(), new ArrayList());
    }
    
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
    
    // should these methods go here or maybe in the controller
    public boolean isObligatory(FurnitureType ftype) {
        return mustHave.contains(ftype);
    }
    
    public boolean isForbidden(FurnitureType ftype) {
        return cantHave.contains(ftype);
    }
            
            
}
