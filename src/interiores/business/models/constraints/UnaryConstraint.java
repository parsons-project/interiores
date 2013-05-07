
package interiores.business.models.constraints;

import interiores.business.models.backtracking.Domain;
import interiores.business.models.constraints.unary.AreaConstraint;
import interiores.business.models.constraints.unary.ColorConstraint;
import interiores.business.models.constraints.unary.MaterialConstraint;
import interiores.business.models.constraints.unary.ModelConstraint;
import interiores.business.models.constraints.unary.OrientationConstraint;
import interiores.business.models.constraints.unary.PriceConstraint;
import interiores.business.models.constraints.unary.SizeConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Generic class for representing constraints over a furniture piece.
 */
@XmlRootElement
@XmlSeeAlso({AreaConstraint.class, ColorConstraint.class, MaterialConstraint.class, ModelConstraint.class,
    OrientationConstraint.class, PriceConstraint.class, SizeConstraint.class})
public abstract class UnaryConstraint {
     /**
     * Eliminates all values from the variables' domain that do not fulfill the
     * restriction.
     * @param variable The specific variable that has to pass the constraint
     */
    public abstract void eliminateInvalidValues(Domain domain);
}
