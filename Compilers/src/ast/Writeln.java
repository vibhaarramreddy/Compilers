package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Writeln prints out the value of an expression
 * 
 * @author Vibha Arramreddy
 * @version 17 March 2020 
 */
public class Writeln extends Statement 
{

    private Expression exp;
    
    /**
     * Writeln constructor creates a Writeln comment which
     * takes an expression.
     * 
     * @param exp the expression 
     */
    public Writeln(Expression exp)
    {
        this.exp = exp;
    }
    
    /**
     * Prints out the expression
     * 
     * @param env the environment
     */
    public void exec(Environment env)
    {
        System.out.println(exp.eval(env));
    }
    
    /**
     * Uses a specified Emitter to write print out an expression
     * in MIPS. 
     * 
     * @param e the Emitter 
     */
    public void compile(Emitter e)
    {
        exp.compile(e);
        
        e.emit("move $a0 $v0");
        e.emit("li $v0 1");
        e.emit("syscall");
        
        e.emit("la $a0 space");
        e.emit("li $v0 4");
        e.emit("syscall");
    }

}
