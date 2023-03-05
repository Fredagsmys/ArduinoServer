package db;

public class Data {
	public double value;
	public int sensorType;
	public int ID;
	public String timeStamp;
	public int sensorID;
	
	public Data(double value, int sensorType, int ID, String timeStamp, int sensorID) {
		this.value = value;
		this.sensorType = sensorType;
		this.ID = ID;
		this.timeStamp = timeStamp;
		this.sensorID = sensorID;
		
	}

	public Data() {
	}
	
		
	
}
