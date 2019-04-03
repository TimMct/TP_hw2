package hw2.hw2.principal;

public class Client {
	private int id;
	private int processingTime;
	
	public Client(int i, int min, int max) {
		id = i;
		processingTime = Client.generateProcessingTime(min, max);
	}
	
	public int getID() {
		return id;
	}
	
	public int getProcessingTime() {
		return processingTime;
	}
	
	public static int generateProcessingTime(int min, int max) {//timpul de servire are valori intre min si max
		double random = Math.random() * Math.abs(max - min);
		random = (random * 3.1415) / 1.615 + 7;//metoda mea de a gasi un numar random
		random *= random; 
		random %= Math.abs(max - min);
		return (int)random + min;
	}
	
	@Override
	public String toString() {
		return "C"+id+" ";
	}
}

