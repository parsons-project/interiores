package interiores.utils;

import interiores.core.business.BusinessException;
import interiores.data.adapters.ColorAdapter;
import java.awt.Color;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author hector
 */
public enum CoolColor {
    
    BLACK("#000000"),
    GRAY("#CCCCCC"),
    WHITE("#FFFFFF"),
    BROWN("#802A2A"),
    RED("#CC0000"),
    BLUE("#4096EE"),
    GREEN("#6BBA70"),
    ORANGE("#FF7400"),
    YELLOW("#FFAE4B"),
    GLAZED("#FFFACD");
    
    private static final String BLACK_HEX = "#000000";
    
    @XmlAttribute
    @XmlJavaTypeAdapter(ColorAdapter.class)
    private Color color;
    
    private CoolColor(String hexColor) {
        if(hexColor.equals(BLACK_HEX))
            color = Color.black;
        else
            this.color = Color.decode(hexColor);
    }
    
    public Color getColor() {
        return color;
    }
    
    public static CoolColor getEnum(String name) throws BusinessException {
        for(CoolColor coolColor : values())
            if(coolColor.name().equalsIgnoreCase(name))
                return coolColor;
        
        throw new BusinessException("There is no color named as " + name);
    }
    
    @Override
    public String toString() {
        return "name=" + name() + ",r=" + color.getRed() + ",g=" + color.getGreen() + ",b=" + color.getBlue();
    }
}
