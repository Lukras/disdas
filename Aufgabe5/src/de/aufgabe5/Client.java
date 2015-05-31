package de.aufgabe5;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Client extends Thread {
	
	protected List<Integer> ids;
	
	public Client(List<Integer> ids) {
		this.ids = ids;
	}
	
	public void run() {
		int taid = PersistenceManager.getInstance().beginTransaction();
		for (int i = 0; i < ids.size(); i++) {
			try {
				int paid = ids.get(i);
				PersistenceManager.getInstance().write(taid, paid,
						"Daten zum Laden");
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		PersistenceManager.getInstance().commit(taid);
	}
}
