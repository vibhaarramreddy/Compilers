package ast;

import environment.Environment;
import emitter.Emitter;

/**
 * Abstract class which handles all expressions.  
 * 
 * @author Vibha Arramreddy
 * @version 17 March 2020 
 */
public abstract class Expression 
{
    
    /**
     * Abstract method which handles the evaluation of all expressions.
     * 
     * @param env the environment
     * @return the value of the expression
     */
    public abstract int eval(Environment env);
    
    /**
     * Uses a specified Emitter to write an expression's functionality  
     * in MIPS
     * @param e the Emitter 
     */
    public void compile(Emitter e)
    {
      throw new RuntimeException("Implement me!!!!!");
    }
    
}
