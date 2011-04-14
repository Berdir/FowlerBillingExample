package fowler.energybilling;

import org.joda.time.DateTime;

public class Zone {
	private String 	name;
	private DateTime 	summerEnd;
	private DateTime 	summerStart;
	private double 	winterRate;
	private double 	summerRate;


	public  Zone(String name, double summerRate, double winterRate, DateTime summerStart, DateTime summerEnd) {
		this.name = name;
		this.summerRate = summerRate;
		this.winterRate = winterRate;
		this.summerStart = summerStart;
		this.summerEnd = summerEnd;
	};
	public DateTime getSummerEnd() {
		return summerEnd;
	}
	public DateTime getSummerStart() {
		return summerStart;
	}
	public double getWinterRate() {
		return winterRate;
	}
	public double getSummerRate() {
		return summerRate;
	}
	
	public String getName(){
		return this.name;
		
	}
	/**
	 * @param start
	 * @param end
	 * @return
	 */
	public double getSummerFraction(DateTime start, DateTime end) {
		double summerFraction;
		// Find out how much of period is in the summer
		if (start.isAfter(getSummerEnd()) || end.isBefore(getSummerStart()))
			summerFraction = 0;
		else if (!start.isBefore(getSummerStart())
				&& !start.isAfter(getSummerEnd())
				&& !end.isBefore(getSummerStart())
				&& !end.isAfter(getSummerEnd()))
			summerFraction = 1;
		else { // part in summer part in winter
			double summerDays;
			if (start.isBefore(getSummerStart())
					|| start.isAfter(getSummerEnd())) {
				// end is in the summer
				summerDays = end.getDayOfYear() - getSummerStart().getDayOfYear() + 1;
			} else {
				// start is in summer
				summerDays = getSummerEnd().getDayOfYear() - start.getDayOfYear() + 1;
			}
			summerFraction = summerDays / end.getDayOfYear() - start.getDayOfYear() + 1;
		}
		return summerFraction;
	}

}
	
	
