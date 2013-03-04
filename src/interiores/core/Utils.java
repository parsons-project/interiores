package interiores.core;

/**
 * Class of utilities.
 * @author hector
 */
public class Utils
{
    static public String capitalize(String s)
    {
        char[] arrayString = s.toCharArray();
        arrayString[0] = Character.toUpperCase(arrayString[0]);
        
        return new String(arrayString);
    }
}
