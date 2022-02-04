package scanner;

import java.io.IOException;
import java.io.*;

/**
 * Scanner is a simple scanner for Compilers and Interpreters (2014-2015) lab exercise 1
 * @author Vibha Arramreddy
 * @version 6 February 2020 
 *
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;
    
    
    /**
     * Scanner constructor for construction of a scanner that 
     * uses an InputStream object for input.  
     * 
     * Usage: 
     * FileInputStream inStream = new FileInputStream(new File(<file name>);
     * 
     * Scanner lex = new Scanner(inStream);
     * 
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }
    
    /**
     * Scanner constructor for constructing a scanner that 
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * 
     * Usage: Scanner lex = new Scanner(input_string);
     * 
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }
    
    /**
     * The getNextChar method attempts to get the next character from the input
     * stream.  It sets the endOfFile flag true if the end of file is reached on
     * the input stream.  Otherwise, it reads the next character from the stream
     * and converts it to a Java String object.
     * 
     * postcondition: The input stream is advanced one character if it is not at
     * end of file and the currentChar instance field is set to the String 
     * representation of the character read from the input stream.  The flag
     * endOfFile is set true if the input stream is exhausted.
     */
    private void getNextChar()
    {
        try
        {
            int inp = in.read();
            if (inp == -1) 
                eof = true;
            else 
                currentChar = (char) inp;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    /**
     * Advances to the next character in the input stream.
     *  
     * @param expected the expected character
     * @throws ScanErrorException if expected character does match the current character
     */
    private void eat(char expected) throws ScanErrorException
    {
        if (currentChar == expected)
        {
            getNextChar();
            
            if(currentChar == '.')
            {
                eof = true;
            }
           
        }
        else
        {
            throw new ScanErrorException("Illegal character: expected" + expected 
                    + "and found" + currentChar);
        }
    }
    
    /**
     * Determines if a character is a digit or not.
     * 
     * @param character the char being identified 
     * @return true if the character is a digit; otherwise, false
     */
    public static boolean isDigit(char character)
    {
        return character >= '0' && character <= '9';
    }
    
    /**
     * Determines if a character is a letter or not. 
     * 
     * @param character the char being identified
     * @return true if the character is a letter; otherwise, false
     */
    public static boolean isLetter(char character)
    {
        return (character >= 'A' && character <= 'Z') || (character >= 'a' && character <= 'z');
    }
    
    /**
     * Determines if a character is a white space or not. 
     * 
     * @param character the char being identified
     * @return true if the character is a white space; otherwise, false
     */
    public static boolean isWhiteSpace(char character)
    {
        return character == ' ' || character == '\t' || character == '\r' || character == '\n';
    }
    
    /**
     * Determines if a character is an operand or not.
     * 
     * @param character the char being identified
     * @return true if the character is an operand; otherwise, false
     */
    public static boolean isOperand(char character)
    {
        return (character == '=' || character == '<' || character == '>' || character == '+' 
                || character == '-' || character == '*' || character == '/' || character == '%' 
                || character == '(' || character == ')' || character == ';' || character == ':'
                || character == ',' || character == '!');
    }
    
    /**
     * Scans the number token and returns its lexeme. A number lexeme consists of only digits. 
     * 
     * postcondition: the input stream advances to the first character after the number. 
     * 
     * @return a lexeme which is a number
     * @throws ScanErrorException if the character being scanned does not match expected value 
     */
    private String scanNumber() throws ScanErrorException
    {
        String num = "";
        
        while (isDigit(currentChar))
        {
            num += currentChar;
            eat(currentChar);
        }
        
        return num;
    }
    
    /**
     * Scans the identifier token and returns its lexeme. 
     * An identifier lexeme starts with a letter and is followed any number of letters and digits. 
     * 
     * postcondition: the input stream advances to the first character after the identifier. 
     * 
     * @return a lexeme of an number token
     * @throws ScanErrorException if the character being scanned does not match expected value 
     */
    private String scanIdentifier() throws ScanErrorException
    {
        String indentifier = "";
        
        while (isLetter(currentChar) || isDigit(currentChar))
        {
            indentifier += currentChar;
            eat(currentChar);
        }
        
        return indentifier;
    }
    
    /**
     * Scans the operand token and returns its lexeme. 
     * 
     * postcondition: the input stream advances to the first character after the operand. 
     * 
     * @return a lexeme of an identifier token
     * @throws ScanErrorException if the character being scanned does not match expected value 
     */
    private String scanOperand() throws ScanErrorException
    {
        String operand = "";
        
        operand += currentChar;
        eat(currentChar);
        
        if (currentChar == '=')
        {
            operand += currentChar;
            eat(currentChar);
        }

        return operand;
    }
    
    
    /**
     * Scans an in-line comment, which starts with '//' and ends with a line break. 
     * It throws out the comment by not return anything. 
     * 
     * postcondition: the input stream advances to the first character after the comment. 
     * 
     * @return a null string
     * @throws ScanErrorException if the character being scanned does not match expected value 
     */
    private void scanLineComment() throws ScanErrorException
    {
        
        while (currentChar != '\n')
        {
            eat(currentChar);
        }
        
    }
    
    /**
     * Determines if there is a next character or if the scanner is at the end of the file.
     * 
     * @return true if there is a next character; otherwise, false
     */
    public boolean hasNext()
    {
        return (!eof);
    }
    
    
    /**
     * Scans the next token and returns its lexeme. 
     * It ignores and does not tokenize white spaces and comments.
     * It returns 'END' when it reaches a period of the end of the file. 
     * 
     * postcondition: the input stream advances to the first character after the token. 
     * 
     * @return the lexeme of the token
     * @throws ScanErrorException if the character being scanned does not match expected value 
     */
    public String nextToken() throws ScanErrorException
    {
        String lexeme = "";
        
        try 
        {  
            while (isWhiteSpace(currentChar) && !eof)
            {
                eat(currentChar);
            }
            
            if (isDigit(currentChar))
            {
                lexeme = scanNumber();
            }
            else if (isLetter(currentChar))
            {
                lexeme = scanIdentifier();
            }
            else if (isOperand(currentChar))
            {
                lexeme = scanOperand(); 
                
                if(lexeme.equals("/") && currentChar == '/')
                {
                    lexeme = "";
                    scanLineComment();
                }
                
            }
            else if (eof)
            {
                lexeme = "END";
            }
            else
            {
                throw new ScanErrorException("Unrecongized character:" + currentChar);
            }
            
        }
        catch (ScanErrorException e)
        {
            System.out.print(e.getMessage());
            eat(currentChar);
            //System.exit(-1);
        }
        
        return lexeme;

    }    
}
