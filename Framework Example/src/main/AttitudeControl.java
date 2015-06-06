package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.json.simple.JSONObject;
import org.apache.log4j.Logger;


/**
 * @author Hani, Antony, Sachin, Vatsal
 *
 */
public class AttitudeControl {
	
	private static final Logger logger = Logger.getLogger(AttitudeControl.class);
	
	
	float centerX;
	float centerY;
	int roverLength = 10;
	int roverWidth = 9;
	/** X coordinate of center of mass of the rover  */
	public static float cogX = 0;
	/** Y coordinate of center of mass of the rover  */
	public static float cogY = 0;
	/** Angle to the XY plane */
	public static float angle = 0;
	char direction = ' ';
	
	public AttitudeControl() 
	{	}
	
	/**
	 * The method 'getCenterOfGravity' gets the distance and the angle from the input, and finds the new Center Of Gravity of the Rover after the movement.
	 * 
	 * 
	 * @param distance distance given by the Mobility team to move.
	 * @param angleG angle given by the Mobility team to rotate
	 * @return centerOfGravity
	 */
	public float[] getCenterOfGravity(float distance, float angleG)	{
		
		float centerOfGravity[] = new float[2];
		
		centerOfGravity[0] = (float) (cogX + distance * Math.cos(angleG));
		centerOfGravity[1] = (float) (cogY + distance * Math.sin(angleG));
		return centerOfGravity;
	} 
	
	public float[] rotate(float x,float y,float p,float q,float angleP)
	{
		float new_coordinates[] = new float[2];
		new_coordinates[0] = (float) ((x-p)*Math.cos(angle+angleP) - (y-q)*Math.sin(angle+angleP) + p);
		new_coordinates[1] = (float) ((x-p)*Math.sin(angle+angleP) - (y-q)*Math.cos(angle+angleP) + q);
		return new_coordinates; 	
	}
	
	
	/* This function will return co-ordinates of all wheels */
	/**
	 * The method 'getWheelsCoordinate' reads the current center of mass of the rover and gives all other coordinates of the rover
	 * 
	 * @return edgeMap
	 */
	@SuppressWarnings("unused")
	public Map<Integer, ArrayList<Float>> getWheelsCoordinate() {
		
		float wheelX1 = cogX + (float) roverLength/2;
		float wheelY1 = cogY + (float) roverWidth/2;
		float wheel1[] = rotate(wheelX1, wheelY1, cogX, cogY, angle);
		
		float wheelX2 = cogX + (float) roverLength/2;
		float wheelY2 = cogY - (float) roverWidth/2;
		float wheel2[] = rotate(wheelX2, wheelY2, cogX, cogY, angle);
		
		float wheelX6 = cogX - (float) roverLength/2;
		float wheelY6 = cogY - (float) roverWidth/2;
		float wheel6[] = rotate(wheelX6, wheelY6, cogX, cogY, angle);
		
		float wheelX5 = cogX - (float) roverLength/2;
		float wheelY5 = cogY + (float) roverWidth/2;
		float wheel5[] = rotate(wheelX5, wheelY5, cogX, cogY, angle);
		
		float wheelX3 = cogX;
		float wheelY3 = cogY + (float) roverWidth/2;
		float wheel3[] = rotate(wheelX3, wheelY3, cogX, cogY, angle);
		
		float wheelX4 = cogX;
		float wheelY4 = cogY - (float) roverWidth/2;
		float wheel4[] = rotate(wheelX4, wheelY4, cogX, cogY, angle);
		
		logger.info("Position for Center Of Gravity X : :"+cogX+" And Y:"+cogY);
		logger.info("Angle of Movement:"+angle);
		logger.info("Wheel X1: "+wheelX1+" And Y1: "+wheelY1);
		logger.info("Wheel X2: "+wheelX2+" And Y2: "+wheelY2);
		logger.info("Wheel X3: "+wheelX3+" And Y3: "+wheelY3);
		logger.info("Wheel X4: "+wheelX4+" And Y4: "+wheelY4);
		logger.info("Wheel X5: "+wheelX5+" And Y5: "+wheelY5);
		logger.info("Wheel X6: "+wheelX6+" And Y6: "+wheelY6);
		
		Map<Integer, ArrayList<Float>> edgeMap = new HashMap<Integer, ArrayList<Float>>();
		ArrayList<Float> array1 = new ArrayList<Float>();
		ArrayList<Float> array2 = new ArrayList<Float>();
		ArrayList<Float> array3 = new ArrayList<Float>();
		ArrayList<Float> array4 = new ArrayList<Float>();
		ArrayList<Float> array5 = new ArrayList<Float>();
		ArrayList<Float> array6 = new ArrayList<Float>();
		ArrayList<Float> array7 = new ArrayList<Float>();
		
		array1.add(wheelX1);array1.add(wheelY1);
		array2.add(wheelX2);array2.add(wheelY2);
		array3.add(wheelX3);array3.add(wheelY3);
		array4.add(wheelX4);array4.add(wheelY4);
		array5.add(wheelX5);array5.add(wheelY5);
		array6.add(wheelX6);array6.add(wheelY6);
		array7.add(cogX); array7.add(cogY);
		
		edgeMap.put(1, array1);
		edgeMap.put(2, array2);
		edgeMap.put(3, array3);
		edgeMap.put(4, array4);
		edgeMap.put(5, array5);
		edgeMap.put(6, array6);
		edgeMap.put(7, array7);
		
		return edgeMap ;
	}
	
