/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.utils;

import java.awt.Point;
import java.awt.geom.Area;
import java.util.Iterator;

/**
 *
 * @author larribas
 */
public class ExtendedArea implements Iterable<Point> {
    
    // This attribute may be a custom type instead of the awt.geom one
    private Area area;
    
    public ExtendedArea() {
        area = new Area();
    }
    
    public ExtendedArea(Area a) {
        area = a;
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
        
        public AreaIterator(ExtendedArea ea, int resolution) {
            a = ea.getArea();
            p = new Point(0,0);
        }

        @Override
        public boolean hasNext() {
            
//            int max_x = a.getBounds().x + a.getBounds().width;
//            int max_y = a.getBounds().y + a.getBounds().height;
//            
//            for (int i = p.x; i < max_x; i += RES)
//                for (int j = p.y; j < max_y; j += RES)
                    
            return false;
        }

        @Override
        public Point next() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }

    @Override
    public Iterator iterator() {
        return new AreaIterator(this, resolution);
    }
    
}
