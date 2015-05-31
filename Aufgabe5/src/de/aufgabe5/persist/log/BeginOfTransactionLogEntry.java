package de.aufgabe5.persist.log;

import de.aufgabe5.persist.numbers.TransactionID;

public class BeginOfTransactionLogEntry extends LogEntry {

	public BeginOfTransactionLogEntry(TransactionID taid) {
		super(taid);
	}

	@Override
	public boolean isBOT() {
		return true;
	}

}