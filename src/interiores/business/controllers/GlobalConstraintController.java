package interiores.business.controllers;

import interiores.business.controllers.abstracted.InterioresController;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.business.models.constraints.room.global.BudgetConstraint;
import interiores.business.models.constraints.room.global.SameColorConstraint;
import interiores.business.models.constraints.room.global.SameMaterialConstraint;
import interiores.business.models.constraints.room.global.SpecificColorConstraint;
import interiores.business.models.constraints.room.global.SpecificMaterialConstraint;
import interiores.core.data.JAXBDataController;
import interiores.utils.Material;
import java.awt.Color;
import java.util.Collection;

/**
 *
 * @author alvaro
 */
public class GlobalConstraintController
   extends InterioresController
{ 
    public GlobalConstraintController(JAXBDataController data) {
        super(data);
    // Define aliases for unary constraints
        enableConstraint("budget", BudgetConstraint.class);
        enableConstraint("same-color", SameColorConstraint.class);
        enableConstraint("same-material", SameMaterialConstraint.class);
        enableConstraint("specific-color", SpecificColorConstraint.class);
        enableConstraint("specific-material", SpecificMaterialConstraint.class);
    }
    
    private void enableConstraint(String name, Class<? extends GlobalConstraint> uClass) {
        GlobalConstraint.addConstraintClass(name, uClass);
    }
    public void addBudgetConstraint(float maxBudget) {
        GlobalConstraint constraint = new BudgetConstraint(maxBudget);
        getWishList().addGlobalConstraint(constraint);
    }
    
    public void addSameColorConstraint() {
        GlobalConstraint constraint = new SameColorConstraint();
        getWishList().addGlobalConstraint(constraint);
    }
    
    public void addSameMaterialConstraint() {
        GlobalConstraint constraint = new SameMaterialConstraint();
        getWishList().addGlobalConstraint(constraint);
    }
    
    public void addSpecificColorConstraint(Color color) {
        GlobalConstraint constraint = new SpecificColorConstraint(color);
        getWishList().addGlobalConstraint(constraint);
    }
    
    public void addSpecificMaterialConstraint(Material material) {
        GlobalConstraint constraint = new SpecificMaterialConstraint(material);
        getWishList().addGlobalConstraint(constraint);
    }
    
    public void removeGlobalConstraint(String name) {
        getWishList().removeGlobalConstraint(name);
    }
    
    public void removeGlobalConstraint(Class<? extends GlobalConstraint> gc) {
        getWishList().removeGlobalConstraint(gc.getName());
    }
    
    public Collection<GlobalConstraint> getConstraints() {
        return getWishList().getGlobalConstraints();
    }
    
    public Collection<String> getAvailableConstraints() {
        return GlobalConstraint.getConstraintNames();
    }
}
