package REMS;

import generic.RoverThreadHandler;
import java.io.IOException;

public class REMSMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int port = 9001;
		
		try {
			
			REMSServer remsServer = new REMSServer(port);
			Thread server = RoverThreadHandler.getRoverThreadHandler().getNewThread(remsServer);
			
			REMSClient remsClient = new REMSClient(port, null);
			Thread client = RoverThreadHandler.getRoverThreadHandler().getNewThread(remsClient);
			
			server.start();
			
			client.start();
			
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
