package interiores.business.models;

import interiores.business.models.backtracking.FurnitureValue;
import interiores.core.Utils;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author hector
 */
public class RoomDesign
{
    private Map<String, FurnitureValue> furniture;
    
    public RoomDesign() {
        furniture = new TreeMap();
    }
    
    public RoomDesign(Map<String, FurnitureValue> furniture) {
        this.furniture = furniture;
    }
    
    public void put(String name, FurnitureValue value) {
        furniture.put(name, value);
    }
    
    public void remove(String name) {
        furniture.remove(name);
    }
    
    public Set<Entry<String, FurnitureValue>> getEntries() {
        return furniture.entrySet();
    }
    
        
    @Override
    public String toString() {
        String result = "Room design:\n";
        
        for(String id : furniture.keySet())
            result += Utils.padRight(id, 20) + furniture.get(id).toString() + "\n";
        
        return result;
    }
}
