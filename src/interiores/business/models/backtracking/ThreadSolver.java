package interiores.business.models.backtracking;

import interiores.business.events.backtracking.SolveDesignFinishedEvent;
import interiores.business.events.backtracking.SolveDesignStartedEvent;
import interiores.business.events.room.RoomDesignChangedEvent;
import interiores.business.exceptions.SolverNotFinishedException;
import interiores.core.Event;
import interiores.core.Observable;
import interiores.core.Observer;
import interiores.shared.backtracking.NoSolutionException;
import interiores.utils.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hector0193
 */
public class ThreadSolver
    extends Solver
    implements Observable, Observer
{
    private List<Observer> listeners;
    private boolean isSolutionFound;
    private boolean isTimerEnabled;
    final private Thread thread;
    private long time;
    
    public ThreadSolver() {
        this(new VariableConfig(new Dimension(0, 0))); // Default empty solver
    }
    
    public ThreadSolver(VariableConfig variableConfig) {
        super(variableConfig);
        
        thread = new Thread(){
            @Override
            public void run() {
                computeSolution();
            }
        };
        
        listeners = new ArrayList();
        isSolutionFound = false;
        isTimerEnabled = false;
    }
    
    public void enableTimer() {
        isTimerEnabled = true;
    }
    
    @Override
    public void solve() {
        if(isSolving())
            throw new SolverNotFinishedException();
        
        thread.start(); // Start solving!
    }
    
    private void computeSolution() {
        isSolutionFound = false;
        SolveDesignFinishedEvent solveFinished = new SolveDesignFinishedEvent();

        if(isTimerEnabled)
            time = System.nanoTime();
        
        // And try to solve it
        try {
            notify(new SolveDesignStartedEvent());
            
            super.solve();
            
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
        return thread.isAlive();
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
