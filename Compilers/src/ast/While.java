package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * While creates a basic While construct that 
 * continues executes a statement until the condition is false. 
 * 
 * @author Vibha Arramreddy
 * @version 17 March 2020 
 */
public class While extends Statement 
{

    private Condition cond;
    private Statement stmt;
    
    /**
     * While constructor creates a While object which
     * takes a condition and a statement
     * 
     * @param cond the condition to be checked
     * @param stmt the statement to be executed
     */
    public While(Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
    }
    
    /**
     * Executes a statement until the condition is false.
     * 
     * @param env the environment
     */
    public void exec(Environment env)
    {
        while(cond.eval(env) == true)
        {
            stmt.exec(env);
        }
    }
    
    /**
     * Uses a specified Emitter to write the specified While statement   
     * in MIPS
     * @param e the Emitter 
     */
    public void compile(Emitter e)
    {
        int label = e.nextLabelID();
        String targetLabel = "endif" + label;
        e.emit("loop" + label +":");
        cond.compile(e, targetLabel);
        e.emit("j endloop" + label);
        e.emit(targetLabel +":");
        stmt.compile(e);
        e.emit("j loop" + label);
        e.emit("endloop" + label + ":");
        
        
    }
}

