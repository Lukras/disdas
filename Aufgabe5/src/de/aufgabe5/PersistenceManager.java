package de.aufgabe5;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.aufgabe5.util.BufferEntry;
import de.aufgabe5.util.LogEntry;
import de.aufgabe5.util.LogEntryType;
import de.aufgabe5.util.Page;
import de.aufgabe5.util.Transaction;

public class PersistenceManager {
	
	private static final int BUFFERSIZE = 5;
	
	static final private PersistenceManager persistenceManager;
	static {
		try {
			persistenceManager = new PersistenceManager();
		} catch (Throwable e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	static public PersistenceManager getInstance() {
		return persistenceManager;
	}
	
	private ObjectOutputStream out;
	private Map<Integer, BufferEntry> buffer;
	private Map<Integer, Transaction> transactions;
	
	private PersistenceManager() {
		out = null;
		transactions = new Hashtable<Integer, Transaction>();
		buffer = new Hashtable<Integer, BufferEntry>();
	}
	
	public synchronized int beginTransaction() {
		Transaction ta = new Transaction();
		transactions.put(ta.getId(), ta);
		LogEntry log = new LogEntry(ta.getId(), LogEntryType.BEGIN_OF_TRANSACTION);
		writeLogEntry(log);
		return ta.getId();
	}
	
	public synchronized void commit(int taid) {
		transactions.get(taid).gotCommitted();
		LogEntry log = new LogEntry(taid, LogEntryType.COMMIT);
		writeLogEntry(log);
	}
	
	public void write(int taid, int paid, String data) {
		Page p = null;
		synchronized(this) {
			LogEntry log = new LogEntry(taid, paid, data, LogEntryType.REGULAR);
			int lsn = writeLogEntry(log);
			p = new Page(paid);
			p.setData(lsn, data);
		}
		synchronized(buffer) {
			buffer.put(paid, new BufferEntry(transactions.get(taid),p));
			if (buffer.size() > BUFFERSIZE) {
				flush();
			}
		}
	}
	
	private synchronized int writeLogEntry(LogEntry log) {
		if (out == null) {
			try {
				out = new ObjectOutputStream(new FileOutputStream("log.res"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			out.writeObject(log);
			System.out.print(log.getLsn()
					+ ":"
					+ log.getTaid()
					+ " "
					+ (log.isRegular() ? log.getPaid()
							+ " "
							+ log.getData() : log.getType().name()) + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return log.getLsn();
	}
	
	public void flush() {
		synchronized(buffer) {
			Set<Integer> paids = new HashSet<Integer>();
			for (Entry<Integer,BufferEntry> e : buffer.entrySet()) {
				if (e.getValue().ta.isCommitted()) {
					e.getValue().p.flush();
					paids.add(e.getKey());
				}
			}
			for (int paid : paids) {
				buffer.remove(paid);
			}
		}
	}
	
	public void end() {
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
