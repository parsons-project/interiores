package interiores.business.models.constraints;

import interiores.business.models.FurnitureModel;
import interiores.business.models.constraints.SizeConstraint;
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
public class SizeConstraintTest {
    
    FurnitureModel model = new FurnitureModel("Model",new Dimension(110,45), (float) 99.99,Color.BLACK,"wood");
    
    public SizeConstraintTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of isSatisfied method, of class SizeConstraint.
     */
    @Test
    public void testIsSatisfied1() {
        System.out.println("Testing: isSatisfied() for a model of size 110x45 against a min of 100x40 and a max of 110x45");
                
        Dimension min = new Dimension(100,40);
        Dimension max = new Dimension(110,45);
        SizeConstraint instance = new SizeConstraint(min,max);
        boolean expResult = true;
        boolean result = instance.isSatisfied(model);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of isSatisfied method, of class SizeConstraint.
     */
    @Test
    public void testIsSatisfied2() {
        System.out.println("Testing: isSatisfied() for a model of size 110x45 against a width between 110-130. Any height validates");
        
        Dimension min = new Dimension(110,0);
        Dimension max = new Dimension(130,0);
        SizeConstraint instance = new SizeConstraint(min,max);
        boolean expResult = true;
        boolean result = instance.isSatisfied(model);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of isSatisfied method, of class SizeConstraint.
     */
    @Test
    public void testIsSatisfied3() {
        System.out.println("Testing: isSatisfied() for a model of size 110x45 against a height between 20-40. Any width validates");
        
        Dimension min = new Dimension(0,20);
        Dimension max = new Dimension(0,40);
        SizeConstraint instance = new SizeConstraint(min,max);
        boolean expResult = false;
        boolean result = instance.isSatisfied(model);
        assertEquals(expResult, result);
    }
    
}