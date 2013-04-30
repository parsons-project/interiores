package interiores.core;

/**
 *
 * @author hector
 */
public class Debug {
    private static boolean enabled = false;
    
    public static void enable() {
        enabled = true;
    }
    
    public static void println(String s) {
        if(enabled)
            System.err.println("[DEBUG] " + s);
    }
    
    public static boolean isEnabled() {
        return enabled;
    }
}
