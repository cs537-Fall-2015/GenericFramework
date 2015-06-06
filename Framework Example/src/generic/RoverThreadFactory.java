package generic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;


/**
 * @author Project Management Team
 *
 */
class RoverThreadFactory implements ThreadFactory {

	private int          counter;
    private String       name;
    private List<String> stats;
    private static RoverThreadFactory roverThreadFactory;
    
    /**
     * @return roverThreadFactory
     */
    public static RoverThreadFactory getRoverThreadFactory(){
    	if(roverThreadFactory == null){
    		roverThreadFactory = new RoverThreadFactory("");
    	}
    	
    	return roverThreadFactory;
    }
    
    /**
     * @param name
     */
    private RoverThreadFactory(String name)
    {
       counter = 1;
       this.name = name;
       stats = new ArrayList<String>();
    }

	/* (non-Javadoc)
	 * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
	 */
	@Override
	public Thread newThread(Runnable runnable) {
		Thread t = new RoverThread(runnable, name + "-Thread_" + counter);
	      counter++;
	      stats.add(String.format("Created thread %d with name %s on %s \n", t.getId(), t.getName(), new Date()));
	      return t;
	}
	
	/**
	 * @return buffer.toString()
	 */
	public String getStats(){
      StringBuffer buffer = new StringBuffer();
      Iterator<String> it = stats.iterator();
      while (it.hasNext())
      {
         buffer.append(it.next());
      }
      return buffer.toString();
   }
	   
	/**
	 * @return counter
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * @param counter
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param stats
	 */
	public void setStats(List<String> stats) {
		this.stats = stats;
	}
}
