/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints;

import interiores.business.models.FurnitureModel;
import java.awt.Color;
import java.awt.Dimension;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author larribas
 */
public class ColorConstraintTest {
    
    FurnitureModel model = new FurnitureModel("Model",new Dimension(110,45), (float) 99.99,Color.BLACK,"wood");
    
    public ColorConstraintTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of isSatisfied method, of class ColorConstraint.
     */
    @Test
    public void testIsSatisfied1() {
        System.out.println("Testing: isSatisfied() for a model painted in Black against a color=Blue constraint.");
        
        ColorConstraint instance = new ColorConstraint(Color.BLUE);
        boolean expResult = false;
        boolean result = instance.isSatisfied(model);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of isSatisfied method, of class ColorConstraint.
     */
    @Test
    public void testIsSatisfied2() {
        System.out.println("Testing: isSatisfied() for a model painted in Black against a color=Black constraint.");
        
        ColorConstraint instance = new ColorConstraint(Color.BLACK);
        boolean expResult = true;
        boolean result = instance.isSatisfied(model);
        assertEquals(expResult, result);
    }

}