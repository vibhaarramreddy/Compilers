package ast;

import environment.Environment;
import emitter.Emitter;

/**
 * Abstract class which handles all statements.  
 * 
 * @author Vibha Arramreddy
 * @version 17 March 2020 
 */
public abstract class Statement 
{
    
    /**
     * Abstract method which handles the evaluation of all statements.
     * 
     * @param env the environment
     * @return the value of the expression
     */
    public abstract void exec(Environment env);
    
    /**
     * Uses a specified Emitter to write a statement's functionality  
     * in MIPS
     * @param e the Emitter 
     */
    public void compile(Emitter e)
    {
      throw new RuntimeException("Implement me!!!!!");
    }
}
