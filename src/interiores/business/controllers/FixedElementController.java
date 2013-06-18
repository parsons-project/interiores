package interiores.business.controllers;

import interiores.business.controllers.abstracted.InterioresController;
import interiores.business.events.furniture.ElementSelectedEvent;
import interiores.business.events.furniture.ElementUnselectedEvent;
import interiores.business.events.room.WantedFixedChangedEvent;
import interiores.business.models.room.elements.WantedFixed;
import interiores.business.models.room.elements.fixed.Door;
import interiores.business.models.room.elements.fixed.Pillar;
import interiores.business.models.room.elements.fixed.Window;
import interiores.core.data.JAXBDataController;
import interiores.utils.Dimension;
import java.awt.Point;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author alvaro
 */
public class FixedElementController extends InterioresController{
    
    private final Collection<String> selectable = Arrays.asList(new String[]{"door", "window", "pillar"});
    
    public FixedElementController(JAXBDataController data) {
        super(data);
    }
    
    
    public void addDoor(String door, int displacement, int length) {
        WantedFixed wf = new Door( door, displacement, length, getRoom().getDimension() );
        addFixedElement(wf);
    }
    
    public void addWindow(String wall, int displacement, int length) {
        WantedFixed wf = new Window( wall, displacement, length, getRoom().getDimension() );
        addFixedElement(wf);
    }
    
    public void addPillar(Point point, Dimension dimension) {
        WantedFixed wf = new Pillar(point, dimension);
        addFixedElement(wf);
    }
    
    public void remove(String name) {
        getWishList().removeWantedFixed(name);
        
        notify(new WantedFixedChangedEvent());
        notify(new ElementUnselectedEvent(name));
    }
    
    public Collection<WantedFixed> getWantedFixed() {
        return getWishList().getWantedFixed();
    }
    
    public Collection<String> getFixedNames() {
        return getWishList().getFixedNames();
    }

    public Collection<String> getSelectable() {
        return selectable;
    }
    
    private boolean isWellPositioned(WantedFixed wf) {
        Collection<WantedFixed> wfc = getWantedFixed();
        
        for (WantedFixed w : wfc)
            if ( w.assignedValue.getWholeArea().intersects(wf.assignedValue.getWholeArea()) ) return false;
        
        return true;
    }
    
    private void addFixedElement(WantedFixed wf) {
        if (isWellPositioned(wf)) {
            String id = getWishList().addWantedFixed(wf);
            notify(new WantedFixedChangedEvent());
            notify(new ElementSelectedEvent(id));
        }
    }
}
