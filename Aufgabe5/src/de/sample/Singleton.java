package de.sample;

public class Singleton {
	static final private Singleton singleton;
	static {
		try {
			singleton = new Singleton();
		} catch (Throwable e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private Singleton() {}
	
	static public Singleton getInstance() {
		return singleton;
	}
}
