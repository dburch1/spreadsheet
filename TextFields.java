/**
 * TextFields Class determines number and size of rows and controls events in the text area - Needs to be broken into two classes, TextColumns and DoublesColumns
 *
 * @author David Burch
 * @version 1.0
 */


import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;


public class TextFields {
	private final String FILE_NAME;
	private Configuration config;
	private ArrayList<Character> fileContents;

	/**
	 * Constructor for TextFields class
	 * @param none
	 * @return none
	 */
	public TextFields() {
		FILE_NAME = "test-file";
		config = new Configuration();
		fileContents = Data.readFile(FILE_NAME);
	}

	/**
	 * Accessor method to get the file name for reading and writing
	 * @param none
	 * @return String
	 */
	public String getFileName() {
		return FILE_NAME;
	}

	/**
	 * Take Characters from Data class and put into a String ArrayList, each string should contain the text for an individual text field.
	 * @param none
	 * @return An ArrayList of String to put in text fields
	 */
	public ArrayList<String> getText() {
		int nextArr = 0;
		String stringIn = "";
		ArrayList<String> strList = new ArrayList<String>();
		for (Character eachCh : fileContents) {
			if (eachCh == '\t' || eachCh == '\n') {
				strList.add(stringIn);
				nextArr += 1;
				stringIn = "";
			} else {
				stringIn += Character.toString(eachCh);
			}
		}
		return strList;
	}

	/**
	 * Find the last line of text and set a marker for two rows down and the first column to focus on; starts on first row if file is empty.
	 * @param none
	 * @return int - marks the spot to focus on
	 */
	public int getMarker() {
		if (fileContents.size() == 0) {
			return 0; // If no text in file then don't set focus.
		}
		int marker = -1; // Changed from zero to -1.  Was counting one too many.
		for (Character eachCh : fileContents) {
			if (eachCh == '\n' || eachCh == '\t') {
				marker += 1;
			}
		}
		// Take existing marker value and add two more rows minus the number of columns from the left
		marker = marker + ((2 * Integer.parseInt(config.getValue("NumberColumns"))) - (marker % Integer.parseInt(config.getValue("NumberColumns"))));
		return marker;
	}


	/**
	 * Get the number of lines from a file, right now the file is specified by the object
	 * @param none
	 * @return int - the number of lines
	 */
	public int getLineCount() {
		int lineCount = 0;
		for (Character eachCh : fileContents) {
			if (eachCh == '\n') {
				lineCount += 1;
			}
		}
		return lineCount;
	}

}
