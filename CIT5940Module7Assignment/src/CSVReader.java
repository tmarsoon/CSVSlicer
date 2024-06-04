/*
 * I attest that the code in this file is entirely my own except for the starter
 * code provided with the assignment and the following exceptions:
 * <Enter all external resources and collaborations here. Note external code may
 * reduce your score but appropriate citation is required to avoid academic
 * integrity violations. Please see the Course Syllabus as well as the
 * university code of academic integrity:
 *  https://catalog.upenn.edu/pennbook/code-of-academic-integrity/ >
 * Signed,
 * Author: Tony Marsalla
 * Penn email: <marsalla@seas.upenn.edu>
 * Date: 2024-03-05
 */


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * {@code CSVReader} provides a stateful API for streaming individual CSV rows
 * as arrays of strings that have been read from a given CSV file.
 *
 * @author Tony Marsalla
 */
public class CSVReader {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 5130409650040L;
    private final CharacterReader reader;
    private State state;
    private int currentLine = 1; // Added to track the current line number
    private int currentColumn = 0;
    public CSVReader(CharacterReader reader) {
        this.reader = reader;
        this.state = State.INIT;
    }

    /**
     * This method uses the class's {@code CharacterReader} to read in just enough
     * characters to process a single valid CSV row, represented as an array of
     * strings where each element of the array is a field of the row. If formatting
     * errors are encountered during reading, this method throws a
     * {@code CSVFormatException} that specifies the exact point at which the error
     * occurred.
     *
     * @return a single row of CSV represented as a string array, where each
     *         element of the array is a field of the row; or {@code null} when
     *         there are no more rows left to be read.
     * @throws IOException when the underlying reader encountered an error
     * @throws CSVFormatException when the CSV file is formatted incorrectly
     */

    public String[] readRow() throws IOException, CSVFormatException {
    	List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean endOfFile = false;
        int c;

        while ((c = reader.read()) != -1) {
            char ch = (char) c;

            switch (state) {
                case INIT:
                case BETWEEN_FIELDS:
                    if (ch == ',') {
                        fields.add(currentField.toString());
                        currentField.setLength(0); // Reset the StringBuilder
                        // Stay in BETWEEN_FIELDS state
                    } else if (ch == '"') {
                        state = State.IN_QUOTED_FIELD;
                    } else if (ch == '\n') {
                        // Handle end of row
                        fields.add(currentField.toString());
                        return fields.toArray(new String[0]);
                    } else {
                        currentField.append(ch);
                        state = State.IN_FIELD;
                    }
                    break;
                case IN_FIELD:
                    if (ch == ',') {
                        fields.add(currentField.toString());
                        currentField.setLength(0); // Reset
                        state = State.BETWEEN_FIELDS;
                    } else if (ch == '\n') {
                        // Handle end of row
                        fields.add(currentField.toString());
                        return fields.toArray(new String[0]);
                    } else {
                        currentField.append(ch);
                        // Stay in IN_FIELD state
                    }
                    break;
                case IN_QUOTED_FIELD:
                    if (ch == '"') {
                        state = State.AFTER_QUOTE;
                    } else {
                        currentField.append(ch);
                        // Stay in IN_QUOTED_FIELD state
                    }
                    break;
                case AFTER_QUOTE:
                    if (ch == '"') { // Escaped quote
                        currentField.append('"');
                        state = State.IN_QUOTED_FIELD;
                    } else if (ch == ',') {
                        fields.add(currentField.toString());
                        currentField.setLength(0);
                        state = State.BETWEEN_FIELDS;
                    } else if (ch == '\n') {
                        fields.add(currentField.toString());
                        return fields.toArray(new String[0]);
                    } else if (endOfFile) {
                        fields.add(currentField.toString());
                        return fields.toArray(new String[0]);
                    } 
                    break;
            }
        }

        // Handle end of file without a newline
        if (currentField.length() > 0 || state == State.BETWEEN_FIELDS) {
            fields.add(currentField.toString());
        }
        return fields.isEmpty() ? null : fields.toArray(new String[0]);
    }


enum State {
    INIT, // Initial state, before reading any character
    IN_FIELD, // Inside a non-quoted field
    IN_QUOTED_FIELD, // Inside a quoted field
    AFTER_QUOTE, // Immediately after a closing quote in a quoted field
    BETWEEN_FIELDS // Between fields, such as after a comma or at the start of a line
}


    // main method remains unchanged

    
  
    /**
     * Feel free to edit this method for your own testing purposes. As given, it
     * simply processes the CSV file supplied on the command line and prints each
     * resulting array of strings to standard out. Any reading or formatting errors
     * are printed to standard error.
     *
     * @param args command line arguments (1 expected)
     * @throws CSVFormatException 
     * @throws IOException 
     */
    
    public static void main(String[] args) {
   
            
    if (args.length ==0) {
           //  System.err.println("Usage: java Main <filename1> [<filename2> ...]");
             return;
         }
    	for (String filename : args) {
    	
    	try (CharacterReader reader = new CharacterReader(filename)) {
    		
    	        var csvReader = new CSVReader(reader);
    	      
    	        String[] excelRow;
    	        System.out.println("Filename: " + filename);
    	   
    	     while ((excelRow = csvReader.readRow()) != null) {
    	        
    	         if (excelRow.length > 0) { 
    	             // Iterate through each cell in the row and print it
    	             for (int i = 0; i < excelRow.length; i++) {
    	                
    	                 System.out.print(excelRow[i]);
    	                 // Print comma if it's not the last cell
    	                 if (i < excelRow.length-1) {
    	                     System.out.print(", ");
    	                 }
    	          
    	             }
    	           
    	         }
    	   
					
					}
				
    	     
    } catch (IOException |CSVFormatException e ) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
    	
}
    
}         
         

        	
    /**
     * This is a private method that is used to process the non-empty rows and read the data from the file.
     *This private method will be used in the main method since the main method will be responsible for only utilizing the command line arguments to process the file.
     * @param filename is the name of the CSV file to process
     */

    	        //if a file is not found, throw an exception and continue parsing the files until the end of the arguments are reached
/*    	    } catch (java.io.FileNotFoundException exclude) {
    	    	//utilizing system.err to print information out in red if a file isn't found
    	    	//essentially this will catch the error that is thrown from args if the command line argument has an error in the naming of the file
    	      //  System.err.println("File not found: " + filename);
    	        exclude.printStackTrace();
    	        //creating a multi-catch clause 
    	    } catch (java.io.IOException | CSVFormatException exclude) {
    	        //System.err.println("Error reading CSV: " + exclude.getMessage());
    	        exclude.printStackTrace();
    	    }
    	}
}*/
    	  
     
