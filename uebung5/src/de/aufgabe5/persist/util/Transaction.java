package de.aufgabe5.persist.util;
import java.util.HashSet;
import java.util.Set;

import de.aufgabe5.persist.numbers.PageID;
import de.aufgabe5.persist.numbers.TransactionID;



public class Transaction {

	private TransactionID id;
	private Set<PageID> pages = new HashSet<PageID>();
	private boolean committed = false;

	public Transaction() {
		id = new TransactionID();
	}

	public Transaction(TransactionID id) {
		id.checkLastId();
		this.id = id;
	}

	public TransactionID getID() {
		return id;
	}

	public void addAffectedPage(PageID pid) {
		pages.add(pid);
	}

	public void gotCommitted() {
		committed = true;
	}

	public Set<PageID> getAffectedPages() {
		return new HashSet<PageID>(pages);
	}

	public boolean isCommitted() {
		return committed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Transaction other = (Transaction) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}
