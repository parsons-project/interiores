/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.controllers;

import interiores.business.models.backtracking.FurnitureVariableSet;
import interiores.core.business.BusinessController;
import interiores.core.data.JAXBDataController;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author larribas
 */
public class ConstraintController extends BusinessController {
    
    public ConstraintController(JAXBDataController data)
    {
        super(data);
    }

    //public Collection getConstraints(String variable) {
    //    return getVariableSet().getVariable(variable).getUnaryConstraints();
    //}

    public void add(String type, List<Object> parameters, String variable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private FurnitureVariableSet getVariableSet() {
        return (FurnitureVariableSet) data.get("variableSet");
    }
    
    
}
