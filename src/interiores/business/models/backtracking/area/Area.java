/*
Area is an alternative way to represent a set of discrete square-shaped surface
points. Area dismisses the standard approach of a boolean matrix or hash table
in favor of a geometric approach.
The motivation behind the creation of this class is the singularities of the
set of points we are required to represent. As furniture pieces are continuous
in space and (in our simplified environment) right-angled, the resulting
areas share common traits. Thus, the same way a sparse matrix can be represented
more optimally taking advantage of its properties, we also think that the
characteristics of our set of points can be extensively exploited to achieve
a large efficiency speed-up from the standard approach, both assymptotically
and in practice.
 *
The operations that we needed our set of discrete surface points to do
efficiently were set operations, iteration through the points and
containibility checks. Here, our approach to this problem.



The Grid Plane is an invented plane with a special topology.
It is made of adjacent square-shaped surface units that form a grid thorough
the plane.
These elemental, undivisible surface units are called squares.
A square of this plane is not infinitely small; It has a positive area.

The plane extends infinitely in all directions.
The X coordinate grows from left to right, which is the usual
way, whereas the Y coordinate grows downward.
Coordinates are associated to squares. The square (0,0) is the coordinates
origin.

Between rows and columns of squares, there are infinitely thin lines
called grid lines or simply lines. Similarily, a finite portion of a grid line
is calleed grid segment.
The intersection of 2 grid lines is an area-less point, called grid point
or point. Grid points are located between 4 squares.

An area of the Grid Plane is a finite set of squares.
Given an area, every square in the plane can either be contained or not.
Thus, it can contain separated regions and 'holes'.
Because an area of the Grid Plane might not contain a square only
partially, it is restricted to contain only vertical and horizontal edges.

This class (Area, with capital A) is a special geometric construction that
represents any area of the Grid Plane in a specific way.
In essence, it is a set of grid segments, called edges, with which it wraps
the contained squares.

Characterization of the edges of an Area:
Any edge of an Area is adjacent to a contained square and a non contained
square at any given point.

If the extreme of two edges meet, we call the meeting grid point a vertex. 
As a consequence, vertexs of an Area do not have coordinates;
they are found in the middle of four squares, each of which has
its own coordinates. We set the convention that a vertex is identified by
the coordinate of the square that spawn below and to the right of the
vertex.

A vertex of an Area is an ending point of exactly 2
edges; an horizontal one and a vertical one.
Note that 2 edges of an area can intersect in a grid point that is not an ending
point of either. In this case, there is no vertex in the intersection.

An interesting property of the Grid Plane is that there is a bijection
between the set of all possible areas and the set of all possible
collections of grid points such that any grid line of the plane contains an
even number of them.
Concretely, the bijection is performed by considering the grid points of the
collection as vertexs of the area, and the other way around.

As a result of this, an arbitrary area of the Grid Plane is
characterized by the set of its vertexs. This is not the case for the
Cartesian Plane, in which the order of the vertex matter.

Given the previous property, Area stores its area as the unordered
set of its vertexs.
We will see how this allows us to avoid an asymptotic linear time with
respect to the size of the area when performing set operations, such as
union, intersection or difference of areas.

Area supports several operations.
For each of them, an algorithm has been designed.
The explanations are included here, as well as a brief cost analysis and
comparison with the standard representation (i.e. a HashSet).
For the rest of this text, assume that this object Area is called a1, n1 is
the size of the area (number of contained points) and m1 is its number of
vertexs. When a method receives another Area as parameter, a2, n2 and m2
are used.
Note that the hypothesis we work with is that for our areas, m << n.


1) contains(Square sq): returns whether a square is contained in this area.
 *
For this method, the Even-odd rule has been used. This rule, which is applicable
to any kind of area and not just one of the Grid Plane, states that
"a point is contained within an area if a ray projected in
any direction from that point intersects with the perimeter of the area
an odd number of times."
A ray is a line that has only one end. It starts at one point and extends
infinitely.
In this case, we propagate the ray horizontally in the positive sense of
the X axis (could be any of the four directions), and count with how many
vertical edges we intersect. As all horizontal edges are parallel to the
propagated line, we don't need to check those for intersections.
As sq is a square of the Grid Plane, the propagated ray has a width of a
surface unit. We call such rays wide rays.
We consider that a wide ray intersects with an edge if the whole width of
the ray intersects with the edge (disregarding the extremes).

The cost of this operation for the standard methods is theta(1), whereas for
Area it is theta(m).


2) contains(Area a2): returns whether every square contained in a2
is contained in a1.

For this method, we use the following property:
An Area a2 is contained in an Area a1 if, and only if, the following 3
conditions are met:
1. No edges of a1 and a2 intersect.
2. At least one vertex of a2 is contained in a1.
3. No vertex of a1 is contained in a2.

The first condition is the general case. For a2 to be contained in a1, no
intersections can happen.
The second condition validates that the case in which a1 and a2 are disjoint
returns false.
The third condition validates that the case in which a2 is seemingly bounded
by a1 but a1 has a hole within the bounds of a2 returns false.

Note that the intersection of the first condition is different in nature than
the one we treated before. In this case, both edges are infinitely thin. If
they just concur on a vertex, we consider that they did not intersect. That
is, they intersect if they form a cross ('+') in the plane, not a letter 'l'
('L').
Whenever 2 edges of different areas intersect, of the surrounding four squares,
there is always one which belongs to each area, one which belongs to both and
one which belong to none. That's why for an area to be contained inside another,
no intersections can happen.

Note also that we abused language when we refered to a vertex being contained by
an area, as everything an area of the Grid Plane can contain is squares.
The actual meaning of a vertex being contained in an area is that
all of its four surrounding squares are contained. This implies that
a vertex over an edge of the area is not contained.

This operation is clearly theta(n2) for an standard representation.
For Area, the cost can be splited in the three cathegories:
1. theta(m1*m2), as the number of vertical or horizontal edges of
an area is equal to the number of vertexs.
2. theta(m1), as we check whether one square is contained in a1, and
we have seen this has cost theta(m1).
3. theta(m1*theta(m2)) = theta(m1*m2), as we have to check whether all m1
vertexs of a1 are contained in a2.
Thus, the total cost is theta(m1*m2).


3) union(Area a2): every square contained in a2 which was not
already contained in a1 is added to a1.

The philosophy to design algorithms for the set operations will be to
simply find all the vertexs that will be found in the resulting area, since,
as we know, they determine the area unequivocally.

To greatly simplify this problem, we will use the following characterization
of the vertexs of an Area:
A grid point is area vertex of an area if, and only if, exactly one or three of
the adjacent squares are contained in the area.

Thus, everything we need to do is to look for grid points that have exactly 1 or 3
adjacent squares contained in either of the areas.

Potential candidates are vertexs of a1 and a2 and the grid points where their
edges intersect, as the count of adjacent squares contained in a1 or a2 will
not change for any other grid point in the plane.

In fact, all intersection of edges will be vertexs. As we have seen before,
in an intersection between edges of diferent areas, one surrounding square
belong to each area, whereas one belongs to both and one to none. Consequently,
3 of the surrounding squares belong to the resulting area and it is a vertex
of the union.

There is a exception to this case: if at some grid point p botn a1 and a2
intersect themselves, p is not a vertex of the union of a1 and a2.
By analyzing each possible case scenario, it can be deduced that
in such cases p will have 2 or 4 adjacent squares of the union.

The cost for a standard representation is theta(n2).
The cost for Area is the sum of:
- finding all edge intersections: theta(m1*m2)
- for each vertex of either area, check whether their adjacent squares are
contained in either area: theta((m1+m2)*(theta(m1)+theta(m2)) =
theta((m1+m2)^2)


4) difference(Area a2): every square contained in a2 which is also
contained in a1 is removed.

For this method, we will use the following property of set theory:
Let A and B be sets, A+B the union of A and B, A-B the difference of A
minus B and A XOR B the symmetric difference of A and B. Then,
A-B = (A+B) XOR B

Applying this property, we just have to do an union operation, and
then for each vertex of a2: if it is contained in the result, remove it.
If it is not contained, add it.

The resulting area is a1-a2.

The cost for the standard approach is theta(n2).
For Area, it is theta((m1+m2)^2) + theta(m2) = theta((m1+m2)^2)


5) intersection(Area a2): any square of a1 which is not in a2 is removed.

This case can also be solved efficiently applying set theory.
We will use the following property:
Let A^B be the intersection of A and B. Then,
A^B = (A XOR B) XOR A+B

We will just use the operations already defined.
This operation is convenient because the symmetric difference is specially
fast: we don't have to check wheter any square or vertex is contained in an
area.

The cost for the standard approach is theta(n1+n2).
For Area, it is theta((m1+m2)^2) + theta(m2) = theta((m1+m2)^2)


6) iteration: the area must offer the functionality
of iterating through its contained squares.

The aim here is to iterate only through the contained squares, with the
least overhead.
This operation is specially tricky in terms of efficiency; so far, we have
been able to avoid asymptotic costs in terms of n. But this is not possible,
because iteratin through n points is at least theta(n).
In case of a HashSet, assuming it can be iterated, the cost to itearte
through each value is theta(1), and thus the overall cost is theta(n), the
asymptotic minimum.

For Area, the algorithm is as follows:
For each row: A wide ray is projected to the right from the beginning of the
row. The x coordinates of the vertical edges that intersect with the ray are
stored in a queue, sorted from smaller to larger.
Between 2 following x values of the queu, all squares are either contained
or not contained.
Assume the first square of the row is contained. The alternative case is 
similar.
All squares until the first x value are contained. Then there is a gap until
the next x value. This way we can iterate through the row simply comparing
the x coordinate of the square with the next element of the queue, and jump
an ammount of squares also determined by the queue when necessary.

A preliminar sort of all edges takes theta(m*log(m)).
Projecting a ray and check with what edges it intersects is theta(m), and it
is done once per row (apoximately theta(log(n))).
Iterating through each point is theta(n).
Thus, the total time is theta(n + m*log(n) + m*log(m)).


7) areaSize(): returns the cardinality of the set of contained squares.

We were not able to find an algorithm for this. Instead, a probabilistic
approach is taken. First, the smallest rectangle containing the whole are is
found. The size is then calculated as the rectangle area multiplied by a
estimation of its density. To estimate the density, 20 points are chosen
randomly within the rectangle and it is checked how many of them are contained.

Luckily, this does not compromise the correctness of the code, as the area
size is only used to help choose the most appropiate next variable.


8) distance(Area r1, Area r2): finds the distance between r1 and r2.

For this operation, we have the precondition that r1 and r2 are rectangles
(i.e. have exactly 4 vertexs).

The distance is the length of the shortest segment such that one extrem of
the segment is in each area.

For 2 rectangles, the length of this segment is the length of the hypotenuse
of a triangle with the x offset and the y offset of the rectangles as
cathetuss. The x offset is the distance between their horizontal edges if
they were alineated. Similarly for the y offset.



Those were the operations that Area offer as its API. It also has private
methods with algorithms.


9) build edges: given a set of vertexs, build all the edges that form the
area.

This operation is needed to rebuild the area after a set operation.
We know that each vertex of the area is an ending point of exactly 2
edges; an horizontal one and a vertical one.

Moreover, there is a property of areas of the Grid Plane that allows us to
find out if a vertex is the top or bottom end of the vertical edge (and
similarly for the horizontal edge).
Remember that each grid line has an even number of vertexs. Consequently,
a vertex has an odd number of neightbour vertexs in the same vertical line;
an even number of vertexs in one direction and an odd number in the other.

The property says as follows:
There is an edge between a vertex and the closest vertex in the direction
where there is an odd number of them.

The algorithm to build the edges is as follows:
For each vertex, if it is the top end of its vertical edge, add the edge.
                 else, do nothing.
                 if it is the left end of its horizontal edge, add the edge.
                 else, do nothing.

So, in other words, the top end of each edge is responsible for 'building' it.
This way, each edge is added exactly once. Similarly for horizontal edges.

 */
