package interiores.core;

/**
 * Simple class for enable/disable debugging mesages.
 * @author hector
 */
public class Debug {
    /**
     * Tells whether the debug mode is enabled or not.
     */
    private static boolean enabled = false;
    
    /**
     * Enables the debug mode.
     */
    public static void enable() {
        enabled = true;
    }
    
    /**
     * If the debug mode is enabled, prints as a line the given string.
     * @param s The string to print
     */
    public static void println(String s) {
        if(enabled)
            System.err.println("[DEBUG] " + s);
    }
    
    /**
     * Tells whether the debug mode is enabled or not
     * @return Ture if the debug mode is enabled, false otherwise
     */
    public static boolean isEnabled() {
        return enabled;
    }
}
