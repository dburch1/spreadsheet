/**
 * The Configuration Class retrieves the values from a .conf file and places them into a HashMap for easy access 
 *
 * @author David Burch
 * @version 1.0
 */


import java.util.ArrayList;
import java.util.HashMap;


public class Configuration {
	private ArrayList<Character> fileContents;
	private HashMap<String, String> map;

	/**
	 * Constructor for the Configuration class
	 * @param none
	 * @return none
	 */
	public Configuration() {
		fileContents = Data.readFile("books.conf");
		map = new HashMap<String, String>();
		setMap();
	}

	/**
	 * Get the value associated with a given key.
	 * @param key to match
	 * @return String value
	 */
	public String getValue(String key) {
		return map.get(key);
	}

	/**
	 * Get the value associated with a given key and change to an int.
	 * @param key to match
	 * @return int equivalent of value
	 */
	public int getValueAsInt(String key) {
		return Integer.parseInt(map.get(key));
	}

	/**
	 * Take contents from a list of Characters and place into a HashMap
	 * @param none
	 * @return none
	 */
	private void setMap() {
		String aString = "";
		String key = "";
		String value = "";
		String[] strArr = new String[fileContents.size()];
		String[] nextStrArr;
		
		for (Character eachChar : fileContents) {
			aString += eachChar;	// Convert each Character to a string and concatenate
		}

		strArr = aString.split("\n");
		nextStrArr = new String[2];

		for (String eachStr : strArr) {
			nextStrArr = eachStr.split(":");
			key = nextStrArr[0];
			value = nextStrArr[1].trim();
			map.put(key, value);
		}
	}

	/**
	 * Get an array of values that match given key plus a number at the end, use if there may be multiple entries of a similar type in .conf file.
	 * @param String of key to get from map (will append number)
	 * @return String array of the corresponding values 
	 */
	public ArrayList<String> getArrayOfValues(String partialKey) {
		ArrayList<String> valuesList = new ArrayList<String>();
		for (int i = 0; i < map.size(); i++) {
			if(map.containsKey(partialKey + i)) {
				valuesList.add(map.get(partialKey + i));
			}
		}
		return valuesList;
	}
}

