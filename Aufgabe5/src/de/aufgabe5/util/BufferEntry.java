package de.aufgabe5.util;

public class BufferEntry {
	public final Transaction ta;
	public final Page p;
	
	public BufferEntry(Transaction ta, Page p) {
		this.ta = ta;
		this.p = p;
	}
}
