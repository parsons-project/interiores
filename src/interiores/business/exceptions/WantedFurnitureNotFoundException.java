package interiores.business.exceptions;

/**
 *
 * @author hector
 */
public class WantedFurnitureNotFoundException
    extends WantedElementNotFoundException
{
    public WantedFurnitureNotFoundException(String furnitureElementId) {
        super("furniture", furnitureElementId);
    }
}
