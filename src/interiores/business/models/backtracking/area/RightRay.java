/**
 * Represents a wide ray that propagates to the right.
 */
package interiores.business.models.backtracking.area;

/**
 * Represents a wide ray of the GridPlane that propagates the right.
 * @author nil.mamano
 */
class RightRay {
    public Square origin;

    public RightRay(Square origin) {
        this.origin = origin;
    }
    
    @Override
    public boolean equals(Object p) {
        return this.origin.equals(((RightRay)p).origin);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.origin != null ? this.origin.hashCode() : 0);
        return hash;
    }
}
