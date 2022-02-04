package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Condition determines the relationship one expression and another expression
 * based on a comparative operator. 
 * 
 * @author Vibha Arramreddy
 * @version 17 March 2020 
 */
public class Condition 
{
    
    private String op;
    private Expression exp1;
    private Expression exp2;
    
    /**
     * Condition constructor creates a Condition object 
     * which takes an operation (such as =, !=, <, >, >=, <=),
     * and two expression 
     * 
     * @param op the comparative operation
     * @param exp1  expression 1
     * @param exp2  expression 2
     */
    public Condition(String op, Expression exp1, Expression exp2)
    {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }
    
    /**
     * Evaluates whether the conditional statement 
     * between the two expressions is true
     * 
     * @param env the environment
     * @return true if the conditional statement is true; otherwise,
     *         false
     */
    public boolean eval(Environment env)
    {
        boolean cond = false; 
        
        if(op.equals("=")) 
        {
            cond = exp1.eval(env) == exp2.eval(env);
        }
        else if(op.equals("!=")) 
        {
            cond = exp1.eval(env) != exp2.eval(env);
        }
        else if(op.equals("<")) 
        {
            cond = exp1.eval(env) < exp2.eval(env);
        }
        else if(op.equals(">")) 
        {
            cond = exp1.eval(env) > exp2.eval(env);
        }
        else if(op.equals("<=")) 
        {
            cond = exp1.eval(env) < exp2.eval(env);
        }
        else if(op.equals(">=")) 
        {
            cond = exp1.eval(env) > exp2.eval(env);
        }
        
        return cond;
    }
    
    /**
     * Uses a specified Emitter to write condition's functionality  
     * in MIPS
     * @param e the Emitter 
     */
    public void compile(Emitter e, String targetLabel)
    {
        exp1.compile(e);
        e.emit("move $t0, $v0");
        exp2.compile(e);
        
        if(op.equals("=")) 
        {
            e.emit("beq $t0, $v0, " + targetLabel);
        }
        else if(op.equals("!=")) 
        {
            e.emit("bne $t0, $v0, " + targetLabel);
        }
        else if(op.equals("<")) 
        {
            e.emit("blt $t0, $v0, " + targetLabel);
        }
        else if(op.equals(">")) 
        {
            e.emit("bgt $t0, $v0, " + targetLabel);
        }
        else if(op.equals("<=")) 
        {
            e.emit("ble $t0, $v0, " + targetLabel);
        }
        else if(op.equals(">=")) 
        {
            e.emit("bge $t0, $v0, " + targetLabel);
        }
    }

}
