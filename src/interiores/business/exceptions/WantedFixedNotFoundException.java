package interiores.business.exceptions;

/**
 *
 * @author hector
 */
public class WantedFixedNotFoundException
    extends WantedElementNotFoundException
{
    public WantedFixedNotFoundException(String fixedElementId) {
        super("fixed element", fixedElementId);
    }
}
