package fowler.energybilling;

import java.util.Calendar;
import java.util.Date;

/**
 * Helper class for date calculations.
 *
 * @author berdir
 */
public class DateHelper {
	/**
	 * Get the day of the year of a given date.
	 *
	 * @param date The date of which the day should be returned.
	 *
	 * @return
	 *   The day of year as an integer.
	 */
	public static int dayOfYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_YEAR);
	}
}
