package de.aufgabe5.persist.log;

import de.aufgabe5.persist.numbers.PageID;
import de.aufgabe5.persist.numbers.TransactionID;
import de.aufgabe5.persist.util.DBData;

public class RegularLogEntry extends LogEntry {


	private PageID paid;
	private DBData<?> data;

	public RegularLogEntry(TransactionID taid, PageID paid, DBData<?> data) {
		super(taid);
		this.paid = paid;
		this.data = data;
	}

	@Override
	public boolean isRegular() {
		return true;
	}

	/**
	 * @return the paid
	 */
	public PageID getPaid() {
		return paid;
	}

	/**
	 * @return the data
	 */
	public DBData<?> getData() {
		return data;
	}
}