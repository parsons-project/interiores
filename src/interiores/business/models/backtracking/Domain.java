
package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.shared.backtracking.Value;
import interiores.shared.backtracking.Variable;
import interiores.utils.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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
     * A Stage represents a subset of the domain with the values that were
     * available until a given iteration.
     */
    private class Stage {
        public List<FurnitureModel> models;
        public HashSet<Point> positions;
        public List<Orientation> orientations;
    }
    
    
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
    
    
    // The following variables are used to iterate over the domain.
    //Iteration is done in this order: 1) Position, 2) Orientation, 3) Models
    private Iterator positionIterator;
    private Iterator orientationIterator;
    private Iterator modelIterator;
    
    private Point currentPosition;
    private Orientation currentOrientation;
    private FurnitureModel currentModel;
    
    private boolean firstValueIteration;
    
    
    public Domain(List<FurnitureModel> models, Dimension roomSize,
            int variableCount, int resolution) {
        
        domain = new Stage[variableCount];
        
        // initialize models
        domain[0].models = models;
        
        // initialize positions
        domain[0].positions = new HashSet<Point>();
        for (int i = 0; i < roomSize.depth; i += resolution) {
            for (int j = 0; j < roomSize.width; j += resolution)
                domain[0].positions.add(new Point(i,j));
        }
        
        //initialize orientations
        domain[0].orientations = defaultOrientations();
  
        //initialize iterators
        currentPosition = null;
        currentOrientation = null;
        currentModel = null;
        
        positionIterator = domain[0].positions.iterator();
        orientationIterator = domain[0].orientations.iterator();
        modelIterator = domain[0].models.iterator();
        
    }
    
    
     /**
     * Returns a list with all orientations.
     */
    private ArrayList<Orientation> defaultOrientations() {
        ArrayList<Orientation> orientations = new ArrayList();
        orientations.add(Orientation.N);
        orientations.add(Orientation.E);
        orientations.add(Orientation.S);
        orientations.add(Orientation.W);
        return orientations;
    }
    
    //Pre: we have not iterated through all domain values yet.
    public Value getNextDomainValue(int iteration) {
        
        //1) iterate
        if (firstValueIteration) {
            firstValueIteration = false;
        }
        else if (positionIterator.hasNext()) {
            currentPosition = (Point) positionIterator.next();
        }
        else if (orientationIterator.hasNext()) {
            positionIterator = domain[iteration].positions.iterator();
            currentPosition = (Point) positionIterator.next();
            currentOrientation = (Orientation) orientationIterator.next();
        }
        else if (modelIterator.hasNext()) {
            positionIterator = domain[iteration].positions.iterator();
            currentPosition = (Point) positionIterator.next();
            orientationIterator = domain[iteration].orientations.iterator();
            currentOrientation = (Orientation) orientationIterator.next();
            currentModel = (FurnitureModel) modelIterator.next();
        }
        else {
            throw new UnsupportedOperationException("There are no more domain values");
        }
        
        //2) return the new current value
        OrientedRectangle area = new OrientedRectangle(currentPosition,
            currentModel.getSize(), Orientation.S);
        area.setOrientation(currentOrientation);
        
        return new FurnitureValue(area, currentModel);
    }
    
    
    
    public boolean hasMoreValues(int iteration) {
        if(domain[iteration].models.isEmpty() ||
            domain[iteration].positions.isEmpty() ||
            domain[iteration].orientations.isEmpty())
                return false;
        
        return modelIterator.hasNext() || positionIterator.hasNext() ||
                orientationIterator.hasNext();
    }
    
    public void pushPositions(int iteration) {
        domain[iteration+1].positions = domain[iteration].positions;
        domain[iteration].positions = new HashSet<Point>();
    }
    
    /**
     * Pushes positions from the iteration i+1 to iteration i.
     * @param invalidArea
     * @param iteration 
     */
    public void trimInvalidRectangle(OrientedRectangle invalidRectangle, int iteration) {
        int x = invalidRectangle.x;
        int y = invalidRectangle.y;
        int x_max = x+invalidRectangle.width;
        int y_max = y+invalidRectangle.height;
        for (int i = x; i < x_max; ++i) {
            for (int j = y; j < y_max; ++j) {
                Point p = new Point(i,j);
                if (domain[iteration+1].positions.contains(p)) {
                    domain[iteration].positions.add(p);
                    domain[iteration+1].positions.remove(p);
                }
            }
        }
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
        
        positionIterator = domain[iteration].positions.iterator();
        orientationIterator = domain[iteration].orientations.iterator();
        modelIterator = domain[iteration].models.iterator();
        
        // 
        if(positionIterator.hasNext() && modelIterator.hasNext() && orientationIterator.hasNext()) {
            currentPosition = (Point) positionIterator.next();
            currentModel = (FurnitureModel) modelIterator.next();
            currentOrientation = (Orientation) orientationIterator.next();
        }
        
        firstValueIteration = true;
    }
    
    
    
    
}
