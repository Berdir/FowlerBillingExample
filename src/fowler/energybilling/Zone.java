package fowler.energybilling;

import java.util.Date;

public class Zone {
	private String 	name;
	private Date 	summerEnd;
	private Date 	summerStart;
	private double 	winterRate;
	private double 	summerRate;


	public  Zone(String name, double summerRate, double winterRate, Date summerStart, Date summerEnd) {
		this.name = name;
		this.summerRate = summerRate;
		this.winterRate = winterRate;
		this.summerStart = summerStart;
		this.summerEnd = summerEnd;
	};
	public Date getSummerEnd() {
		return summerEnd;
	}
	public Date getSummerStart() {
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
	public double getSummerFraction(Date start, Date end) {
		double summerFraction;
		// Find out how much of period is in the summer
		if (start.after(getSummerEnd()) || end.before(getSummerStart()))
			summerFraction = 0;
		else if (!start.before(getSummerStart())
				&& !start.after(getSummerEnd())
				&& !end.before(getSummerStart())
				&& !end.after(getSummerEnd()))
			summerFraction = 1;
		else { // part in summer part in winter
			double summerDays;
			if (start.before(getSummerStart())
					|| start.after(getSummerEnd())) {
				// end is in the summer
				summerDays = DateHelper.dayOfYear(end) - DateHelper.dayOfYear(getSummerStart())
						+ 1;
			} else {
				// start is in summer
				summerDays = DateHelper.dayOfYear(getSummerEnd()) - DateHelper.dayOfYear(start)
						+ 1;
			}
			;
			summerFraction = summerDays
					/ (DateHelper.dayOfYear(end) - DateHelper.dayOfYear(start) + 1);
		}
		;
		return summerFraction;
	}

}
	
	
