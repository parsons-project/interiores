package horarios.shared;

/**
 *
 * @author hector
 */
public class IntegerIdObject extends IdObject<Integer>
{
    public IntegerIdObject(int id) {
        super(id);
    }
    
    /**
     * compareTo function overrided 
     * @param o Second object to do the comparation
     * @return <0 -> this < o; =0 -> this = o; >0 -> this > o
     */
    @Override
    public int compareTo(IdObject o) {
        return identifier.compareTo((Integer) o.identifier);
    }
}
