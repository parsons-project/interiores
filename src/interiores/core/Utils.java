package interiores.core;

/**
 * Class of utilities.
 * @author hector
 */
public class Utils
{
    public static String capitalize(String s)
    {
        char[] arrayString = s.toCharArray();
        arrayString[0] = Character.toUpperCase(arrayString[0]);
        
        return new String(arrayString);
    }
    
    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }
}
