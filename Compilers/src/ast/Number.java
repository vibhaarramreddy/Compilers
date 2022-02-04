package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Number is a class which holds an int value
 * 
 * @author Vibha Arramreddy
 * @version 17 March 2020 
 */
public class Number extends Expression 
{
    
    private int value;
    
    /**
     * Number constructor creates a Number object which
     * takes an int value 
     * 
     * @param value the value of the number
     */
    public Number(int value)
    {
        this.value = value;
    }
    
    /**
     * Returns the int value of the Number
     * 
     * @param env the environment
     */
    public int eval(Environment env)
    {
        return value;
    }
    
    /**
     * Uses a specified Emitter to write a number 
     * in MIPS
     * @param e the Emitter 
     */
    public void compile(Emitter e)
    {
        e.emit("li $v0 " + value);
    }

}
