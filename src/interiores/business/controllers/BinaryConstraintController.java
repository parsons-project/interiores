/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.controllers;

import interiores.business.controllers.abstracted.InterioresController;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.business.models.constraints.BinaryConstraint;
import interiores.business.models.constraints.binary.MaxDistanceConstraint;
import interiores.business.models.constraints.binary.MinDistanceConstraint;
import interiores.business.models.constraints.binary.PartialFacingConstraint;
import interiores.business.models.constraints.binary.StraightFacingConstraint;
import interiores.core.business.BusinessException;
import interiores.core.data.JAXBDataController;
import java.util.Collection;

/**
 *
 * @author larribas
 */
public class BinaryConstraintController
    extends InterioresController {
    
    /**
     * Creates a particular instance of the binary constraint controller
     * @param data The data controller that will give access to the objects this controller will use
     */
    public BinaryConstraintController(JAXBDataController data)
    {
        super(data);
    }

    public void addDistanceConstraint(String parameter, int distance, String[] furniture) throws BusinessException {
        
        BinaryConstraint bc;
        if (parameter.equals("min")) bc = new MinDistanceConstraint(distance);
        else if (parameter.equals("max")) bc = new MaxDistanceConstraint(distance);
        else throw new BusinessException(parameter + " is not a valid distance constraint");
            
        getWishList().addBinaryConstraint(parameter, bc, furniture[0], furniture[1]);
    }

    public void addFaceConstraint(String parameter, String[] furniture) throws BusinessException {
        BinaryConstraint bc;
        if (parameter.equals("straight")) bc = new StraightFacingConstraint();
        else if (parameter.equals("partial")) bc = new PartialFacingConstraint();
        else throw new BusinessException(parameter + " is not a valid facing constraint");
            
        getWishList().addBinaryConstraint(parameter, bc, furniture[0], furniture[1]);
        
    }

    public void remove(String kind, String[] furniture) throws NoRoomCreatedException {
        getWishList().removeBinaryConstraint(kind, furniture[0], furniture[1]);
    }

    public Collection getConstraints(String furn) throws NoRoomCreatedException {
        return getWishList().getBinaryConstraints(furn);
    }
    
    
    
}
