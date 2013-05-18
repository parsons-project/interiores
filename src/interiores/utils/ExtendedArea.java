/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.utils;

import interiores.business.models.FurnitureModel;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author larribas
 */
public class ExtendedArea implements Iterable<Point> {
    
    // This attribute may be a custom type instead of the awt.geom one
    private Area area;
    
    // Represents the vertical segments in 'area', such that there are valid points to their right
    private List<int[]> vsegments;
    
    public ExtendedArea() {
        area = new Area();
    }
    
    public ExtendedArea(Area a, Dimension d) {
        area = a;
        // applyIntersections(...) reduces the area of valid
        // positions and saves the result in 'area'
        applyIntersections(d.width,d.depth);
        
        // computeVerticalSegments() puts in 'vsegments' a list
        // containing representations of all the vertical segments needed
        computeVerticalSegments();        
    }
    
    public Area getArea() {
        return area;
    }
    
    
    // ITERATOR
    private class AreaIterator implements Iterator<Point>
    {
        private Area a;
        private Point p;
        
        private int min_x, max_x;
        private int min_y, max_y;
        
        private static final int RES = 1;
        
        public AreaIterator(ExtendedArea ea) {
            a = ea.getArea();
            
            min_x = a.getBounds().x; max_x = min_x + a.getBounds().width;
            min_y = a.getBounds().y; max_y = min_y + a.getBounds().height;
            
            p = new Point(min_x - RES,min_y);
            advance_to_next();
        }

        @Override
        public boolean hasNext() {
            return area.contains(p);
        }
    
        @Override
        public Point next() {
            
            if (area.contains(p)) {
                Point ret = (Point) p.clone();
                advance_to_next();
                return ret;
            }
            else
                throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Removal of a particular element in the domain is not supported");
        }
        
        private void advance_to_next() {
            // First, we advance towards the next position
            p.x += RES;
            
            while (p.y <= max_y) {
                while (p.x <= max_x) {
                    if ( area.contains(p) ) return;
                    else if ( area.contains(p.x+=RES,p.y) ) return;
                    else {
                        int next_x = bin_search(vsegments, p.x, p.y);
                        if (next_x > 0) p.x = next_x;
                        else p.x = max_x + 1;
                    }
                }
                p.y += RES;
                p.x = min_x;
            } 
        }
        
    }

    @Override
    public Iterator iterator() {
        return new AreaIterator(this);
    }
    
    private void applyIntersections(int h_offset, int v_offset) {
        // We create the translation scheme (both vertical and horizontal)
        // and intersect the area with the two areas resulting from applying
        // the current one the vertical and horizontal translations
        AffineTransform horizontal = new AffineTransform();
	horizontal.translate(-h_offset,0);
        area.intersect( area.createTransformedArea(horizontal) );
        
        AffineTransform vertical = new AffineTransform();
	vertical.translate(0,-v_offset);
        area.intersect( area.createTransformedArea(vertical) );
    }
    
    private void computeVerticalSegments() {
        
        vsegments = new ArrayList<int[]>();
        
        double[] current = new double[2];
        double[] start = new double[2];
        double[] previous = new double[2];
        for (PathIterator pi = area.getPathIterator(null); !pi.isDone(); pi.next()) {
            
            int type = pi.currentSegment(current);
            if (type == PathIterator.SEG_MOVETO) {
                start[0] = current[0];
                start[1] = current[1];
            }
            else {
                double[] coords = (type == PathIterator.SEG_LINETO)? previous : start;
                if (coords[0] == current[0] &&
                    area.contains(coords[0]+1,(current[1] + coords[1])/2))
                {
                    int min_y = (int) Math.min(coords[1],current[1]);
                    int max_y = (int) Math.max(coords[1],current[1]);
                    vsegments.add(new int[]{(int) coords[0], min_y, max_y});
                }
            }
            previous[0] = current[0];
            previous[1] = current[1];
        }
        
        Collections.sort(vsegments, new xComparator());
    }
    
    private static class xComparator implements Comparator<int[]> {

        @Override
        public int compare(int[] o1, int[] o2) {
            return (o1[0] > o2[0])? 1 : (o1[0] == o2[0])? 0 : -1;
        }
    }
    
    // TODO think if the number of points deserves this search to become an actual binary search
    private static int bin_search(List<int[]> l, int x, int y) {
        for (int[] i : l)
            if (i[0] > x && i[1] <= y && y <= i[2])
                return i[0];
        
        return -1;
    }
    
}
