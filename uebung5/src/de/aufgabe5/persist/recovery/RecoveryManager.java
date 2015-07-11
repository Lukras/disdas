package de.aufgabe5.persist.recovery;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.aufgabe5.persist.log.LogEntry;
import de.aufgabe5.persist.log.RegularLogEntry;
import de.aufgabe5.persist.numbers.LSN;
import de.aufgabe5.persist.numbers.TransactionID;
import de.aufgabe5.persist.util.Page;
import de.aufgabe5.persist.util.Transaction;

public class RecoveryManager {

	Map<TransactionID, Transaction> winner = new HashMap<TransactionID, Transaction>();

	public void startRecovery() {
		LSN lastLSN = null;
		try {
			{
				Map<TransactionID, Transaction> foundTA = new HashMap<TransactionID, Transaction>();
				//FileInputStream inputStream = new FileInputStream("log.res");
				ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream("log.res"));
				try {
					while (true) {
						LogEntry log = (LogEntry) objectInput.readObject();
						lastLSN = log.getLSN();
						if (log.isBOT()) {
							foundTA.put(log.getTaid(), new Transaction(log.getTaid()));
						} else if (log.isCommit()) {
							foundTA.get(log.getTaid()).gotCommitted();
							winner.put(log.getTaid(), foundTA.get(log.getTaid()));
						}
						System.out.print(log.getLSN() + ": " + log.getTaid() + "\n");
					}
				} catch (EOFException e) {
					LSN.setLastLSN(lastLSN);
					objectInput.close();
				}
			}
			// Winner sind identifiziert:
			for (Transaction ta : winner.values()) {
				System.out.print("Winner: " + ta.getID() + "\n");
			}

			// --- Jetzt: Redo beginnen
			{
				FileInputStream inputStream = new FileInputStream("log.res");
				ObjectInputStream objectInput = new ObjectInputStream(inputStream);
				try {
					Collection<Transaction> win = winner.values();
					while (true) {
						LogEntry log = (LogEntry) objectInput.readObject();
						if (log.isRegular() && win.contains(new Transaction(log.getTaid()))) {
							RegularLogEntry reglog = (RegularLogEntry) log;
							boolean failOnPageRead = false;
							Page persPage = null;
							try {
								persPage = Page.read(reglog.getPaid());
							} catch (FileNotFoundException e) {
								failOnPageRead = true;
							} catch (EOFException e) {
								failOnPageRead = true;
							}
							if (failOnPageRead
									|| reglog.getLSN().compareTo(persPage.getLastLSN()) > 0) {
								System.out.print("![ReDo]!"
										+ ((!failOnPageRead) ? reglog.getLSN() + " >  "
												+ persPage.getLastLSN() : "Failure on Read!") + "("
										+ reglog.getLSN() + "): (" + reglog.getPaid() + ")"
										+ reglog.getData().getContent().toString() + "\n");
								Page.redo(reglog.getLSN(), reglog.getData(), reglog.getPaid());
							} else {
								System.out.print(" [NoDo] "
										+ ((!failOnPageRead) ? reglog.getLSN() + " <= "
												+ persPage.getLastLSN() : "Failure on Read!") + "("
										+ reglog.getLSN() + "): (" + reglog.getPaid() + ")"
										+ reglog.getData().getContent().toString() + "\n");
							}
						}
					}
				} catch (EOFException e) {
					inputStream.close();
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
