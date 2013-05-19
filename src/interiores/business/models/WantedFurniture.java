package interiores.business.models;


/**
 * This class represents a furniture that the user wants to add to the room
 * @author larribas
 */
public class WantedFurniture extends WantedElement
{
    
    /**
     * Simple creator. Creates a wanted furniture given its type
     * @param ft The funiture type of this wanted furniture
     */
    public WantedFurniture(String name, String furnitureTypeName) {
        super(name, furnitureTypeName);
    }
    
 
}
