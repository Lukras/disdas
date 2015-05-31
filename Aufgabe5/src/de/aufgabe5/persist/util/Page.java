package de.aufgabe5.persist.util;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import de.aufgabe5.persist.numbers.LSN;
import de.aufgabe5.persist.numbers.PageID;

public class Page {
	private PageID ID;
	private LSN lastLSN;
	private DBData<?> data;

	public Page(PageID id) {
		this.ID = id;
	}

	private Page() {
	}

	/**
	 * @return the ID
	 */
	public PageID getID() {
		return ID;
	}

	/**
	 * @return the lastLSN
	 */
	public LSN getLastLSN() {
		return lastLSN;
	}

	/**
	 * @return the data
	 */
	public DBData<?> getData() {
		return data;
	}

	/**
	 * @param lastLSN
	 *          logSN
	 * @param data
	 *          Data to be written
	 */
	public void setData(LSN lastLSN, DBData<?> data) {
		this.lastLSN = lastLSN;
		this.data = data;
	}

	/**
	 * schreiben auf die platte wenn commit erfolgt und persMgn dies verlangt
	 */
	public void flush() {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream("pages/" + ID.getID() + ".res");
			out = new ObjectOutputStream(fos);
			out.writeObject(lastLSN);
			out.writeObject(data);

			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Page read(PageID paid) throws FileNotFoundException,
			EOFException {
		Page p = null;
		FileInputStream inputStream = new FileInputStream("pages/" + paid.getID()
				+ ".res");
		try {
			ObjectInputStream objectInput = new ObjectInputStream(inputStream);
			p = new Page();
			p.lastLSN = (LSN) objectInput.readObject();
			p.data = (DBData<?>) objectInput.readObject();
			objectInput.close();
		} catch (IOException e) {
			if (e instanceof EOFException) {
				throw (EOFException) e;
			} else {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}

	public static void redo(LSN lsn, DBData<?> data, PageID paid) {
		Page p = new Page();
		p.ID = paid;
		p.lastLSN = lsn;
		p.data = data;
		p.flush();
	}
}
