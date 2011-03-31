package fowler.energybilling;

import java.util.Calendar;
import java.util.Date;

public class Site {

	protected int lastReading;
	protected Reading[] _readings = new Reading[1000];

	public Site() {
		super();
	}

	protected int dayOfYear(Date arg) {
		Calendar c = Calendar.getInstance();
		c.setTime(arg);
		return c.get(Calendar.DAY_OF_YEAR);
	}

}