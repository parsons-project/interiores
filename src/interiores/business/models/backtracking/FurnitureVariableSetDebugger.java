package interiores.business.models.backtracking;

import interiores.business.events.backtracking.NextValueEvent;
import interiores.business.models.Room;
import interiores.business.models.WishList;
import interiores.core.Event;
import interiores.core.Observable;
import interiores.core.Observer;
import interiores.shared.backtracking.Value;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hector
 */
public class FurnitureVariableSetDebugger
    extends FurnitureVariableSet
    implements Observable
{
    private List<Observer> debuggers;
    
    public FurnitureVariableSetDebugger(Room room, WishList wishList)
    {
        super(room, wishList);
        
        debuggers = new ArrayList();
    }
    
    @Override
    protected void setActualVariable() {
        super.setActualVariable();
        
        /*if(! allAssigned)
            notify("variableSet", "variable", actual);*/
    }
    
    @Override
    protected Value getNextActualDomainValue() {
        Value value = super.getNextActualDomainValue();
        
        notify(new NextValueEvent((FurnitureValue) value));
        
        return value;
    }
    
    @Override
    public void addListener(Observer observer) {
        debuggers.add(observer);
    }
    
    @Override
    public void notify(Event event) {
        for(Observer debugger : debuggers)
            debugger.notify(event);
    }
}
