package de.aufgabe5.util;

public class Page {
	private int paid;
	private int lastLsn;
	private String data;
	
	public Page(int paid) {
		this.paid = paid;
	}
	
	public int getId() {
		return paid;
	}
	
	public int getLastLsn() {
		return lastLsn;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(int lastLsn, String data) {
		this.lastLsn = lastLsn;
		this.data = data;
	}
	
	public void flush() {
	}
}
