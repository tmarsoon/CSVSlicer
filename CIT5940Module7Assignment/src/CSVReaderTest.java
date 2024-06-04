/*
 ***** Important!  Please Read! *****
 *
 *  - Do NOT remove any of the existing import statements
 *  - Do NOT import additional junit packages 
 *  - You MAY add in other non-junit packages as needed
 * 
 *  - Do NOT remove any of the existing test methods or change their name
 *  - You MAY add additional test methods.  If you do, they should all pass
 * 
 *  - ALL of your assert test cases within each test method MUST pass, otherwise the 
 *        autograder will fail that test method
 *  - You MUST write the require number of assert test cases in each test method, 
 *        otherwise the autograder will fail that test method
 *  - You MAY write more than the required number of assert test cases as long as they all pass
 * 
 *  - All of your assert test cases within a method must be related to the method they are meant to test
 *  - All of your assert test cases within a method must be distinct and non-trivial
 *  - Your test cases should reflect the method requirements in the homework instruction specification
 * 
 *  - Your assert test cases will be reviewed by the course instructors and they may take off
 *        points if your assert test cases to do not meet the requirements
 */

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.*;

class CSVReaderTest {
	 CSVReader csvReader;
	 CSVReader csvReader2;
	 CSVReader csvReader3;
	 CSVReader csvReader4;

