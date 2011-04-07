package fowler.energybilling.sites;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fowler.energybilling.NoReadingsException;
import fowler.energybilling.Reading;

/**
 * Base class for all Site classes.
 * 
 * Managed Readings and provides helper methods to get information from them.
 * 
 * @author berdir
 */
public class Site {

	/**
	 * List of Readings for a given Site.
	 */
	protected ArrayList<Reading> _readings = new ArrayList<Reading>();

	public Site() {
		super();
	}

	/**
	 * Add a new reading.
	 *
	 * @param newReading
	 */
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

	/**
	 * @return
	 * @throws NoReadingsException
	 */
	protected int getRecentUsage() throws NoReadingsException {
		int usage = getRecentReading().amount() - getRecentReading(1).amount();
		return usage;
	}
}