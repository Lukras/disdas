package de.aufgabe5.persist.log;
import java.io.Serializable;

import de.aufgabe5.persist.numbers.LSN;
import de.aufgabe5.persist.numbers.TransactionID;


public abstract class LogEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6182098237368552605L;

	@Override
	public String toString() {
		return "LogEntry [lsn=" + lsn + ", taid=" + taid + "]";
	}

	protected LSN lsn;
	protected TransactionID taid;

	public LogEntry(TransactionID taid) {
		this.lsn = new LSN();
		this.taid = taid;
	}

	/**
	 * @return the lsn
	 */
	public LSN getLSN() {
		return lsn;
	}

	/**
	 * @return the taid
	 */
	public TransactionID getTaid() {
		return taid;
	}

	public boolean isBOT() {
		return false;
	}

	public boolean isCommit() {
		return false;
	}

	public boolean isAbort() {
		return false;
	}

	public boolean isRegular() {
		return false;
	}
}
