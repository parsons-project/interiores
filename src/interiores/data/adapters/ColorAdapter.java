package interiores.data.adapters;

import java.awt.Color;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author hector
 */
public class ColorAdapter extends XmlAdapter<String, Color>
{   
    @Override
    public String marshal(Color color) {
        String rgb = Integer.toHexString(color.getRGB());
        rgb = rgb.substring(2, rgb.length());
        
        return "#" + rgb;
    }
    
    @Override
    public Color unmarshal(String rgb) {
        return Color.decode(rgb);
    }
}
