package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * BinOp is class that does basic binary operations between two expressions
 * (addition, subtraction, multiplication, division)
 * 
 * @author Vibha Arramreddy
 * @version 17 March 2020 
 */
public class BinOp extends Expression 
{
    
    private String op;
    private Expression exp1;
    private Expression exp2;
    
    /**
     * BinOp constructor constructs a BinOp object that takes in  
     * an operation and two expressions.
     * 
     * @param op    the operation to be done
     * @param exp1  the first expression 
     * @param exp2  the second expression
     */
    public BinOp(String op, Expression exp1, Expression exp2)
    {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }
    
    /**
     * Does a binary operation between two expressions. 
     * 
     * @param env the environment
     */
    public int eval(Environment env)
    {  
        if(op.equals("+")) 
        {
            return exp1.eval(env) + exp2.eval(env);
        }
        else if(op.equals("-")) 
        {
            return exp1.eval(env) - exp2.eval(env);
        }
        else if(op.equals("*")) 
        {
            return exp1.eval(env) * exp2.eval(env);
        }
        else
        {
            return exp1.eval(env) / exp2.eval(env);
        }
    }
    
    /**
     * Uses a specified Emitter to write the specified condition
     * in MIPS
     * 
     * @param e the Emitter 
     */
    public void compile(Emitter e)
    {
                
        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);
        e.emitPop("$t0");
        
        if(op.equals("+")) 
        {
            e.emit("addu $v0 $t0 $v0");
        }
        else if(op.equals("-")) 
        {
            e.emit("subu $v0 $t0 $v0");
        }
        else if(op.equals("*")) 
        {
            e.emit("mult $t0 $v0");
            e.emit("mflo  $v0");
        }
        else
        {
            e.emit("div $t0 $v0");
            e.emit("mflo  $v0");
        }
    }

}
