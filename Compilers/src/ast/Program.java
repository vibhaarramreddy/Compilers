package ast;

import java.util.List;
import emitter.Emitter;
import environment.Environment;

/**
 * Program included all the procedures and statements
 * in a given file. 
 * A period signals the end of a program.
 * 
 * @author Vibha Arramreddy
 * @version 22 April 2020 
 *
 */
public class Program
{   
    private List<ProcedureDeclaration> procedures;
    private Statement stmt;
    private List<String> variables;
    
    /**
     * Program constructor creates a Program object 
     * which has list of procedures and a statement to execute
     * 
     * @param procedures the list of procedures in the program
     * @param stmt the statement to be executed
     * @param variables the list of global variables
     */
    public Program(List<ProcedureDeclaration> procedures, List<String> variables, Statement stmt)
    {
        this.procedures = procedures;
        this.stmt = stmt;
        this.variables = variables;
    }
    
    /**
     * Executes the statement associated with the program
     * 
     * @param env the environment
     */
    public void exec(Environment env)
    {
        for(int i = 0; i <= procedures.size()-1; i++)
        {
            procedures.get(i).exec(env);
        }
        
        stmt.exec(env);
    }
    
    /**
     * Takes in an output file name and uses an Emitter to write 
     * Program in MIPS.
     * 
     * @param outputFileName
     */
    public void compile(String outputFileName)
    {
        Emitter e = new Emitter(outputFileName);
        
        e.emit(".data");
        e.emit("space: .asciiz \"\\n\"");
        
        for(int i = 0; i <= variables.size()-1; i++)
        {
            e.emit("var" + variables.get(i) + ": .word 0");
        }
        
        e.emit(".text");
        e.emit(".globl main");
        e.emit("main:");
        
        for(int i = 0; i <= procedures.size()-1; i++)
        {
            procedures.get(i).compile(e);
        }
        
        stmt.compile(e);
        
        e.emit("li $v0 10");
        e.emit("syscall");
    }

}
