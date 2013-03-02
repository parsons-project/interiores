package interiores.core.terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;

/**
 *
 * @author hector
 */
public class IOStream
{
    private Scanner ibuffer;
    private BufferedReader istream;
    private PrintStream ostream;
    
    
    public IOStream()
    {
        
    }
    
    public void setInputStream(InputStream stream)
    {
        istream = new BufferedReader(new InputStreamReader(stream));
    }
    
    public void setOutputStream(PrintStream stream)
    {
        ostream = stream;
    }
    
    public void setInputBuffer(String buffer)
    {
        this.ibuffer = new Scanner(buffer);
    }
    
    public String readLine() throws IOException
    {
        if(istream == null)
            throw new IOException();
        
        return istream.readLine();
    }
    
    public String readString() throws IOException
    {
        try
        {
            return ibuffer.next();
        }
        catch(Exception e)
        {
            setInputBuffer(readLine());
            
            return readString();
        }
    }
    
    public int readInt() throws IOException
    {
        try
        {
            return ibuffer.nextInt();   
        }
        catch(Exception e)
        {
            setInputBuffer(readLine());
            
            return readInt();
        }
    }
    
    public void println(String s)
    {
        ostream.println(s);
    }
}
