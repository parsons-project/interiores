package interiores.business.models.backtracking;

import interiores.business.events.backtracking.DebugSolveDesignStartedEvent;
import interiores.business.events.backtracking.SolveDesignFinishedEvent;
import interiores.business.events.backtracking.SolveDesignStartedEvent;
import interiores.business.events.room.RoomDesignChangedEvent;
import interiores.business.exceptions.SolverNotFinishedException;
import interiores.business.models.WishList;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.business.models.room.FurnitureType;
import interiores.core.Event;
import interiores.core.Observable;
import interiores.core.Observer;
import interiores.shared.backtracking.NoSolutionException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hector0193
 */
public class Solver
    implements Observable, Observer
{
    private List<Observer> listeners;
    private boolean isDebugEnabled;
    private boolean isTimerEnabled;
    private boolean isSolutionFound;
    private Thread thread;
    private long time;
    
    
    public Solver() {
        listeners = new ArrayList();
        isDebugEnabled = false;
        isTimerEnabled = false;
        isSolutionFound = false;
    }
    
    public void setDebug(boolean debug) {
        isDebugEnabled = debug;
    }
    
    public void setTimer(boolean timer) {
        isTimerEnabled = timer;
    }
    
    public void solve(WishList wishList, NamedCatalog<FurnitureType> typesCatalog) {
        if(isSolving())
            throw new SolverNotFinishedException();
        
        final FurnitureVariableSet furVarSet = createFurnitureVariableSet(wishList, typesCatalog);
        
        thread = new Thread(){
            @Override
            public void run() {
                computeSolution(furVarSet);
            }
        };
        
        thread.start();
    }
    
    private FurnitureVariableSet createFurnitureVariableSet(WishList wishList, NamedCatalog<FurnitureType> typesCatalog)
    {
        FurnitureVariableSet furVarSet;
        
        if(isDebugEnabled) {
            furVarSet = new FurnitureVariableSetDebugger(wishList, typesCatalog);
            ((FurnitureVariableSetDebugger) furVarSet).addListener(this);
            notify(new DebugSolveDesignStartedEvent());
        }
        else
            furVarSet = new FurnitureVariableSet(wishList, typesCatalog);
        
        return furVarSet;
    }
    
    public void computeSolution(FurnitureVariableSet furVarSet) {
        isSolutionFound = false;
        SolveDesignFinishedEvent solveFinished = new SolveDesignFinishedEvent();

        if(isTimerEnabled)
            time = System.nanoTime();
        
        // And try to solve it
        try {
            notify(new SolveDesignStartedEvent());
            
            furVarSet.solve();
            
            isSolutionFound = true;
            solveFinished.solutionFound();

            notify(new RoomDesignChangedEvent());
        }
        catch (NoSolutionException nse) {
            isSolutionFound = false;
        }
        
        if(isTimerEnabled)
            solveFinished.setTime(System.nanoTime() - time);
        
        notify(solveFinished);
    }
    
    public boolean isSolutionFound() {
        return isSolutionFound;
    }
    
    public boolean isSolving() {
        return (thread != null && thread.isAlive());
    }
    
    public boolean isPaused() {
        return (thread.getState() == Thread.State.WAITING);
    }
    
    public void continueSolving() {
        synchronized (thread) {
            thread.notify();
        }
    }
    
    public void stopSolving() {
        continueSolving();
        
        thread.interrupt();
    }
    
    @Override
    public void addListener(Observer o) {
        listeners.add(o);
    }
    
    @Override
    public void notify(Event e) {
        for(Observer listener : listeners)
            listener.notify(e);
    }
}
