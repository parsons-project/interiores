
package interiores.business.models.backtracking.area;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a grid point of the Grid Plane.
 * @author nil.mamano
 */
@XmlRootElement
class GridPoint {
    @XmlAttribute
    public int x;
    
    @XmlAttribute
    public int y;
    
    /**
     * Empty constructor needed for persistance.
     */
    public GridPoint()
    { }
    
    public GridPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
    
    @Override
    public boolean equals(Object p) {
//        Debug.println("In GridPoint.equals");
//        Debug.println("This:" + this.toString());
//        Debug.println("Param:" + p.toString());

        Boolean result = this.x == ((GridPoint)p).x && this.y == ((GridPoint)p).y;

//        Debug.println("Result:" + result.toString());
        return result;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.x;
        hash = 67 * hash + this.y;
        return hash;
    }
}
