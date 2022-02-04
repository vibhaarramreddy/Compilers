package ast;

import java.util.List;

import environment.Environment;

/**
 * ProcedureCall is a class that call a previously declared procedure 
 * that has a id, a list arguments which will be passed through 
 * the parameters 
 * 
 * @author Vibha Arramreddy
 * @version 22 April 2020 
 */
public class ProcedureCall extends Expression 
{
    private String id;
    private List<Expression> args;
    
    /**
     * ProcedureCall constructor declares a ProcedureCall object
     * which creates a procedure that has an id, list of arguments of 
     * zero of more.
     * 
     * @param id the name of the procedure
     * @param args the arguements that the procedure takes 
     */
    public ProcedureCall(String id, List<Expression> args)
    {
        this.id = id;
        this.args = args;
    }
    
    /**
     * Evaluates the statement associated with the given procedure 
     * 
     * @param env the environment 
     */
    public int eval(Environment env)
    {
        ProcedureDeclaration prcd = env.getProcedure(id);
        Statement stmt = prcd.getStmt();
        List<String> param = prcd.getParameters();
        Environment subEnv = new Environment(env);
        
        subEnv.declareVariable(id, 0);
        
        
        for(int i = 0; i <= args.size()-1; i++)
        {
            subEnv.declareVariable(param.get(i), args.get(i).eval(env));
        }
        
        stmt.exec(subEnv);
        
        return subEnv.getVariable(id);
    }
}
