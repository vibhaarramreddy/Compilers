package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import scanner.ScanErrorException;
import scanner.Scanner;
import ast.Assignment;
import ast.Expression;
import ast.If;
import ast.Writeln;
import ast.BinOp;
import ast.Block;
import ast.Condition;
import ast.Number;
import ast.ProcedureCall;
import ast.ProcedureDeclaration;
import ast.Program;
import ast.Statement;
import ast.Variable;
import ast.While;

/**
 * Parser is a simple parser for Compilers and Interpreters (2014-2015) lab exercise 2.
 * 
 * @author Vibha Arramreddy
 * @version 16 April 2020 
 *
 */
public class Parser {

    Scanner scanner;
    String current;
    
    /**
     * Parser constructor for construction of a parser that 
     * uses an sequence of tokens from the Scanner as input.
     * 
     * @param sc the scanner
     * @throws ScanErrorException if expected token does match the current token
     */
    public Parser(Scanner sc) throws ScanErrorException 
    {
        scanner = sc; 
        current = sc.nextToken();
    }
    
    /**
     * Advances to the next token in the input stream.
     *  
     * @param expected the expected token
     * @throws ScanErrorException if expected token does match the current token
     */
    private void eat(String expected) throws ScanErrorException
    {
        if (current.equals(expected))
        {
           current = scanner.nextToken();
        }
        else
        {
            throw new ScanErrorException("Illegal token: expected" + expected 
                    + "and found" + current);
        }
    }
    
    /**
     * Parses a number token. 
     * 
     * precondition: current token is an integer
     * postcondition: number token has been eaten
     * 
     * @return the value of the parsed integer
     * @throws ScanErrorException if expected token does match the current token
     */
    private Number parseNum() throws ScanErrorException 
    {
        Number num = new Number(Integer.parseInt(current));
        eat(current);
        return num;
        
    }
    
    /**
     * Parses a factor, which is any expression that can be multiplied or divided. 
     * 
     * @return the integer value of the factor
     * @throws ScanErrorException if expected token does match the current token
     */
    private Expression parseFactor() throws ScanErrorException
    {
        Expression exp;

        if(current.equals("-"))
        {
            eat("-");
            Number num = new Number(-1);
            exp = new BinOp("*", (Expression) parseFactor(), num);
        }
        else if(current.equals("("))
        {
            eat("("); 
            exp = parseExpr();
            eat(")");
        }
        else if(scanner.isLetter(current.charAt(0)))
        {
            String id = current;
            eat(current);
            
            if(current.equals("("))
            {
                eat("(");
                List<Expression> args = parseArgs();
                ProcedureCall prcd = new ProcedureCall(id, args);
                eat(")");
                
                return prcd;
            }
            else
            {
                Variable var = new Variable(id);
                return var;
            }
        }
        else
        {
            exp = parseNum();
        }
        
        return exp;
    }
    

    /**
     * Parses a term, which is any expression that can added or subtracted.
     * 
     * @return the integer value of a term
     * @throws ScanErrorException if expected token does match the current token
     */
    private Expression parseTerm() throws ScanErrorException
    {
        Expression exp = parseFactor();
        
        while (current.equals("*") || current.equals("/"))
        {
            if(current.equals("*"))
            {
                eat("*");
                exp = new BinOp("*", exp, parseFactor());
            }
            else if(current.equals("/"))
            {
                eat("/");
                exp = new BinOp("/", exp, parseFactor());
            }
        }
        
        return exp;
    } 
    
    /**
     * Parses the expression, which a statements that include addition and subtraction 
     * along with previously mentioned functionality such as multiplication and division. 
     * 
     * @return the integer value of the expression
     * @throws ScanErrorException if expected token does match the current token
     */
    private Expression parseExpr() throws ScanErrorException
    {
        Expression exp = parseTerm();
        
        while (current.equals("+") || current.equals("-"))
        {
            if(current.equals("+"))
            {
                eat("+");
                exp = new BinOp("+", exp, parseTerm());
            }
            else if(current.equals("-"))
            {
                eat("-");
                exp = new BinOp("-", exp, parseTerm());
            }
        }
        
        return exp;
    } 
    
    /**
     * Parses the condition within conditionals and loops 
     * based on the grammar:
     * cond → expr relop expr
     * 
     * @return true if the condition is true; otherwise
     *         false
     * @throws ScanErrorException
     */
    public Condition parseCond() throws ScanErrorException
    {
        Expression exp1 = parseExpr();
        
        if(current.equals("="))
        {
            eat(current);
            return new Condition("=", exp1, parseExpr());
        }
        else if(current.equals("!="))
        {
            eat(current);
            return new Condition("!=", exp1, parseExpr());
        }
        else if(current.equals(">"))
        {
            eat(current);
            return new Condition(">", exp1, parseExpr());
        }
        else if(current.equals("<"))
        {
            eat(current);
            return new Condition("<", exp1, parseExpr());
        }
        else if(current.equals(">="))
        {
            eat(current);
            return new Condition(">=", exp1, parseExpr());
        }
        else
        {
            eat(current);
            return new Condition("<=", exp1, parseExpr());
        }
    }
    
