package interiores.utils;

import interiores.core.presentation.swing.SwingException;
import java.awt.Desktop;
import java.net.URI;
import java.util.Arrays;

/**
 *
 * @author alvaro
 */
public class OpenDefaultBrowser {

    private static final String[] browsers = {"google-chrome", "firefox", "opera", "epiphany", "konqueror", "conkeror",
                                              "midori", "kazehakase", "mozilla"};

    public static void openURL(String url) {
        
        if(Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            }
            catch (Exception exc) {
                throw new SwingException("Unable to open " + url, exc);
            }
        }
        else {
            String osName = System.getProperty("os.name");
            try {
                if (osName.startsWith("Mac OS")) {
                    Class.forName("com.apple.eio.FileManager").getDeclaredMethod("openURL", new Class[]{String.class}).invoke(null, new Object[]{url});
                } else if (osName.startsWith("Windows")) {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
                } else { // *nix
                    for (String browser : browsers) {
                        int exitStatus = Runtime.getRuntime().exec(new String[]{"which", browser}).getInputStream().read();
                        if ( exitStatus != -1) {
                            Runtime.getRuntime().exec(new String[]{browser, url});
                            return;
                        }
                    }
                    throw new Exception(Arrays.toString(browsers));
                }
            } 
            catch (Exception exc) {
                throw new SwingException("Unable to open " + url, exc);
            }
        }
    }
}