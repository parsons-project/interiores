/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints;

import interiores.business.models.constraints.unary.MaterialConstraint;
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
public class MaterialConstraintTest {
    
    FurnitureType type = new FurnitureType("Bed", new Dimension(100,100), new Dimension(200, 200));
    
    FurnitureModel model = new FurnitureModel(type, "Model",new Dimension(110,45), (float) 99.99,Color.BLACK,"wood");
    
    public MaterialConstraintTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of isSatisfied method, of class MaterialConstraint.
     */
    @Test
    public void testIsSatisfied1() {
        System.out.println("Testing: isSatisfied() for a model made in Wood against a material=Wood constraint.");
        
        MaterialConstraint instance = new MaterialConstraint("wood");
        boolean expResult = true;
        boolean result = instance.isSatisfied(model);
        assertEquals(expResult, result);
        
    }
    
    /**
     * Test of isSatisfied method, of class MaterialConstraint.
     */
    @Test
    public void testIsSatisfied2() {
        System.out.println("Testing: isSatisfied() for a model made in Wood against a material=Metal constraint.");
        
        MaterialConstraint instance = new MaterialConstraint("metal");
        boolean expResult = false;
        boolean result = instance.isSatisfied(model);
        assertEquals(expResult, result);
        
    }

}