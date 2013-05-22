package interiores.business.events.backtracking;

import interiores.core.Event;

/**
 *
 * @author hector
 */
public class SolveDesignFinishedEvent 
    implements Event
{
    private boolean isSolutionFound = false;
    private long time = -1;
    
    public void solutionFound() {
        isSolutionFound = true;
    }
    
    public boolean isSolutionFound() {
        return isSolutionFound;
    }
    
    public void setTime(long time) {
        this.time = time;
    }
    
    public long getTime() {
        return time;
    }
    
    public boolean hasTime() {
        return time > 0;
    }
}
