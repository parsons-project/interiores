package interiores.business.models.backtracking;

import interiores.shared.backtracking.Value;
import interiores.shared.backtracking.Variable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hector
 */
@XmlRootElement
@XmlSeeAlso({FurnitureConstant.class, FurnitureVariable.class})
@XmlAccessorType(XmlAccessType.FIELD)
abstract public class InterioresVariable
    implements Variable
{
    @XmlAttribute
    @XmlID
    protected String name;
    
    @XmlAttribute
    protected String typeName;
    
    @XmlElement
    public FurnitureValue assignedValue;
    
    @XmlTransient
    protected boolean isDirty = true;
    
    public InterioresVariable() {
        
    }
    
    public InterioresVariable(String typeName) {
        this(typeName, typeName);
    }
    
    public InterioresVariable(String name, String typeName) {
        this.name = name;
        this.typeName = name;
        
        assignedValue = null;
    }
        
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public String getTypeName() {
        return typeName;
    }
    
    public boolean isDirty() {
        return isDirty;
    }
    
    @Override
    public boolean isAssigned() {
        return assignedValue != null;
    }
    
    @Override
    public FurnitureValue getAssignedValue() {
        return assignedValue;
    }
       
    @Override
    public void assignValue(Value value) {
        assignedValue = (FurnitureValue) value;
    }

    
    @Override
    public void undoAssignValue() {
        assignedValue = null;        
    }
    
    abstract public boolean isConstant();
}
