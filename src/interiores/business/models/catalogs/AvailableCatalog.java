package interiores.business.models.catalogs;

/**
 *
 * @author hector
 */
public enum AvailableCatalog {
    ROOM_TYPES("roomTypes"), FURNITURE_TYPES("furnitureTypes");
    
    private String key;
    
    private AvailableCatalog(String key) {
        this.key = key;
    }
    
    @Override
    public String toString() {
        return key;
    }
}
