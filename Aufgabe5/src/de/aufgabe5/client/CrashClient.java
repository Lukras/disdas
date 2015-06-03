package de.aufgabe5.client;
import java.util.List;

public class CrashClient extends Client {

	public CrashClient(List<Integer> ids){
		super(ids);
	}
	
	public void run() {

		super.run();
		
		System.exit(1);
	}

}
