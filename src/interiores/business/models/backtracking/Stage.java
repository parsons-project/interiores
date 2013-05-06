
package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.shared.backtracking.Value;
import interiores.utils.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * A Stage represents a subset of the domain with the values that were
 * available until a given iteration.
 * @author nil.mamano
 */
public class Stage {

    private List<FurnitureModel> models;
    private HashSet<Point> positions;
    private List<Orientation> orientations;

    
    // The following variables are used to iterate over the domain.
    //Iteration is done in this order: 1) Position, 2) Orientation, 3) Models
    private Iterator positionIterator;
    private Iterator orientationIterator;
    private Iterator modelIterator;
    
    private Point currentPosition;
    private Orientation currentOrientation;
    private FurnitureModel currentModel;
    
    private boolean firstValueIteration;
    
    public Stage(List<FurnitureModel> models, Dimension roomSize,
            int resolution) {
        
        // initialize models
        this.models = models;
        
        // initialize positions
        positions = new HashSet<Point>();
        for (int i = 0; i < roomSize.width; i += resolution) {
            for (int j = 0; j < roomSize.depth; j += resolution)
                positions.add(new Point(i,j));
        }
        
        //initialize orientations
        orientations = defaultOrientations();
  
        //initialize iterators
        currentPosition = null;
        currentOrientation = null;
        currentModel = null;
        
        positionIterator = positions.iterator();
        orientationIterator = orientations.iterator();
        modelIterator = models.iterator();
        
    }
    
    
    //Pre: we have not iterated through all domain values yet.
    public Value getNextDomainValue() {
        
        //1) iterate
        if (firstValueIteration) {
            firstValueIteration = false;
        }
        else if (positionIterator.hasNext()) {
            currentPosition = (Point) positionIterator.next();
        }
        else if (orientationIterator.hasNext()) {
            positionIterator = positions.iterator();
            currentPosition = (Point) positionIterator.next();
            currentOrientation = (Orientation) orientationIterator.next();
        }
        else if (modelIterator.hasNext()) {
            positionIterator = positions.iterator();
            currentPosition = (Point) positionIterator.next();
            orientationIterator = orientations.iterator();
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
    
    
    public boolean hasMoreValues() {
        if(models.isEmpty() || positions.isEmpty() || orientations.isEmpty())
                return false;
        
        return modelIterator.hasNext() || positionIterator.hasNext() ||
                orientationIterator.hasNext();
    }    
   

    void resetIterators() {      
        positionIterator = positions.iterator();
        orientationIterator = orientations.iterator();
        modelIterator = models.iterator();
        
        if(positionIterator.hasNext() && modelIterator.hasNext() && orientationIterator.hasNext()) {
            currentPosition = (Point) positionIterator.next();
            currentModel = (FurnitureModel) modelIterator.next();
            currentOrientation = (Orientation) orientationIterator.next();
        }
        
        firstValueIteration = true;
    }  

    
    /**
     * Returns a list with all orientations.
     */
    private ArrayList<Orientation> defaultOrientations() {
        ArrayList<Orientation> allOrientations = new ArrayList();
        allOrientations.add(Orientation.N);
        allOrientations.add(Orientation.E);
        allOrientations.add(Orientation.S);
        allOrientations.add(Orientation.W);
        return allOrientations;
    }

    List<FurnitureModel> getModels() {
        return models;
    }
    
    HashSet<Point> getPositions() {
        return positions;
    }
    
    List<Orientation> getOrientations() {
        return orientations;
    }
    
    void setModels(List<FurnitureModel> models) {
        this.models = models;
    }
    
    void setPositions(HashSet<Point> positions) {
        this.positions = positions;
    }
        
    void setOrientations(List<Orientation> orientations) {
        this.orientations = orientations;
    }

    Collection<Point> trimInvalidRectangle(OrientedRectangle invalidRectangle) {
        Collection<Point> trimedPositions = new ArrayList();
        int x = invalidRectangle.x;
        int y = invalidRectangle.y;
        int x_max = x+invalidRectangle.width;
        int y_max = y+invalidRectangle.height;
        for (int i = x; i < x_max; ++i) {
            for (int j = y; j < y_max; ++j) {
                Point p = new Point(i,j);
                if (positions.contains(p)) {
                    trimedPositions.add(p);
                    positions.remove(p);
                }
            }
        }
        return trimedPositions;
    }
    
    void addPositions(Collection<Point> newPositions) {
        positions.addAll(newPositions);
    }
    
    
}
