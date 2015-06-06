package att_cntrl_module;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import generic.RoverClientRunnable;

/**
 * @author Hani, Antony, Sachin, Vatsal
 *
 */
public class AttCntrlClient extends RoverClientRunnable{

	/**
	 * @param port Application port
	 * @param host Host Address
	 * @throws UnknownHostException
	 */
	public AttCntrlClient(int port, InetAddress host)
			throws UnknownHostException {
		super(port, host);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try{
			ObjectOutputStream outputToAnotherObject = null;
		    ObjectInputStream inputFromAnotherObject = null;
		    Thread.sleep(5000);
		    
		    //Send 5 messages to the Server
	        for(int i = 0; i < 5; i++){
	            //write to socket using ObjectOutputStream
	            outputToAnotherObject = new ObjectOutputStream(getRoverSocket().getNewSocket().getOutputStream());
	            
	            System.out.println("=================================================");
	            System.out.println("Module 1 Client: Sending request to Socket Server");
	            System.out.println("=================================================");
	            
	            if(i == 4){
	            	outputToAnotherObject.writeObject("exit");
	            }
	            else {
	            	outputToAnotherObject.writeObject("Message #" + i + " from module 1.");
	            }
	            
	            //read the server response message
	            inputFromAnotherObject = new ObjectInputStream(getRoverSocket().getSocket().getInputStream());
	            String message = (String) inputFromAnotherObject.readObject();
	            System.out.println("Module 1 Client: Message from Server - " + message.toUpperCase());
	            
	            //close resources
	            inputFromAnotherObject.close();
	            outputToAnotherObject.close();
	            Thread.sleep(5000);
	        }
	        closeAll();
		}	        
        catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception error) {
			System.out.println("Client: Error:" + error.getMessage());
		}
		
	}
}