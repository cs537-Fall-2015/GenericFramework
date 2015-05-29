package REMS;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import generic.RoverClientRunnable;

public class REMSClient extends RoverClientRunnable {

	public REMSClient(int port, InetAddress host) throws UnknownHostException {
		super(port, host);
	}

	@Override
	public void run() {
		try {
			ObjectOutputStream outputToAnotherObject = null;
			ObjectInputStream inputFromAnotherObject = null;
			Thread.sleep(5000);

			// REMSData objREMSdata = new REMSData();

			// Send 5 messages to the Server
			for (int i = 0; i < 3; i++) {
				// write to socket using ObjectOutputStream
				outputToAnotherObject = new ObjectOutputStream(getRoverSocket()
						.getNewSocket().getOutputStream());

				System.out
						.println("=================================================");
				System.out
						.println("REMS Client: Sending request to Socket Server");
				System.out
						.println("=================================================");

				if (i == 2) {
					outputToAnotherObject.writeObject("exit");
				} else {
					// outputToAnotherObject.writeObject("Message #" + i
					// + " from module REMS.");

					switch (i) {
					case 0:
						outputToAnotherObject.writeObject("REMS_ON");
						break;
					case 1:
						outputToAnotherObject.writeObject("GET_DATA");
						break;
					default:
						break;
					}
				}

				// read the server response message
				inputFromAnotherObject = new ObjectInputStream(getRoverSocket()
						.getSocket().getInputStream());
				String message = (String) inputFromAnotherObject.readObject();
				// System.out.println("Module REMS Client: Message from Server - "
				// + message.toUpperCase());

				// close resources
				inputFromAnotherObject.close();
				outputToAnotherObject.close();
				Thread.sleep(5000);
			}
			closeAll();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception error) {
			System.out.println("Client: Error:" + error.getMessage());
		}

	}

}
