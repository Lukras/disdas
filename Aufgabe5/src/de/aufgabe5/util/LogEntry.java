package de.aufgabe5.util;

public class LogEntry {
	
	private static int lsn_count = 0;
	public static int getNewLsn() {
		return lsn_count++;
	}
	
	private int lsn;
	private int taid;
	private int paid;
	private LogEntryType type;
	private String data;
	
	public LogEntry(int taid, LogEntryType type) {
		this.lsn = getNewLsn();
		this.taid = taid;
		this.type = type;
	}
	
	public LogEntry(int taid, int paid, String data, LogEntryType type) {
		this.lsn = getNewLsn();
		this.taid = taid;
		this.paid = paid;
		this.data = data;
		this.type = type;
	}
	
	public int getLsn() {
		return lsn;
	}
	
	public int getTaid() {
		return taid;
	}
	
	public int getPaid() {
		return paid;
	}
	
	public LogEntryType getType() {
		return type;
	}
	
	public String getData() {
		return data;
	}
	
	public boolean isBOT() {
		return this.type == LogEntryType.BEGIN_OF_TRANSACTION;
	}
	
	public boolean isCommit() {
		return this.type == LogEntryType.COMMIT;
	}
	
	public boolean isAbort() {
		return this.type == LogEntryType.ABORT;
	}
	
	public boolean isRegular() {
		return this.type == LogEntryType.REGULAR;
	}
}
