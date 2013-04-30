//NIL: I think this driver is obsolete because the tested class has changed much.


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.backtracking;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Orientation;
import interiores.business.models.Room;
import interiores.business.models.constraints.ColorConstraint;
import interiores.business.models.constraints.MaterialConstraint;
import interiores.business.models.constraints.ModelConstraint;
import interiores.business.models.constraints.PriceConstraint;
import interiores.shared.Value;
import interiores.shared.Variable;
import java.awt.Color;
import java.awt.Dimension;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author larribas
 */
public class FurnitureVariableTest {
    
    List<FurnitureModel> l = new ArrayList<FurnitureModel>();
    Room room;
    
    
    public FurnitureVariableTest() {
        
        // Preparing initial values for FurnitureVariable
        l.add(new FurnitureModel("Model1",new Dimension(10,20), (float) 10,Color.BLACK,"wood"));
        l.add(new FurnitureModel("Model2",new Dimension(20,30), (float) 20,Color.BLACK,"wood"));
        l.add(new FurnitureModel("Model3",new Dimension(30,40), (float) 30,Color.WHITE,"wood"));
        l.add(new FurnitureModel("Model4",new Dimension(40,50), (float) 40,Color.WHITE,"steel"));
        l.add(new FurnitureModel("Model5",new Dimension(50,60), (float) 50,Color.BLACK,"steel"));
        l.add(new FurnitureModel("Model6",new Dimension(60,70), (float) 60,Color.BLACK,"wood"));
        
        // Creating a room for which FurnitureVariable will work
        room = new Room("Kitchen",1000,1000);
        
        
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void genericTest2() {
        System.out.println("Preparing for test #2... (Bringing constraints into play!)");
        FurnitureVariable instance = new FurnitureVariable(l, room);
        
        System.out.println("The first thing we will do is apply some constraints to the variable.");
        
        instance.applyConstraint(new ColorConstraint(Color.BLACK));
        System.out.println("Color=Black will leave us with models 1,2,5,6");
        System.out.println("#models left = " + instance.getConstraints());
        
        instance.applyConstraint(new MaterialConstraint("wood"));
        System.out.println("Material=Wood will leave us with models 1,2,6");
        System.out.println("#models left = " + instance.getConstraints());
        
        instance.applyConstraint(new PriceConstraint((float) 55.00));
        System.out.println("Price<=55 will leave us with models 1,2");
        System.out.println("#models left = " + instance.getConstraints());
        
        instance.restrictOrientation(Orientation.E);
        System.out.println("We want the furniture to be oriented towards the East of the room");
        
        Boolean[][] intersect = new Boolean[room.getHeight()][room.getWidth()];
        for (int i = 0; i < intersect.length; i++)
            for (int j = 0; j < intersect[i].length; j++)
                intersect[i][j] = (i == 0) || (i == room.getWidth()-1 ) || (j == 0) || (j == room.getHeight()-1 );
        //This means the only valid positions are those clung to the walls
        instance.restrictArea(intersect);
        System.out.println("We want the furniture to be stuck to a wall");
        
        System.out.println("Now we'll try out every possible value in the domain of the variable, and see what happens:");
        int i = 0;
        while (instance.hasMoreValues()) {
            if (i % 1 == 0) {
                FurnitureValue nextVal = instance.getNextDomainValue();
                String s = "The #" + i + " value is a model named \"" + nextVal.getModel().getName() + "\". ";
                s += "Positioned at (" + nextVal.getPosition().x + "," + nextVal.getPosition().y + "). ";
                s += "With an area of width=" + nextVal.getArea().width + " and height=" + nextVal.getArea().height + ".";
                s += " And oriented towards the " + nextVal.getArea().getOrientation().toString() + ".";
                System.out.println(s);
            }
            i++;
        }
        
        System.out.println("AND THAT'S ABOUT EVERYTHING THERE IS TO TEST AS TO CONSTRAINT MANAGEMENT ON A VARIABLE");
        
    }
    
    /**
     * Test of getNextDomainValue method, of class FurnitureVariable.
     */
    @Test
    public void genericTest1() {
        System.out.println("Preparing for test #1... (No constraints attached)");
        FurnitureVariable instance = new FurnitureVariable(l, room);
        
        if (instance.hasMoreValues()) {
            System.out.println("FurnitureVariable does have a first value. Let's get it");
            FurnitureValue nextVal = instance.getNextDomainValue();
            String s = "The first value is a model named \"" + nextVal.getModel().getName() + "\". ";
            s += "Positioned at (" + nextVal.getPosition().x + "," + nextVal.getPosition().y + "). ";
            s += "With an area of width=" + nextVal.getArea().width + " and height=" + nextVal.getArea().height + ".";
            s += " And oriented towards the " + nextVal.getArea().getOrientation().toString() + ".";
            System.out.println(s);
        }
        else fail("Variable doesn't have a first value in its domain");
        
        System.out.println("Now, let's get a couple of domain values ahead!");
        if (instance.hasMoreValues() && instance.hasMoreValues())
        {
            FurnitureValue nextVal = instance.getNextDomainValue();
            String s = "The third value is a model named \"" + nextVal.getModel().getName() + "\". ";
            s += "Positioned at (" + nextVal.getPosition().x + "," + nextVal.getPosition().y + "). ";
            s += "With an area of width=" + nextVal.getArea().width + " and height=" + nextVal.getArea().height + ".";
            s += " And oriented towards the " + nextVal.getArea().getOrientation().toString() + ".";
            System.out.println(s);
        }
        else fail("Variable doesn't have a third value in its domain");
        
        System.out.println("Everything's fine by now. Supposedly, the variable can't have an assigned value at first");
        if (instance.isAssigned()) System.out.println("But it does!!");
        else {
            System.out.println("Let's try assigning one!");
            FurnitureValue nextVal = instance.getNextDomainValue();
            instance.setAssignedValue(nextVal);
            System.out.println("Have we assigned it correctly?  " + (instance.isAssigned()? "yes" : "no") );
            FurnitureValue assignedVal = instance.getAssignedValue();
            System.out.println("The value we assigned was " + nextVal + ", where the assigned one is " + assignedVal);
            if (nextVal == assignedVal) System.out.println("Looks like those values coincide");
            else System.out.println("So there was a problem");
            
            System.out.println("Now let's unassign it:");
            instance.unsetValue();
            System.out.println("Now the state of the variable is " + (instance.isAssigned()? "assigned" : "unassigned") );
            System.out.println("And its value (should be 'null') is " + instance.getAssignedValue());
        }
        
        System.out.println("Finally, we'll try going over every single value of the domain (printing it every 400 iterations");
        System.out.println("Clock'll be tickin' slow, so you better wait...");
        
        int i = 0;
        while (instance.hasMoreValues()) {
            if (i % 163632 == 0) {
                FurnitureValue nextVal = instance.getNextDomainValue();
                String s = "The #" + i + " value is a model named \"" + nextVal.getModel().getName() + "\". ";
                s += "Positioned at (" + nextVal.getPosition().x + "," + nextVal.getPosition().y + "). ";
                s += "With an area of width=" + nextVal.getArea().width + " and height=" + nextVal.getArea().height + ".";
                s += " And oriented towards the " + nextVal.getArea().getOrientation().toString() + ".";
                System.out.println(s);
            }
            i++;
        }
        
        System.out.println("QUITE NEAT INDEED. TEST 2 WILL DEEPEN A LITTLE MORE IN THE FUNCTIONALITY OF THE CLASS");
        
    }
//
//    /**
//     * Test of hasMoreValues method, of class FurnitureVariable.
//     */
//    @Test
//    public void testHasMoreValues() {
//        System.out.println("hasMoreValues");
//        FurnitureVariable instance = null;
//        boolean expResult = false;
//        boolean result = instance.hasMoreValues();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setAssignedValue method, of class FurnitureVariable.
//     */
//    @Test
//    public void testSetAssignedValue_FurnitureValue() {
//        System.out.println("setAssignedValue");
//        FurnitureValue value = null;
//        FurnitureVariable instance = null;
//        instance.setAssignedValue(value);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of unsetValue method, of class FurnitureVariable.
//     */
//    @Test
//    public void testUnsetValue() {
//        System.out.println("unsetValue");
//        FurnitureVariable instance = null;
//        instance.unsetValue();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of trimDomain method, of class FurnitureVariable.
//     */
//    @Test
//    public void testTrimDomain() {
//        System.out.println("trimDomain");
//        Variable variable = null;
//        FurnitureVariable instance = null;
//        instance.trimDomain(variable);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of undoTrimDomain method, of class FurnitureVariable.
//     */
//    @Test
//    public void testUndoTrimDomain() {
//        System.out.println("undoTrimDomain");
//        Variable variable = null;
//        Value value = null;
//        FurnitureVariable instance = null;
//        instance.undoTrimDomain(variable, value);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isAssigned method, of class FurnitureVariable.
//     */
//    @Test
//    public void testIsAssigned() {
//        System.out.println("isAssigned");
//        FurnitureVariable instance = null;
//        boolean expResult = false;
//        boolean result = instance.isAssigned();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getAssignedValue method, of class FurnitureVariable.
//     */
//    @Test
//    public void testGetAssignedValue() {
//        System.out.println("getAssignedValue");
//        FurnitureVariable instance = null;
//        FurnitureValue expResult = null;
//        FurnitureValue result = instance.getAssignedValue();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setAssignedValue method, of class FurnitureVariable.
//     */
//    @Test
//    public void testSetAssignedValue_Value() {
//        System.out.println("setAssignedValue");
//        Value value = null;
//        FurnitureVariable instance = null;
//        instance.setAssignedValue(value);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of applyConstraint method, of class FurnitureVariable.
//     */
//    @Test
//    public void testApplyConstraint() {
//        System.out.println("applyConstraint");
//        ModelConstraint newConstr = null;
//        FurnitureVariable instance = null;
//        instance.applyConstraint(newConstr);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of restrictOrientation method, of class FurnitureVariable.
//     */
//    @Test
//    public void testRestrictOrientation() {
//        System.out.println("restrictOrientation");
//        Orientation o = null;
//        FurnitureVariable instance = null;
//        instance.restrictOrientation(o);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of restrictArea method, of class FurnitureVariable.
//     */
//    @Test
//    public void testRestrictArea() {
//        System.out.println("restrictArea");
//        boolean[][] intersect = null;
//        FurnitureVariable instance = null;
//        instance.restrictArea(intersect);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}