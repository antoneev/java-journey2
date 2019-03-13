package project2;

/*
 * Dr. Doderer's solution for Project #1
 * CSCI 3381
 * Spring 2019
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.TreeSet;

public class Predictor {
	/*
	 * This class manages two collections of Day objects
	 * allows for adding new days
	 * allows for predicting unknown days.
	 */
	private TreeSet<Day> myData; // data organized by Day's compareTo method  incorportate yes, no and maybe for data
	private ArrayList<Day> orginalData; // original data with no changes to class value. set by file and adding days
	private String fileName;
	
	public Predictor() {
		myData = new TreeSet<Day>();
		orginalData = new ArrayList<Day>();
		fileName = null;
	}
	public Predictor(String fn) {
		this(); // call the default constructor to instantiate the TreeSet
		fileName = fn;
		readFile();
	}
	public String predict (Day d) {
		Day closestFloor = myData.floor(d);
		Day closestCeiling = myData.ceiling(d);
		if (closestFloor == null && closestCeiling == null)
			return "yes";
		if (closestFloor != null && closestFloor.equals(d)) {  // found an exact match
			if (closestFloor.getPlayTennis().equals("no"))
				return "no";
			else
				return "yes"; //manages both yes and maybe
		}
		if (closestCeiling != null && closestCeiling.equals(d)) {  // found an exact match
			if (closestCeiling.getPlayTennis().equals("no"))
				return "no";
			else
				return "yes"; //manages both yes and maybe
		}		
		if (closestFloor != null && closestCeiling == null) {
			if( closestFloor.getPlayTennis().equals("no"))
				return "no";
			else 
				return "yes"; // closestFloor could have been maybe
		}
		if (closestFloor == null && closestCeiling != null) {
			if( closestCeiling.getPlayTennis().equals("no"))
				return "no";
			else 
				return "yes"; // closestCeiling could have been maybe			
		}	
		
		// if I get here  both are not null.
		// if both are no, predict no. 
		if (closestFloor.getPlayTennis().equals("no") && closestCeiling.getPlayTennis().equals("no"))
			return "no";
		return "yes";
	}
	public void addDay (Day d) {
		orginalData.add(new Day(d.getOutlook(),d.getTemp(),d.getHumidity(),d.getWindy(),d.getPlayTennis())); // maintain unmodified data for training error
		// when adding a day, if a similar day exists by the compareTo definition modify the existing
		// day as follows
		// if they agree, keep the same
		// if existing is yes and new is no make maybe
		// if existing is maybe and new is no make no
		// if existing is maybe and new is yes make yes
		// if existing is no and new is yes make maybe
		
		Day closestDay = myData.floor(d);
		if (closestDay == null || !closestDay.equals(d)) {
			myData.add(d);	
			return;
		}
		if (closestDay.getPlayTennis().equals(d.getPlayTennis()))
			return; // they agree
		if (closestDay.getPlayTennis().equals("yes") && d.getPlayTennis().equals("no")) {
			closestDay.setPlayTennis("maybe");
		}
		else if (closestDay.getPlayTennis().equals("maybe") && d.getPlayTennis().equals("no"))
			closestDay.setPlayTennis("no");
		else if (closestDay.getPlayTennis().equals("maybe") && d.getPlayTennis().equals("yes"))
			closestDay.setPlayTennis("yes");
		else if (closestDay.getPlayTennis().equals("no") && d.getPlayTennis().equals("yes")) {
			closestDay.setPlayTennis("maybe");
		}
	}
	public double trainingError () {
		// test the predictive ability on all of the data
		int count = 0;
		for (Day d:orginalData) {
			String thisPrediction = predict (d);
			if (thisPrediction.equals(d.getPlayTennis()))
				count++;
		}	
		return (double)count/orginalData.size();
	}
	public String toString() {
		// returns a string representation of this Predictor
		String toReturn = "";
		for (Day d:myData) {
			toReturn += d.toString()+"\n";
		}
		return toReturn+"training error: "+trainingError();
	}
	private void readFile () {
		// private method that reads the file and stores into TreeSet
		BufferedReader lineReader = null;
		try {
			//BufferedReader lineReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName)));
			//FileReader fr = new FileReader(fileName);
			lineReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), Charset.forName("UTF-8")));  
			//lineReader = new BufferedReader(fr);
			String line = null;
			while ((line = lineReader.readLine())!=null) {
				String[] tokens = line.split(",");
				addDay (new Day(tokens[0],Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),Boolean.parseBoolean(tokens[3].toLowerCase()),tokens[4]));
//				orginalData.add(new Day(tokens[0],Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),Boolean.parseBoolean(tokens[3].toLowerCase()),tokens[4]));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("there was a problem with the file.  either no such file or format error");
		} finally {
			if (lineReader != null)
				try {
					lineReader.close();
				} catch (IOException e) {
					System.err.println("could not close BufferedReader");
				    System.out.println("TEST");
				}
		}
	} // end of readFile method
	
	public void writeFile () {
		// overloaded method: this calls doWrite with file used to read data
		// use this for saving data between runs
		doWrite(fileName);
	} // end of writeFile method

	public void writeFile(String altFileName) {
		// overloaded method: this calls doWrite with different file name 
		// use this for testing write
		doWrite(altFileName);		
	}// end of writeFile method
	
	private void doWrite(String fn) {
		// this method writes all of the data in the persons array to a file
		try
		{

			FileWriter fw = new FileWriter(fn);
			BufferedWriter myOutfile = new BufferedWriter(fw);			
			
			for (Day d:orginalData) {
				myOutfile.write(d.getOutlook()+","+d.getTemp()+","+d.getHumidity()+","+d.getWindy()+","+d.getPlayTennis()+"\n");
			}
			myOutfile.flush();
			myOutfile.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Didn't save to " + fn);
		}
	}	
	
}
