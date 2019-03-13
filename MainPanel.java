//Antone J. Evans Jr.
//CSCI 3381 OO with Java 

package project2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import java.awt.Font;
import java.awt.TextField;
import javax.swing.JSlider;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.event.ChangeListener;

import javax.swing.event.ChangeEvent;
import javax.swing.SpinnerNumberModel;

public class MainPanel extends JPanel{
	private JComboBox comboBox;
	private JCheckBox chckbxNotWindy;
	private JCheckBox chckbxWindy;
	private JTextField textField;
	private JLabel lblHumility;
	private JSpinner spinner;
	private JMenuBar menuBar;
	private JSlider slider;
	private JMenuItem mntmOpen;
	private JLabel lblNewLabel;
	private JMenu mnNewMenu;
	private JMenuItem mntmNewMenuItem_1;
	private JMenuItem mntmNewMenuItem;
	private JLabel lblTemperature;
	private JRadioButton rdbtnSunny;
	private JRadioButton rdbtnRainy;
	private JRadioButton rdbtnOvercast;
	private JButton btnNewButton_2;
	private JLabel lblAddAPrediction;
	private JLabel lblNewLabel_1;
	private JLabel lblNotSet;
	private JButton btnPredictor;
	private JButton btnNewButton_1;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();

	Predictor myPred = new Predictor ("./project2/data.txt");	
	String tennis = "";
	String weather = "";
	boolean wind;
	int temp;
	int hum;

