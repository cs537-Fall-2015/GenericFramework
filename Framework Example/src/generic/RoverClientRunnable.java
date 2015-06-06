package generic;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Project Management Team
 *
 */
public abstract class RoverClientRunnable implements Runnable{
	
	private RoverSocket roverSocket;
	
	/**
	 * @param port Application port
	 * @param host Host Address
	 * @throws UnknownHostException
	 */
	public RoverClientRunnable(int port, InetAddress host) throws UnknownHostException{
		setRoverSocket(port, host);
	}

	/**
	 * @param port Application port
	 * @param host Host Address
	 * @throws UnknownHostException
	 */
	private void setRoverSocket(int port, InetAddress host) throws UnknownHostException{		
		this.roverSocket = new RoverSocket(port, host);		
	}
	
	/**
	 * @return roverSocket
	 */
	public RoverSocket getRoverSocket(){		
		return roverSocket;		
	}
	/**
	 * @throws IOException
	 */
	public void closeAll() throws IOException{
		if(roverSocket != null)
			roverSocket.closeAll();
	}
}
