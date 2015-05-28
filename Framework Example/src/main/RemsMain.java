package main;

import generic.RoverThreadHandler;

import java.io.IOException;

import REMS.REMSClient;
import REMS.REMSServer;

public class RemsMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		int port = 9001;

		try {

			REMSServer remsServer = new REMSServer(port);
			Thread server = RoverThreadHandler.getRoverThreadHandler()
					.getNewThread(remsServer);

			REMSClient remsClient = new REMSClient(port, null);
			Thread client = RoverThreadHandler.getRoverThreadHandler()
					.getNewThread(remsClient);

			server.start();

			client.start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
