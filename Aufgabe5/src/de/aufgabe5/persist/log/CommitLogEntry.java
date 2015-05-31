package de.aufgabe5.persist.log;

import de.aufgabe5.persist.numbers.TransactionID;

public class CommitLogEntry extends LogEntry {

	public CommitLogEntry(TransactionID taid) {
		super(taid);
	}

	@Override
	public boolean isCommit() {
		return true;
	}

}