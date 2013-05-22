package interiores.business.models;

import interiores.business.models.room.elements.WantedFixed;
import interiores.business.models.backtracking.FurnitureValue;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author hector
 */
public class RoomDesign
{
    private Map<String, WantedFixed> fixedElements;
    private Map<String, FurnitureValue> furniture;
    
    public RoomDesign() {
        fixedElements = new TreeMap();
        furniture = new TreeMap();
    }
    
    public void setFixedElement(String name, WantedFixed fixedElement) {
        
    }
    
    public void setFurniture(String name, FurnitureValue value) {
        furniture.put(name, value);
    }
}
