package module1;


public class Requirement {
	private int myInteger;
	private int power;
	private int time;

	
	Requirement(int someParameter, int power , int time){
		this.setMyInteger(someParameter);
		this.setPower(power);
		this.setTime(time);
	}
	
	

	public Requirement(int ten) {
		this.setMyInteger(ten);
	}



	public int getMyInteger() {
		return myInteger;
	}

	public void setMyInteger(int myInteger) {
		this.myInteger = myInteger;
	}

	
	public int getPower() {
		return power;
	}



	public void setPower(int power) {
		this.power = power;
	}



	public int getTime() {
		return time;
	}



	public void setTime(int time) {
		this.time = time;
	}



	public void printObject() {
		System.out.println("===========================================");
		System.out.println("myInteger = " + this.myInteger);
		System.out.println("myString = " + this.power);
		System.out.println("myString = " + this.time);

		System.out.println("===========================================");
	}


}
