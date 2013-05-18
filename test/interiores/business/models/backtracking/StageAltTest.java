/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.backtracking;

import interiores.shared.backtracking.Value;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author larribas
 */
public class StageAltTest {
    
    public StageAltTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getNextDomainValue method, of class StageAlt.
     */
    @Test
    public void testGetNextDomainValue() {
        System.out.println("Testing getNextDomainValue() .........");
        
        // Here we have a room with a complex topography
        int xPoly[] = {0,20,20,10,10,30,30,40,40,50,50,60,60,120,120,100,100,90,90,70,70,15,15,50,50,30,30,0};
        int yPoly[] = {60,60,70,70,90,90,75,75,100,100,75,75,110,110,60,60,30,30,15,15,0,0,20,20,50,50,40,40};
        Area room = new Area(new Polygon(xPoly, yPoly, xPoly.length));
        room.subtract(new Area(new Rectangle(70, 70, 30, 20)));
        room.subtract(new Area(new Rectangle(80, 50, 5, 40)));
        
        StageAlt instance = new StageAlt();
        Value expResult = null;
        Value result = instance.getNextDomainValue();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasMoreValues method, of class StageAlt.
     */
    @Test
    public void testHasMoreValues() {
        System.out.println("hasMoreValues");
        StageAlt instance = new StageAlt();
        boolean expResult = false;
        boolean result = instance.hasMoreValues();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}