package interiores.business.models.backtracking.area;

import interiores.business.models.Orientation;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author nil.mamano
 */
public class Area 
    implements Iterable<Point> {
    
    private List<GridPoint> vertexs;
    private List<VerticalEdge> verticalEdges;
    private List<HorizontalEdge> horizontalEdges;
        
    /**
     * Given area x' value, permits fast access to all vertexs of the area
     * of the form (x,y), where x=x'.
     */
    private HashMap<Integer,List<GridPoint>> vertexsStoredByX;
    /**
     * Given area y' value, permits fast access to all vertexs of the area
     * of the form (x,y), where y=y'.
     */
    private HashMap<Integer,List<GridPoint>> vertexsStoredByY;
    
    
    private HashMap<Integer,List<VerticalEdge>> verticalEdgesStoredByX;
    private HashMap<Integer,List<HorizontalEdge>> horizontalEdgesStoredByY;
    
    private static final int SAMPLE_SIZE = 20;
    
    
    /**
     * Default constructor.
     */
    public Area() {
        vertexs = new ArrayList<GridPoint>();
        initializeAreaFromVertexs();        
    }
    
    /**
     * Constructor from another area.
     */
    public Area(Area a) {        
        vertexs = new ArrayList(a.vertexs);        
        initializeAreaFromVertexs();        
    }
    
    /**
     * Constructor from a rectangle.
     */
    public Area(Rectangle r) {
//        if (r.width == 0 || r.height == 0)
//            throw new UnsupportedOperationException("The rectangle is empty.");
        
        this.vertexs = new ArrayList<GridPoint>();

        vertexs.add(new GridPoint(r.x, r.y));
        vertexs.add(new GridPoint(r.x+r.width, r.y));
        vertexs.add(new GridPoint(r.x+r.width, r.y+r.height));
        vertexs.add(new GridPoint(r.x, r.y+r.height));
        
        initializeAreaFromVertexs();        
    }
        
    /**
     * Private constructor.
     */
    private Area(List<GridPoint> vertexs) {
        this.vertexs = new ArrayList(vertexs);
        initializeAreaFromVertexs();        
    }
    
    /**
     * The Square class is only visible within the package. To communicate
     * with the exterior, Area uses Points.
     * @param p
     * @return 
     */
    public boolean contains(Point p) {        
        return contains(new Square(p.x, p.y));      
    }
    
    /**
     * Returns whether a given square is contained in this area.
     * (operation 1 of the opening explanation)
     * @param sq the square which might be contained.
     * @return true if sq is contained in the area
     */
    boolean contains(Square sq) {
        
        //apply ray-casting algorithm; direction: positive side of the x axis
        int intersectionCount = 0;
        RightRay ray = new RightRay(sq);
        
        for (VerticalEdge edge : verticalEdges)
            if (edge.intersects(ray)) ++intersectionCount;
        
        //return result according to the odd-even rule
        return (intersectionCount % 2) == 1;
    }
    
    
    /**
     * Returns whether a given area is within this area.
     * (operation 2 of the opening explanation)
     * @param area the area which might be contained.
     */
    public boolean contains(Area a) {
        
        //1) check whether at least one vertex is contained
        if (! contains(a.vertexs.get(0)))
            return false;
        
        //2) check that no edge of the the area intersects with an edge of this
        //area
        if (doEdgesIntersect(a)) return false;
        
        //3) check that no vertex of the implicit parameter is within the area
        //of the polygon
        for (GridPoint vertex : vertexs)
            if (a.contains(vertex)) return false;

        return true;       
    }
    
    
    /**
     * This area is the result of the union of this area and a. a is not
     * modified.
     * (operation 3 of the opening explanation)
     * @param a
     */
    public void union(Area a) {
        Set<GridPoint> newAreaVertexs = new HashSet<GridPoint>();
        
        if (vertexs.isEmpty()) {
            newAreaVertexs.addAll(a.vertexs);
            vertexs.clear();
            vertexs.addAll(newAreaVertexs);            
            initializeAreaFromVertexs();
            return;
        }
            
        //1) add intersections between this area and a, except double
        // intersections
        List<GridPoint> intersectPoints = getEdgesIntersect(a);
        for (GridPoint v : intersectPoints) {
            if (newAreaVertexs.contains(v))
                newAreaVertexs.remove(v);
            else
                newAreaVertexs.add(v);
        }     
        
        //2) find vextexs that have an odd number of adjacent contained squares
        // in either area
        for (GridPoint v : vertexs) {
            List<Boolean> thisAdjSqs = areAdjacentSquaresContained(v);
            List<Boolean> aAdjSqs = a.areAdjacentSquaresContained(v);
            int count = 0;
            if (thisAdjSqs.get(0) || aAdjSqs.get(0)) ++count;
            if (thisAdjSqs.get(1) || aAdjSqs.get(1)) ++count;
            if (thisAdjSqs.get(2) || aAdjSqs.get(2)) ++count;
            if (thisAdjSqs.get(3) || aAdjSqs.get(3)) ++count;
            if (count%2 == 1) newAreaVertexs.add(v);
        }
        
        for (GridPoint v : a.vertexs) {
            List<Boolean> thisAdjSqs = areAdjacentSquaresContained(v);
            List<Boolean> aAdjSqs = a.areAdjacentSquaresContained(v);
            int count = 0;
            if (thisAdjSqs.get(0) || aAdjSqs.get(0)) ++count;
            if (thisAdjSqs.get(1) || aAdjSqs.get(1)) ++count;
            if (thisAdjSqs.get(2) || aAdjSqs.get(2)) ++count;
            if (thisAdjSqs.get(3) || aAdjSqs.get(3)) ++count;
            if (count%2 == 1) newAreaVertexs.add(v);
        }
        
        vertexs.clear();
        vertexs.addAll(new ArrayList(newAreaVertexs));
        initializeAreaFromVertexs();
    }
    
    /**
     * This area is the result of the difference of this area minus a. a is not
     * modified.
     * (operation 4 of the opening explanation)
     * @param area 
     */
    public void difference(Area a) {
        union(a);
        symmetricDifference(a);
    }
    
    
    /**
     * This area is the result of the intersection of this area and a. a is not
     * modified.
     * (operation 5 of the opening explanation)
     * @param a
     */
    public void intersection(Area a) {       
        //A^B = (A XOR B) XOR A+B
        Area union = new Area(this);
        union.union(a);
        
        symmetricDifference(a);
        symmetricDifference(union);
    }
    
    
    private void initializeAreaFromVertexs() {
        
//        if (! isValidArea())
//            throw new UnsupportedOperationException("Corrupted area");
                
        //initialize maps of vertexs
        vertexsStoredByX = new HashMap<Integer,List<GridPoint>>();
        vertexsStoredByY = new HashMap<Integer,List<GridPoint>>();
        
        for (GridPoint p : vertexs) {
            if (!vertexsStoredByX.containsKey(p.x))
                vertexsStoredByX.put(p.x, new ArrayList<GridPoint>());
            vertexsStoredByX.get(p.x).add(p);
            
            if (!vertexsStoredByY.containsKey(p.y))
                vertexsStoredByY.put(p.y, new ArrayList<GridPoint>());
            vertexsStoredByY.get(p.y).add(p);
        }

        buildEdges();
        
        //initialize maps of edges
        verticalEdgesStoredByX = new HashMap<Integer,List<VerticalEdge>>();
        horizontalEdgesStoredByY = new HashMap<Integer,List<HorizontalEdge>>();
        
        for (VerticalEdge e : verticalEdges) {
            if (!verticalEdgesStoredByX.containsKey(e.x))
                verticalEdgesStoredByX.put(e.x, new ArrayList<VerticalEdge>());
            verticalEdgesStoredByX.get(e.x).add(e);
        }
        for (HorizontalEdge e : horizontalEdges) {
            if (!horizontalEdgesStoredByY.containsKey(e.y))
                horizontalEdgesStoredByY.put(e.y, new ArrayList<HorizontalEdge>());
            horizontalEdgesStoredByY.get(e.y).add(e);
        }
    }

    
    /**
     * Synchronizes the edges with the vertexs.
     * (operation 9 of the opening explanation)
     */
    private void buildEdges() {
        //restart edges
        verticalEdges = new ArrayList<VerticalEdge>();
        horizontalEdges = new ArrayList<HorizontalEdge>();

        for(GridPoint vertex : vertexs) {

            //vertical edge
            int VerticalCount = 0;
            GridPoint closestAbove = null;
            GridPoint closestBelow = null;
            //count how many vertexs with the same x value have area higher y value
            //and store the closest vertex
            for(GridPoint p : vertexsStoredByX.get(vertex.x)) {
                if (p.y > vertex.y) {
                    ++VerticalCount;
                    //update the closest vertex, if apropiate
                    if (closestBelow == null || closestBelow.y > p.y)
                        closestBelow = p;
                }
                else if (p.y < vertex.y) {
                    if (closestAbove == null || closestAbove.y < p.y)
                        closestAbove = p;
                }
            }
            if (VerticalCount % 2 == 1)
                verticalEdges.add(new VerticalEdge(vertex.x, closestBelow.y, vertex.y));
            //else verticalEdges.add(new VerticalEdge(vertex.x, vertex.y, closestAbove.y));

            //horizontal edge
            int HorizontalCount = 0;
            GridPoint closestLeft = null;
            GridPoint closestRight = null;
            //count how many vertexs with the same y value have area higher x value
            //and store the closest vertex
            for(GridPoint p : vertexsStoredByY.get(vertex.y)) {
                if (p.x > vertex.x) {
                    ++HorizontalCount;
                    //update the closest vertex, if apropiate
                    if (closestRight == null || closestRight.x > p.x)
                        closestRight = p;
                }
                else if (p.x < vertex.x) {
                    if (closestLeft == null || closestLeft.x < p.x)
                        closestLeft = p;
                }
            }
            if (HorizontalCount % 2 == 1)
                horizontalEdges.add(new HorizontalEdge(vertex.y, closestRight.x, vertex.x));
            //else horizontalEdges.add(new HorizontalEdge(vertex.y, vertex.x, closestLeft.x));              
        }
    }
    
    
    /**
     * Returns whether a given gridPoint is contained in the area.
     * @param point the grid point which might be contained.
     * @return true if point is contained in the polygon
     */
    private boolean contains(GridPoint point) {
        List<Boolean> adjacentSquares = areAdjacentSquaresContained(point);
        for (Boolean b : adjacentSquares)
            if (! b) return false; 
        
        return true;
    }
    
    /**
     * Given a Grid Point, returns whether each of the adjacent squares is
     * contained.
     * This is optimized acording to the following observation:
     * to see if a square is contained, if it is known whether an adjacent
     * square is contained or not, it must only be checked whether there
     * is an edge between them.
     * @param p
     * @return list with exactly 4 booleans. Each one indicates whether a
     * certain adjacent square is contained, in this order: top left square,
     * top right square, bottom left square and bottom right square.
     */
    private List<Boolean> areAdjacentSquaresContained(GridPoint p) {
                
        // +-------------+-------------+
        // |             |             |
        // |             |             |
        // |             u             |
        // | topLeftSq   p  topRightSq |
        // |             E             |
        // |             |             |
        // |             |             |
        // +----leftE----p---rightE----+
        // |             |             |
        // |             d             |
        // | bottomLeft  o bottomRight |
        // | Sq          w Sq          |
        // |             n             |
        // |             E             |
        // |             |             |
        // +-------------+-------------+
             
        //one boolean for each adjacent square: true if they are contained,
        //false otherwise
        boolean topLeftSq, topRightSq, bottomRightSq, bottomLeftSq;
        //one boolean for each adjacent line segment: true if they are part
        //of an edge, false otherwise
        //upE is not needed: we will reach topLeftSq through bottomLeftSq
        boolean rightE, downE, leftE;
        rightE = downE = leftE = false;
        
        //find which line segments are edges
        if (verticalEdgesStoredByX.containsKey(p.x)) {
            for (VerticalEdge v : verticalEdgesStoredByX.get(p.x))
                if (v.contain(new VerticalEdge(p.x, p.y+1, p.y)))
                    downE = true;
        }
        if (horizontalEdgesStoredByY.containsKey(p.y)) {
            for (HorizontalEdge v : horizontalEdgesStoredByY.get(p.y)) {
                if (v.contain(new HorizontalEdge(p.y, p.x+1, p.x)))
                    rightE = true;
                if (v.contain(new HorizontalEdge(p.y, p.x, p.x-1)))
                    leftE = true;
            }
        }
        
        //check if bottomRight is contained with usual method
        bottomRightSq = contains(new Square(p.x, p.y));
        
        //bottomLeftSq will be the same as bottomRightSq unless downE is true
        if (downE) bottomLeftSq = ! bottomRightSq;
        else bottomLeftSq = bottomRightSq;
        
        //topRightSq will be the same as bottomRightSq unless rightE is true
        if (rightE) topRightSq = ! bottomRightSq;
        else topRightSq = bottomRightSq;
        
        //topLeftSq will be the same as bottomLeftSq unless leftE is true
        if (leftE) topLeftSq = ! bottomLeftSq;
        else topLeftSq = bottomLeftSq;
        
        List<Boolean> result = new ArrayList<Boolean>();
        result.add(topLeftSq);
        result.add(topRightSq);
        result.add(bottomLeftSq);
        result.add(bottomRightSq);
        
        return result;
    }

    /**
     * Returns whether 2 edges of different areas intersect somewhere in the
     * plane.
     * @param a
     * @return 
     */
    private boolean doEdgesIntersect(Area a) {
        for (VerticalEdge myEdge : verticalEdges)
            for (HorizontalEdge aEdge : a.horizontalEdges)
                if (myEdge.intersects(aEdge)) return true;
        
        for (VerticalEdge aEdge : a.verticalEdges)
            for (HorizontalEdge myEdge : horizontalEdges)
                if (aEdge.intersects(myEdge)) return true;
        
        return false;    
    }
    
    
    /**
     * Returns all grid points where 2 edges of this area and a intersect somewhere
     * in the plane.
     * @param a
     * @return 
     */
    private List<GridPoint> getEdgesIntersect(Area a) {
        List<GridPoint> intersectionPoints = new ArrayList<GridPoint>();
        
        for (VerticalEdge myEdge : verticalEdges)
            for (HorizontalEdge aEdge : a.horizontalEdges)
                if (myEdge.intersects(aEdge))
                    intersectionPoints.add(myEdge.getIntersection(aEdge));

        for (VerticalEdge aEdge : a.verticalEdges)
            for (HorizontalEdge myEdge : horizontalEdges)        
                if (aEdge.intersects(myEdge))
                    intersectionPoints.add(aEdge.getIntersection(myEdge));
        
        return intersectionPoints;
    }

    private void symmetricDifference(Area a) {
        Set<GridPoint> vertexSet = new HashSet<GridPoint>();
        vertexSet.addAll(vertexs);
        
        for (GridPoint p : a.vertexs){
            if (vertexSet.contains(p)) vertexSet.remove(p);
            else vertexSet.add(p);
        }
        
        vertexs.clear();
        vertexs.addAll(vertexSet);
        initializeAreaFromVertexs();       
    }
    
    /**
     * Returns whether the area is empty.
     * @return 
     */
    public boolean isEmpty() {
        return vertexs.isEmpty();
    }
    
    /**
     * Returns the smallest rectangle that contains this area.
     * @return 
     */
    public Rectangle getBoundingRectangle() {
        int maxX, minX, maxY, minY;
        maxX = minX = maxY = minY = 0;
        
        Iterator<GridPoint> it = vertexs.iterator();
        if (it.hasNext()) {
            GridPoint v = it.next();
            maxX = minX = v.x;
            maxY = minY = v.y;
            while (it.hasNext()) {
                v = it.next();
                if (v.x > maxX) maxX = v.x;
                else if (v.x < minX) minX = v.x;
                if (v.y > maxY) maxY = v.y;
                else if (v.y < minY) minY = v.y;
            }
        }
        return new Rectangle(minX, minY, maxX-minX, maxY-minY);
    }
    
    /**
     * Returns an Orthogonal Area that contains the same geometry as this Area,
     * but shifted 'distance' units to 'orientation'.
     * @param distance
     * @param orientation
     * @return 
     */
    public void shift(int distance, Orientation orientation) {
        List<GridPoint> newVertexs = new ArrayList<GridPoint>();
        if (orientation == Orientation.N)
            for (GridPoint v : vertexs)
                newVertexs.add(new GridPoint(v.x, v.y-distance));
        else if (orientation == Orientation.S)
            for (GridPoint v : vertexs)
                newVertexs.add(new GridPoint(v.x, v.y+distance));
        else if (orientation == Orientation.W)
            for (GridPoint v : vertexs)
                newVertexs.add(new GridPoint(v.x-distance, v.y));
        else if (orientation == Orientation.E)
            for (GridPoint v : vertexs)
                newVertexs.add(new GridPoint(v.x+distance, v.y));
        
        vertexs.clear();
        vertexs.addAll(newVertexs);
        initializeAreaFromVertexs();
    }

    /**
     * Returns area probabilistic estimation of the area size.
     * SAMPLE_SIZE points from inside the bounding rectangle are chosen 
     * randomly. From these, an estimation of the density of area inside the
     * bounding rectangle is infered.
     * (operation 7 of the opening explanation)
     * This can be optimized using uniformly distributed squares.
     * 
     * @return an estimation of the size.
     */
    public int areaSize() {
        if (vertexs.isEmpty()) return 0;
        
        Rectangle boundingRectangle = getBoundingRectangle();
        int xMin = boundingRectangle.x;
        int xMax = boundingRectangle.x + boundingRectangle.width;
        int yMin = boundingRectangle.y;
        int yMax = boundingRectangle.y + boundingRectangle.height;
        
        Random randomGenerator = new Random();
        int squareDensity = 0;
        for (int i = 0; i < SAMPLE_SIZE; ++i) {
            int Xrand = xMin + randomGenerator.nextInt(boundingRectangle.width);
            int Yrand = yMin + randomGenerator.nextInt(boundingRectangle.height);
            if (contains(new Square(Xrand, Yrand))) ++squareDensity;
        }
        
        int boundingRectangleSize = (xMax - xMin) * (yMax - yMin);        
        return (boundingRectangleSize * squareDensity) / SAMPLE_SIZE;
    }

    /**
     * Pre: this area is a rectangle.
     * Expands the rectangle distance units in all directions.
     * @param distance
     * @return 
     */
    public void expand(int distance) {
        Rectangle r = getBoundingRectangle();
        r.x -= distance;
        r.y -= distance;
        r.height += 2* distance;
        r.width += 2*distance;

        this.vertexs = new Area(r).vertexs;
        initializeAreaFromVertexs();
    }
    
    /**
     * Returns the euclidean distance between 2 points.
     * @param sq1
     * @param sq2
     * @return 
     */
    public static float distance(Point sq1, Point sq2) {
        return euclideanDistance(new Square(sq1.x, sq1.y), new Square(sq2.x, sq2.y));
    }

    private static float euclideanDistance(Square sq1, Square sq2) {
        float xOffset = Math.abs(sq1.x - sq2.x) - 1;
        //-1 because squares are 1 unit wide
        float yOffset = Math.abs(sq1.y - sq2.y) - 1;
        return (float) Math.sqrt(xOffset*xOffset + yOffset*yOffset);
    }
    
    /**
     * Returns the mimimum euclidean distance between 2 Rectangles.
     * (operation 8 of the opening explanation)
     * @param r1
     * @param r2
     * @return 
     */
    public static float distance(Rectangle r1, Rectangle r2) {
        int xOffset, yOffset;
        boolean xCoordinateIntersect = false;
        xCoordinateIntersect |= r1.x >= r2.x && r1.x <= (r2.x+r2.width);
        xCoordinateIntersect |= (r1.x+r1.width) >= r2.x && (r1.x+r1.width) <= (r2.x+r2.width);
        xCoordinateIntersect |= r2.x >= r1.x && r2.x <= (r1.x+r1.width);
        if (xCoordinateIntersect) xOffset = 0;
        else xOffset = Math.min(Math.abs((r1.x+r1.width)-r2.x), Math.abs((r2.x+r2.width)-r1.x));
        
        boolean yCoordinateIntersect = false;
        yCoordinateIntersect |= r1.y >= r2.y && r1.y <= (r2.y+r2.height);
        yCoordinateIntersect |= (r1.y+r1.height) >= r2.y && (r1.y+r1.height) <= (r2.y+r2.height);
        yCoordinateIntersect |= r2.y >= r1.y && r2.y <= (r1.y+r1.height);
        if (yCoordinateIntersect) yOffset = 0;
        else yOffset = Math.min(Math.abs((r1.y+r1.height)-r2.y), Math.abs((r2.y+r2.height)-r1.y));
        
        return euclideanDistance(new Square(0,0), new Square(xOffset, yOffset));
    }

    @Override
    public Iterator<Point> iterator() {
        return new AreaIterator();
    }
 
    /**
     * Area iterator through all contained squares.
     * The area iterates through squares, but returns them as points.
     */
    private class AreaIterator
        implements Iterator<Point> {
        
        private Square currentSquare;
        
        private int xMin, xMax;
        private int yMin, yMax;
        
        private static final int RES = 5;
        
        public AreaIterator() {

            Rectangle boundingRectangle = getBoundingRectangle();
            
            xMin = boundingRectangle.x;
            xMax = xMin + boundingRectangle.width;
            yMin = boundingRectangle.y;
            yMax = yMin + boundingRectangle.height;
            
            currentSquare = new Square(xMin - RES, yMin);
            advanceToNext();
        }

        @Override
        public boolean hasNext() {
            return contains(currentSquare);
        }
    
        @Override
        public Point next() {            
            if (contains(currentSquare)) {
                Point ret = new Point(currentSquare.x, currentSquare.y);
                advanceToNext();
                return ret;
            }
            else throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Removal of a particular element in the domain is not supported");
        }
        
        private void advanceToNext() {
            // First, we advance towards the next position
            currentSquare.x += RES;
            
            while (currentSquare.y <= yMax) {
                while (currentSquare.x <= xMax) {
                    if (contains(currentSquare)) return;  
                    else {
                        Square next = nextContained(currentSquare);
                        if (next != null) currentSquare = next;
                        else currentSquare.x = xMax + 1;
                    }
                } 
                currentSquare.y += RES;
                currentSquare.x = xMin;
            }
        }

        /**
         * Given that square is a non-contained square of this area, returns the
         * next contained square in the same row, assuming squares are sorted
         * from left to right.
         * To find the next square, we have to find the first edge that
         * intersects the ray projected by the square to the right.
         * If there is none, returns null.
         * @param square
         * @return 
         */
        private Square nextContained(Square square) {
            //iterate through all vertical edges and store the x coordinate of
            //the edge that intersects the ray and is more to the left
            
            boolean found = false;
            int mostLeftX = -1; //arbitrary initialization; value is only
                                //relevant if found = true
            for (VerticalEdge edge : verticalEdges) {
                if (edge.intersects(new RightRay(square))) {
                    if (! found || mostLeftX > edge.x) {
                        found = true;
                        mostLeftX = edge.x;
                    }
                }
            }
            if (! found) return null;
            return new Square(mostLeftX, square.y);
        }   
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String nl = System.getProperty("line.separator");

        result.append("Area:" + nl);
        result.append("List of vertexs:" + nl);
        for (GridPoint vertex : vertexs)
            result.append(vertex.toString() + "\t");
        
        result.append(nl + "List of vertical segments:" + nl);
        for (VerticalEdge edge : verticalEdges)
            result.append(edge.toString() + "\t");
        result.append(nl);
        return result.toString();
    }
}
