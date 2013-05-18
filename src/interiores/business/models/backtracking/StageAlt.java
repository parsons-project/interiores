/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Orientation;
import interiores.shared.backtracking.Value;
import interiores.utils.ExtendedArea;
import java.awt.Point;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author larribas
 */
public class StageAlt {
    
    // The current state of the room
    private Area room;
    
    private ExtendedArea positions; // The set of valid reference points. Changes with models and orientations
    private Iterator<Point> pos_it; // Iterator through the valid reference points
    
    private List<Orientation> orientations; // The set of valid orientations
    private Iterator<Orientation> or_it; // Iterator through the valid orientations
    
    private List<FurnitureModel> models; // The set of valid models
    private Iterator<FurnitureModel> mod_it; // Iterator through the valid models
    
    
    public StageAlt() {
        room = new Area();
        positions = new ExtendedArea();
        orientations = new ArrayList();
        models = new ArrayList();        
    }
    
    public StageAlt(List<FurnitureModel> initial_models, Area current_room) {
        
        room = current_room;
        orientations = defaultOrientations();
        models = initial_models;
        
        initialize_iterators();
    }
    
    public Value getNextDomainValue() {
        
        return null;
    }
    
    
    
    
    
    
    
    // PRIVATE UTILS
    private List<Orientation> defaultOrientations() {
        return new ArrayList(Arrays.asList(Orientation.values()));
    }

    private void initialize_iterators() {
        
        or_it = orientations.iterator();
        mod_it = models.iterator();
        
        // If the initial list of orientations and models is not empty
        if (or_it.hasNext() && mod_it.hasNext())
            positions = new ExtendedArea(room,mod_it.next());
        else
            positions = new ExtendedArea();
        
        
        
    }

    
}
