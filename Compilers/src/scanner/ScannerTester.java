package scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * ScannerTester tests to see if Scanner is working correctly. 
 * 
 *@author Vibha Arramreddy
 *@version 6 February 2020
 */
public class ScannerTester
{
    /**
     * Reads in a file. Then, uses Scanner to scan and tokenize the file.
     * It prints out the tokens' lexeme to verify the accuracy of Scanner.    
     * 
     * @param args the arguments passed through the main method
     * @throws ScanErrorException if the character being scanned does not match expected value 
     * @throws FileNotFoundException when file is not found 
     */
    public static void main(String[] args) throws ScanErrorException, FileNotFoundException
    {

        FileInputStream inp = new FileInputStream(new File("./src/parser/parserTestExtra3.txt"));
        Scanner scan = new Scanner(inp);

        while (scan.hasNext())
        {
            System.out.println(scan.nextToken());
        }

    }
    
}