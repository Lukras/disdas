package de.aufgabe5.persist.numbers;
import java.io.Serializable;

public class LSN implements Serializable, Comparable<LSN> {

	static private int lastID = 0;

	private int ID;

	public LSN() {
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
		LSN other = (LSN) obj;
		if (ID != other.ID) {
			return false;
		}
		return true;
	}

	public String toString() {
		return String.valueOf(ID);
	}

	@Override
	public int compareTo(LSN o) {
		return new Integer(ID).compareTo(o.ID);
	}

	public static void setLastLSN(LSN lastLSN) {
		lastID = lastLSN.ID;
	}
}
