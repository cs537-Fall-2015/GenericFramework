package mobility_module;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import generic.RoverClientRunnable;


/**
 * @author Hani, Antony, Sachin, Vatsal
 *
 */
public class MobilityClient extends RoverClientRunnable{
	private static final Logger logger = Logger.getLogger(MobilityClient.class);

	JSONObject json1 = null;
	ObjectOutputStream outputToAnotherObject = null;
    ObjectInputStream inputFromAnotherObject = null;
    String str = "";
	
	/**
	 * @param port Application port
	 * @param host Host Address
	 * @throws UnknownHostException
	 */

	public MobilityClient(int port, InetAddress host)
			throws UnknownHostException {
		super(port, host);
	}

	
	/**
	 * This Method 'getJSONData' is used to read the JSON object given by the Mobility team.
	 * @param command Message given by the Mobility Team
	 * @param i is the flag to read input JSON file
	 * @return jsonObject
	 */
	public JSONObject getJSONData(String command, int i) {
		String myFilePath ="";
		logger.info("Reading JSON File for - "+command);
		if(command.equals("MBLTY_FWRD") &&  i == 0) {
			myFilePath = "18-1.json";
		}
		if(command.equals("MBLTY_TURNLEFT") && i == 0) {
			myFilePath = "18-2.json";
		}
		if(command.equals("MBLTY_TURNRIGHT") && i ==0) {
			myFilePath = "18-2.json";
		}
		if(command.equals("MBLTY_TURNLEFT") && i == 1) {
			myFilePath = "18-3.json";
		}
		
		
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			Object obj = parser.parse(new FileReader(myFilePath));
			jsonObject = (JSONObject) obj;
			
		} catch (FileNotFoundException e) {
			logger.error("No File Found !!!");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("I/O Exception Found !!!");
			e.printStackTrace();
		} catch (ParseException e) {
			logger.error("Parse exception found.");
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	
	/**
	 * @param i is the flag to track the number of client requests.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	public void currentLocationCommand(int i) throws IOException, ClassNotFoundException, InterruptedException {
		
		outputToAnotherObject = new ObjectOutputStream(getRoverSocket().getNewSocket().getOutputStream());
	    logger.info("MOBILITY client ready to send request "+i+" to ATT_CNTRL Server !!!");
	    outputToAnotherObject.writeObject("ATT_CNTRL_LOCTN");
	    Thread.sleep(1000);
	    
        inputFromAnotherObject = new ObjectInputStream(getRoverSocket().getSocket().getInputStream());
        json1 = (JSONObject) inputFromAnotherObject.readObject();
        logger.info("MOBILITY Client: Response from ATT_CNTRL Server - "+json1);
        Thread.sleep(1000);
        
        inputFromAnotherObject.close();
        outputToAnotherObject.close();
        Thread.sleep(1000);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try{
		    
		    /*************************************************/
			currentLocationCommand(1);
			Thread.sleep(25000);
			
            /*************************************************/
		    outputToAnotherObject = new ObjectOutputStream(getRoverSocket().getNewSocket().getOutputStream());
		    logger.info("MOBILITY client ready to send request 2 to ATT_CNTRL Server !!!");
            outputToAnotherObject.writeObject("MBLTY_FWRD"+","+getJSONData("MBLTY_FWRD",0).toJSONString());
            Thread.sleep(1000);

            inputFromAnotherObject = new ObjectInputStream(getRoverSocket().getSocket().getInputStream());
            str  = (String) inputFromAnotherObject.readObject();
            logger.info("MOBILITY Client: Response from ATT_CNTRL Server - "+str);
            Thread.sleep(1000);
            
            if(str.equals("ATT_CNTRL_MOVT_PERMIT"))
            	logger.info("Rover is allowed to move ahead.");
            else if(str.equals("ATT_CNTRL_MOVT_DECLINE"))
            	logger.warn("Rover is not allowed to move ahead. Take Detour !!!\n\n");
            
            inputFromAnotherObject.close();
            outputToAnotherObject.close();
            Thread.sleep(30000);
            /*************************************************/
            
            currentLocationCommand(3);
            Thread.sleep(10000);
            
            /*************************************************/
            outputToAnotherObject = new ObjectOutputStream(getRoverSocket().getNewSocket().getOutputStream());
            logger.info("MOBILITY client ready to send request 4 to ATT_CNTRL Server !!!");
            outputToAnotherObject.writeObject("MBLTY_TURNLEFT"+","+getJSONData("MBLTY_TURNLEFT",0).toJSONString());
            Thread.sleep(1000);

            inputFromAnotherObject = new ObjectInputStream(getRoverSocket().getSocket().getInputStream());
            str  = (String) inputFromAnotherObject.readObject();
            logger.info("MOBILITY Client: Response from ATT_CNTRL Server - "+str);
            Thread.sleep(1000);
            
            if(str.equals("ATT_CNTRL_MOVT_PERMIT"))
            	logger.info("Rover is allowed to move ahead.");
            else if(str.equals("ATT_CNTRL_MOVT_DECLINE"))
            	logger.warn("Rover is not allowed to move ahead. Take Detour !!!\n\n");
            Thread.sleep(30000);
            
            inputFromAnotherObject.close();
            outputToAnotherObject.close();
            Thread.sleep(1000);
            /*************************************************/
            
            currentLocationCommand(5);            
            Thread.sleep(5000);
            
            /*************************************************/
            outputToAnotherObject = new ObjectOutputStream(getRoverSocket().getNewSocket().getOutputStream());
            logger.info("MOBILITY client ready to send request 6 to ATT_CNTRL Server !!!");
            outputToAnotherObject.writeObject("MBLTY_TURNRIGHT"+","+getJSONData("MBLTY_TURNRIGHT",0).toJSONString());
            Thread.sleep(1000);

            inputFromAnotherObject = new ObjectInputStream(getRoverSocket().getSocket().getInputStream());
            str  = (String) inputFromAnotherObject.readObject();
            logger.info("MOBILITY Client: Response from ATT_CNTRL Server - "+str);
            
            if(str.equals("ATT_CNTRL_MOVT_PERMIT"))
            	logger.info("Rover is allowed to move ahead.");
            else if(str.equals("ATT_CNTRL_MOVT_DECLINE"))
            	logger.warn("Rover is not allowed to move ahead. Take Detour !!!\n\n");
            Thread.sleep(10000);
            
            inputFromAnotherObject.close();
            outputToAnotherObject.close();
            Thread.sleep(1000);
            /*************************************************/
            
            currentLocationCommand(7);
            Thread.sleep(5000);
            /*************************************************/
            outputToAnotherObject = new ObjectOutputStream(getRoverSocket().getNewSocket().getOutputStream());
            logger.info("MOBILITY client ready to send request 8 to ATT_CNTRL Server !!!");
            outputToAnotherObject.writeObject("MBLTY_TURNLEFT"+","+getJSONData("MBLTY_TURNLEFT",1).toJSONString());
            Thread.sleep(1000);

            inputFromAnotherObject = new ObjectInputStream(getRoverSocket().getSocket().getInputStream());
            str  = (String) inputFromAnotherObject.readObject();
            logger.info("MOBILITY Client: Response from ATT_CNTRL Server - "+str);
            Thread.sleep(1000);
            
            if(str.equals("ATT_CNTRL_MOVT_PERMIT"))
            	logger.info("Rover is allowed to move ahead.");
            else if(str.equals("ATT_CNTRL_MOVT_DECLINE"))
            	logger.warn("Rover is not allowed to move ahead. Take Detour !!!\n\n");
            
            inputFromAnotherObject.close();
            outputToAnotherObject.close();
            Thread.sleep(30000);
            /*************************************************/
            
            outputToAnotherObject = new ObjectOutputStream(getRoverSocket().getNewSocket().getOutputStream());
            outputToAnotherObject.writeObject("EXIT");
            
            inputFromAnotherObject = new ObjectInputStream(getRoverSocket().getSocket().getInputStream());
            str  = (String) inputFromAnotherObject.readObject();
            logger.info("MOBILITY Client: Response from ATT_CNTRL Server - "+str);
            inputFromAnotherObject.close();
            outputToAnotherObject.close();
            
            closeAll();
		}	        
        catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (Exception error) {
			System.out.println("Client: Error:" + error.getMessage());
		}	
	}
}