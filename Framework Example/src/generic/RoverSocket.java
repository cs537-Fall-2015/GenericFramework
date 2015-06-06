package generic;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Project Management Team
 *
 */
public class RoverSocket {
	
	private int port;
	private InetAddress host;
	private Socket socket;
	
	/**
	 * @param port Application port
	 * @param host Host Address
	 * @throws UnknownHostException
	 */
	public RoverSocket(int port, InetAddress host) throws UnknownHostException{
		setPort(port);
		setHost(host);
	}
	/**
	 * @return port
	 */
	public int getPort() {
		return this.port;
	}
	/**
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * @return socket
	 * @throws IOException
	 */
	public Socket getSocket() throws IOException {
		if(socket == null)
			setSocket();
		return socket;
	}
	/**
	 * @return socket
	 * @throws IOException
	 */
	public Socket getNewSocket() throws IOException {
		setSocket();
		return socket;
	}
	/**
	 * @throws IOException
	 */
	private void setSocket() throws IOException {
		if(this.socket != null)
			socket.close();
		if(host != null)			
			this.socket = new Socket(host, port);
	}
	/**
	 * @return host
	 * @throws UnknownHostException
	 */
	public InetAddress getHost() throws UnknownHostException {
		if(this.host == null)
			setHost(null);
		return host;
	}
	/**
	 * @param host
	 * @throws UnknownHostException
	 */
	public void setHost(InetAddress host) throws UnknownHostException {
		if(host == null)
			this.host = InetAddress.getLocalHost();
		else
			this.host = host;
	}
	
	/**
	 * @throws IOException
	 */
	public void closeAll() throws IOException{
		if(this.socket !=null)
			socket.close();
	}
}
