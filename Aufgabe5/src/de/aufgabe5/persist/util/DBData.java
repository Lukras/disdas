package de.aufgabe5.persist.util;
import java.io.Serializable;

public class DBData<T extends Serializable> implements Serializable {

	private T content;

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public DBData(T input) {
		this.content = input;
	}
}
