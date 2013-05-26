package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.business.models.backtracking.Area.Area;
import interiores.core.Debug;
import interiores.shared.backtracking.Value;
import interiores.utils.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A Stage represents a subset of the domain with the values that were
 * available until a given iteration.
 * @author nil.mamano
 */
public class Stage {
    private int resolution;
    
    private HashSet<FurnitureModel> models;
    private Area positions;
    private HashSet<Orientation> orientations;

    
    // The following variables are used to iterate over the domain.
    //Iteration is done in this order: 1) Position, 2) Orientation, 3) Models
    private Iterator positionIterator;
    private Iterator orientationIterator;
    private Iterator modelIterator;
    
    private Point currentPosition;
    private Orientation currentOrientation;
    private FurnitureModel currentModel;
    
    private boolean firstValueIteration;
    
    public Stage(HashSet<FurnitureModel> models, Dimension roomSize,
            int resolution)
    {
        this.resolution = resolution;
        
        // initialize models
        this.models = models;
        
        // initialize positions
        positions = new Area(new Rectangle(0, 0, roomSize.width, roomSize.depth));

        
        //initialize orientations
        orientations = defaultOrientations();
  
        //initialize iterators
        currentPosition = null;
        currentOrientation = null;
        currentModel = null;
        
        positionIterator = positions.iterator();
        orientationIterator = orientations.iterator();
        modelIterator = this.models.iterator();
        
        resetIterators();
    }
    
    /**
     * Default constructor. Empty stage.
     */
    public Stage(int resolution) {
        this.resolution = resolution;
        
        models = new HashSet();
        positions = new Area();
        orientations = new HashSet();
        
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
            if (positionIterator.hasNext()) currentPosition = (Point) positionIterator.next();
            else throw new UnsupportedOperationException("Position has no next and we are asking for it (at orientation's IF)");
            currentOrientation = (Orientation) orientationIterator.next();
        }
        else if (modelIterator.hasNext()) {
            positionIterator = positions.iterator();
            if (positionIterator.hasNext()) currentPosition = (Point) positionIterator.next();
            else throw new UnsupportedOperationException("Position has no next and we are asking for it (at model's IF)");
            orientationIterator = orientations.iterator();
            if (orientationIterator.hasNext()) currentOrientation = (Orientation) orientationIterator.next();
            else throw new UnsupportedOperationException("Orientation has no next and we are asking for it (at model's IF)");
            currentModel = (FurnitureModel) modelIterator.next();
        }
        else {
            throw new UnsupportedOperationException("There are no more domain values");
        }
        
        //2) return the new current value
        return new FurnitureValue(currentPosition, currentModel, currentOrientation);
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
    private HashSet<Orientation> defaultOrientations() {
        HashSet<Orientation> allOrientations = new HashSet<Orientation>();
        allOrientations.add(Orientation.E);
        allOrientations.add(Orientation.W);
        allOrientations.add(Orientation.S);
        allOrientations.add(Orientation.N);
        return allOrientations;
    }

    HashSet<FurnitureModel> getModels() {
        return models;
    }
    
    Area getPositions() {
        return positions;
    }
    
    HashSet<Orientation> getOrientations() {
        return orientations;
    }
    
    void setModels(HashSet<FurnitureModel> models) {
        this.models = models;
    }
    
    void setPositions(Area positions) {
        this.positions = positions;
    }
        
    void setOrientations(HashSet<Orientation> orientations) {
        this.orientations = orientations;
    }

    int size() {
        int modelCount = models.size();
        int oriCount = orientations.size();
        int areaSize = positions.areaSize();
        return modelCount * oriCount * areaSize;
    }

    int smallestModelSize() {
        if (models.isEmpty()) return 0;

        FurnitureModel smallestModel = null;
        for (FurnitureModel model : models) {
            if (smallestModel == null ||
                    model.areaSize() < smallestModel.areaSize())
                smallestModel = model;
        }
        
        return smallestModel.areaSize();
     }

    
    
    
    void swapPositions(Stage stage) {
        Area aux = this.positions;
        this.positions = stage.positions;
        stage.positions = aux;
        
        Iterator it = positionIterator;
        positionIterator = stage.positionIterator;
        stage.positionIterator = it;
    }

