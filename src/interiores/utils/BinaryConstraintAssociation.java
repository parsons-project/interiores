/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.utils;

import interiores.business.models.constraints.BinaryConstraint;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a binary constraint between two furniture pieces
 * within the wish list
 * @author larribas
 */
@XmlRootElement
public class BinaryConstraintAssociation
{
    @XmlAttribute
    public String furniture1;
    
    @XmlAttribute
    public String furniture2;
    
    @XmlElement
    public BinaryConstraint constraint;
    
    public BinaryConstraintAssociation() {
        
    }
    
    public BinaryConstraintAssociation(BinaryConstraint constraint, String furniture1, String furniture2) {
        this.constraint = constraint;
        this.furniture1 = furniture1;
        this.furniture2 = furniture2;
    }
    
    public BinaryConstraint getConstraint() {
        return constraint;
    }
    
    @Override
    public String toString() {
        return constraint + " defined between " + furniture1 + " and " + furniture2;
    }
}
