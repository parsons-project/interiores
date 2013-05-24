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
 * Represents the stage of the domain at a determined stage within the algorithm.
 * A domain is composed by:
 *  - An area representing the valid positions
 *  - A list of valid orientations
 *  - A list of valid furniture models
 * 
 * Each of these elements is iterable on its own. That is, it must implement java's
 * iterator interface.
 * 
 * With these properties in mind, StageAlt implements its own operations to iterate
 * through an ordered collection of valid domain values.
 * @author larribas
 */
public class StageAlt {
    
    // 'room' provides a reference to the current state of the room (at this stage of the algorithm)
    // It is used as a basic area upon which we can build subsequent 'valid-position areas'.
    // [ACTUALLY] This value is not intended to represent just the current state of the room,
    // but also eliminate any invalid positions marked for the variable due to constraints of any type.
    // For instance, if the variable had a position constraint saying it can only be positioned within
    // a rectangle R, 'room' should represent the intersection between:
    //      - The current state of the room
    //      - The rectangle R
    //      - Perhaps other trimmed positions apart from the aforementioned
    private Area room;
    
    private ExtendedArea positions; // The set of valid reference points
    private Iterator<Point> pos_it; // Iterator through the valid reference points
    private Point current_pos; // The current position in the iteration process
    
    private List<Orientation> orientations; // The set of valid orientations
    private Iterator<Orientation> or_it; // Iterator through the valid orientations
    private Orientation current_or; // The curent orientation
    
    private List<FurnitureModel> models; // The set of valid models
    private Iterator<FurnitureModel> mod_it; // Iterator through the valid models
    private FurnitureModel current_mod; // The current model under consideration
    
    /**
     * Empty constructor so that this class does not raise persistency issues (DO NOT USE)
     */
    public StageAlt() {}
    
    
    /**
     * Builds a StageAlt instance for a room R, with a determined list of models and the 4 default orientations
     * @param valid_pos The valid positions known a priori for this variable
     * @param valid_mod A list containing the valid models for this variable at this stage of the algorithm
     * @param valid_or A list containing the valid orientations for this variable at this stage of the algorithm
     */
    public StageAlt(Area valid_pos, List<FurnitureModel> valid_mod, List<Orientation> valid_or) {
        
        room = valid_pos;
        orientations = valid_or;
        models = valid_mod;
        
        // Sorting of models and orientations is performed in order to
        // minimize the number of times we have to build ExtendedAreas
        sort_orientations();
        sort_models();
        initialize_iterators();
    }
    
    /**
     * Returns a Furniture Value with the next value of the domain of this variable
     * @return Furniture Value with the next value of the domain of this variable
     */
    public Value getNextDomainValue() {
        
        // If there is a next position, we simply advance to it and return
        if (pos_it.hasNext()) current_pos = pos_it.next();            
        else {
            // If, however, there are not more positions, we have to change the orientation
            // or the model under cosideration. Thus, we have two alternatives:
            //  1) The next model-orientation combination has the same dimensions as the previous,
            //     And therefore we don't need to build a new Area
            //  2) The dimensions do change, and therefore we need to build a new Area
            boolean hasSameSize = true;
            
            // This piece of code is difficult to comment. Say, it has undercome a number of optimizations
            // in order to minimize the number of lines. I'll try, though:
            //     A) If there is a new orientation, we store the previous orientation and iterate to the next one
            //        If it has the same size (which is true if we haven't changed the model), and the orientation
            //        doesn't make a difference (it doesn't if we switch from N to S, or from W to E; it does otherwise)
            //        we don't need to rebuild the area, and can reset the positions iterator and call this very
            //        function recursively (this allows us to reuse the position iteration code).
            //        In any other case, we need to rebuild the position and continue.
            //     B) If it is the model that need to change, we get the next model and see if it has the same size as
            //        the previous one. This allows us to know if we have to rebuild the positions.
            //        Also, we reset the orientation iterator and continue.
            //
            //    Note: This code is tested and works correctly. I would recommend touching it the least when possible
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
    
    /**
     * Finds out whether there are more values left in the domain of the variable at this stage of the algorithm
     * @return 'true' if there are more positions. 'false' otherwise
     */
    public boolean hasMoreValues() {
        if( models.isEmpty() || orientations.isEmpty() ) return false;
        return mod_it.hasNext() || or_it.hasNext() || pos_it.hasNext();
    }
    
    
    /**
     * Initializes all the iterators. It should be called from the constructor, when building StageAlt
     */
    private void initialize_iterators() {
        mod_it = models.iterator();
        or_it = orientations.iterator();
        
        if (mod_it.hasNext() && or_it.hasNext()) {
            current_mod = mod_it.next();
            current_or = or_it.next();
            rebuild_positions();
            pos_it = positions.iterator();
        }        
    }

    /**
     * Returns the dimension of the model if we rotate it from its starting orientation.
     * It is intended to give the valid dimension of a model when the orientation is W or E
     * @param m The model we want get rotated
     * @return A dimension representing the one of the model when rotated to a W or E orientation
     */
    private Dimension rotateModel(FurnitureModel m) {
        return new Dimension(m.getSize().depth,m.getSize().width);
    }

    /**
     * Takes into account the current model and orientation, and builds a new area
     * representing the valid positions
     */
    private void rebuild_positions() {
        Dimension d = (current_or.ordinal() % 2 == 0)? current_mod.getSize() : rotateModel(current_mod);
        positions = new ExtendedArea(new Area(room),d);
    }
    
    /**
     * Sorts the orientations in a certain order such that when iterating them we have to perform
     * the minimum amount of changes. For example, N,S,W,E, so that the dimension of the furniture
     * rotates once (between S and W) instead of 3.
     */
    private void sort_orientations() {
        Collections.sort(orientations, new oComparator());
    }
    
    /**
     * Sorts the models in ascending order of sizes (first width, then depth)
     */
    private void sort_models() {
        Collections.sort(models, new mComparator());
    }
    
    /**
     * Comparator of orientations that gives the right order
     */
    private static class oComparator implements Comparator<Orientation> {

        @Override
        public int compare(Orientation o1, Orientation o2) {
            return (o1.ordinal() % 2 == 0)? 1 : (o2.ordinal() % 2 != 0)? 0 : -1;
        }
    }
        
    /**
     * Comparator of models in ascending order
     */
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