    void swapModels(Stage stage) {
        HashSet<FurnitureModel> aux = this.models;
        this.models = stage.models;
        stage.models = aux;
        
        Iterator it = modelIterator;
        modelIterator = stage.modelIterator;
        stage.modelIterator = it;
    }

    void swapOrientations(Stage stage) {
        HashSet<Orientation> aux = this.orientations;
        this.orientations = stage.orientations;
        stage.orientations = aux;
        
        Iterator it = orientationIterator;
        orientationIterator = stage.orientationIterator;
        stage.orientationIterator = it;
    }
    
    
    //SET OPERATION: THE DEFINITIVE ONES
    
    
    /**
     * Makes the intersection of positions and returns the positions not
     * contained in the intersection.
     * @param validPositions
     * @return 
     */
    Area intersectionP(Area area) {
        Area startingPositions = new Area(positions);
        positions.intersection(area);
        startingPositions.difference(positions);
        return startingPositions;
    }

    /**
     * Makes the intersection of models and returns the models not
     * contained in the intersection.
     * @param validPositions
     * @return 
     */
    HashSet<FurnitureModel> intersectionM(HashSet<FurnitureModel> validModels) {
        HashSet<FurnitureModel> notContainedModels = new HashSet<FurnitureModel>();
        for (FurnitureModel model : models) {
            if (! validModels.contains(model)) {
                notContainedModels.add(model);
                models.remove(model);
            }
        }
        return notContainedModels;
    }
    
    HashSet<Orientation> intersectionO(HashSet<Orientation> validOrientations) {
        HashSet<Orientation> notContainedOrientations = new HashSet<Orientation>();
        for (Orientation orientation : orientations) {
            if (! validOrientations.contains(orientation)) {
                notContainedOrientations.add(orientation);
                orientations.remove(orientation);
            }
        }
        return notContainedOrientations;
    }
        
    void unionP(Area area) {
        positions.union(area);
    }
    
    void unionM(HashSet<FurnitureModel> newModels) {
        //we know that there aren't repeated models because each model
        //is found only one in each domain
        models.addAll(newModels);
    }
    
    void unionO(HashSet<Orientation> newOrientations) {
        //we know that there aren't repeated orienttations because each
        //orientation is found only one in each domain
        orientations.addAll(newOrientations);        
    }
    
    void union(Stage stage) {
        unionP(stage.positions);
        unionM(stage.models);
        unionO(stage.orientations);
    }

    Area difference(Area area) {
        Area startingPositions = new Area(positions);
        positions.difference(area);
        startingPositions.difference(positions);
        return startingPositions;
    }
    
    
    
    
    
    
    //DEPRECATED METHODS
    
    

//    
//    void addPositions(Area newPositions) {
//        positions.unionM(newPositions);
//        resetIterators();
//    }
//    
//        void merge(Stage stage) {
//        
//        //A) process positions        
//        // 1) check if swap is beneficial
//        boolean shouldSwapPositions = false;
//        
//        // 2) swap
//        if (shouldSwapPositions) swapPositions(stage);
//        
//        // 3) merge
//        positions.unionM(stage.positions);
//        stage.positions = new Area();
//        
//        //B) process models
//        // 1) check if swap is beneficial
//        boolean shouldSwapModels = models.size() < stage.models.size();
//        
//        // 2) swap
//        if (shouldSwapModels) swapModels(stage);
//        
//        // 3) merge
//        models.addAll(stage.models);
//        stage.models.clear();      
//        
//        //B) process orientations   
//        // 1) merge
//        orientations.addAll(stage.orientations);
//        stage.orientations.clear();
//    }

    void eliminateExceptM(HashSet<FurnitureModel> validModels) {
        Iterator<FurnitureModel> it = models.iterator();
        while(it.hasNext())
            if (! validModels.contains(it.next()))
                it.remove();
    }

    void eliminateExceptO(HashSet<Orientation> validOrientations) {
        Iterator<Orientation> it = orientations.iterator();
        while(it.hasNext())
            if (! validOrientations.contains(it.next()))
                it.remove();
    }



}
