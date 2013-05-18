/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.core.business.BusinessException;
import interiores.shared.backtracking.Value;
import interiores.utils.Dimension;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author larribas
 */
public class StageTest {
    
    public StageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getNextDomainValue method, of class Stage.
     */
    @Test
    public void testGetNextDomainValue() throws BusinessException {
       System.out.println("Testing has/get-NextDomainValue() for TIME .........");
        
        // PARAMETERS
        int nb_models = 50; // The number of models
        int resolution = 1; // The resolution
        Dimension dim = new Dimension(1000,1000); // The size of the room
        
        // Quite the worst-case scenario for a single variable domain
        Area room = new Area(new Rectangle(0, 0, dim.width, dim.depth));
        
        // And a basic list of furniture models
        List<FurnitureModel> l = new ArrayList();
        for (int i = 0; i < nb_models; i++) { 
            Random rand = new Random();
            Dimension d = new Dimension(20 + rand.nextInt(80),20 + rand.nextInt(80));
            FurnitureModel fm = new FurnitureModel("m"+i,d,10,"white","wood");
            l.add(fm);
            System.out.println("Added model " + fm.toString());
        }
            
        // We create the stage
        Stage instance = new Stage(l, dim, resolution);
            
        long startTime = System.nanoTime();
        
        // And start going through the values in the domain.
        // Now, note that even this simple example is complex in the current context.
        // This test, thus, only serves the purpose of verifying whether the methods
        // are consistent and do not throw any kind of exception
        while(instance.hasMoreValues()) {
            FurnitureValue fv = (FurnitureValue) instance.getNextDomainValue();
        }
        
        long endTime = System.nanoTime();

        long duration = endTime - startTime;
                
        System.out.println("........... THE END ........ elapsed " + duration + "nanoseconds.");
    }

}