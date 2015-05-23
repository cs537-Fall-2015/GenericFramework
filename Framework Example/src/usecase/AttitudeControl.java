package usecase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttitudeControl {
	
	float centerX;
	float centerY;
	int roverLength = 10;
	int roverWidth = 9;
	public static float cogX = 0;
	public static float cogY = 0;
	public static float angle = 0;
	char direction = ' ';
	
	public AttitudeControl() 
	{	}
	
	public float[] getCenterOfGravity(float distance, float angleG)	{
		float centerOfGravity[] = new float[2];
		
		centerOfGravity[0] = (float) (cogX + distance * Math.cos(angleG));
		centerOfGravity[1] = (float) (cogY + distance * Math.cos(angleG));
		cogX = centerOfGravity[0];
		cogY = centerOfGravity[1];
		angle = angleG;
		return centerOfGravity;
	}
	/* We need to consider angle for clockwise and anti-clockwise direction */ 
	
	public float[] rotate(float x,float y,float p,float q,float angle)
	{
		float new_coordinates[] = new float[2];
		new_coordinates[0] = (float) ((x-p)*Math.cos(angle) - (y-q)*Math.sin(angle) + p);
		new_coordinates[1] = (float) ((x-p)*Math.sin(angle) - (y-q)*Math.cos(angle) + q);
		return new_coordinates; 	
	}
	
	
	/* This function will return co-ordinates of all wheels */
	@SuppressWarnings("unused")
	public Map<Integer, ArrayList<Float>> getWheelsCoordinate() {
		
		float wheelX1 = cogX + (float) roverLength/2;
		float wheelY1 = cogY + (float) roverWidth/2;
		
		float wheel1[] = rotate(wheelX1, wheelY1, cogX, cogY, angle);
		
		
		float wheelX2 = cogX + (float) roverLength/2;
		float wheelY2 = cogY - (float) roverWidth/2;
		
		float wheel2[] = rotate(wheelX2, wheelY2, cogX, cogY, angle);
		
		float wheelX3 = cogX - (float) roverLength/2;
		float wheelY3 = cogY - (float) roverWidth/2;
		
		float wheel3[] = rotate(wheelX3, wheelY3, cogX, cogY, angle);
		
		float wheelX4 = cogX - (float) roverLength/2;
		float wheelY4 = cogY + (float) roverWidth/2;
		
		float wheel4[] = rotate(wheelX4, wheelY4, cogX, cogY, angle);
		
		System.out.println("Wheel X1: "+wheelX1+" And Y1: "+wheelY1);
		System.out.println("Wheel X2: "+wheelX2+" And Y2: "+wheelY2);
		System.out.println("Wheel X3: "+wheelX3+" And Y3: "+wheelY3);
		System.out.println("Wheel X4: "+wheelX4+" And Y4: "+wheelY4);
		
		
		Map<Integer, ArrayList<Float>> edgeMap = new HashMap<Integer, ArrayList<Float>>();
		ArrayList<Float> array1 = new ArrayList<Float>();
		ArrayList<Float> array2 = new ArrayList<Float>();
		ArrayList<Float> array3 = new ArrayList<Float>();
		ArrayList<Float> array4 = new ArrayList<Float>();
		
		array1.add(wheelX1);array1.add(wheelY1);
		array2.add(wheelX2);array2.add(wheelY2);
		array3.add(wheelX3);array3.add(wheelY3);
		array4.add(wheelX4);array4.add(wheelY4);
		
		edgeMap.put(1, array1);
		edgeMap.put(2, array2);
		edgeMap.put(3, array3);
		edgeMap.put(4, array4);
		
		return edgeMap ;
	}
}
