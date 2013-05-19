/*
 * An Orthogonal Area represents an arbitrary area of the Grid Plane.
 * The Grid Plane is a plane with a special topology designed to behave
 * nicely in the context of the problem of Interiors design.
 *
 * The Grid Plane:
 * It is made of adjacent surface units, which have the shape of a square.
 * A point of this plane is not infinitessimaly small. It has a positive area,
 * equal to one surface unit. Similarily, a line of this plane is as wide as
 * a surface unit.
 * 
 * The plane only has positive coordinates, which can be arbitrarily large.
 * The X coordinate grows from left to right, which is the usual
 * way, whereas the Y coordinate grows downward.
 * Thus, the coordinate origin is the top left surface unit of the plane.
 * 
 * To distinguish from the lines mentioned before, the infinitely thin lines
 * between rows and columns of surface units are called grid lines.
 * The intersection of 2 grid lines is an area-less point, called grid point.
 * 
 * An area of the Grid Plane is a finite set of surface units (called points).
 * Given an area, every point in the plane can either be contained or not.
 * Because an area of the Grid Plane might not contain a surface unit only
 * partially, it is restricted to contain only vertical and horizontal edges
 * (hence, the name).
 * 
 * An Orthogonal Area is a special geometric construction designed to represent
 * any area of the Grid Plane.
 * Necessarily, it can contain separated regions, and each of those can have 'holes'.
 * In essence, it is a set of grid segments, called edges.
 * The way an Orthogonal Area represents its area
 * is by wrapping the contained surface units with its edges.
 * 
 * All edges of an Orthogonal Area divide contained points from non contained
 * points.
 * 
 * If the extreme of two edges meet, we call the meeting grid point a vertex. 
 * As a consequence, vertexs of an Orthogonal Area do not have coordinates;
 * they are found in the middle of up to four points, each of which has
 * its own coordinates. We set the convention that a vertex is identified by
 * the coordinate of the surface unit that spawn below and to the right of the
 * vertex.
 * 
 * A vertex of an Orthogonal Area of the Grid Plane is an ending point of exactly 2
 * edges; an horizontal one and a vertical one.
 * Note that 2 edges of an area can intersect in a grid point that is not an ending
 * point of either. In this case, there is no vertex in the intersection.
 * 
 * An interesting property of the Grid Plane is that there is a bijection
 * between the set of all possible areas and the set of all possible
 * collections of grid points such
 * that any grid line of the plane contains an even number of them.
 * Concretely, the bijection is performed by considering the grid points of the
 * collection as vertexs of the area, and the other way around.
 * As a result of this, an arbitrary area of the Grid Plane is
 * characterized by the set of its vertexs. This is not the case for the
 * Cartesian Plane, in which the order of the vertex matter.
 * 
 * Given the previous property, OrthogonalArea stores its area as the unordered
 * set of its vertexs.
 * The purpose of this representation, instead of directly storing all points
 * contained in the area (for example, with a matrix of booleans), is being able
 * to avoid an asymptotic linear time with respect to the size of the area when
 * performing set operations, such as union, intersection or difference of areas.
 * 
 * OrthogonalArea supports 6 operations. An algorithm has been designed for
 * each of them.
 * 
 * 
 * 1) contains(Point p): returns whether a point is contained in this area.
 *
 * For this method, the Even-odd rule has been used. This rule, which is applicable
 * to any kind of area and not just one of the Grid Plane, states that
 * "a point is contained within an area if a ray projected in
 * any direction from that points intersects with the perimeter of the area
 * an odd number of times."
 * 
 * In this case, we propagate the line horizontally in the positive sense of
 * the X axis (could be any of the four directions), and count with how many
 * vertical edges we intersect. As all horizontal edges are parallel to the
 * propagated line, we don't need to check those for intersections.
 * As the point p is a point of the Grid Plane,
 * the propagated line has a width of a surface unit. We consider that such a 
 * line intersects with a edge if the whole width of the line intersects with
 * the edge (disregarding the extremes).
 * 
 * 
 * 2) contains(Area a): returns whether every surface unit contained in 'a'
 * is contained in this area.
 * 
 * For this method, we use the following property:
 * An Area a2 is contained in an Area a1 if, and only if, the following 3
 * conditions are met:
 * 1. No edges of a1 and a2 intersect.
 * 2. At least one vertex of a2 is contained in a1.
 * 3. No vertex of a1 is contained by a2.
 * 
 * The first condition is the general case. For a2 to be contained in a1, no
 * intersections can happen.
 * The second condition validates that the case in which a1 and a2 are disjoint
 * returns false.
 * The third condition validates that the case in which a2 is seemingly bounded
 * by a1 but a1 has a hole inside the bounds of a2 returns false.
 * 
 * Note that the intersection of the first condition is different in nature than
 * the one we treated before. In this case, both edges are infinitely thin. If
 * they just concur on a vertex, we consider that they did not intersect. That
 * is, they intersect if they form a cross ('+') in the plane, not a letter 'l'
 * ('L') or 't' ('T').
 * Whenever 2 edges of different areas intersect, of the surrounding four points,
 * there is always one which belongs to each area, one which belongs to both and
 * one which belong to none. That's why for an area to be contained inside another,
 * no intersections can happen.
 * 
 * Note also that we abused language when we refered to a vertex being contained by
 * an area, as everything an area of the Grid Plane can contain is points of
 * said plane. The actual meaning of a vertex being contained in an area is that
 * all of its four surrounding points are contained. This implies that
 * a vertex over an edge of the area is not contained.
 * 
 * 
 * 3) union(Area a): every surface unit contained in 'a' which was not
 * already contained is added to this area.
 * 
 * The philosophy to design algorithms for the set operations will be to
 * simply find all the vertexs that will be found in the resulting area, since,
 * as we know, they determine the area unequivocally.
 * 
 * To greatly simplify this problem, we will use the following characterization
 * of the vertexs of an Orthogonal Area:
 * A grid point is a vertex of an area if, and only if, exactly one or three of
 * the adjacent points are contained in the area.
 * 
 * Thus, everything we need to do is to look for grid points that have exactly 1 or 3
 * adjacent points contained in either of the areas (say, a1 and a2).
 * 
 * Moreover, we only have to check as potential vertexs the vertexs of a1 and
 * a2 and the grid points where their edges intersect, as the count of adjacent
 * points contained in a1 or a2 will not change for any other drid point in the plane.
 * 
 * Based on the forementioned principles, we can further finetune the algorithm.
 * The resulting area's vertexs will be all the grid points that fall under one of the
 * following categories:
 * 1. It is a vertex of one of the areas and it is disjoint from the other.
 * 2. It is an intersection of edges of both areas.
 * 3. It is a vertex of one of the areas not disjoint nor contained from the
 * other and the count of adjacent points contained in either area is 1 or 3.
 * 
 * In the category 1 we introduced the notion of a vertex being disjoint from an area.
 * We say a vertex is disjoint from an area if none of its surrounding points
 * are contained in it. Please note that, therefore, the contains and the
 * disjoint operations for grid points and areas are NOT opposite; a grid point in the
 * edge is neither contained nor disjoint.
 * 
 * As the vertex is disjoint from the other area, its surrounding points will
 * not change state, and thus the vertex remains in the union.
 * 
 * In the category 2 we add all intersection of edges. As we have seen before,
 * in an intersection between edges of diferent areas, one surrounding point
 * belong to each area, whereas one belongs to both and one to none. Consequently,
 * 3 of the surrounding points belong to the resulting area and it is a vertex.
 * 
 * Finally, in the third category fall the vertexs of a1 or a2 that are over an
 * edge of the other area.
 * 
 * Notice that vertexs which are contained in either area do not need to be
 * considered. They have four surrounding spots contained to that area.
 * 
 * 
 * 4) difference(Area a): every surface unit contained in 'a' which is also
 * contained in this area is removed.
 * 
 * For this method, we will use the following property of set theory:
 * Let A and B be sets, A+B the union of A and B, A-B the difference of A
 * minus B and A XOR B the symmetric difference of A and B. Then,
 * A-B = (A+B) XOR B
 * 
 * Applying this property, we just have to do an union operation, and
 * then for each vertex of B: if it is contained in the result, remove it.
 * If it is not contained, add it.
 * 
 * The resulting area is A-B.
 * 
 * 
 * 5) intersection(Area a): any point which is not in a is removed from this area.
 * 
 * This case can also be solved efficiently applying set theory.
 * We will use the following property:
 * Let A^B be the intersection of A and B. Then,
 * A^B = (A XOR B) XOR A+B
 * 
 * We will just use the operations already defined.
 * This operation is convenient because the symmetric difference is specially
 * fast: we don't have to check wheter any point or vertex is contained in an
 * area.
 * 
 * 6) iteration: the area must offer the functionality
 * of iterating through its contained points.
 * 
 * The aim here is to iterate only through the contained points, with the
 * least overhead. The algorithm goes as follows:
 * 
 * Set (0,0) as 'currentPoint', the point being iterated.
 * If currentPoint is valid, move it one point to the right.
 * If currentPoint is not valid, project a ray from currentPoint to the right.
 *      Continue iterating from the point right of the first edge intersected
 *      by the ray.
 *      If the ray does not intersect with any edge, move currentPoint to the
 *      start of the next row.
 * 
 * Proceed until the row of the currentPoint is larger than the row of any
 * vertex.
 * 
 * 
 * Those were the 6 operations that OrthogonalArea offer as its API. Naturally,
 * it also has private methods with algorithms.
 * 
 * 7) build edges: given a set of vertexs, build all the edges that form the
 * area.
 * 
 * This operation is needed to rebuild the area after a set operation.
 * We know that each vertex of the area is an ending point of exactly 2
 * edges; an horizontal one and a vertical one.
 * 
 * Moreover, there is a property of areas of the Grid Plane that tells us
 * if a vertex is the top or bottom end of the vertical edge (and similarly for
 * the horizontal edge).
 * Remember that each grid schuss has an even number of vertexs. Consequently,
 * in the vertical schuss where the vertex is located there will be an even
 * number of vertexs in one direction and an odd number in the other.
 * 
 * The property says as follows:
 * There is an edge between the vertex and the closest vertex in the direction
 * where there is an odd number of them.
 * 
 * The algorithm to build the edges is as follows:
 * For each vertex, if it is the top end of its vertical edge, add the edge.
 *                  else, do nothing.
 *                  if it is the left end of its horizontal edge, add the edge.
 *                  else, do nothing.
 * 
 * So, in other words, the top end of each edge is responsible for 'building' it.
 * This way, each edge is added exactly once. Similarly for horizontal edges.
 * 
 */
