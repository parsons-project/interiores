/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.shared.backtracking.Value;
import interiores.utils.Dimension;
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
    private Point current_pos;
    
    private List<Orientation> orientations; // The set of valid orientations
    private Iterator<Orientation> or_it; // Iterator through the valid orientations
    private Orientation current_or;
    
    private List<FurnitureModel> models; // The set of valid models
    private Iterator<FurnitureModel> mod_it; // Iterator through the valid models
    private FurnitureModel current_mod;
    
    
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
                
        if (pos_it.hasNext()) current_pos = pos_it.next();            
        else {
            if (or_it.hasNext()) current_or = or_it.next();
            else {
                if (mod_it.hasNext()) {
                    current_mod = mod_it.next();
                    or_it = orientations.iterator();
                    current_or = or_it.next(); 
                }
                else throw new UnsupportedOperationException("There are no more domain values");
            }
            rebuild_positions(); // build new Extended Area
            pos_it = positions.iterator();
            return getNextDomainValue();
        }        
        
        // Now, we create the next furniture value
        // TODO refactor the way Oriented rectangles and Furniture Values behave, it seems a bit akward
        OrientedRectangle rect = new OrientedRectangle(current_pos,
            current_mod.getSize(), Orientation.S);
        rect.setOrientation(current_or);
        
        return new FurnitureValue(rect, current_mod);
    }
    
    public boolean hasMoreValues() {
        if( models.isEmpty() || orientations.isEmpty() ) return false;
        return mod_it.hasNext() || or_it.hasNext() || pos_it.hasNext();
    }
    
    
    // UTILS
    private List<Orientation> defaultOrientations() {
        return new ArrayList(Arrays.asList(Orientation.values()));
    }

    private void initialize_iterators() {
        // In order to provide initialization right from the start, we initialize
        // mod_it to the models list, and set (pos_it,or_it) to fictive values,
        // so that the next .hasNext() returns false for both of them.
        // This way, getNextDomainValue() can rectify on its own
        mod_it = models.iterator();
        
        List l = new ArrayList();
        or_it = l.iterator();
        pos_it = l.iterator();
    }

    private Dimension rotateModel(FurnitureModel m) {
        return new Dimension(m.getSize().depth,m.getSize().width);
    }

    private void rebuild_positions() {
        Dimension d = (current_or.ordinal() % 2 == 0)? current_mod.getSize() : rotateModel(current_mod);
        positions = new ExtendedArea(room,d);
    }

    
}
