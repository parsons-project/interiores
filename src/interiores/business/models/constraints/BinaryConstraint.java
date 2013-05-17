package interiores.business.models.constraints;

import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.binary.MaxDistanceConstraint;
import interiores.business.models.constraints.binary.MinDistanceConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author alvaro
 */
@XmlRootElement
@XmlSeeAlso({MaxDistanceConstraint.class, MinDistanceConstraint.class})
public abstract class BinaryConstraint {
    
    public abstract boolean isSatisfied(FurnitureVariable fvariable1, FurnitureVariable fvariable2);
}
