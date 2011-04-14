package fowler.energybilling;

import java.util.Date;

import org.joda.time.DateTime;

public class Reading {
	private DateTime _date;
	private int _amount;

	public Reading(int amount, DateTime date) {
		_amount = amount;
		_date = date;
	}

	public Reading(int amount, Date date) {
		_amount = amount;
		_date = new DateTime(date);
	}

	public DateTime date() {
		return _date;
	}

	public int amount() {
		return _amount;
	}

}
