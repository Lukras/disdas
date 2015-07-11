package de.aufgabe5.persist.log;

import de.aufgabe5.persist.numbers.TransactionID;

public class CommitLogEntry extends LogEntry {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2546941032898989530L;

	@Override
	public String toString() {
		return "CommitLogEntry [lsn=" + lsn + ", taid=" + taid + "]";
	}

	public CommitLogEntry(TransactionID taid) {
		super(taid);
	}

	@Override
	public boolean isCommit() {
		return true;
	}

}