package ast;

import java.util.List;

import environment.Environment;

/**
 * ProcedureDeclaration is a class that declares a procedure 
 * that has a id, a list of zero or more parameters, and a 
 * statement to execute. 
 * 
 * @author Vibha Arramreddy
 * @version 22 April 2020 
 */
public class ProcedureDeclaration extends Statement
{

    private String id;
    private Statement stmt;
    private List<String> parameters;
    
    /**
     * ProcedureDeclaration constructor declares a ProcedureDeclaration object
     * which creates a procedure that has an id, list of parameters (can be 0),
     * and a statement to be executed.
     * 
     * @param id the name of the procedure
     * @param stmt the statement that the procedure executes
     * @param parameters the parameters that the procedure takes
     */
    public ProcedureDeclaration(String id, Statement stmt, List<String> parameters)
    {
        this.id = id;
        this.stmt = stmt;
        this.parameters = parameters;
    }
    
    /**
     * Declares the procedure in the global environment. 
     * 
     * @param env the environment
     */
    public void exec(Environment env)
    {
        env.setProcedure(id, new ProcedureDeclaration(id, stmt, parameters));
    }
    
    /**
     * Returns statement associated with the given procedure
     * 
     * @return the statement that the procedure executes
     */
    public Statement getStmt()
    {
        return stmt;
    }
    
    /**
     * Returns parameters associated with the given procedure
     * 
     * @return the parameters that the procedure takes
     */
    public List<String> getParameters()
    {
        return parameters;
    }
    
}
