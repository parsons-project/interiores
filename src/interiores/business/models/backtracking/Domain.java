
package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Orientation;
import interiores.business.models.backtracking.Area.Area;
import interiores.shared.backtracking.Value;
import interiores.utils.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
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
   
    private static final int RESOLUTION = 5;
    
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
    private Stage[] domain;
    
    
    public Domain(List<FurnitureModel> models, Dimension roomSize,
            int variableCount) {
        
        domain = new Stage[variableCount];
        
        domain[0] = new Stage(models, roomSize, RESOLUTION);
        
        for (int i = 1; i < variableCount; ++i) {
            domain[i] = new Stage(RESOLUTION);
        }
        
        
    }
    
    public static Domain empty() {
        return new Domain(new ArrayList(), new Dimension(0, 0), 0);
    }
    
    public void resetIterators(int iteration) {
        domain[iteration].resetIterators();
    }
    
    //Pre: we have not iterated through all domain values yet.
    public Value getNextDomainValue(int iteration) {   
        return domain[iteration].getNextDomainValue();
    }
    
    public boolean hasMoreValues(int iteration) {
        return domain[iteration].hasMoreValues();
    }

    

    

    
    public HashSet<FurnitureModel> getModels(int iteration) {
        return domain[iteration].getModels();
    }
    
    public Area getPositions(int iteration) {
        return domain[iteration].getPositions();
    }
    
    public HashSet<Orientation> getOrientations(int iteration) {
        return domain[iteration].getOrientations();
    }



    /**
     * Returns the size of the domain.
     * @param iteration
     * @return 
     */
    int domainSize(int iteration) {
        return domain[iteration].size();
    }

    int smallestModelSize(int iteration) {
        return domain[iteration].smallestModelSize();
    }

    void forwardIteration(int iteration) {
        domain[iteration].swapPositions(domain[iteration+1]);
        domain[iteration].swapModels(domain[iteration+1]);
        domain[iteration].swapOrientations(domain[iteration+1]);
    }
    
    
    void trimExceptP(Area validPositions, int iteration) {
        //modify domain[iteration+1]
        Area discardedPositions = domain[iteration+1].intersectionP(validPositions);
        //modify domain[iteration]
        domain[iteration].unionP(discardedPositions);
        
    }
    
    void trimExceptM(HashSet<FurnitureModel> validModels, int iteration) {
        //modify domain[iteration+1]
        HashSet<FurnitureModel> discardedModels = domain[iteration+1].intersection(validModels);
        //modify domain[iteration]
        domain[iteration].unionM(discardedModels);
    }
    
    void trim(Area invalidPositions, int iteration) {
        //modify domain[iteration+1]
        Area discardedPositions = domain[iteration+1].difference(invalidPositions);
        //modify domain[iteration]
        domain[iteration].unionP(discardedPositions);

    }
    
    
    void eliminateExceptM(HashSet<FurnitureModel> validModels) {
        domain[0].eliminateExceptM(validModels);
    }
    
    void eliminateExceptO(HashSet<Orientation> validOrientations) {
        domain[0].eliminateExceptO(validOrientations);
    }
    
    
    
    
    
    
    //DEPRECATED METHODS
    
    //deprecated methods: substituted by forwardIteration()
    public void saveAllPositions(int iteration) {
        domain[iteration].swapPositions(domain[iteration+1]);
    }
        
    public void saveAllModels(int iteration) {
        domain[iteration].swapModels(domain[iteration+1]);
    }

    public void saveAllOrientations(int iteration) {
        domain[iteration].swapOrientations(domain[iteration+1]);
    }

    

    
//    /**
//     * Moves the positions from the current iteration to the next iteration,
//     * and leaves the set of positions of the current iteration empty.
//     * @param iteration 
//     */
//    public void pushPositions(int iteration) {
//        domain[iteration+1].setPositions(domain[iteration].getPositions());
//        domain[iteration].setPositions(new ArrayList<Point>());
//    }



//    /**
//     * Pushes positions from the iteration i+1 to iteration i.
//     * @param invalidArea
//     * @param iteration 
//     */
//    public void trimAndPushInvalidRectangle(Rectangle invalidRectangle, int iteration) {
//        Collection<Point> trimedPositions = trimInvalidRectangle(invalidRectangle, iteration + 1);
//        
//        domain[iteration].addPositions(trimedPositions);
//    }
//    
//    public Collection<Point> trimInvalidRectangle(Rectangle invalidRectangle, int iteration) {
//        return domain[iteration].trimInvalidRectangle(invalidRectangle);
//    }
//    
//    /**
//     * merges stage iteration+1 into stage iteration.
//     */
//    void undoTrimDomain(int iteration) {
//
//        domain[iteration].merge(domain[iteration+1]);
//    }



    
}
