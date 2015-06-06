package generic;

/**
 * @author Project Management Team
 *
 */
public class RoverThread extends Thread{
	
	/**
	 * @param runnable
	 * @param name
	 */
	public RoverThread(Runnable runnable, String name){
		super(runnable, name);
	}
}
