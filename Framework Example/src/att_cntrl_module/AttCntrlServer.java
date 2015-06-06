package att_cntrl_module;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import main.AttitudeControl;
import generic.RoverServerRunnable;

/**
 * @author Hani, Antony, Sachin, Vatsal
 *
 */
public class AttCntrlServer extends RoverServerRunnable {

	final Logger logger = Logger.getLogger(AttCntrlServer.class);
	
	/**
	 * @param port Application port number
	 * @throws IOException
	 */
	public AttCntrlServer(int port) throws IOException {
		super(port);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		AttitudeControl att_cntrl = new AttitudeControl();
		JSONObject json = null;
		Object obj = null;
		JSONParser parser = new JSONParser();
		
		try {
			while (true) {
				logger.info("ATT_CNTRL Server: Waiting for MOBILITY request !!!");
				
				getRoverServerSocket().openSocket();
				
				ObjectInputStream inputFromAnotherObject = new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
				ObjectOutputStream outputToAnotherObject = new ObjectOutputStream(getRoverServerSocket().getSocket().getOutputStream());
				String message = (String) inputFromAnotherObject.readObject();
				logger.info("ATT_CNTRL Server: Message Received from MOBILITY !!!");
				logger.info("Message from MOBILITY Client - "+message);
				
				if(message.toUpperCase().equals("ATT_CNTRL_LOCTN"))
				{	
					logger.info("MOBILITY requesting for current location of Rover !!!");
					json = att_cntrl.get_ATT_CNTRL_LOCTN();
					att_cntrl.writeToJSONFile(json);
					outputToAnotherObject.writeObject(json);
				}
				
				if(message.startsWith("MBLTY_")) {
					logger.info("MOBILITY requesting to Move Rover");
					String msg[] = message.split(",",2);
					obj = parser.parse(msg[1]);
					json = (JSONObject) obj;
					
					float distance = Float.parseFloat(""+json.get("distance"));
					float turn_angle = Float.parseFloat(""+json.get("turn_angle"));
					
					float new_angle = 0;
					
					logger.info("MOBILITY Requesting for - "+msg[0]);
					if(message.startsWith("MBLTY_FWRD")) {
						new_angle = AttitudeControl.angle;
					}
					else if(message.startsWith("MBLTY_TURNLEFT")) {
						new_angle = AttitudeControl.angle + turn_angle;
					}
					else if(message.startsWith("MBLTY_TURNRIGHT")) {
						new_angle = AttitudeControl.angle - turn_angle;
					}
					else if(message.startsWith("MBLTY_BCK")) {
						if(AttitudeControl.angle > 180)
							new_angle = AttitudeControl.angle - 180;
						else
							new_angle = AttitudeControl.angle + 180;
					}
					
					float new_position[] = att_cntrl.getCenterOfGravity(distance, new_angle);
					float tilt = att_cntrl.getTilt(new_position[0], new_position[1]);
					
					logger.info("Tilt for Center of Gravity :"+tilt);
					
					if(tilt > 30) {
						logger.warn("Tilt is above 30 degree. Can not Move Rover !!!");
						outputToAnotherObject.writeObject("ATT_CNTRL_MOVT_DECLINE");
					}
					else {
						
						File file =new File("Rover-Trace.txt");
						String fileString ="OldX : "+AttitudeControl.cogX+", OldY : "+AttitudeControl.cogY+", Input_Angle : "+turn_angle+", Input_Distance : "+distance+", NewX : "+new_position[0]+", NewY : "+new_position[1]+", Command : "+msg[0]+"\n";                      
						if(!file.exists())
						{
			    			file.createNewFile();
			    		}
						FileWriter fileWritter = new FileWriter(file.getName(),true);
						BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		    	        bufferWritter.write(fileString);
		    	        bufferWritter.close();
						
						
						AttitudeControl.cogX = new_position[0];
						AttitudeControl.cogY = new_position[1];
						AttitudeControl.angle = new_angle;
						logger.info("Tilt is below 30 degree. Rover can moved !!!");
						outputToAnotherObject.writeObject("ATT_CNTRL_MOVT_PERMIT");
					}	
				}
				
				if (message.equalsIgnoreCase("EXIT")) {
					outputToAnotherObject.writeObject("Server: Shutting down Socket server ");
					break;
				}
				outputToAnotherObject.close();
				inputFromAnotherObject.close();
				
			}
			closeAll();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception error) {
			System.out.println("Server: Error:" + error.getMessage());
		}
	}
}