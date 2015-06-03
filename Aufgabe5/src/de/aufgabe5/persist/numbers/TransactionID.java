package de.aufgabe5.persist.numbers;
import java.io.Serializable;

public class TransactionID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9062184777832784612L;

	static private int lastID = 0;

	private int ID;

	public TransactionID() {
		ID = ++lastID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		return result;
	}

	/* (non-Javadoc)
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
		TransactionID other = (TransactionID) obj;
		if (ID != other.ID) {
			return false;
		}
		return true;
	}

	public String toString() {
		return String.valueOf(ID);
	}

	public void checkLastId() {
		if (ID > lastID) {
			lastID = ID + 1;
		}
	}
}
