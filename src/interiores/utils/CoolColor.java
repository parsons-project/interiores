package interiores.utils;

import interiores.core.business.BusinessException;
import interiores.data.adapters.ColorAdapter;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author hector
 */
@XmlRootElement
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
    
    @XmlAttribute
    @XmlJavaTypeAdapter(ColorAdapter.class)
    private Color color;
    
    private CoolColor(String hexColor) {
        this.color = Color.decode(hexColor);
    }
    
    public Color getColor() {
        return color;
    }
    
    public static Collection<String> getNames() {
        Collection<String> names = new ArrayList();
        for(CoolColor coolColor : values()) {
            names.add(coolColor.name().toLowerCase());
        }
        return names;
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
