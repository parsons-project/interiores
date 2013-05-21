package interiores.business.events.furniture;

/**
 *
 * @author hector
 */
public class FurnitureTypeUnselectedEvent
    extends FurnitureTypeSelectedEvent
{
    public FurnitureTypeUnselectedEvent(String name) {
        super(name);
    }
}
