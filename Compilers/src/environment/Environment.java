package environment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ast.ProcedureDeclaration;

import ast.Block;
import ast.Statement;

/**
 * Environment keeps track of all the variables
 * for assignment and recall purposes. 
 * 
 * @author Vibha Arramreddy
 * @version 22 April 2020  
 */
public class Environment 
{
    
    Map<String, Integer> variables;
    Map<String, ProcedureDeclaration> procedures;
    Environment root;
    
    /**
     * Environment constructor creates a Environment variable which
     * has a HashMap of variables. 
     * 
     */
    public Environment(Environment env)
    {
        variables = new HashMap<String, Integer>();
        procedures = new HashMap<String, ProcedureDeclaration>();
        root = env;
    }
    
    /**
     * Associates the given variable name with the given value 
     * in the child environment
     * 
     * @param variable the variable name
     * @param value the variable value
     */
    public void declareVariable(String variable, int value)
    {
        variables.put(variable, value);
    }
    
    /**
     * Checks if variable name is already defined in the environment
     * 
     * @param variable the variable name
     */
    public boolean hasVariable(String variable)
    {
        return variables.containsKey(variable);
    }

    /**
     * Associates the given variable name with the given value
     * 
     * @param variable the variable name
     * @param value the variable value
     */
    public void setVariable(String variable, int value)
    {
        if(root == null)
        {
            if(hasVariable(variable))
            {
                variables.replace(variable, value);
            }
            else
            {
                variables.put(variable, value);
            }
        }
        else if(variables.get(variable) == null && root.hasVariable(variable) == true)
        {
            root.setVariable(variable, value);
        }
        else 
        {
            variables.put(variable, value);
        }
    }

    /**
     * Returns the value associated with the given variable
     * 
     * @param variable the variable name
     * @return value of the variable
     */
    public int getVariable(String variable)
    {
        if(variables.get(variable) != null)
        {
            return variables.get(variable);
        }
        else
        {
            return root.getVariable(variable);
        }
    }
    
    /**
     * Associates the the given list of statements
     * with the given procedure id
     * 
     * @param variable the variable name
     * @return value of the variable
     */
    public void setProcedure(String id, ProcedureDeclaration stmts)
    {
        if(root == null)
        {
            procedures.put(id, stmts);
        }
        else 
        {
            root.setProcedure(id, stmts);
        }
    }
    
    /**
     * Returns the statements associated with the given procedure
     * 
     * @param variable the variable name
     * @return value of the variable
     */
    public ProcedureDeclaration getProcedure(String id)
    {
        if(root == null)
        {
            return procedures.get(id);
        }
        else 
        {
            return root.getProcedure(id);
        }
    }
}
