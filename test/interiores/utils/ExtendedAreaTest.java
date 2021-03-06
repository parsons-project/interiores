/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.utils;


import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.Iterator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author larribas
 */
public class ExtendedAreaTest {
    
    public ExtendedAreaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of iterator method, of class ExtendedArea.
     */
    @Test
    public void testIterator() {
        System.out.println("Testing the iterator interface.........");
        
        int xPoly[] = {0,20,20,10,10,30,30,40,40,50,50,60,60,120,120,100,100,90,90,70,70,15,15,50,50,30,30,0};
        int yPoly[] = {60,60,70,70,90,90,75,75,100,100,75,75,110,110,60,60,30,30,15,15,0,0,20,20,50,50,40,40};
        Area area = new Area(new Polygon(xPoly, yPoly, xPoly.length));
        area.subtract(new Area(new Rectangle(70, 70, 30, 20)));
        area.subtract(new Area(new Rectangle(80, 50, 5, 40)));

//       UNCOMMENT THESE LINES TO PRINT THE AREA PRIOR TO ITS TRANSFORMATION       
//        for (int i = 0; i < 110; i++) {
//            for (int j = 0; j < 120; j++) {
//                String mark = (area.contains(j ,i))? "X" : "_";
//                System.out.print(mark);
//            }
//            System.out.print("\n");
//        }
//        
//        System.out.print("\n\n\n\n\n\n\n");
        
        Dimension dim = new Dimension(10, 10);
        
        long startTime = System.nanoTime();
        ExtendedArea instance = new ExtendedArea(area,dim);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Time elapsed creating a complex extended area " + duration + " nanoseconds.");
        
//        UNCOMMENT THESE LINES TO PRINT THE AREA AFTER ITS TRANSFORMATION        
//        for (int i = 0; i < 110; i++) {
//            for (int j = 0; j < 120; j++) {
//                String mark = (area.contains(j ,i))? "X" : "_";
//                System.out.print(mark);
//            }
//            System.out.print("\n");
//        }
        
        Iterator<Point> it = instance.iterator();
        while (it.hasNext()) {
            Point q = it.next();
            
            //System.out.println("Next point: (" + q.x + "," + q.y + ");");
        }
        
    }
    
    /**
     * Test of iterator method, of class ExtendedArea.
     */
    @Test
    public void testIterator2() {
        System.out.println("Testing the iterator interface.........");
        
        Area area = new Area(new Rectangle(0, 0, 1000, 1000));
        System.out.println("area contains (1000,0)? : " + area.contains(1000, 0));

        Dimension dim = new Dimension(10, 10);
        
        long startTime = System.nanoTime();
        ExtendedArea instance = new ExtendedArea(area,dim);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Time elapsed creating a simple yet large extended area " + duration + " nanoseconds.");
        
        
        Iterator<Point> it = instance.iterator();
        while (it.hasNext()) {
            Point q = it.next();
            
            //System.out.println("Next point: (" + q.x + "," + q.y + ");");
        }
        
    }
}