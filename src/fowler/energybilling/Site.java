package fowler.energybilling;

import java.util.Calendar;
import java.util.Date;

public class Site {

	public Site() {
		super();
	}

	protected int dayOfYear(Date arg) {
		Calendar c = Calendar.getInstance();
		c.setTime(arg);
		return c.get(Calendar.DAY_OF_YEAR);
	}

}