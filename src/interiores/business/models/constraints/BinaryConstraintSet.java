package interiores.business.models.constraints;

import interiores.business.models.backtracking.FurnitureVariable;
import java.util.HashMap;


// FurnitureVariable hashCode and equals should be indepenedent of the value it has
// and only consider part of it???

/**
 * This class mantains a set of binary constraints that can be accessed by giving two FurnitureVariables
 * @author alvaro
 */
public class BinaryConstraintSet {
    
    /**
     * This class is an unordered pair of FurnitureVariables. It's unordered because
     * <f1,f2> equals <f2,f1> and also has the same hashCode. It's useful to not duplicate restrictions
     * and being able to acces it from <f1,f2> and also <f2,f1>
     */
    public class UnorderedFurnitureVariablePair {
        
        private final FurnitureVariable first, second;
     
        public UnorderedFurnitureVariablePair(FurnitureVariable first, FurnitureVariable second) {
            super();
            this.first = first;
            this.second = second;
        }

        @Override
        public int hashCode() {
            // We assure that both <p1, p2> and <p2,p1> have the same Hash
            int hashFirst = first.hashCode();
            int hashSecond = second.hashCode();
            return (hashFirst + hashSecond);
        }

        @Override
        public boolean equals(Object other) {
            // To be consistent with the hashCode we have to assure that <f1,f2> == <f2,f1>
            if (other instanceof UnorderedFurnitureVariablePair) {
                    UnorderedFurnitureVariablePair otherPair = (UnorderedFurnitureVariablePair) other;
                    return ( (this.first.equals(otherPair.first) && this.second.equals(otherPair.second)) ||
                             (this.first.equals(otherPair.second) && this.second.equals(otherPair.first)) );
            }
            else return false;
        }

        @Override
        public String toString() { 
               return "(" + first + ", " + second + ")"; 
        }


    }
    
    // The set itself
    private HashMap<UnorderedFurnitureVariablePair, BinaryConstraint >binConstraintsSet;
    
    /**
     * Void constructor
     */
    public BinaryConstraintSet() {
        
    }
    
    /**
     * Creates a BinaryConstraintSet from another BinaryConstraintSet, copying its content
     * @param other The other BinaryConstraintSet to be copied
     */
    public BinaryConstraintSet(BinaryConstraintSet other) {
        this.binConstraintsSet = other.binConstraintsSet;
    }
    
    /**
     * Adds a constraint to the set
     * @param fv1 A furniture variable
     * @param fv2 Another furniture variable
     * @param bc The binary constraint that associates fv1 with fv2
     * @return True if everything went correctly
     */
    public boolean addConstraint(FurnitureVariable fv1, FurnitureVariable fv2, BinaryConstraint bc) {
        binConstraintsSet.put(new UnorderedFurnitureVariablePair(fv1,fv2), bc);
        return true;
    }
    
    /**
     * Checks if the constraint between fv1 and fv2 
     * @param fv1
     * @param fv2
     * @return 
     */
    public boolean isSatisfied(FurnitureVariable fv1, FurnitureVariable fv2) {
        
        UnorderedFurnitureVariablePair pair = new UnorderedFurnitureVariablePair(fv1, fv2);
        
        return binConstraintsSet.containsKey(pair) && binConstraintsSet.get(pair).isSatisfied();
    }
}
