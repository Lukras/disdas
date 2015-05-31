package de.aufgabe5.persist.log;

import de.aufgabe5.persist.numbers.TransactionID;

public class AbortLogEntry extends LogEntry {

	public AbortLogEntry(TransactionID taid) {
		super(taid);
	}

	@Override
	public boolean isAbort() {
		return true;
	}

}