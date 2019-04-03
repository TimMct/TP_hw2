package hw2.hw2.simulator;



import javax.swing.*;

import hw2.hw2.principal.*;

public class Simulator extends Thread{

	private Server[] servers;
	private JTextField[] fields;
	private JTextArea logger;
	private JLabel time;
	private JPanel rightDown;
	private JButton statButton;
	private int timeLimit;
	private int maxProcessingTime;
	private int minProcessingTime;
	private int maxBetweenTime;
	private int minBetweenTime;
	private int nrOfServers;
	private int nrOfClients;
	
	
	public Server getServer(int i) {
		return servers[i];
	}
	
	public Simulator(int timeL, int minPT, int maxPT, int minBT, int maxBT,int nrS, int nrC, JTextField[] fs, JTextArea log, JLabel t, JPanel rightD, JButton statB) {
		super();
		timeLimit = timeL;
		minProcessingTime = minPT;
		maxProcessingTime = maxPT;
		minBetweenTime = minBT;
		maxBetweenTime = maxBT;
		nrOfServers = nrS;
		nrOfClients = nrC;
		servers = new Server[nrOfServers];
		fields = new JTextField[nrOfServers];
		logger = log;
		time = t;
		rightDown = rightD;
		statButton = statB;
		for(int i=0; i<nrOfServers; i++) {
			servers[i] = new Server("Server #"+i+" ", logger);
			fields[i] = fs[i];
		}
	}
	
	public void run() {
		startServers();Client c;int curTime = 0, pauseTime = 0, id = 1, perfectIdx, curSize;boolean notGeneratedTime = true;
		while(curTime <= timeLimit) {
			time.setText("Current time = "+curTime);
			perfectIdx = getPerfectIndex();
			try {
				if(id <= nrOfClients) {
					c = new Client(id, minProcessingTime, maxProcessingTime);
					if(notGeneratedTime) {
						pauseTime = generateBetweenTime(minBetweenTime, maxBetweenTime);
						logger.append("waiting "+pauseTime+" sec for the next client\n\n");
						notGeneratedTime = false;
					}
					if(pauseTime == 0) {
						servers[perfectIdx].addClient(c);
						servers[perfectIdx].setNrClients(servers[perfectIdx].getNrClients() + 1);//cresc nr de clienti
						servers[perfectIdx].setAverageTime(servers[perfectIdx].getAverageTime() + curTime);//timpul de asteptare al fiecarui client 
						servers[perfectIdx].setAverageSerTime(servers[perfectIdx].getAverageSerTime() + c.getProcessingTime());//timpul de procesare pt fiecare client
						curSize = servers[perfectIdx].getSize();//dimensiunea curenta a cozii
						if(curSize > servers[perfectIdx].getMaxSize()) {
							servers[perfectIdx].setMaxSize(curSize);//apdatez dimensiunea maxima
							servers[perfectIdx].setPeakHour(curTime);//apdatez si momentul cand a fost mai "incarcata" coada
						}
						notGeneratedTime = true;
						logger.append(c+" was added to "+servers[perfectIdx].getName()+" at moment "+curTime+"\n\n");
						id++;
					}
					else pauseTime--;
				}
				putServers();sleep(1000);
			}catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
			curTime++;
		}
		for(int i=0; i<nrOfServers; i++)
			if(servers[i].getNrClients() > 0) {
				servers[i].setAverageTime(servers[i].getAverageTime() / servers[i].getNrClients());
				servers[i].setAverageSerTime(servers[i].getAverageSerTime() / servers[i].getNrClients());
			}
		stopServers();putServers();
		time.setText("End of simulation");rightDown.add(statButton);		
	}
	
	public int generateBetweenTime(int min, int max) {//timpul de pauza in care niciun client nu este introdus
		double random = Math.random() * Math.abs(max - min);
		random = (random * 3.1415) / 1.615 + 7;//metoda mea de a gasi un numar random
		random *= random; 
		random %= Math.abs(max - min);
		return (int)random + min;
	}
	
	public int getPerfectIndex() {//abordare: primul server care este mai liber
		int idx = 0;
		int size = servers[0].getSize();
		for(int i = 1; i < nrOfServers; i++) {
			if( size > servers[i].getSize()) {
				size = servers[i].getSize();
				idx = i;
			}
		}
		return idx;
	}
	
	public void startServers() {
		for(int i=0; i<nrOfServers; i++) {
			servers[i].start();
		}
	}
	
	public void showServers() {
		for(int i = 0; i < nrOfServers; i++) {
			System.out.println(servers[i].getName()+servers[i].getClients());
		}
	}
	
	public void putServers() {
		for(int i = 0; i < nrOfServers; i++) {
			fields[i].setText(servers[i].getClients());
		}
	}
	
	
	public void stopServers() {
		for(int i = 0; i < nrOfServers; i++)
			servers[i].setRun(false);
	}
}
