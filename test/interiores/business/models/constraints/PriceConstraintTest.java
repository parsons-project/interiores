/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints;

import interiores.business.models.constraints.unary.PriceConstraint;
import interiores.business.models.FurnitureType;
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
public class PriceConstraintTest {
    
    FurnitureType type = new FurnitureType("Bed", new Dimension(100,100), new Dimension(200, 200));
    
    FurnitureModel model = new FurnitureModel(type, "Model",new Dimension(110,45), (float) 99.99,Color.BLACK,"wood");
    
    public PriceConstraintTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of isSatisfied method, of class PriceConstraint.
     */
    @Test
    public void testIsSatisfied1() {
        System.out.println("Testing: isSatisfied(). Checking a model that costs 99.99 against a constraint of price<=100");
        
        PriceConstraint instance = new PriceConstraint((float) 100);
        boolean expResult = true;
        boolean result = instance.isSatisfied(model);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of isSatisfied method, of class PriceConstraint.
     */
    @Test
    public void testIsSatisfied2() {
        System.out.println("Testing: isSatisfied(). Checking a model that costs 99.99 against a constraint of price<=50");
        
        PriceConstraint instance = new PriceConstraint((float) 50);
        boolean expResult = false;
        boolean result = instance.isSatisfied(model);
        assertEquals(expResult, result);
        
    }
    
}