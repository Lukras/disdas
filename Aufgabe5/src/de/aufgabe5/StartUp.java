package de.aufgabe5;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.aufgabe5.client.Client;
import de.aufgabe5.client.CrashClient;
import de.aufgabe5.persist.PersistenceManager;
import de.aufgabe5.persist.recovery.RecoveryManager;

public class StartUp {

	private static List<Client> clients = new ArrayList<Client>();

	private static void startClient(List<Integer> l) {
		Client c = new Client(l);
		c.start();
		clients.add(c);
	}
	
	private static void startCrashClient(List<Integer> l) {
		Client c = new CrashClient(l);
		c.start();
		clients.add(c);
	}

	private static void prepareEnv() {
		File f = new File("pages/");
		if (!f.exists()) {
			f.mkdir();
		}
	}

	private static void doRecovery() {
		File f = new File("log.res");
		if (f.exists()) {
			new RecoveryManager().startRecovery();
		}
	}

	private static void startClients() {

		startClient(Arrays.asList(1, 2, 3, 4, 5, 6));
		startClient(Arrays.asList(11, 12, 13, 14));
		startClient(Arrays.asList(21, 22, 23, 24, 25, 26, 27));
		startClient(Arrays.asList(31, 32, 33));
		startCrashClient(Arrays.asList(41, 42, 43, 44, 45));
		
		System.out.print("[StartUp]Clients Startet!!!\n");
	}

	private static void joinClients() {
		for (Client c : clients) {
			try {
				c.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.print("[startUp]Clients Joined!\n");
	}

	private static void flush() {
		PersistenceManager.INSTANCE.flush();
		System.out.print("[startUp]Flushed!\n");
	}

	private static void end() {
		PersistenceManager.INSTANCE.end();
		System.out.print("[startUp]Ended!\n");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		prepareEnv(); // Ordner "pages" anlegen

		doRecovery(); // wenn "log.res" vorhanden, führe Recovery durch

		startClients(); // starte Clients mit verschiedenen Page-IDs
		joinClients(); // warte aufs Ende der Client-Threads
		flush(); // leere den Buffer
		end(); // schließe den Output-Stream
	}

}
