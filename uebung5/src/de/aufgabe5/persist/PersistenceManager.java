package de.aufgabe5.persist;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.aufgabe5.persist.log.BeginOfTransactionLogEntry;
import de.aufgabe5.persist.log.CommitLogEntry;
import de.aufgabe5.persist.log.LogEntry;
import de.aufgabe5.persist.log.RegularLogEntry;
import de.aufgabe5.persist.numbers.LSN;
import de.aufgabe5.persist.numbers.PageID;
import de.aufgabe5.persist.numbers.TransactionID;
import de.aufgabe5.persist.util.DBData;
import de.aufgabe5.persist.util.Page;
import de.aufgabe5.persist.util.Transaction;

public enum PersistenceManager {
	INSTANCE;

	private ObjectOutputStream out = null;
	private static final int BUFFERSIZE = 5;

	private static class BufferEntry {

		public final Transaction t;
		public final Page p;

		public BufferEntry(Transaction t, Page p) {
			this.t = t;
			this.p = p;
		}
	}

	private Map<PageID, BufferEntry> buffer = new Hashtable<PageID, BufferEntry>();

	private Map<TransactionID, Transaction> transactions = new Hashtable<TransactionID, Transaction>();

	public synchronized TransactionID beginTransaction() {
		Transaction t = new Transaction();
		transactions.put(t.getID(), t);
		LogEntry log = new BeginOfTransactionLogEntry(t.getID());
		writeLogEntry(log);
		return t.getID();
	}

	public synchronized void commit(TransactionID taid) {
		transactions.get(taid).gotCommitted();
		LogEntry log = new CommitLogEntry(taid);
		writeLogEntry(log);
	}

	public void write(TransactionID taid, PageID paid, DBData<?> data) {
		Page p = null;
		synchronized (this) {
			LogEntry log = new RegularLogEntry(taid, paid, data);
			LSN lsn = writeLogEntry(log);
			p = new Page(paid);
			p.setData(lsn, data);
		}
		synchronized (buffer) {
			buffer.put(paid, new BufferEntry(transactions.get(taid), p));
			if (buffer.size() > BUFFERSIZE) {
				flush();
			}
		}
	}

	private synchronized LSN writeLogEntry(LogEntry log) {
		if (out == null) {
			try {
				out = new ObjectOutputStream(new FileOutputStream("log.res"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			out.writeObject(log);
			System.out.print(log.getLSN()
					+ ":"
					+ log.getTaid()
					+ " "
					+ ((log instanceof RegularLogEntry) ? ((RegularLogEntry) log)
							.getPaid()
							+ " "
							+ ((RegularLogEntry) log).getData().getContent().toString() : log
							.getClass().getName()) + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return log.getLSN();
	}

	public void flush() {
		synchronized (buffer) {
			Set<PageID> ps = new HashSet<PageID>();
			for (Entry<PageID, BufferEntry> e : buffer.entrySet()) {
				if (e.getValue().t.isCommitted()) {
					e.getValue().p.flush();
					ps.add(e.getKey());
				}
			}
			for (PageID page : ps) {
				buffer.remove(page);
			}
		}
	}

	public void end() {
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
