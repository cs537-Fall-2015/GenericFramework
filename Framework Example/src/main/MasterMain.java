package main;

import generic.RoverThreadHandler;

import java.io.IOException;

import org.apache.log4j.Logger;

import att_cntrl_module.AttCntrlServer;
import mobility_module.MobilityClient;


/**
 * @author Hani, Antony, Sachin, Vatsal
 *
 */

public class MasterMain {
	
	private static final Logger logger = Logger.getLogger(MasterMain.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("*************************************************************************************************");
		logger.info("Starting ATTITUDE_CONTROL Application");
		int port_one = 9002;
		try {
			AttCntrlServer serverOne = new AttCntrlServer(port_one);
			Thread server_1 = RoverThreadHandler.getRoverThreadHandler().getNewThread(serverOne);
			logger.info("ATTITUDE_CONTROL Server Started !!!");
			server_1.start();
		
			MobilityClient clientTwo = new MobilityClient(port_one, null);
			Thread client_2 = RoverThreadHandler.getRoverThreadHandler().getNewThread(clientTwo);
			logger.info("MOBILITY Server Started !!!");
			client_2.start();
		} 
		catch (IOException e) {
			logger.error("Error In Starting Server !!!");
			e.printStackTrace();
		}
	}
}