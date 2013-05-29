/**

Part 1: Introduction to the constraint system.

A Constraint is a class that represents any form of restriction upon the
values the variables might take.
There are several kinds, each of which has its own class.
The fields of a constraint class include the information inherent to the kind.
For example, for a maximum-distance constraint, there might be a distance
field.

There can be several instances of a same kind of constraint. Constraints are
identifiable entities.
Constraint (this class) is the ancestor of every other constraint. It is
abstract, and all it does is define methods to identify and constraints.

Constraints are classified in two main cathegories: furniture constraints
and room constraints.
Furniture constraints are linked to a specific variable, whereas room
constraints are related to all of them.
In essence, their main difference is the parameters they receive in their
operations (a single variable vs a set of variables).

Notice that, from the point of view of the algorithm, the origin of a
restriction (implicit or user-defined) is irrelevant.



Part 2: The roles of a constraint.

Each constraint can have up to 3 functionalities, in the form of interfaces.
The interfaces receive different parameters for room and furniture constraints,
but their semantics are the same.

1) PreliminarTrimmer interface: this interface allows for detecting and
eliminating values that fall in conflict with the restriction by themselves,
and thus lead to no valid solution.
For this purpose, the interface has the method:
preliminarTrim()
This method is called only once, before the backtracking process starts.

Notice that not all restrictions must implement this interface. For instance,
the maximum-distance constraint can not detect any values that violate the
restriction when no variables have assigned values.


2) BacktrackingTimeTrimmer interface: this interface allows for detecting
values that, at a given point in the process of assigning values to variables,
are no longer valid. Those values are not eliminated but 'trimmed', which is
the act of tagging them as no longer valid and storing from what point.
This interface has the method:
trim()
This method is called everytime a variable takes a value.

Notice that not all constraints must implement this interface. For instance,
the color-blue constriant does not implement this interface, as this can be
checked in pre-backtracking time.


3) InexhaustiveTrimmer interface:
Are trims and preliminar trims exhaustive? If a trim is exhaustive, we do not
need to check explicitly if it is fulfilled or not, given that we have trimmed
the domain of the variable.

We assume that backtracking time trims are never exhaustive, whereas the
preliminar trims might be.

Any trim that is not exhaustive should implement this interface. It includes
the method
boolean isSatisfied()
This method is called everytime we try to assign a value to a variable, to
make sure it is fulfilled.


For some constraints one interface might make sense, or two, or
three.
Which interface combinations make sense?
Since trims are not exhaustive, any constraint that implements the
BacktrackingTimeTrimmer (B) interface must also implement the
InexhaustiveTrimmer (I) interface. For this reason the B interface implements
the I interface.
There are no other dependences between interfaces.
So, the possible combinations are:
 *
PBI (e.g. maximum-budget constraint)
PI (e.g. area constraint)
BI (e.g. maximum-distance constraint)
P (e.g. blue-color constraint)
I

 *
Important: A constraint is not defined by the interfaces it implemenets, but
by its semantics. For example, the budget constraint might be able to
eliminate some values in the beginning if a model is really expensive, and
also be useful later on once some models have already been picked and their
price is known. Since the three operations (including isSatisfied) check
the same condition, they all the belong to the same constraint.

Following this philosophy, one constraint shouldn't check for different
things simply because they belong to the same interface.



Part 3: Furniture constraints.

A furniture constraint is linked to the variable v it affects. The methods of
the interfaces it can implement receive v as parameter.
Rule of thumb: A furniture constraint is associated with ONE variable.

preliminarTrim(FurnitureVariable v): eliminates the values of v that do not
fulfil the constraint.

trim(FurnitureVariable v): trims the values of v that are no longer valid.
This method is called only as long as v does not have an assigned value.

isSatisfied(FurnitureVariable v): returns whether the value assigned to v
violates the constraint.

Any other information that a constraint needs to perform this operations
must be a field of the constraint, initialized upon its creation.


Any class that extends Constraint and implements some of the forementioned
methods, is, by definition, a furniture constraint.
However, actual furniture constraints do not inherit straight from
Constraint. They inherit from one of the two following
abstract subclasses of Constaint:


1) Unary constraint: it is a constraint with no additional fields (in the
class itself) that implements the PreliminarTrimmer interface.

Notice that any given subclass of the Unary constraint can also implement
other interfaces, although it is not the usual case.
An example of inexhaustive unary constraint is the area constraint (a
variable can only be in space that belong to a given area); it is not
exhaustive because it depends on the exact shape of the model assigned to
the variable.


2) Binary constraint: it is a constraint that checks a relation between
the values of v and another variable, which might actually be a constant
(a fixedFurniture).

BinaryConstraint implements the BacktrackingTimeTrimmer interface and
has a protected field:
InterioresVariable otherVariable

As we said, any field or information (other than v)
that a constraint needs, must be passed as a value in the creation of such
constraint. Thus, in most cases, two instances will be created:
For each variable, a binary constraint in which the
field otherVariable is the other variable. If one of the variables is
actually a constant, this is not required (and, in fact, not possible).

BinaryConstraint implements hook methods:
- If the other variable does not have an assigned value, isSatisfied() returns
true. Otherwise, it is left to each subclass to decide.

- If the other variable does not have an assigned value, trim() does nothing.
Otherwise, it is left to each subclass to act.

Notice that a FurnitureConstant is always assigned.

Binary constraint also has an abstract method that all subclasses must
implement
int getWeight()
which returns an estimation of the restrictiviness of the constraint.
The use of this method is commented below.

As expected, a binary constraint might implement the PreliminarTrimmer
interface.



Part 4: Room constraints.

The critical difference between a furniture and a room constraint is the
parameters of the methods of the interfaces they implement. However, we will
see they also have important semantic differences.

There can't be more than one instance of a room constraint.

The interfaces share the same names, except the keyword Room is added in
the beginning of their names.
Thier methods are:

preliminarTrim(List<FurnitureVariable> variables,
               List<FurnitureConstant> fixedFurniture):
eliminates the values of each variable that do not fulfil the constraint.

trim(List<FurnitureVariable> assignedVariables,
     List<FurnitureVariable> unassignedVariables,
     List<FurnitureConstant> fixedFurniture,
     FurnitureVariable actual):
trims the values of unassignedVariables that are no longer valid.

isSatisfied(List<FurnitureVariable> assignedVariables,
            List<FurnitureVariable> unassignedVariables,
            List<FurnitureConstant> fixedFurniture,
            FurnitureVariable actual):
returns whether the values assigned to every assignedVariable and actual
fulfil the constraint.


Similarily to furniture constraints, room constraints do not inherit
directly from Constraint. They inherit from Constraint's third and last
direct subclass: Global constraint.

Global constraint does not implement by default either interface. It is left
for subclasses to decide which ones are suited.

For instance: an all-blue color constraint might implement the
PreliminarTrimmer interface, whereas the same-color constraint implements
the BacktrackingTimeTrimmer interface.


What isSatisfied() really does is assume that the variables of the
assignedVariables list do not violate the restriction. Thus, it only must
be ensured that the actual variable does not violate it.

Similarily, the trim() method only trims considering the assignment to
actual.



Part 5: the flow of the algorithm.

The algorithm receives two lists of already created constraints:
one for furniture constraints and one for room constraints.

The lists are not necessarily sorted in any particular way. 

For each furniture constraint, there
is a connecton to the variable with which they are related (remember that
binary constraints are only related to one variable: constraints have already
been constructed with all the required fields, including binary constraints
'converted' to one or two 'unary' constraints).

The first step consists on creating the "matrix of dependence" (mod).
This matrix stores, for each variable v, a weight for each other variable w.
A weight is an estimated indicator of how much the values of w are conditioned
by the value of v: the higher the weight, the more trimmed the domain of w
will be when v is assigned a value.

Algorithm to build the matrix of dependence:
Initialize mod to all 0.
For each binary constraint bc:
     //assume v is the variable associated with bc
     //and w is the otherVariable of bc (bc.getOtherVariable())
     mod[w][v] = bc.getWeight()
     //this means that if w is assigned a value, the domain of v will be
     //restricted by the weight of bc.

The use of this matrix will be shown later.


Next, the preliminarTrim() method of all constraints that implement said
interface is triggered, for the first and last time. 

At this point, all variables which only implemented the preliminarTrimmer
interface are ditched.
 *
Constraints do not come back into play again until a value is tried to
assign to a variable v. This means that v is the "actual" variable.
When such event happens, the isSatisfied() method of
all furniture constraints associated with v is triggered with v as parameter
(with the candidate value assigned to v)
and the same with the isSatisfied() method of all room constraints, with
v as the actual variable.

If the assignment is successful, it will be followed by a round of
backtracking time trimmers:
For each variable v without an assigned value, the trim() method of all its
associated furniture constraints that implement the BacktrackingTimeTrimmer
interface are called with v as parameter.
Similarily, the trim() method of all room constraints that implemented it
is called, with v as the actual variable.

After an assginment has been made and all domains have been trimmed, the
Matrix of Dependence plays a role in the election of the next actual
variable: those that get an overall added higher weight, considering only
unassigned variables, will have precedence.
 *


Part 6: Package structure

constraints:
Constraint (this class)

constraints.furniture:
The interfaces of furniture constraints and the subclasses of Constraint from
which furniture constraints inherit (UnaryConstraint and BinaryConstraint).

constraints.furniture.unary:
The actual subclasses of UnaryConstraint.

constraints.furniture.binary:
The actual subclasses of BinaryConstraint.

constraints.room:
The interfaces of room constraints and the subclass of Constraint from which
room constraints inherit (GlobalConstraint).

constraints.room.global:
The actual subclasses of GlobalConstraint.



Part 7: interface between constraints and variables
To perform trimes and preliminarTrims, constraints trigger methods upon
variables.

Assume for the next methods that vs is either an Area (a collection of
positions), a colletion of Models or a collection of Orientations.

eliminate(values vs):
If any value of the variable's stage[0] is in vs, it is removed.

eliminateExcept(values vs):
If any value of the variable's stage[0] is not in vs, it is removed.

trim(values vs):
If any value of the variables' stage[iteration+1] is in vs, it is moved to
stage[iteration].

trimExcept(values vs):
If any value of the varirables' stage[iteration+1] is not in vs, it is moved
to stage[iteration].


Apart from this methods, Constaints might invoke getters to the varirable to
get the information they need, such as getDomain() or getAssignedValue(), in
the case of isSatisfied().


The whole process of trimming a variable consists on:
1) moving all values of stage[iteration] to stage[iteration+1]. This is
called forward iteration.

2) for each constraint, some values of stage[iteration+1] are moved back to
stage[iteration] through trim() and trimExcept() calls.

 */
package interiores.business.models.constraints;

import interiores.core.business.BusinessException;
import java.util.Map;

/**
 *
 * @author Nil
 */
public abstract class Constraint
{    
    public static void addConstraintClass(Map<String,
            Class<? extends Constraint>> availableConstraints,
            String name, Class<? extends Constraint> constraintClass) {
        
        availableConstraints.put(name, constraintClass);
    }
    
    public static Class<? extends Constraint> getConstraintClass(
            Map<String, Class<? extends Constraint>> availableConstraints,
            String name, String type)            
            throws BusinessException {
        
        if(! availableConstraints.containsKey(name))
            throw new BusinessException("There is no " + type + " constraint known as " + name);
        
        return availableConstraints.get(name);
    }
}
