/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.utils;

import interiores.business.models.FurnitureModel;
import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
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
        
        FurnitureModel furn = new FurnitureModel("Yo", new Dimension(10, 10), 33, Color.yellow,"Yo");
        
        ExtendedArea instance = new ExtendedArea(area,furn);
        
//        UNCOMMENT THESE LINES TO PRINT THE AREA AFTER ITS TRANSFORMATION        
//        for (int i = 0; i < 110; i++) {
//            for (int j = 0; j < 120; j++) {
//                String mark = (area.contains(j ,i))? "X" : "_";
//                System.out.print(mark);
//            }
//            System.out.print("\n");
//        }
        
        Area comp = new Area();
        Iterator<Point> it = instance.iterator();
        while (it.hasNext()) {
            Point q = it.next();
            
            System.out.println("Next point: (" + q.x + "," + q.y + ");");
        }
        
    }
}