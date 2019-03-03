/**
 * The GUI Class handles all the Java Swing components
 *
 * @author David Burch
 * @version 1.0
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*; // Added to use ArrayList


public class GUI implements ActionListener {
	private Configuration config;
	private TextFields textFields;
	private JPanel header;	
	private int rowHeight;
	private int numberCols;
	private	JLabel notify;
	private JFrame frame;
	private JScrollPane scrollPane;
	private JPanel contentPanePanel, topPanel, bottomPanel, leftPanel, rightPanel, centerPanel;
	private ArrayList<String> strList;
	private ArrayList<JTextField> jTextArray;
		
		
	/**
	 * Constructor for the GUI class
	 * @param none
	 * @return none
	 */
	public GUI() {
		config = new Configuration();
		textFields = new TextFields();
		header = new JPanel();
		strList = textFields.getText();
		jTextArray = new ArrayList<JTextField>();
		rowHeight = config.getValueAsInt("RowHeight");
		numberCols = config.getValueAsInt("NumberColumns");
		contentPanePanel = new JPanel();
		topPanel = new JPanel();
		bottomPanel = new JPanel();
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		centerPanel = new JPanel();
 		notify = new JLabel("This is the display section");
		// Method calls
		setHeader(); 
		addRows(textFields.getLineCount() + 20);
		setTextInJTextFields();
		setButtons();
		setDisplayArea();
		setFrameAndMainPanels();
		jTextArray.get(textFields.getMarker()).requestFocus(); // Set focus
	}

	
	/**
	 * Get column names and dimensions and place into JLabels which go into a JPanel  
	 * @param none
	 * @return none
	 */
	private void setHeader() {
		ArrayList<String> colNames = config.getArrayOfValues("Column");
		ArrayList<String> colWidths = config.getArrayOfValues("ColumnWidth");
		JLabel[] labelList = new JLabel[colNames.size()];
		for (int i = 0; i < colNames.size(); i++) {
			labelList[i] = new JLabel(colNames.get(i));
		}

		// Make JPanel and set up header row
		header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));
		header.setPreferredSize(new Dimension (config.getValueAsInt("HeaderRowX"), config.getValueAsInt("HeaderRowY")));

		for (int j = 0; j < labelList.length; j++) {
			labelList[j].setMaximumSize(new Dimension (Integer.parseInt(colWidths.get(j)), config.getValueAsInt("ColY")));
			// Add component to top JPanel
			header.add(labelList[j]);
		}
		topPanel.add(header);
	}

	/**
	 * Add rows and text fields, the rows are in a JPanel and fields are JTextField.
	 * @param integer value for number of rows to add
	 * @return none
	 */
	public void addRows(int numRows) {
		// Outer loop is number of rows
		for (int i = 0; i < numRows; i++) {
			JPanel eachRow = new JPanel();
			centerPanel.add(eachRow);
			eachRow.setPreferredSize(new Dimension(840, rowHeight));
			// Inner loop is number of columns
			for (int j = i * numberCols; j < (i * numberCols) + numberCols; j++) {
				JTextField enter = new JTextField();
				if (j == (i * numberCols) || j == (i * numberCols) + 2) {
					enter.setPreferredSize(new Dimension (70, rowHeight));
				} else if (j == (i * numberCols) + 1) {
					enter.setPreferredSize(new Dimension (500, rowHeight));
				} else {
					enter.setPreferredSize(new Dimension (100, rowHeight));
				}
				eachRow.add(enter);
				highlight(enter);
				jTextArray.add(enter);
			}
		}
	}

	/**
	 * Set text in JTextFields
	 * @param none
	 * @return none
	 */
	public void setTextInJTextFields() {
		int i = 0;
		for (String eachString : strList) {
			jTextArray.get(i).setText(eachString);
			i++;
		}
	}

	/**
	 * Highlight a text field when cursor is there, also select all text in the field
	 * @param The field to be highlighted
	 * @return none
	 */
	public void highlight(JTextField fieldToHighlight) {
		fieldToHighlight.addFocusListener(new FocusListener() {
			@Override public void focusLost(final FocusEvent focus) {
				fieldToHighlight.setCaretPosition(0); // Needed something to take focus away, the highlight was sometimes staying even after focus left text field.
				fieldToHighlight.setBackground(new Color(255, 255, 255));
			}
			@Override public void focusGained(final FocusEvent focus) {
				fieldToHighlight.selectAll();
				fieldToHighlight.setBackground(new Color(245, 245, 100));
			}
		});
	}

	/**
	 * Make buttons and set text on buttons.
	 * @param none
	 * @return none
	 */
	public void setButtons() {
		// Add write button
		JButton button = new JButton("Write");
		button.setPreferredSize(new Dimension (100, 40));
		button.setActionCommand("buttonPressed");
		button.addActionListener(this);
		bottomPanel.add(button);

		// Add rows button
		JButton rowsButton = new JButton("Add Rows");
		rowsButton.setPreferredSize(new Dimension (150, 40));
		rowsButton.setActionCommand("rowsButtonPressed");
		rowsButton.addActionListener(this);
		bottomPanel.add(rowsButton);
	}
	
	/**
	 * Setup and add notification area which goes at very bottom
	 * @param none
	 * @return none
	 */
	public void setDisplayArea() {
		notify.setPreferredSize(new Dimension (1000, 150));
		bottomPanel.add(notify);
	}
	
	
	/**
	 * Event capture for adding rows when button is pressed
	 * @param none
	 * @return none
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("rowsButtonPressed")) {
			addRows(config.getValueAsInt("NumberRowsToAdd"));
			notify.setText("Rows added");
		}
		if (e.getActionCommand().equals("buttonPressed")) {
			ArrayList<JTextField> skinnyList = Data.makeSkinnyList(jTextArray);
			Data.writeToFile(textFields.getFileName(), skinnyList, numberCols); // what to pass here?
			notify.setText("Written to " + textFields.getFileName());
		}
	}
	
	/**
	 * Event capture and write to file.
	 * @param none
	 * @return none
	 */
	public void writeToFileEvent(ActionEvent e) {
		if (e.getActionCommand().equals("buttonPressed")) {
			//setTextInJTextFields();
			Data.writeToFile(textFields.getFileName(), jTextArray, numberCols); // what to pass here?
			notify.setText("Written to " + textFields.getFileName());
		}
	}

	/**
	 * Set up frame and main panels
	 * @param none
	 * @return none
	 */
	public void setFrameAndMainPanels() {
		// Make new frame
		JFrame frame = new JFrame("The Frame");

		// Set initial window size
		frame.setSize(1000, 750);

		// Terminate program when user closes
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// Main panels section
		// Scroll pane
		JScrollPane scrollPane = new JScrollPane(centerPanel);

		// Add contentPanePanel to frame.  Now the "contentPanePanel" will be the content pane.
		frame.setContentPane(contentPanePanel);

		// Set layout to BorderLayout
		contentPanePanel.setLayout(new BorderLayout());

		// Add 1 panel to each section of border layout
		contentPanePanel.add(topPanel, BorderLayout.PAGE_START);
		contentPanePanel.add(bottomPanel, BorderLayout.PAGE_END);
		contentPanePanel.add(leftPanel, BorderLayout.LINE_START);
		contentPanePanel.add(rightPanel, BorderLayout.LINE_END);
		contentPanePanel.add(scrollPane, BorderLayout.CENTER);

		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));

		// Set layout of center panel
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
// End Main panels

		// Display frame
		frame.setVisible(true);
	}
}

