package generic;

import java.io.IOException;

/**
 * @author Project Management Team
 *
 */
public abstract class RoverServerRunnable implements Runnable{
	
	private RoverServerSocket roverServerSocket;
	
	
	/**
	 * @param port Application port
	 * @throws IOException
	 */
	public RoverServerRunnable(int port) throws IOException{
		setRoverServerSocket(port);
	}
	
	/**
	 * @throws IOException
	 */
	public void closeAll() throws IOException{		
		if(roverServerSocket != null)
			roverServerSocket.closeAll();
	}
	
	/**
	 * @return roverServerSocket
	 */
	public RoverServerSocket getRoverServerSocket() {
		return roverServerSocket;
	}

	/**
	 * @param port Application port
	 * @throws IOException
	 */
	private void setRoverServerSocket(int port) throws IOException {
		this.roverServerSocket = new RoverServerSocket(port);
	}
		
}
