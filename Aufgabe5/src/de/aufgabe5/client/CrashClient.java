package de.aufgabe5.client;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.aufgabe5.persist.PersistenceManager;
import de.aufgabe5.persist.numbers.PageID;
import de.aufgabe5.persist.numbers.TransactionID;
import de.aufgabe5.persist.util.DBData;
import de.aufgabe5.persist.util.Transaction;

public class CrashClient extends Client {

	public CrashClient(List<Integer> ids){
		super(ids);
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
		System.exit(1);
	}

}
