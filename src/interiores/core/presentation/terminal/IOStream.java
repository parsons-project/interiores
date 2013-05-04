package interiores.core.presentation.terminal;

import interiores.core.Debug;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Tool for reading and printing to input/output streams.
 * @author hector
 */
public class IOStream
{
    /**
     * Input buffer
     */
    private Scanner ibuffer;
    
    /**
     * Input stream
     */
    private BufferedReader istream;
    
    /**
     * Output stream
     */
    private PrintStream ostream;
    
    /**
     * Input prompt
     */
    private char prompt;
    
    /**
     * Creates a new IOStream with the input and output streams given.
     * @param istream An input stream
     * @param ostream An output stream
     */
    public IOStream(InputStream istream, PrintStream ostream)
    {
        this.istream = new BufferedReader(new InputStreamReader(istream));
        this.ostream = ostream;
        ibuffer = new Scanner("");
        prompt = '>';
    }
    
    /**
     * Sets the input prompt.
     * @param prompt The new input prompt
     */
    public void setPrompt(char prompt) {
        this.prompt = prompt;
    }
    
    /**
     * Puts a string into the input buffer.
     * @param buffer The string to put into the buffer
     */
    public void putIntoInputBuffer(String buffer)
    {
        this.ibuffer = new Scanner(buffer);
    }
    
    /**
     * Reads a new line from the input stream.
     * @return The read line
     */
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
    
    public boolean readBoolean() {
        String input = readString();
        
        return input.equals("y") || input.equals("yes");
    }
    
    public boolean readBoolean(String question) {
        println(question + " (yes/no)");
        
        return readBoolean();
    }
    
    /**
     * Reads a string from the input buffer/stream.
     * @return The read string
     */
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
    
    /**
     * Reads a string from the input buffer/string ansking a question
     * @param question The question to ask
     * @return The read string
     */
    public String readString(String question)
    {
        if(! ibuffer.hasNext())
            println(question);
        
        return readString();
    }
    
    /**
     * Reads the strings left in the input buffer asking a question.
     * @param question The question to ask
     * @return The read strings
     */
    public Collection<String> readStrings(String question) {
        Collection<String> strings = new ArrayList();
        
        strings.add(readString(question));
        
        while(ibuffer.hasNext())
            strings.add(ibuffer.next());
        
        return strings;
    }
    
    /**
     * Reads an integer from the input buffer/stream.
     * @return The read integer
     */
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
    
    /**
     * Reads an integer from the input buffer/stream asking a question.
     * @param question The question to ask
     * @return The read integer
     */
    public int readInt(String question)
    {
        if(! ibuffer.hasNextInt())
            println(question);
        
        return readInt();
    }
    
    /**
     * Reads a float from the input buffer/stream asking a question.
     * @return The read float
     */
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
    
    /**
     * Reads a float from the input buffer/stream asking a question.
     * @param question The question to ask
     * @return The read float
     */
    public float readFloat(String question)
    {
        if(! ibuffer.hasNextFloat())
            println(question);
        
        return readFloat();
    }
    
    /**
     * Tells whether the input buffer has one or more strings left.
     * @return True if the input buffer has one or more strings left, false otherwise
     */
    public boolean hasNext() {
        return ibuffer.hasNext();
    }
    
    public boolean hasNext(String pattern) {
        return ibuffer.hasNext(pattern);
    }
    
    /**
     * Tells whether the input buffer has one or more integers left.
     * @return True if the input buffer has one or more integers left, false otherwise
     */
    public boolean hasNextInt() {
        return ibuffer.hasNextInt();
    }
    
    /**
     * Prints a string as a line in the output stream.
     * @param s The string to print
     */
    public void println(String s)
    {
        ostream.println(s);
    }
}
