package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Assignment is class that assigns a specific variable a specific value
 * 
 * @author Vibha Arramreddy
 * @version 17 March 2020 
 */
public class Assignment extends Statement 
{
    
    private String name;
    private Expression exp;
    
    /**
     * Assignment constructor constructs a Assignment object that 
     * uses a name and an expression.
     * 
     * @param name the name of the variable that is being assigned a value
     * @param exp  the value to be assigned to the variable
     */
    public Assignment(String name, Expression exp)
    {
        this.name = name;
        this.exp = exp;
    }
    
    /**
     * Assigns the variable 'name' its value
     * 
     * @param env the environment
     */
    public void exec(Environment env)
    {
        env.setVariable(name, exp.eval(env));
    }

    /**
     * Uses a specified Emitter to write change or assign a variable's value
     * in MIPS
     * @param e the Emitter 
     */
    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("la $t0, var" + name);
        e.emit("sw $v0 ($t0)");
    }
}
