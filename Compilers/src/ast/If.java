package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * If creates a basic If construct that 
 * executes a statement if the condition is true and
 * does not execute a statement if the condition is false 
 * 
 * @author Vibha Arramreddy
 * @version 17 March 2020 
 */
public class If extends Statement 
{

    private Condition cond;
    private Statement stmt;
    
    /**
     * If constructor creates an If object that takes 
     * a conditional and a statement 
     * 
     * @param cond the condition to be checked
     * @param stmt the statement to be executed 
     */
    public If(Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
    }
    
    /**
     * Executes a statement if the condition is true
     * does not execute if the condition is false.
     * 
     * @param env the environment
     */
    public void exec(Environment env)
    {
        if(cond.eval(env) == true)
        {
            stmt.exec(env);
        }
    }
    
    /**
     * Uses a specified Emitter to write the specified if statement
     * in MIPS
     * @param e the Emitter 
     */
    public void compile(Emitter e)
    {
        int label = e.nextLabelID();
        String targetLabel = "endif" + label;
        cond.compile(e, targetLabel);
        e.emit("j after" + label);
        e.emit(targetLabel +":");
        stmt.compile(e);
        e.emit("after" + label + ":");
    }
}
