package hw2.hw2.principal;


import java.util.LinkedList;
import javax.swing.JTextArea;


public class Server extends Thread{
	private LinkedList<Client> clients;
	private boolean stillRun;
	private JTextArea logger;
	
	private int averageWaitingTime = 0;
	private int averageServTime = 0;
	private int peakHour = 0;
	private int nrOfClients = 0;
	private int maxSize = 0;
	
	public Server(String name, JTextArea log) {
		super();
		setName(name);
		logger = log;
		clients = new LinkedList<Client>();
	}
	
	
	public int getAverageTime() {
		return averageWaitingTime;
	}
	public void setAverageTime(int time) {
		averageWaitingTime = time;
	}
	
	public int getAverageSerTime() {
		return averageServTime;
	}
	public void setAverageSerTime(int time) {
		averageServTime = time;
	}
	
	
	public int getPeakHour() {
		return peakHour;
	}
	public void setPeakHour(int time) {
		peakHour = time;
	}
	
	public int getNrClients() {
		return nrOfClients;
	}
	public void setNrClients(int nr) {
		nrOfClients = nr;
	}
	
	public int getMaxSize() {
		return maxSize;
	}
	
	public void setMaxSize(int size) {
		maxSize = size;
	}
	
	
	public void setRun(boolean set) {
		stillRun = set;
	}
	
	public boolean getRun() {
		return stillRun;
	}
	
	
	public void run() {
		setRun(true);
		int clientTime;
		Client c;
		while(getRun()) {
			try{
				if(getSize() > 0) {
					clientTime = getClientTime();
					c = getFirstClient();
					logger.append(c+" needs "+clientTime+" sec\n\n");
					
					sleep(clientTime * 1000);
					removeClient();
					logger.append(c+" left "+getName()+"\n\n");
				}
			 }
			 catch( InterruptedException e ){
				 System.out.println(e.getMessage());
			 }
		}
	}
	
	public synchronized int getSize(){
		notify();
		return clients.size();
	}
	
	public synchronized void addClient(Client c){
		notify();
		clients.add(c);
	}
	
	public synchronized Client getFirstClient() {
		notify();
		return clients.peek();
	}
	
	public synchronized int getClientTime() {
		notify();
		return clients.peek().getProcessingTime();
	}
	
	public synchronized void removeClient(){
		notify();
		clients.poll();
	}
	
	public synchronized String getClients() {
		String s = new String();
		for(Client c: clients) {
			s+=c+" ";
		}
		return s;
	}
}

