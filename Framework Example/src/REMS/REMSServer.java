package REMS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import json.MyWriter;
import generic.RoverServerRunnable;

public class REMSServer extends RoverServerRunnable {

	public REMSServer(int port) throws IOException {
		super(port);
	}

	REMSData objREMSdata = new REMSData();
	MyWriter objMyWriter = new MyWriter();

	@Override
	public void run() {

		try {
			while (true) {

				System.out
						.println("Module REMS Server: Waiting for client request \n");

				// creating socket and waiting for client connection
				getRoverServerSocket().openSocket();

				// read from socket to ObjectInputStream object
				ObjectInputStream inputFromAnotherObject = new ObjectInputStream(
						getRoverServerSocket().getSocket().getInputStream());

				// convert ObjectInputStream object to String
				String message = (String) inputFromAnotherObject.readObject();
				System.out
						.println("Module REMS Server: Message Received from Client - "
								+ message.toUpperCase());

				// create ObjectOutputStream object
				ObjectOutputStream outputToAnotherObject = new ObjectOutputStream(
						getRoverServerSocket().getSocket().getOutputStream());

				switch (message) {
				case "REMS_ON":
					System.out.println("Command received to start the REMS \n");
					Thread.sleep(3000);
					System.out.println("REMS is getting started....\n");
					Thread.sleep(5000);
					System.out.println("REMS is on now \n");
					break;
				case "WRITE_DATA":
					System.out
							.println("Command received to write data from client....\n");
					Thread.sleep(3000);
					System.out.println("Writing data to file....\n");
					Thread.sleep(5000);
					objMyWriter.writeJSONData();
					System.out
							.println("JSON data saved to a text file at location: \n");
					System.out.println(objREMSdata.getJSONFilePath() + "\n");
					break;
				case "READ_DATA":
					System.out
							.println("Command received to read data from client....\n");
					Thread.sleep(3000);
					System.out.println("Reading data from file....\n");
					Thread.sleep(5000);
					objREMSdata.readJSONData();
					System.out.println("JSON data read from a text file. \n");
					System.out.println(objREMSdata.readJSONData()
							.toJSONString() + "\n");
					break;
				default:
					break;
				}

				// write object to Socket
				outputToAnotherObject
						.writeObject("Module REMS Server response - "
								+ message);

				// close resources
				inputFromAnotherObject.close();
				outputToAnotherObject.close();

				// terminate the server if client sends exit request
				if (message.equalsIgnoreCase("exit"))
					break;
			}
			System.out.println("Server: Shutting down Socket server REMS!!");
			// close the ServerSocket object
			closeAll();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception error) {
			System.out.println("Server: Error:" + error.getMessage());
		}

	}

}
