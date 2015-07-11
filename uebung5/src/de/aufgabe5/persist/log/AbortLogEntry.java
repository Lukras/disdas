package de.aufgabe5.persist.log;

import de.aufgabe5.persist.numbers.TransactionID;

public class AbortLogEntry extends LogEntry {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2742518323888453599L;

	@Override
	public String toString() {
		return "AbortLogEntry [lsn=" + lsn + ", taid=" + taid + "]";
	}

	public AbortLogEntry(TransactionID taid) {
		super(taid);
	}

	@Override
	public boolean isAbort() {
		return true;
	}

}