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
    
    public ExtendedArea(Area a, FurnitureModel m) {
        area = a;
        // applyIntersections(...) reduces the area of valid
        // positions and saves the result in 'area'
        applyIntersections(m.getSize().width,m.getSize().depth);
        
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
        private static final int RES = 1;
        
        public AreaIterator(ExtendedArea ea) {
            a = ea.getArea();
            p = new Point(0,0);
        }

        @Override
        public boolean hasNext() {
            
            int max_x = a.getBounds().x + a.getBounds().width;
            int max_y = a.getBounds().y + a.getBounds().height;
            
            for (int i = p.x; i < max_x; i += RES)
                for (int j = p.y; j < max_y; j += RES) {
                    if ( area.contains(i, j) ) return true;
                    
                }
                    
            return false;
        }

        @Override
        public Point next() {
            
            int max_x = a.getBounds().x + a.getBounds().width;
            int max_y = a.getBounds().y + a.getBounds().height;
            
            for (int i = p.x; i < max_x; i += RES)
                for (int j = p.y; j < max_y; j += RES) {
                    if ( area.contains(i, j) ) return new Point(i,j);
                    else {
                        Collections.binarySearch(i, j, j);
                    }
                    
                }
            
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}
