package generic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Project Management Team
 *
 */
public class RoverServerSocket {
	private ServerSocket serverSocket;
	private Socket socket;
	
	private int port;

	/**
	 * @param port Application port
	 * @throws IOException
	 */
	public RoverServerSocket(int port) throws IOException{
		setPort(port);
		serverSocket = getServerSocket();
	}
	/**
	 * @throws IOException
	 */
	public void closeAll() throws IOException{
		if (serverSocket != null)
			serverSocket.close();
		if(socket != null)
			socket.close();
	}
	
	/**
	 * @throws IOException
	 */
	public void closeSocket() throws IOException{
		if(socket != null)
			socket.close();
	}
	
	/**
	 * @return serverSocket
	 * @throws IOException
	 */
	public ServerSocket getServerSocket() throws IOException {
		if(serverSocket == null)
			serverSocket = new ServerSocket(getPort());
		return serverSocket;
	}
	/**
	 * @throws IOException
	 */
	public void openSocket() throws IOException{
		setSocket(serverSocket.accept());
	}
	/**
	 * @return port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port Application port
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * @return socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * @param socket Application socket
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
