package de.aufgabe5.persist.log;

import de.aufgabe5.persist.numbers.TransactionID;

public class BeginOfTransactionLogEntry extends LogEntry {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4656010143195149154L;

	@Override
	public String toString() {
		return "BeginOfTransactionLogEntry [lsn=" + lsn + ", taid=" + taid
				+ "]";
	}

	public BeginOfTransactionLogEntry(TransactionID taid) {
		super(taid);
	}

	@Override
	public boolean isBOT() {
		return true;
	}

}