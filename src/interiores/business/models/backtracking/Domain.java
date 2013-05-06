
package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.shared.backtracking.Value;
import interiores.shared.backtracking.Variable;
import interiores.utils.Dimension;
import java.awt.Point;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Domain represents the set of values which a given variable can take.
 * Moreover, it has information staged by algorithm iterations.
 * For each iteration, we know which values were available and which were not.
 * This allows stepping back.
 * @author nil.mamano
 */
public class Domain {
   
    
    /**
     * The domain has a stage for each iteration.
     * It gives information about what values have been discarded and,
     * in such cases, in which iteration of the algorithm they were discarded:
     * At any given iteration i (depth = i) of the algorithm, all values which
     * have not been discarded are in the Stage at the i position of the vector.
     * Values which have been discarded are in the Stage at the position
     * correspondent to the iteration in which they were discarded.
     * Stages beyond the position i are empty.
     */
    public Stage[] domain;
    
    
    public Domain(List<FurnitureModel> models, Dimension roomSize,
            int variableCount, int resolution) {
        
        domain = new Stage[variableCount];
        
        domain[0] = new Stage(models, roomSize, resolution);
        
        
    }
    
    //Pre: we have not iterated through all domain values yet.
    public Value getNextDomainValue(int iteration) {      
        return domain[iteration].getNextDomainValue();
    }
    
    
    
    public boolean hasMoreValues(int iteration) {
        return domain[iteration].hasMoreValues();
    }

    
    /**
     * Moves the positions from the current iteration to the next iteration,
     * and leaves the set of positions of the current iteration empty.
     * @param iteration 
     */
    public void pushPositions(int iteration) {
        domain[iteration+1].setPositions(domain[iteration].getPositions());
        domain[iteration].setPositions(new HashSet<Point>());
    }
    
    /**
     * Pushes positions from the iteration i+1 to iteration i.
     * @param invalidArea
     * @param iteration 
     */
    public void trimInvalidRectangle(OrientedRectangle invalidRectangle, int iteration) {
        Collection<Point> trimedPositions = 
                domain[iteration+1].trimInvalidRectangle(invalidRectangle);
        
        domain[iteration].addPositions(trimedPositions);
    }
    
    
    void undoTrimDomain(Variable variable, Value value, int iteration) {

        // 1) check if swap is beneficial
        boolean shouldSwap = domain[iteration].positions.size() <
                             domain[iteration+1].positions.size();
        
        // 2) swap
        if (shouldSwap) {
            HashSet<Point> aux = domain[iteration].positions;
            domain[iteration].positions = domain[iteration+1].positions;
            domain[iteration+1].positions = aux;
        }
        
        // 3) merge
        domain[iteration].positions.addAll(domain[iteration+1].positions);
        domain[iteration+1].positions = null;
    }

    
    
    void resetIterators(int iteration) {
        domain[iteration].resetIterators();
    }
    
    List<FurnitureModel> getModels(int iteration) {
        return domain[iteration].getModels();
    }
    
    HashSet<Point> getPositions(int iteration) {
        return domain[iteration].getPositions();
    }
    
    List<Orientation> getOrientations(int iteration) {
        return domain[iteration].getOrientations();
    }
    
    
}
