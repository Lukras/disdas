package de.aufgabe5.util;

import java.util.HashSet;
import java.util.Set;

public class Transaction {
	
	private static int taid_count = 0;
	public static int getNewTaid() {
		return taid_count++;
	}
	
	private int id;
	private Set<Integer> pages;
	private boolean committed;
	
	public Transaction() {
		this.id = getNewTaid();
		this.pages = new HashSet<Integer>();
		this.committed = false;
	}
	
	public int getId() {
		return id;
	}
	
	public void addAffectedPage(int paid) {
		pages.add(paid);
	}
	
	public void gotCommitted() {
		committed = true;
	}
	
	public Set<Integer> getAffectedPages() {
		return new HashSet<Integer>(pages);
	}
	
	public boolean isCommitted() {
		return committed;
	}
}
