
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
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Special geometric construction that represents any area of the Grid Plane.
 * @author nil.mamano
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Area 
    implements Iterable<Point>
{    
    private static final int SAMPLE_SIZE = 20;
    
    @XmlElementWrapper
    private List<GridPoint> vertexs;
    
    @XmlTransient
    private List<VerticalEdge> verticalEdges;
    
    @XmlTransient
    private List<HorizontalEdge> horizontalEdges;
        
    /**
     * Given area x' value, permits fast access to all vertexs of the area
     * of the form (x,y), where x=x'.
     */
    @XmlTransient
    private HashMap<Integer,List<GridPoint>> vertexsStoredByX;
    
    /**
     * Given area y' value, permits fast access to all vertexs of the area
     * of the form (x,y), where y=y'.
     */
    @XmlTransient
    private HashMap<Integer,List<GridPoint>> vertexsStoredByY;
    
    @XmlTransient
    private HashMap<Integer,List<VerticalEdge>> verticalEdgesStoredByX;
    
    @XmlTransient
    private HashMap<Integer,List<HorizontalEdge>> horizontalEdgesStoredByY;
    
    
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
     * JAXB persistance initialization needed
     * @param u
     * @param parent 
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {
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
        
        return (float) Math.sqrt(xOffset*xOffset + yOffset*yOffset);
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