	/**
	 * The method 'getTilt' is used to determine whether tilt is greater than 30 degree or not.
	 * It reads the new position and checks the Mars map for the tilt.   
	 * 
	 * @param x1 new X coordinate
	 * @param y1 new Y coordinate
	 * @return tilt
	 * @throws IOException
	 */
	@SuppressWarnings({ "resource", "unused" })
	public float getTilt(float x1, float y1) throws IOException {
		float tilt = 0;
		String line="";
		float yy = 0;
		String xx = "";
		String zz = "";
		
		File file = new File("normalMap.txt");	
		FileReader fileReader = new FileReader(file);
		BufferedReader br = new BufferedReader(fileReader);
		
		logger.info("Calculating Tilt for New Co-ordinates !!!");
		while((line = br.readLine()) != null)	{
			StringTokenizer st = new StringTokenizer(line,"]");
			while(st.hasMoreTokens()) {
				int x = Integer.parseInt(st.nextToken().substring(1));
				int y = Integer.parseInt(st.nextToken().substring(1));
				String rest = st.nextToken();
				if(x == Math.round(x1) && y == Math.round(y1)) {
					StringTokenizer st1 = new StringTokenizer(rest,",");	
					while(st1.hasMoreTokens()) {
						xx = st1.nextToken();
						yy = Float.parseFloat(st1.nextToken());
						zz = st1.nextToken();
					}
				}
			}
		}
		tilt = Math.round((Math.acos(yy)*180/Math.PI));
		return tilt;
	}
	
	/**
	 * The method 'get_ATT_CNTRL_LOCTN' creates the JSON object, which will have all coordinates of the rover.
	 * 
	 * @return json
	 */
	@SuppressWarnings("unchecked")
	public JSONObject get_ATT_CNTRL_LOCTN() {
		Map<Integer, ArrayList<Float>> map = getWheelsCoordinate();
		JSONObject json = new JSONObject();
		
		json.put("CenterOfMass", map.get(7));
		json.put("FrontWheel1",  map.get(1));
		json.put("FrontWheel2",  map.get(2));
		json.put("RearWheel1",   map.get(5));
		json.put("RearWheel2",   map.get(6));
		json.put("MiddleWheel1", map.get(3));
		json.put("MiddleWheel2", map.get(4));
		
		return json;
	}

	/**
	 * The method 'writeToJSONFile' writes the JSON object to a JSON file '4.json'
	 * 
	 * 
	 * @param json JSON object, which will have all coordinates of the rover.
	 * @throws IOException
	 */
	public void writeToJSONFile(JSONObject json) throws IOException 
	{	
		FileWriter file = new FileWriter("Group-4.json");
		logger.info("Writing data to JSON File !!!");
		file.write(json.toJSONString());
		file.flush();
		file.close();
	}
	
}