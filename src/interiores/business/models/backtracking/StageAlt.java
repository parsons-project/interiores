/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Orientation;
import interiores.shared.backtracking.Value;
import interiores.utils.Dimension;
import interiores.utils.ExtendedArea;
import java.awt.Point;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
    
    
    public StageAlt() {}
    
    public StageAlt(List<FurnitureModel> initial_models, Area current_room) {
        
        room = current_room;
        orientations = defaultOrientations();
        models = initial_models;
        
        // Sorting of models and orientations is performed in order to
        // minimize the number of times we have to build ExtendedAreas
        sort_orientations();
        sort_models();
        initialize_iterators();
    }
        
    public Value getNextDomainValue() {
        
        if (pos_it.hasNext()) current_pos = pos_it.next();            
        else {
            boolean hasSameSize = true;
            if (or_it.hasNext());
            else {
                if (mod_it.hasNext()) {
                    Dimension prev_d = current_mod.getSize();
                    current_mod = mod_it.next();
                    if (prev_d.equals(current_mod.getSize()))
                        Collections.sort(orientations, Collections.reverseOrder(new oComparator()));
                    else hasSameSize = false;
                   
                    or_it = orientations.iterator();
                }
                else throw new UnsupportedOperationException("There are no more domain values");
            }
            int prev_o = current_or.ordinal();
            current_or = or_it.next();
            
            if (hasSameSize && prev_o + current_or.ordinal() % 2 == 0) rebuild_positions(); // build new Extended Area
            pos_it = positions.iterator();
            return getNextDomainValue();
        }
        
        return new FurnitureValue(current_pos, current_mod, current_or);
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
        or_it = orientations.iterator();
        
        if (mod_it.hasNext() && or_it.hasNext()) {
            current_mod = mod_it.next();
            current_or = or_it.next();
            rebuild_positions();
            pos_it = positions.iterator();
        }        
    }

    private Dimension rotateModel(FurnitureModel m) {
        return new Dimension(m.getSize().depth,m.getSize().width);
    }

    private void rebuild_positions() {
        Dimension d = (current_or.ordinal() % 2 == 0)? current_mod.getSize() : rotateModel(current_mod);
        positions = new ExtendedArea(new Area(room),d);
    }
    
    private void sort_orientations() {
        Collections.sort(orientations, new oComparator());
    }
    
    private void sort_models() {
        Collections.sort(models, new mComparator());
    }
    
    private static class oComparator implements Comparator<Orientation> {

        @Override
        public int compare(Orientation o1, Orientation o2) {
            return (o1.ordinal() % 2 == 0)? 1 : (o2.ordinal() % 2 != 0)? 0 : -1;
        }
    }
        
    private static class mComparator implements Comparator<FurnitureModel> {

        @Override
        public int compare(FurnitureModel m1, FurnitureModel m2) {
            Dimension d1 = m1.getSize(); Dimension d2 = m2.getSize();
            
            if ( d1.width < d2.width ) return -1;
            else if ( d1.width > d2.width ) return 1;
            else if ( d1.depth < d2.depth ) return -1;
            else if ( d1.depth > d2.depth ) return 1;
            else return 0;
        }
    }

    
}