	public MainPanel() {
		setBorder(null);
		setBackground(Color.cyan);
		setPreferredSize(new Dimension(512, 429));
		setLayout(null);

		//Not windy check box
		chckbxNotWindy = new JCheckBox("Not Windy");
		//Checks if the user checked the box or not
		chckbxNotWindy.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					//makes windy = false 
					wind = false;
				}
			}
		});
		buttonGroup_1.add(chckbxNotWindy);
		chckbxNotWindy.setBounds(150, 153, 109, 23);
		add(chckbxNotWindy);

		//Windy check box
		chckbxWindy = new JCheckBox("Windy");
		//Checks if the user checked the box or not
		chckbxWindy.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					//makes windy true
					wind = true;
				}
			}
		});		
		buttonGroup_1.add(chckbxWindy);
		chckbxWindy.setBounds(10, 153, 111, 23);
		add(chckbxWindy);		

		//Humility is inputed with the spinner
		spinner = new JSpinner();
		//sets spinner min 0 and max 100
		spinner.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		//Checks the value of the spinner
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//converts the spinner object into an integer
				hum = (Integer)spinner.getValue();
			}
		});
		spinner.setBounds(319, 208, 69, 45);
		add(spinner);

		//prints humility
		lblHumility = new JLabel("Humility");
		lblHumility.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHumility.setBounds(319, 184, 93, 23);
		add(lblHumility);

		//Temperature is checked with a slider
		slider = new JSlider();
		//Checks the value of the temperature
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				temp = slider.getValue();
			}
		});
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setBounds(10, 208, 225, 45);
		add(slider);

		//writes temperature
		lblTemperature = new JLabel("Temperature");
		lblTemperature.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTemperature.setBounds(10, 183, 93, 24);
		add(lblTemperature);

		//Combo Box which allows the current prediction to be visible to the user 
		comboBox = new JComboBox();
		comboBox.setModel(new   DefaultComboBoxModel(myPred.toString().split("\n")));
		comboBox.setBounds(131, 35, 262, 38);
		add(comboBox);
		
		//The following code creates and menu bar at the top of the screen
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 97, 21);
		add(menuBar);

		mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);

		//Allows a file to be opened from laptop
		mntmOpen = new JMenuItem("Open\r\n");
		mntmOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				JFileChooser jfc = new JFileChooser("./");
				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					String baseName= "";
					baseName = jfc.getSelectedFile().getAbsolutePath();
					myPred = new Predictor(baseName);
					//updates combo box
					comboBox.setModel(new   DefaultComboBoxModel(myPred.toString().split("\n")));
					
				}  
			}
		});
		mnNewMenu.add(mntmOpen);

		mntmNewMenuItem = new JMenuItem("Save");
		mnNewMenu.add(mntmNewMenuItem);

		mntmNewMenuItem_1 = new JMenuItem("Edit");
		menuBar.add(mntmNewMenuItem_1);
	
		//writes prediction label
		lblNewLabel = new JLabel("Current Prediction ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(186, 0, 131, 24);
		add(lblNewLabel);

		//Check if sunny
		rdbtnSunny = new JRadioButton("sunny");
		buttonGroup.add(rdbtnSunny);
		rdbtnSunny.setBounds(10, 107, 109, 23);
		add(rdbtnSunny);

		//Check if rainy
		rdbtnRainy = new JRadioButton("rainy");
		buttonGroup.add(rdbtnRainy);
		rdbtnRainy.setBounds(150, 107, 109, 23);
		add(rdbtnRainy);

		//Check if overcast
		rdbtnOvercast = new JRadioButton("overcast");
		buttonGroup.add(rdbtnOvercast);
		rdbtnOvercast.setBounds(282, 107, 109, 23);
		add(rdbtnOvercast);

		//Image at the right of the screen
		btnNewButton_2 = new JButton("New button");
		btnNewButton_2.setIcon(new ImageIcon(MainPanel.class.getResource("/project2/swtennis.png")));
		btnNewButton_2.setBounds(419, 4, 93, 149);
		add(btnNewButton_2);

		//writes "add a prediction" on the screen 
		lblAddAPrediction = new JLabel("Add a Prediction");
		lblAddAPrediction.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAddAPrediction.setBounds(10, 71, 111, 29);
		add(lblAddAPrediction);

		//User inputs yes/no/maybe into the textbox when updating a prediction
		textField = new JTextField();
		textField.setBounds(265, 395, 86, 20);
		add(textField);
		textField.setColumns(10);

		//writes text to indicate to users what the above textbox is for
		lblNewLabel_1 = new JLabel("Play Tennis (Yes/No/Maybe)?");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(224, 370, 177, 33);
		add(lblNewLabel_1);

		//Indicates that a predication is currently not set
		lblNotSet = new JLabel("Not Set");
		lblNotSet.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNotSet.setBounds(268, 299, 69, 30);
		add(lblNotSet);

		//Users must click to populate a predication
		btnPredictor = new JButton("Predict\r\n");
		//Checks which type of day was clicked
		btnPredictor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Enumeration<AbstractButton> bg = buttonGroup.getElements();
				while (bg.hasMoreElements()) {
					JRadioButton jrd = (JRadioButton) bg.nextElement();
					if(jrd.isSelected())
						weather = jrd.getText();			
				}

				//stores user input
				Day d1 = new Day(weather, temp, hum, wind);
				//prints user input
				JOptionPane.showMessageDialog(null, "Play Tennis Today? " +myPred.predict(d1)+ " \nthe forecast is " 
						+ weather + " "+ temp + " " + hum + " "+ wind + " weather today!" );
			}
		});

		btnPredictor.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPredictor.setBounds(10, 294, 140, 45);
		add(btnPredictor);

		//Update button for users to add data
		btnNewButton_1 = new JButton("Update");
		//reads user "Yes/No/Maybe" input
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {   
				//ensures valid user input
				if(textField.getText().equalsIgnoreCase("yes") || textField.getText().equalsIgnoreCase("no") 
						|| textField.getText().equalsIgnoreCase("maybe") )
				tennis = textField.getText();     
				else
					JOptionPane.showMessageDialog(null, "invalid text");
				//reads which type of day it is (sunny/rainy/overcast)
				btnNewButton_1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						Enumeration<AbstractButton> bn = buttonGroup.getElements();
						while (bn.hasMoreElements()) {
							JRadioButton jrdd = (JRadioButton) bn.nextElement();
							if(jrdd.isSelected())
								weather = jrdd.getText();
						}
						//declares user input
						Day d2 = new Day(weather, temp, hum, wind, tennis);
						//prints user input
						JOptionPane.showMessageDialog(null, d2 + " has successfully added to the predictor and written to the output file.");
						//adds user input to file
						myPred.addDay(d2);
						myPred.writeFile("./project2/testFileWrite.txt");
					}
					
				}); 
				
			}
		}); 
		btnNewButton_1.setBounds(10, 370, 140, 45);
		add(btnNewButton_1);
		
	}
}


