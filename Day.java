package project2;

/*
 * Dr. Doderer's solution for Project #1
 * CSCI 3381
 * Spring 2019
 */
public class Day implements Comparable<Day> {
	/*
	 * this class represents a single Day.  I also compare days
	 * ordered by quality of tennis days (my definition of quality).  
	 * the smallest day very much
	 * does not want to play tennis.  the largest day very
	 * much wants to play tennis
	 */
	private String outlook;
	private boolean windy;
	private int temp;
	private int humidity;
	private String playTennis; // "yes,no,maybe"
	
	public Day() {
		outlook = "none";
		windy = false;
		temp = -1;
		humidity = -1;
		playTennis = "yes";
	}
	public Day(String o, int t, int h, boolean w) {
		outlook = o;
		windy = w;
		temp = t;
		humidity = h;
		playTennis = "unknown";
	}
	public Day(String o, int t, int h, boolean w, String c) {
		outlook = o;
		windy = w;
		temp = t;
		humidity = h;
		playTennis = c;
	}
	public String toString () {
		return outlook+" "+ windy+" "+temp+" "+humidity+" "+playTennis;
	}
	public boolean equals (Day rhs) {
		if (this.compareTo(rhs)==0)
			return true;
		return false;
	}
	@Override
	public int compareTo(Day rhs) {
		// first test rainy versus not rainy
		// and test sunny versus not sunny as 
		if (outlook.equals("rainy") && !rhs.getOutlook().equals("rainy")) 
			return -1;
		if (outlook.equals("sunny") && !rhs.getOutlook().equals("sunny")) 
			return 1;
		// use temp as the next key attribute.  use 80 degrees as a cut off
		if (temp<80 && rhs.getTemp()>=80)
			return 1;
		if (temp>=80 && rhs.getTemp()<80)
			return -1;		
		// use humidity as the next key attribute.  use 85% as a cut off
		if (humidity<85 && rhs.getHumidity()>=85)
			return 1;
		if (humidity>=85 && rhs.getHumidity()<85)
			return -1;			
		// I see that windy doesn't seem to be a good predictor, so
		// I am going to ignore it.
		
		// If there was no difference between these Days according to 
		// the previous criteria, consider them the same
		return 0;
	}
	public String getOutlook() {
		return outlook;
	}
	public void setOutlook(String outlook) {
		this.outlook = outlook;
	}
	public boolean isWindy() {
		return windy;
	}
	public void setWindy(boolean windy) {
		this.windy = windy;
	}
	public boolean getWindy() {
		return windy;
	}
	public int getTemp() {
		return temp;
	}
	public void setTemp(int temp) {
		this.temp = temp;
	}
	public int getHumidity() {
		return humidity;
	}
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	public String getPlayTennis() {
		return playTennis;
	}
	public void setPlayTennis(String pt) {
		playTennis = pt;
	}
}

