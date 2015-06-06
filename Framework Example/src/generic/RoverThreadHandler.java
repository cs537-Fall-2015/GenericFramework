package generic;

/**
 * @author Project Management Team
 *
 */
public class RoverThreadHandler {
	
	private static RoverThreadHandler roverThreadHandler;	
	
	private RoverThreadHandler(){
		
	}
	
	/**
	 * @return roverThreadHandler
	 */
	public static RoverThreadHandler getRoverThreadHandler(){
		
		if(roverThreadHandler == null){
			roverThreadHandler = new RoverThreadHandler();
		}
		
		return roverThreadHandler;		
	}
	
	/**
	 * @param runnable
	 * @return RoverThreadFactory.getRoverThreadFactory().newThread(runnable);
	 */
	public Thread getNewThread(Runnable runnable){		
		return RoverThreadFactory.getRoverThreadFactory().newThread(runnable);
	}
}
