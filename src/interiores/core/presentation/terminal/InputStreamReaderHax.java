package interiores.core.presentation.terminal;

import interiores.core.Debug;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author hector
 */
public class InputStreamReaderHax extends InputStreamReader {

    public InputStreamReaderHax(InputStream tream)
    {
        super(tream);
    }
    
    @Override
    public int read(char[] cbuf, int offset, int length) throws IOException {
        int read = super.read(cbuf, offset, length);
        
        for(char c : cbuf)
            Debug.println("Read: " + (int) c);
        
        return read;
    }
    
}
