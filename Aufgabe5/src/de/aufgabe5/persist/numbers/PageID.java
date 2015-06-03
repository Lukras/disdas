package de.aufgabe5.persist.numbers;
import java.io.Serializable;

public class PageID implements Serializable {

//	static private int lastID = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = 408097673440193394L;
	private int ID;

	public PageID(int ID) {
		this.ID = ID;
	}

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
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
		PageID other = (PageID) obj;
		if (ID != other.ID) {
			return false;
		}
		return true;
	}

	public String toString() {
		return String.valueOf(ID);
	}
}
