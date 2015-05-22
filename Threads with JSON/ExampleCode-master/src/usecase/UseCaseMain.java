package usecase;

import generic.RoverThreadHandler;

import java.io.IOException;

public class UseCaseMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int port = 9897;
		
		try {
			
			CHEMIN_Server useCaseServer = new CHEMIN_Server(port);
			Thread server = RoverThreadHandler.getRoverThreadHandler().getNewThread(useCaseServer);
			
			CHEMIN_Client useCaseClient = new CHEMIN_Client(port, null);
			Thread client = RoverThreadHandler.getRoverThreadHandler().getNewThread(useCaseClient);
			
			server.start();
			
			client.start();
			
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
