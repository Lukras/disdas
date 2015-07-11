package de.aufgabe5.client;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.aufgabe5.persist.PersistenceManager;
import de.aufgabe5.persist.numbers.PageID;
import de.aufgabe5.persist.numbers.TransactionID;
import de.aufgabe5.persist.util.DBData;

public class Client extends Thread {

	protected List<Integer> ids;
	
	public Client(List<Integer> ids){
		this.ids = ids;
	}
	
	public void run() {

		TransactionID taid = PersistenceManager.INSTANCE.beginTransaction();
		for (int i = 0; i < ids.size(); i++) {
			try {
				PageID paid = new PageID(ids.get(i));
				PersistenceManager.INSTANCE.write(taid, paid,
						new DBData<String>("Daten zum Laden"));
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		PersistenceManager.INSTANCE.commit(taid);
	}

}
