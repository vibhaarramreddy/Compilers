package emitter;
import java.io.*;

public class Emitter
{
    private PrintWriter out;
    private int countLabel;

    /**
     * Creates an Emitter for writing into a new file with a given name
     * 
     * @param outputFileName the file being written into
     */
    public Emitter(String outputFileName)
    {
        try
        {
            out = new PrintWriter(new FileWriter(outputFileName), true);
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
        
        this.countLabel = 0;
    }

   /**
    * prints one line of code to the file 
    * @param code the code to be printed
    */
    public void emit(String code)
    {
        if (!code.endsWith(":"))
            code = "\t" + code;
        out.println(code);
    }

    /**
     * closes the file and is called after all calls to emit
     */
    public void close()
    {
        out.close();
    }
    
    /**
     * Pushes a specific register onto the stack 
     * 
     * @param reg the register
     */
    public void emitPush(String reg)
    {
        emit("subu $sp $sp 4");
        emit("sw " + reg + " ($sp)");
    }
    
    /**
     * Pushes a specific register onto the stack 
     * 
     * @param reg the register
     */
    public void emitPop(String reg)
    {
        emit("lw " + reg + " ($sp)");
        emit("addu $sp $sp 4"); 
    }
    
    /**
     * Gets the number of the next label.
     * Increases by one each it is called (a new label is created). 
     * 
     * @return the number of the next label
     */
    public int nextLabelID()
    {
        countLabel++;
        return countLabel;
    }
     
    
}