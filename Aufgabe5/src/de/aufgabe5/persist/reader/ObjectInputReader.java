package de.aufgabe5.persist.reader;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputReader {
	public static void main(String[] args) {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream("log.res"));
			//in = new ObjectInputStream(new FileInputStream("pages/44.res"));
			
			while (true) {
				Object log = in.readObject();
				System.out.println(log.toString());
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) {
			//e.printStackTrace();
			try {
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
