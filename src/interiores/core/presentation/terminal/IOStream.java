package interiores.core.presentation.terminal;

import interiores.core.Debug;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.NoSuchElementException;
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
    private char prompt;
    
    
    public IOStream(InputStream istream, PrintStream ostream)
    {
        this.istream = new BufferedReader(new InputStreamReader(istream));
        this.ostream = ostream;
        ibuffer = new Scanner("");
        prompt = '>';
    }
    
    public void setPrompt(char prompt) {
        this.prompt = prompt;
    }
    
    public void putIntoInputBuffer(String buffer)
    {
        this.ibuffer = new Scanner(buffer);
    }
    
    public String readLine()
    {
        try {
            // Print prompt when buffer empty
            ostream.print(prompt + " ");
            
            return istream.readLine();
        }
        catch(IOException e) {
            Debug.println("Error reading line.");
            
            return readLine();
        }
    }
    
    public String readString()
    {
        try
        {
            return ibuffer.next();
        }
        catch(NoSuchElementException e)
        {
            putIntoInputBuffer(readLine());
            return readString();
        }
    }
    
    public String readString(String question)
    {
        if(! ibuffer.hasNext())
            println(question);
        
        return readString();
    }
    
    public int readInt()
    {
        try
        {
            return ibuffer.nextInt();   
        }
        catch(NoSuchElementException e)
        {
            putIntoInputBuffer(readLine());
            return readInt();
        }
    }
    
    public int readInt(String question)
    {
        if(! ibuffer.hasNextInt())
            println(question);
        
        return readInt();
    }
    
    public float readFloat()
    {
        try
        {
            return ibuffer.nextFloat();   
        }
        catch(NoSuchElementException e)
        {
            putIntoInputBuffer(readLine());
            return readFloat();
        }
    }
    
    public float readFloat(String question)
    {
        if(! ibuffer.hasNextFloat())
            println(question);
        
        return readFloat();
    }
    
    
    public boolean hasNext() {
        return ibuffer.hasNext();
    }
    
    public boolean hasNextInt() {
        return ibuffer.hasNextInt();
    }
    
    public void println(String s)
    {
        ostream.println(s);
    }
}
