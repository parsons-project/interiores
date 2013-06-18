package interiores.business.models.backtracking;

import interiores.business.events.backtracking.ActualVariableSetEvent;
import interiores.business.events.backtracking.DebugSolveDesignStartedEvent;
import interiores.business.events.backtracking.NextValueEvent;
import interiores.business.events.backtracking.ValueAssignedEvent;
import interiores.business.events.backtracking.ValueUnassignedEvent;
import interiores.core.business.BusinessException;
import interiores.shared.backtracking.Value;

/**
 *
 * @author hector
 */
public class ThreadSolverDebugger
    extends ThreadSolver
{
    public ThreadSolverDebugger(VariableConfig variableConfig)
            throws BusinessException
    {
        super(variableConfig);
    }
    
    @Override
    public void solve() {
        notify(new DebugSolveDesignStartedEvent());
        
        super.solve();
    }
    
    @Override
    protected void initVariables() {
        super.initVariables();
    }
    
    @Override
    protected void setActualVariable() {
        super.setActualVariable();
        
        if(! allAssigned())
            notify(new ActualVariableSetEvent(actual));
    }
    
    @Override
    protected Value getNextActualDomainValue() {
        Value value = super.getNextActualDomainValue();
        notify(new NextValueEvent((FurnitureValue) value));
        
        Thread current = Thread.currentThread();
        
        synchronized (current) {
            if(!shouldStop) { 
                try {
                    current.wait();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        return value;
    }
    
    @Override
    protected void assignToActual(Value value) {
        super.assignToActual(value);
        
        notify(new ValueAssignedEvent((FurnitureValue) value));
    }
    
    @Override
    protected void undoAssignToActual() {
        super.undoAssignToActual();
        
        notify(new ValueUnassignedEvent());
    }
    
    @Override
    public void undoSetActualVariable() {
        super.undoSetActualVariable();
        
        notify(new ActualVariableSetEvent(actual));
    }
}
