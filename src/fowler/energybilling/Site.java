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
	
	/**
	 * Return the most recent reading.
	 *
	 * @return
	 *  The most recent Reading instance.
	 *
	 * @throws NoReadingsException 
	 */
	protected Reading getRecentReading() throws NoReadingsException {
		return getRecentReading(0);
	}
	
	/**
	 * Return the n-th recent reading.
	 *
	 * @param reverseKey Which reading should be returned, 
	 *                   0 is the most recent, 1 the second most recent and so on.  
	 * @return
	 *   The n-th most recent Reading, as requested.
	 *
	 * @throws NoReadingsException 
	 */
	protected Reading getRecentReading(int reverseKey) throws NoReadingsException {
		try {
			return _readings.get(_readings.size() - (reverseKey + 1));
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new NoReadingsException();
		}
	}
}