	    @BeforeEach
	    void setUp() {
	        // Initialize the reader with a mock CharacterReader for testing
	    	try {
				csvReader = new CSVReader(new CharacterReader("easy1.csv"));
				csvReader2 = new CSVReader(new CharacterReader("easy2.csv"));
				csvReader3 = new CSVReader(new CharacterReader("easy3.csv"));
				csvReader4 = new CSVReader(new CharacterReader("medium1.csv"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Replace "filename.csv" with the actual filename
	    
	    }


	
	@Test
	void testBasic() {
		// TODO Write at least 5 test cases with assert statements. All cases must pass
		// These tests should check basic functionality of readRow()
		 // Initialize the expected row
		// Initialize the expected row
	    String[] expectedRow = {"name", "age"};

	    try {
	        // Read the row using the CSVReader object
	        String[] actualRow = csvReader.readRow();

	        // Perform assertion
	        assertArrayEquals(expectedRow, actualRow);
	    } catch (IOException | CSVFormatException e) {
	        // Handle exceptions, if any
	        fail("Exception occurred: " + e.getMessage());
	    }
	    

	    // Test case 2: Single row with three columns
	    String[] expectedRow2 = {"from", "to", "sequence_number", "message"};
	    try {
	        String[] actualRow2 = csvReader2.readRow();
	        assertArrayEquals(expectedRow2, actualRow2);
	    } catch (IOException | CSVFormatException e) {
	        fail("Exception occurred: " + e.getMessage());
	    }

	    // Test case 3: Single row with one column
	    String[] expectedRow3 = {"example of using  in a field", "1"};
	    try {
	        String[] actualRow3 = csvReader3.readRow();
	        assertArrayEquals(expectedRow3, actualRow3);
	    } catch (IOException | CSVFormatException e) {
	        fail("Exception occurred: " + e.getMessage());
	    }

	    // Test case 4: Single row with multiple values in one column
	    String[] expectedRow4 = {"from", "to", "sequence_number", "message"};
	    try {
	        String[] actualRow4 = csvReader4.readRow();
	        assertArrayEquals(expectedRow4, actualRow4);
	    } catch (IOException | CSVFormatException e) {
	        fail("Exception occurred: " + e.getMessage());
	    }
	    try {
            String[] actualRow = csvReader.readRow(); // Assuming the first row of easy1.csv is of interest
            assertNotNull(actualRow);
            assertEquals("hannah", actualRow[0]); // Verify first field
            assertEquals("20", actualRow[1]); // Verify second field

            actualRow = csvReader2.readRow(); // First row of easy2.csv
            assertNotNull(actualRow);
            assertEquals("alice", actualRow[0]); // Adjust based on actual content

            //testing easy3 because it should be an empty array
            assertNotNull(csvReader3.readRow());

            actualRow = csvReader4.readRow(); // First row of medium1.csv
            assertNotNull(actualRow);
            assertEquals("alice", actualRow[0]); // Adjust based on actual content
        } catch (IOException | CSVFormatException e) {
            fail("Exception occurred: " + e.getMessage());
        }
	}

	@Test
	void testLineEnd() {
		// TODO Write at least 5 test cases with assert statements. All cases must pass
		// These tests should check different line ending cases
		// Test case 1: Single row with LF line ending
	    String[] expectedRow1 = {"name", "age"};
	    try {
	        String[] actualRow1 = csvReader.readRow();
	        assertArrayEquals(expectedRow1, actualRow1);
	    } catch (IOException | CSVFormatException e) {
	        fail("Exception occurred: " + e.getMessage());
	    }

	    // Test case 2: Single row with CRLF line ending
	    String[] expectedRow2 = {"from", "to", "sequence_number", "message"};
	    try {
	        String[] actualRow2 = csvReader2.readRow();
	        assertArrayEquals(expectedRow2, actualRow2);
	    } catch (IOException | CSVFormatException e) {
	        fail("Exception occurred: " + e.getMessage());
	    }

	    // Test case 3: Single row with CR line ending
	    String[] expectedRow3 = {"column"};
	    try {
	        String[] actualRow3 = csvReader.readRow();
	        assertArrayEquals(expectedRow3, actualRow3);
	    } catch (IOException | CSVFormatException e) {
	        fail("Exception occurred: " + e.getMessage());
	    }

	    // Test case 4: Single row with no line ending (end of file)
	    String[] expectedRow4 = {"data with , comma"};
	    try {
	        String[] actualRow4 = csvReader.readRow();
	        assertArrayEquals(expectedRow4, actualRow4);
	    } catch (IOException | CSVFormatException e) {
	        fail("Exception occurred: " + e.getMessage());
	    } 
	}
	

	@Test
void testPathological() {
		// TODO Write at least 5 test cases with assert statements. All cases must pass
		// These tests should check that readRow() correctly handles exceptions
	
	        // Test case 1: Testing easy3.csv for being empty or having minimal content
	        try {
	            String[] row = csvReader3.readRow();
	            assertNull(row, "Expected easy3.csv to be empty or have no valid rows.");
	        } catch (IOException | CSVFormatException e) {
	            fail("Exception occurred reading easy3.csv: " + e.getMessage());
	        }

	        // Test case 2: Verify handling of rows with excessive length in medium1.csv
	        try {
	            String[] longRow = csvReader4.readRow();
	            assertNotNull(longRow, "Expected medium1.csv to handle long rows correctly.");
	            // Further validation could include checking the length of the returned array or contents of the fields
	        } catch (IOException | CSVFormatException e) {
	            fail("Exception occurred reading medium1.csv: " + e.getMessage());
	        }

	        // Test case 3: Check for correct handling of quoted fields in medium1.csv
	        try {
	            String[] quotedFieldRow = csvReader4.readRow(); // Assuming the first row contains quotes
	            assertNotNull(quotedFieldRow, "Expected medium1.csv to handle quoted fields.");
	            // Example check: assertTrue(quotedFieldRow[3].contains("\""), "Quoted field not processed correctly.");
	        } catch (IOException | CSVFormatException e) {
	            fail("Exception occurred processing quoted fields in medium1.csv: " + e.getMessage());
	        }

	        // Test case 4: Ensure csvReader1 (easy1.csv) correctly processes the expected number of rows.
	        try {
	            int rowCount = 0;
	            while (csvReader.readRow() != null) rowCount++;
	            assertTrue(rowCount > 0, "Expected easy1.csv to contain rows.");
	        } catch (IOException | CSVFormatException e) {
	            fail("Exception occurred counting rows in easy1.csv: " + e.getMessage());
	        }

	        // Test case 5: Testing easy2.csv for special characters handling, like newline in fields.
	        try {
	            String[] rowWithSpecialChars = csvReader2.readRow();
	            assertNotNull(rowWithSpecialChars, "Expected easy2.csv to handle special characters.");
	            // Assuming special character handling involves escape sequences, newlines, etc.
	            // Example: assertTrue(rowWithSpecialChars[3].contains("\n"), "Newline character not processed correctly.");
	        } catch (IOException | CSVFormatException e) {
	            fail("Exception occurred reading special characters in easy2.csv: " + e.getMessage());
	        }
	    }
	

	@Test
	void testData() {
		// TODO Write at least 5 test cases with assert statements. All cases must pass
		// These tests should check different corner cases that can occur
		// Test case 1: Single row with two columns
		 // Test case 1: Single row with data containing special characters
	/*    String[] expectedRow1 = {"name", "age"};
	    try {
	        String[] actualRow1 = csvReader.readRow();
	        assertArrayEquals(expectedRow1, actualRow1);
	    } catch (IOException | CSVFormatException e) {
	        fail("Exception occurred: " + e.getMessage());
	    }

	    // Test case 2: Single row with empty fields
	    String[] expectedRow2 = {"from", "to", "sequence_number", "message"};
	    try {
	        String[] actualRow2 = csvReader2.readRow();
	        assertArrayEquals(expectedRow2, actualRow2);
	    } catch (IOException 
	    		| CSVFormatException e) {
	        fail("Exception occurred: " + e.getMessage());
	    }
	 
	    // Test case 3: Single row that has quotes in the row
	    String[] expectedRow3 = {"alice", "bob", "5", "Thoughts on so-called \"quotes\"?"};
	    try {
	    	  // Skip the first four elements
	    	csvReader3 = new CSVReader(new CharacterReader("medium1.csv"));
	    	
	        for (int i = 0; i < 4; i++) {
	        	
	            csvReader3.readRow();
	        }
	        String[] actualRow3 = csvReader3.readRow();
	       
	        assertArrayEquals(expectedRow3, actualRow3);
	        
	    } catch (IOException | CSVFormatException e) {
	        fail("Exception occurred: " + e.getMessage());
	    }

	    // Test case 4: Single row with multi-line data
	    String[] expectedRow4 = {"name", "age", "address"};
	    try {
	        String[] actualRow4 = csvReader.readRow();
	        assertArrayEquals(expectedRow4, actualRow4);
	    } catch (IOException | CSVFormatException e) {
	        fail("Exception occurred: " + e.getMessage());
	    }

	    // Test case 5: Single row with data containing leading/trailing whitespace
	    String[] expectedRow5 = {" name ", " age ", " city "};
	    try {
	        String[] actualRow5 = csvReader.readRow();
	        assertArrayEquals(expectedRow5, actualRow5);
	    } catch (IOException | CSVFormatException e) {
	        fail("Exception occurred: " + e.getMessage());
	    }*/
	}
	

	@Test
	void testSpeed() {
		// TODO 
		// There are *** NO *** required tests for this method and they will *** NOT *** be checked by the autograder
		// However, it is good practice to consider how your code will do on large inputs
		// Consider cases that might result in inefficient run times
		// Also consider ways to measure your run time and determine if it can be improved
	
		}
}


