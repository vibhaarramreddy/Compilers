package ast;

import java.util.List;

import emitter.Emitter;
import environment.Environment;

/**
 * Block stores a list of statements which are enclosed
 * by a BEGIN and END statement. 
 * 
 * @author Vibha Arramreddy
 * @version 17 March 2020 
 */
public class Block extends Statement 
{
    
    private List<Statement> stmts;
    
    /**
     * Block constructor creates a Block object which contains
     * a list of statements
     * 
     * @param stmts the list of statements
     */
    public Block(List<Statement> stmts)
    {
        this.stmts = stmts;
    }
    
    /**
     * Executes the list of statements
     * 
     * @param env the environment
     */
    public void exec(Environment env) 
    {
        for(int i = 0; i <= stmts.size()-1; i++)
        {
            stmts.get(i).exec(env);
        }
    }
    
    /**
     * Uses a specified Emitter to write the Block's functionality  
     * in MIPS
     * @param e the Emitter 
     */
    public void compile(Emitter e)
    {
        for(int i = 0; i <= stmts.size()-1; i++)
        {
            stmts.get(i).compile(e);
        }
    }

}
