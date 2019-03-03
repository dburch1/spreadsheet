/**
 * The Data Class provides a way to access data in files and edit files.
 * This class has no fields or construtors.  No instances needed so methods are all static.
 *
 * @author David Burch
 * @version 1.0
 */


import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.JTextField;


public class Data {
	/**
	 * Read a file one character at a time until end of file, wraps each char in a Character wrapper then places in an ArrayList.
	 * @param The file to be read
	 * @return ArrayList<Character> - a list of each individual Characters from file
	 */
	public static ArrayList<Character> readFile(String file) {
		ArrayList<Character> fileContents = new ArrayList<Character>();
		int intRead;	// To hold int values returned by reader
		char ch;	// Cast ints to chars
		try (FileReader reader = new FileReader(file)) {
			do {	
				intRead = reader.read();
				if (intRead != -1) {
					ch = (char) intRead;
					Character eachChar = new Character(ch);	 // wrapper
					fileContents.add(eachChar);	 // Add to ArrayList of Character objects
				}
			} while (intRead != -1);
		} catch (IOException io) {
			System.out.println("IOException on reading file");
		}
		return fileContents;
	}

	/**
	 * Overloaded method writes a string to a file
	 * @param The file to write to and String to be written
	 * @return none
	 */
	public static void writeToFile(String file, String text) {
		try (FileWriter writer = new FileWriter(file)) {
			writer.write(text);
		} catch (IOException io) {
			System.out.println("IOException on writing file");
			return;
		}	
	}

	/**
	 * Overloaded method gets text from an ArrayList of JTextFields and writes each to a file, separating values by tabs.
	 * @param 1. The file to write to 2. An arraylist of JTextFields 3. Number of columns
	 * @return none
	 */
	public static void writeToFile(String file, ArrayList<JTextField> arrList, int numberCols) {
		try (FileWriter writer = new FileWriter(file)) {
			for (int i = 0; i < arrList.size(); i++) {
				String tempText = arrList.get(i).getText();
				if ((i - (numberCols - 1)) % numberCols == 0) { // Start new line when last column reached
					writer.write(tempText + "\n");
				} else {
					writer.write(tempText + "\t");
				}
			}
		} catch (IOException io) {
			System.out.println("IOException on writing file");
			return;
		}
	}

	/**
	 * Make a new ArrayList of JTextFields that contains only fields with text or blank fields that come before the last non-blank field.
	 * @param ArrayList<JTextField>
	 * @return A new ArrayList<JTextField> (not necessarily changed)
	 */
	public static ArrayList<JTextField> makeSkinnyList(ArrayList<JTextField> arrList) {
		ArrayList<JTextField> skinnyList = new ArrayList<JTextField>();
		int lastNonBlank = 0;
		for (int i = 0; i < arrList.size(); i++) {
			if (!arrList.get(i).getText().equals("")) {
				lastNonBlank = i + 1;
			}
		}
		
		for (int j = 0; j < lastNonBlank; j++) {
			skinnyList.add(arrList.get(j));
		}
		return skinnyList;
	}
}

