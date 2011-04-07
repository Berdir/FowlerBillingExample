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

}
	
	
