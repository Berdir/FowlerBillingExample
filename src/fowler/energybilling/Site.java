package fowler.energybilling;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Site {

	protected ArrayList<Reading> _readings = new ArrayList<Reading>();

	public Site() {
		super();
	}

	protected int dayOfYear(Date arg) {
		Calendar c = Calendar.getInstance();
		c.setTime(arg);
		return c.get(Calendar.DAY_OF_YEAR);
	}

	public void addReading(Reading newReading) {
		_readings.add(newReading);
	}

}