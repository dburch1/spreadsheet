/**
 * Simple bookkeeping program
 *
 * This program writes journal entries to a file and allows for editing the field.
 *
 * @author David Burch
 * @version 1.0
 */


import javax.swing.SwingUtilities;


public class Books {
	/**
	 * Main method, entry point for program
	 *
	 * @param none
	 * @return none
	 */
	public static void main(String args[]) {
		Runnable start = new Runnable() {
			public void run() {
				new GUI ();
			}
		};
		SwingUtilities.invokeLater(start);
	}
}

