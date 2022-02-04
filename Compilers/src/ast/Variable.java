package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Variable is a variable with a String name that 
 * is a associated with an int value
 * 
 * @author Vibha Arramreddy
 * @version 17 March 2020 
 */
public class Variable extends Expression 
{
    
    private String name;
    
    /**
     * Variable constructor creates a Variable object
     * which has a name
     * 
     * @param name the name of the variable
     */
    public Variable(String name)
    {
        this.name = name;
    }
    
    /**
     * Return the int value of the variable
     * 
     * @param env the environment
     */
    public int eval(Environment env)
    {
        return env.getVariable(name);
    }
    
    /**
     * Uses a specified Emitter to call a variable in
     * in MIPS
     * @param e the Emitter 
     */
    public void compile(Emitter e)
    {
        e.emit("la $t0 var" + name);
        e.emit("lw $v0 ($t0)");
    }

}
