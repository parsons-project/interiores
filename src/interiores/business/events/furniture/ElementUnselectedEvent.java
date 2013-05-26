package interiores.business.events.furniture;

/**
 *
 * @author hector
 */
public class ElementUnselectedEvent
    extends ElementSelectedEvent
{
    public ElementUnselectedEvent(String name) {
        super(name);
    }
}
