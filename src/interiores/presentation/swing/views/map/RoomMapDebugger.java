package interiores.presentation.swing.views.map;

import interiores.business.models.OrientedRectangle;
import interiores.core.Debug;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hector
 */
public class RoomMapDebugger
    extends InteractiveRoomMap
{
    private static final Color COLOR_GRID = Color.decode("#EEEEEE");
    private static final Color COLOR_AREA = new Color(0, 0, 0, 70);
    private static final String[] COLOR_POINTS = new String[]{
                                                                "#C3D9FF", // Gmail blue
                                                                "#C79810", // 43 Things Gold
                                                                "#CDEB8B", // Qoop Mint
                                                                "#D15600", // Etsy Vermillion
                                                                "#356AA0", // Digg Blue
                                                                "#73880A", // Writely Olive
                                                                "#FF0084", // Flickr Pink
                                                                "#FF7400", // RSS Orange
                                                                "#4096EE", // Flock blue
                                                                "#6BBA70"  // Basecamp green
                                                            };
    
    private boolean isGridEnabled;
    private boolean shouldDrawFurniture;
    private boolean shouldDrawPillars;
    private List<String> furnitures;
    private Map<String, List<Point>> points;
    private Map<String, Color> colors;
    private Map<String, Rectangle> areas;
    private String furnitureActive;
        
    public RoomMapDebugger(int roomWidth, int roomDepth) {
        super(roomWidth, roomDepth);
        
        isGridEnabled = Debug.isEnabled();
        shouldDrawFurniture = true;
        shouldDrawPillars = true;
        furnitures = new ArrayList();
        points = new HashMap();
        colors = new HashMap();
        areas = new HashMap();
    }
    
    public void enableGrid() {
        isGridEnabled = true;
    }
    
    public void disableGrid() {
        isGridEnabled = false;
    }
    
    public boolean isGridEnabled() {
        return isGridEnabled;
    }
    
    public void enableDrawFurniture() {
        shouldDrawFurniture = true;
    }
    
    public void disableDrawFurniture() {
        shouldDrawFurniture = false;
    }
    
    public void enableDrawPillars() {
        shouldDrawPillars = true;
    }
    
    public void disableDrawPillars() {
        shouldDrawPillars = false;
    }
    
    public boolean shouldDrawFurniture() {
        return shouldDrawFurniture;
    }
    
    public boolean shouldDrawPillars() {
        return shouldDrawPillars;
    }
    
    public void addPoint(Point p) {
        List furniturePoints = points.get(furnitureActive);
        
        if(furniturePoints.contains(p))
            furniturePoints.clear();
        
        furniturePoints.add(p);
    }
    
    public void addFurniture(String name, OrientedRectangle area, Color color, Rectangle wholeArea) {
        addFurniture(name, area, color);
        
        Rectangle wholeAreaTranslated = new Rectangle(wholeArea);
        wholeAreaTranslated.translate(getPadding(), getPadding());
        
        areas.put(name, wholeAreaTranslated);
    }
    
    @Override
    public void removeFurniture(String name) {
        super.removeFurniture(name);
        
        areas.remove(name);
    }
    
    public void setActive(String name)
    {
        if(! points.containsKey(name)) {
            furnitures.add(name);
            colors.put(name, Color.decode(COLOR_POINTS[points.size() % COLOR_POINTS.length]));
            points.put(name, new ArrayList());
        }
        
        furnitureActive = name;
    }
    
    @Override
    protected void drawElements(Graphics2D g)
    {
        for(String furnitureName : furnitures) {
            g.setColor(colors.get(furnitureName));
            
            for(Point p : points.get(furnitureName))
                g.fillRect(p.x + getPadding(), p.y + getPadding(), RESOLUTION, RESOLUTION);
            
            if(areas.containsKey(furnitureName)) {
                g.setColor(COLOR_AREA);
                g.fill(areas.get(furnitureName));
            }
        }
        
        if(isGridEnabled)
            drawGrid(g);
        
        drawWalls(g);
        
        if(shouldDrawFurniture)
            drawFurniture(g);
        
        if(shouldDrawPillars)
            drawPillars(g);
    }
    
    private void drawGrid(Graphics2D g) {        
        g.setColor(COLOR_GRID);
        
        for(int i = 0; i < size.width; i += RESOLUTION)
            g.drawLine(i, 0, i, size.depth);
        
        for(int i = 0; i < size.depth; i += RESOLUTION)
            g.drawLine(0, i, size.width, i);
    }

    @Override
    public void clear()
    {
        clearFurniture();
        
        furnitures.clear();
        points.clear();
        colors.clear();
        areas.clear();
    }
}