    /**
     * Helper method for parseStatement that allows for recursion. 
     * Not being used currently 
     * 
     * @throws ScanErrorException if expected token does match the current token
     */
    private void parseWhileBegin() throws ScanErrorException
    {  
        if(current.equals("END"))
        {
            eat(current);
            eat(";");
        }
        else
        {
            parseStatement();
            parseWhileBegin();
        }
    }
    
    /**
     * Parses complete statements which include WRITELN statements, which output a number value, 
     * BEGIN statements which indicate blocks of statements, and variable assignments. 
     * 
     * @throws ScanErrorException if expected token does match the current token
     */
    public Statement parseStatement() throws ScanErrorException
    {
        
        if(current.equals("WRITELN"))
        { 
            eat(current);
            eat("(");
            Expression exp = parseExpr();
            eat(")");
            eat(";");
            return new Writeln(exp);
        }
        else if(current.equals("BEGIN"))
        {
            eat(current);
            List<Statement> stmts = new ArrayList<Statement>();
            
            while (!current.equals("END"))
            {
                stmts.add(parseStatement());
            }
            
            eat("END");
            eat(";");
            
            return new Block(stmts); 
        }
        else if(current.equals("IF"))
        {
            eat("IF");
            Condition cond = parseCond();
            eat("THEN");
            Statement stmt = parseStatement(); 
            
            return new If(cond, stmt);
        }
        else if(current.equals("WHILE"))
        {
            eat("WHILE");
            Condition cond = parseCond();
            eat("DO");
            Statement stmt = parseStatement();
            
            return new While(cond, stmt);
        }
        else
        {  
            String key = current;
            eat(current);
            eat(":=");
            Expression exp = parseExpr();
            eat(";");  
            
            return new Assignment(key, exp);   
        }
    }
    

    /**
     * Parses the parameters that are listed in a procedure declaration 
     * with the grammar:
     * maybeparms → parms | ε
     * parms → parms , id | id
     * 
     * @return a List of type String of the parameters
     * @throws ScanErrorException
     */
    public List<String> parseParm() throws ScanErrorException
    {
        List<String> params = new ArrayList<String>();
        
        while (!current.equals(")"))
        {
            params.add(current);
            eat(current);
            if(current.equals(","))
            {
                eat(",");
            }
        }
        
        return params;
    }
    
    /**
     * Parses the arguments that are listed in a procedure call 
     * with the grammar:
     * maybeargs → args | ε
     * args → args , expr | expr
     * 
     * @return a List of type Expression of the arguments
     * @throws ScanErrorException
     */
    public List<Expression> parseArgs() throws ScanErrorException
    {
        List<Expression> args = new ArrayList<Expression>();
        
        while (!current.equals(")"))
        {
            Expression exp = parseExpr();
            args.add(exp);
            if(current.equals(","))
            {
                eat(",");
            }
        }
        
        return args;
    }
    
    /**
     * Parse a program with the grammar:
     * program → PROCEDURE id ( maybeparms ) ; stmt program | stmt .
     * 
     * @return the program to be executed 
     * @throws ScanErrorException
     */
    public Program parseProgram() throws ScanErrorException
    {
        List<ProcedureDeclaration> procedures = new ArrayList<ProcedureDeclaration>();
        List<String> variables = new ArrayList<String>();
        
        while(current.equals("VAR"))
        {  
            eat("VAR");
            while (!current.equals(";"))
            {
                variables.add(current);
                eat(current);
                if(current.equals(","))
                {
                    eat(",");
                }
            }  
            
            eat(";");
        }
        
        while (current.equals("PROCEDURE"))
        {
            eat("PROCEDURE");
            String id = current;
            eat(current);
            eat("(");
            List<String> params = parseParm();
            eat(")");
            eat(";");
            Statement stmt = parseStatement();
            
            procedures.add(new ProcedureDeclaration(id, stmt, params));
        }
        
        Statement stmt = parseStatement();
        
        return new Program(procedures, variables, stmt);
    }
    
    /**
     * Determines if the parser has reached the end of the file or not
     * 
     * @return true if the parser has not reached the end of file; otherwise,
     *         false 
     */
    public boolean notEnded()  
    {
        return !current.equals("END");
    }

}
