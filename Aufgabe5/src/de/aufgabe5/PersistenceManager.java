package de.aufgabe5;

public class PersistenceManager {
	static final private PersistenceManager persistenceManager;
	static {
		try {
			persistenceManager = new PersistenceManager();
		} catch (Throwable e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private PersistenceManager() {}
	
	static public PersistenceManager getInstance() {
		return persistenceManager;
	}
}