package interiores.business.models.backtracking.orthogonalArea2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author nil.mamano
 */
public class OrthogonalArea {
    private List<OrthogonalPolygon> polygons;
    
    /**
     * Default constructor.
     */
    public OrthogonalArea() {
        polygons = new ArrayList<OrthogonalPolygon>();
    }
    
    /**
     * Returns whether a given point is within the area.
     * A point over an edge or vertex of the area is considered within.
     */
    public boolean contains(Point point) {
        for (OrthogonalPolygon polygon : polygons)
            if (polygon.contains(point)) return true;
        return false;
    }
    
    /**
     * Returns whether a given orthogonal polygon is within the area.
     * A point over an edge or vertex of the area is considered within.
     */
    public boolean contains(OrthogonalPolygon p) {
        for (OrthogonalPolygon polygon : polygons)
            if (polygon.contains(p)) return true;
        return false;        
    }
    
    public void add(OrthogonalArea area) {
        for (OrthogonalPolygon p : area.polygons) add(p);
    }
    
    public void remove(OrthogonalArea area) {
        for (OrthogonalPolygon p : area.polygons) remove(p);
    }
    
    /**
     * Adds the polygon p to the area.
     * Adding a polygon might result in less disjoint polygons.
     * @param p 
     */
    private void add(OrthogonalPolygon p) {
        Iterator<OrthogonalPolygon> it = polygons.iterator();
        
        //we are going to move to a list all polygons that will be merged into one 
        List<OrthogonalPolygon> mergedPolygons = new ArrayList<OrthogonalPolygon>();
        while (it.hasNext()) {
            OrthogonalPolygon myPolygon = it.next();
            if (! myPolygon.disjoint(p)) {
                it.remove();
                mergedPolygons.add(myPolygon);
            }
        }
        
        //now that we have all the polygons that will form a new one, we calculate
        //this new polygon
        for (OrthogonalPolygon myPolygon : mergedPolygons)
            p.add(myPolygon);
        
        //add this new polygon to the list of polygons
        polygons.add(p);
    }
    

    /**
     * Removes the area of a polygon from the actual area.
     * Removing a polygon might result in either more or less disjoint polygons.
     * @param p 
     */
    private void remove(OrthogonalPolygon p) {
        Iterator<OrthogonalPolygon> it = polygons.iterator();
        
        //we are going to move to a list all polygons that will be modified
        //due to the remove opperation
        List<OrthogonalPolygon> affectedPolygons = new ArrayList<OrthogonalPolygon>();
        while (it.hasNext()) {
            OrthogonalPolygon myPolygon = it.next();
            if (p.contains(myPolygon)) {
                //this polygon is entirely deleted
                it.remove();
            }
            else if (! myPolygon.disjoint(p)) {
                affectedPolygons.add(myPolygon);
                it.remove();
            }
        }
        
        //for each polygon in affectedPolygons, after the remove operations
        //there might be 1..n polygons.
        for (OrthogonalPolygon myPolygon : affectedPolygons)
            polygons.addAll(myPolygon.resultingPolygonsFromCut(p));
        
    }
}
