package module1;



import generic.RoverThreadHandler;

import java.io.IOException;

import module1.CHEMIN_Client;
import module1.CHEMIN_Server;

public class MasterMain {

	public static void main(String[] args) {
		
		//Each module has its own port
		int port_one = 9008;
		int port_two = 9009;
		
		try {
			
			// create a thread for module one
			CHEMIN_Server serverOne = new CHEMIN_Server(port_one);
			Thread server_1 = RoverThreadHandler.getRoverThreadHandler().getNewThread(serverOne);
			
			// create a thread for module two
						
			// each server begins listening
			server_1.start();
			
			ComputerCommand_Server serverTwo = new ComputerCommand_Server(port_two);
			Thread server_2 = RoverThreadHandler.getRoverThreadHandler().getNewThread(serverTwo);
			// The following commands are examples of sending data: 
			// from module 1 client to module 2 server
			// and from module 2 client to module 2 server
			
			server_2.start();
			
			// client one server sending messages to server two
			CHEMIN_Client clientOne = new CHEMIN_Client(port_two, null); // notice port_two
			Thread client_1 = RoverThreadHandler.getRoverThreadHandler().getNewThread(clientOne);
			
			
			// start the thread which communicates through sockets
			client_1.start();
			
			
			// client one server sending messages to server two
			//ComputerCommand_Client clientTwo = new ComputerCommand_Client(port_two, null); // notice port_two
			//Thread client_2 = RoverThreadHandler.getRoverThreadHandler().getNewThread(clientTwo);
						
					
			// start the thread which communicates through sockets
			//client_2.start();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
