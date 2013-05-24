/**
 * Part 1: global vision on constraints.
 * 
 * FurnitureConstraint is the ancestor class of all constraints.
 * It has the method that characterizes constraints:
 * isSatisfied(FurnitureVariable variable)
 * Any other information that a constraint needs to determine whether
 * variable satisies it, must be passed as a value in the creation of such
 * constraint.
 * 
 * This is all we would need for a simple backtracking algorithm. But we want
 * to implement check forwarding. Thus, 2 more optional functionalities are
 * added to constraints, in the form of interfaces:
 * 
 * 1) PreliminarTrimmer interface: this interface allows for detecting and
 * eliminating values that
 * fall in conflict with the restriction by themselves, and thus lead to no
 * valid solution.
 * For this purpose, the interface has the method:
 * preliminarTrim(FurnitureVariable variable)
 * This method is called only once, before the backtracking process starts.
 * 
 * Notice that not all restrictions must implement this interface. For instance,
 * the maximum distance constraint can not detect any values that violate the
 * restriction.
 * 
 * 2) BacktrackingTimeTrimmer interface: this interface allows for detecting
 * values that, at a given point in the process of assigning values to variables,
 * are no longer valid. Those values are not eliminated but 'trimmed', which is
 * the act of tagging them as no longer valid and storing from what point.
 * This interface has the method:
 * public void trim(FurnitureVariable variable)
 * This method is called everytime a variable takes a value, as long as the
 * parameter variable does not have a value itself.
 * 
 * Notice that not all constraints must implement this interface. For instance,
 * the color blue constriant does not implement this interface, as this can be
 * checked in pre-backtracking time.
 * 
 * But, are trims exhaustive? This would imply that the isSatisfied() call would
 * not be necessary after the trim.
 * We conclude that backtracking-time trims are never exhaustive, whereas the
 * preliminar trims might be.
 * For this reason, FurnitureConstraint also declares the method
 * boolean isExhaustive(), which by default returns false, but subclasses can
 * override and implement(with a single return true or false statement).
 * 
 * We will see the impact of this method later on.
 * 
 * For some constraints one interface might make sense, or both, or
 * none.
 * Important: A constraint is not defined by the interfaces it implemenets but for
 * its semantics. For example, the budget constraint might be able to eliminate
 * some values in the beginning if a model is really expensive, and also be
 * useful later on once some models have already been picked and their price
 * is known. Since the three operations (including isSatisfied) check the same
 * condition, they are all the same constraint.
 * 
 * Following this philosophy, one constraint shouldn't check for different
 * things simply because they belong to the same interface.
 * 
 * 
 * Part 2: the subclasses of constraint.
 * 
 * FurnitureConstraint is abstract. Moreover, it has exactly 3 subclasses, all of which
 * are also abstract. Those subclasses are then subclassed by the wide catalog
 * of constraints.
 * Rule of thumb: A restriction is associated with ONE variable.
 * 
 * The three subclasses are:
 * 
 * 1) Unary constraint: it is a constraint with no additional fields (in the
 * class itself) that implements the PreliminarTrimmer interface.
 * 
 * Notice that any given subclass of the Unary constraint can also implement the
 * BacktrackingTimeTrimmer interface, although it is not the usual case.
 * 
 * 2) Binary constraint: it is a constraint that checks a relation between
 * the values of 2 variables, one of which might actually be a constant
 * (a fixedFurniture).
 * 
 * BinaryConstraint implements the BacktrackingTimeTrimmer interface and
 * has a protected field:
 * protected InterioresVariable otherVariable
 * 
 * As we said, any field or information (other than the FurnitureVariable parameter)
 * that a constraint needs, must be passed as a value in the creation of such
 * constraint. Thus, in most cases, two instances will be created:
 * For each variable, a binary constraint in which the
 * field otherVariable is the other variable. If one of the variables is
 * actually a constant, this is not required (and, in fact, not possible).
 * 
 * The philosophy for binary constraints is as follows:
 * - If the other variable does not have an assigned value, isSatisfied() returns
 * true, otherwise, it is left to each subclass to decide.
 * 
 * - If the other variable does not have an assigned value, trim() does nothing.
 * Otherwise, it is left to each subclass to act.
 * 
 * Notice that a FurnitureConstant is always assigned.
 * 
 * Binary constraint also has an abstract method that all subclasses must
 * implement
 * int getWeight()
 * which returns an estimation of the restrictiviness of the constraint.
 * 
 * One more detail: as binary constraint implement BacktrackingTimeTrimmer
 * interface, which is not exhaustive, BinaryConstrant overrides isExhaustive()
 * and implements it as final, returning false.
 * 
 * 3) Global constraint: it is a constraint that checks a relation between the
 * values of all variables.
 * 
 * This is a special case in the sense that it doesn't follow the rule of
 * thumb: there is only one instance associated with all variables.
 * This means that it can receive as parameter in the isSatisfied() call any
 * of the variables.
 * 
 * Global constraint does not implement either interface. It is left for
 * subclasses to decide which ones are suited.
 * 
 * For instance: an all-blue color constraint might implement the
 * PreliminarTrimmer interface, whereas the same-color constrant implements
 * the BacktrackingTimeTrimmer interface.
 * 
 * 
 * Part 3: the flow of the algoritm.
 * 
 * The algorithm receives three lists of already created constraints:
 * one for each kind of constraints..
 * 
 * The lists are not necessarily classified in any particular way. 
 * 
 * For each constraint of the unary and binary constraints list, there is a
 * connecton to the variable with which they are related (remember that
 * binary constraints are only related to one variable: constraints have already
 * been constructed with all the required fields, including binary constraints
 * 'converted' to one or two 'unary' constraints).
 * However, they are kept in separated lists for reasons that we will find
 * about later.
 * 
 * In a first iteration through them, the preliminarTrim() method of all
 * constraints that implement said interface is triggered, for the first and
 * last time. In the case of global constraints, it will be triggered for
 * each variable.
 * 
 * At this point, all variables which only implemented the preliminarTrimmer
 * interface and for which isExhaustive() == true, are ditched; they are no
 * longer needed.
 *
 *
 * Constraints do not come back into play again until a value is tried to
 * assign to a variable v. When such event happens, the isSatisfied() method of
 * all constraints associated with v is triggered with v as parameter
 * (including all global constraints).
 * 
 * If the assignment is successful, it will be followed by a round of
 * backtracking-time trimmers:
 * For each variable v without an assigned value, the trim() method of all its
 * associated constraints that implement the BacktrackingTimeTrimmer interface
 * are called with v as parameter (again, including all global constraints that
 * implement the interface).
 * 
 * Constraints have yet another role: after an assginment has been made and
 * all domains have been trimmed, binary constraints play a role in the
 * election of the next Actual variable.
 * This is the reason they were kept separated from unary constraints.
 * Through the getWeight() and the getOtherVariable of BinaryConstraint, we
 * are able to choose variables that will have a wider impact in the domain
 * of other variables.
 */

package interiores.business.models.FurnitureConstraints;

import interiores.business.models.backtracking.FurnitureVariable;
import interiores.core.business.BusinessException;
import java.util.Map;

/**
 *
 * @author hector
 */
abstract public class FurnitureConstraint
{
    public static void addConstraintClass(Map<String, Class<? extends FurnitureConstraint>> availableConstraints,
            String name, Class<? extends FurnitureConstraint> constraintClass)
    {
        availableConstraints.put(name, constraintClass);
    }
    
    public static Class<? extends FurnitureConstraint> getConstraintClass(
            Map<String, Class<? extends FurnitureConstraint>> availableConstraints, String name, String type)
            throws BusinessException
    {
        if(! availableConstraints.containsKey(name))
            throw new BusinessException("There is no " + type + " constraint known as " + name);
        
        return availableConstraints.get(name);
    }
    
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    
    public abstract boolean isSatisfied(FurnitureVariable variable);
    
    public boolean isExhaustive() {
        return false;
    }
}
