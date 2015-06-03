package de.aufgabe5.persist.log;

import de.aufgabe5.persist.numbers.PageID;
import de.aufgabe5.persist.numbers.TransactionID;
import de.aufgabe5.persist.util.DBData;

public class RegularLogEntry extends LogEntry {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1986191738169351120L;

	@Override
	public String toString() {
		return "RegularLogEntry [lsn=" + lsn + ", taid=" + taid + ", paid="
				+ paid + ", data=" + data + "]";
	}

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