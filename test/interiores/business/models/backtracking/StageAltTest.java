/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.FurnitureType;
import interiores.business.models.SpaceAround;
import interiores.core.business.BusinessException;
import interiores.shared.backtracking.Value;
import interiores.utils.Dimension;
import interiores.utils.Range;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;
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
    //@Test
    public void testGetNextDomainValue() throws BusinessException {
        System.out.println("Testing has/get-NextDomainValue() .........");
        
        // Here we have a room with a complex topography
        int xPoly[] = {0,20,20,10,10,30,30,40,40,50,50,60,60,120,120,100,100,90,90,70,70,15,15,50,50,30,30,0};
        int yPoly[] = {60,60,70,70,90,90,75,75,100,100,75,75,110,110,60,60,30,30,15,15,0,0,20,20,50,50,40,40};
        Area room = new Area(new Polygon(xPoly, yPoly, xPoly.length));
        room.subtract(new Area(new Rectangle(70, 70, 30, 20)));
        room.subtract(new Area(new Rectangle(80, 50, 5, 40)));
        
        // Here, we have a simple topology
        Area room2 = new Area(new Rectangle(0, 0, 100, 100));
        
        // And a basic list of furniture models
        List<FurnitureModel> l = new ArrayList();
        SpaceAround sa = new SpaceAround(0, 0, 0, 0);
        l.add(new FurnitureModel("A", new Dimension(10,10), 10, "white", "plastic",sa));
        l.add(new FurnitureModel("B", new Dimension(20,10), 20, "black", "wood",sa));
        l.add(new FurnitureModel("C", new Dimension(20,20), 30, "red", "steel",sa));
            
        // We create the stage
        Area selectedRoom = room;
        StageAlt instance = new StageAlt(l, selectedRoom);
        
        // And start going through the values in the domain.
        // Now, note that even this simple example is complex in the current context.
        // This test, thus, only serves the purpose of verifying whether the methods
        // are consistent and do not throw any kind of exception
        while (instance.hasMoreValues()) {
            FurnitureValue fv = (FurnitureValue) instance.getNextDomainValue();
            System.out.println(fv.toString());
        }
                
        System.out.println("........... THE END");
    }
    
    /**
     * Test of getNextDomainValue method, of class StageAlt.
     */
    //@Test
    public void testGetNextDomainValueTimed() throws BusinessException {
        System.out.println("Testing has/get-NextDomainValue() for TIME .........");
        
        // PARAMETERS
        int nb_models = 50; // The number of models
        // The resolution (should be changed directly on ExtendedArea)
        Dimension dim = new Dimension(1000,1000); // The size of the room
        
        // Quite the worst-case scenario for a single variable domain
        Area room = new Area(new Rectangle(0, 0, dim.width, dim.depth));
        
        // And a basic list of furniture models
        List<FurnitureModel> l = new ArrayList();
        for (int i = 0; i < nb_models; i++) { 
            Random rand = new Random();
            Dimension d = new Dimension(20 + rand.nextInt(80),20 + rand.nextInt(80));
            SpaceAround sa = new SpaceAround(0, 0, 0, 0);
            FurnitureModel fm = new FurnitureModel("m"+i,d,10,"white","wood",sa);
            l.add(fm);
            System.out.println("Added model " + fm.toString());
        }
            
        // We create the stage
        StageAlt instance = new StageAlt(l,room);
            
        long startTime = System.nanoTime();
        
        // And start going through the values in the domain.
        // Now, note that even this simple example is complex in the current context.
        // This test, thus, only serves the purpose of verifying whether the methods
        // are consistent and do not throw any kind of exception
        long values_obtained = 0;
        while (instance.hasMoreValues()) {
            FurnitureValue fv = (FurnitureValue) instance.getNextDomainValue();
            values_obtained++;
        }
        
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        
        System.out.println("........... THE END ........ elapsed " + duration + " nanoseconds.");
        System.out.println("Values obtained = " + values_obtained);
    }
    
    @Test
    public void testGetNextDomainValueTimedComplex() throws BusinessException {
        System.out.println("Testing has/get-NextDomainValue() for TIME WITH A COMPLEX SHAPE.........");
        
        // PARAMETERS
        int nb_models = 30; // The number of models
        // The resolution (should be changed directly on ExtendedArea)
        
        // Here we have a room with a complex topography
        int xPoly[] = {0,20,20,10,10,30,30,40,40,50,50,60,60,120,120,100,100,90,90,70,70,15,15,50,50,30,30,0};
        int yPoly[] = {60,60,70,70,90,90,75,75,100,100,75,75,110,110,60,60,30,30,15,15,0,0,20,20,50,50,40,40};
        Area room = new Area(new Polygon(xPoly, yPoly, xPoly.length));
        room.subtract(new Area(new Rectangle(70, 70, 30, 20)));
        room.subtract(new Area(new Rectangle(80, 50, 5, 40)));
        
        // And a basic list of furniture models
        List<FurnitureModel> l = new ArrayList();
        for (int i = 0; i < nb_models; i++) { 
            Random rand = new Random();
            Dimension d = new Dimension(4 + rand.nextInt(10),4 + rand.nextInt(10));
            SpaceAround sa = new SpaceAround(0, 0, 0, 0);
            FurnitureModel fm = new FurnitureModel("m"+i,d,10,"white","wood",sa);
            l.add(fm);
            System.out.println("Added model " + fm.toString());
        }
            
        // We create the stage
        StageAlt instance = new StageAlt(l,room);
            
        long startTime = System.nanoTime();
        
        // And start going through the values in the domain.
        // Now, note that even this simple example is complex in the current context.
        // This test, thus, only serves the purpose of verifying whether the methods
        // are consistent and do not throw any kind of exception
        long values_obtained = 0;
        while (instance.hasMoreValues()) {
            FurnitureValue fv = (FurnitureValue) instance.getNextDomainValue();
            values_obtained++;
        }
        
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        
        System.out.println("........... THE END ........ elapsed " + duration + " nanoseconds.");
        System.out.println("Values obtained = " + values_obtained);
    }
        
}