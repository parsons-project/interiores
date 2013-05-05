package interiores.business.models.backtracking;

import interiores.business.models.Room;
import interiores.business.models.WishList;
import interiores.core.Observable;
import interiores.core.Observer;
import interiores.shared.backtracking.Value;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        
        if(! allAssigned)
            notify("variableSet", "variable", actual);
    }
    
    @Override
    protected Value getNextActualDomainValue() {
        Value value = super.getNextActualDomainValue();
        
        notify("nextValue", "value", value);
        
        return value;
    }
    
    @Override
    public void addListener(Observer observer) {
        debuggers.add(observer);
    }
    
    @Override
    public void notify(String event, Map<String, ?> data) {
        for(Observer debugger : debuggers)
            debugger.notify(event, data);
    }
    
    public void notify(String event, String keyName, Object data) {
        Map<String, Object> map = new HashMap();
        map.put(keyName, data);
        
        notify(event, map);
    }
}
