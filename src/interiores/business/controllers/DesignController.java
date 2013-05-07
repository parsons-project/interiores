package interiores.business.controllers;

import interiores.business.controllers.abstracted.InterioresController;
import interiores.business.events.room.DebugRoomDesignStartedEvent;
import interiores.business.events.room.RoomDesignFinishedEvent;
import interiores.business.events.room.RoomDesignStartedEvent;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.business.models.Room;
import interiores.business.models.WishList;
import interiores.business.models.backtracking.FurnitureVariableSet;
import interiores.business.models.backtracking.FurnitureVariableSetDebugger;
import interiores.core.Debug;
import interiores.core.Observer;
import interiores.core.data.JAXBDataController;
import interiores.shared.backtracking.NoSolutionException;


/**
 * Business Controller covering the operations related to the design of a room, whether it is automatic or manual
 * @author alvaro
 */
public class DesignController
    extends InterioresController
    implements Observer
{
    
    private boolean solutionFound = false;
    private String lastSolution;
    private FurnitureVariableSet currentFurVarSet;
    
    /**
     * Creates a particular instance of the design controller
     * @param data The data controller that will give access to the objects this controller will use
     */
    public DesignController(JAXBDataController data) {
        super(data);
    }
    
    /**
     * Transforms the furniture and constraints defined in the wish list into proper types to execute
     * the automatic design generation algorithm. Then, it tries to solve this algorithm and it such
     * case, returns the solution.
     */
    public void solve()
            throws NoRoomCreatedException
    {
        WishList wishList = getWishList();
        Room room = getRoom();
        
        FurnitureVariableSet furVarSet = new FurnitureVariableSet(room, wishList);
        
        computeSolution(furVarSet);
    }
    
    public void debug()
            throws NoRoomCreatedException
    {     
        WishList wishList = getWishList();
        Room room = getRoom();
        
        FurnitureVariableSetDebugger furVarSetDebug = new FurnitureVariableSetDebugger(room, wishList);
        furVarSetDebug.addListener(this);
        
        notify(new DebugRoomDesignStartedEvent());
        computeSolution(furVarSetDebug);
    }
    
    private void computeSolution(FurnitureVariableSet furVarSet)
    {
        currentFurVarSet = furVarSet;
        RoomDesignFinishedEvent roomDesigned = new RoomDesignFinishedEvent();
        
        // And try to solve it
        try {
            notify(new RoomDesignStartedEvent());
            furVarSet.solve();
            solutionFound = true;
            lastSolution = furVarSet.toString();
            
            
            roomDesigned.setDesign(furVarSet.getValues());
        }
        catch (NoSolutionException nse) {
            solutionFound = false;
        }
        
        notify(roomDesigned);
    }
    
    /**
     * Indicates if there is a design that contains all the furniture and satisfies all the constraints
     * @return boolean indicating if a solution has been found for the current wish list
     */
    public boolean hasSolution() {
        return solutionFound;
    }
    
    /**
     * Gets a text representation of the generated design
     * @return A String containing a text representation of the design
     */
    public String getDesign() {
        return lastSolution;
    }
    
    public void resumeSolver()
    {
        synchronized (currentFurVarSet) {
            currentFurVarSet.notify();
        }
        
        Debug.println("Solver resumed!");
    }
}
