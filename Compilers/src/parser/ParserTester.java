package parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import ast.Program;
import ast.Statement;
import emitter.Emitter;
import environment.Environment;
import scanner.ScanErrorException;
import scanner.Scanner;

/**
 * ParserTester tests to see if Parser is working correctly. 
 * 
 *@author Vibha Arramreddy
 *@version 6 February 2020
 */
public class ParserTester {
    
    /**
     * Reads in a file. Then, uses Parser to parse the series of tokens,
     * which the Scanner outputted after scanning the previously mentioned file. 
     * 
     * @param args the arguments passed through the main method
     * @throws ScanErrorException if the token being scanned does not match expected value 
     * @throws FileNotFoundException when file is not found 
     */
    public static void main(String[] args) throws ScanErrorException, FileNotFoundException
    {

        FileInputStream inp = new FileInputStream(new File("./src/parser/parserTest9.txt"));
        Scanner scan = new Scanner(inp);
        Parser parser = new Parser(scan);
        Environment env = new Environment(null);

        while (parser.notEnded())
        {
            Program prog = parser.parseProgram();
            prog.compile("./src/emitter/compileOutput.txt");
        }
    }

}

