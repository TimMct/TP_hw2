package hw2.hw2.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import hw2.hw2.simulator.Simulator;

public class Windows {
	
	//datele de intrare
	private int timeLimit;
	private int maxProcessingTime;
	private int minProcessingTime;
	private int maxBetweenTime;
	private int minBetweenTime;
	private int nrOfQueues;
	private int nrOfClients;
	
	//cele 3 ferestre
	private JFrame inputWindow;//aici se introduc date
	private JFrame simulationWindow;//aici se petrece simularea
	private JFrame statisticsWindow;//aici se afiseaza statisticile
	
	
	//pt prima fereastra
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JPanel p4;
	private JPanel p5;
	private JPanel p6;
	private JPanel p7;
	private JLabel welcome;
	private JLabel inputBetTime;//timpul dintre clienti
	private JLabel inputSerTime;//timpul de servire
	private JLabel inputNrQueues;//nr de servere
	private JLabel inputNrClients;//nr de servere
	private JLabel inputSimTime;//timpul simularii
	private JTextField min1;
	private JTextField max1;
	private JTextField min2;
	private JTextField max2;
	private JTextField nrQueues;
	private JTextField nrClients;
	private JTextField simTime;
	private JButton openSimulation;
	
	
	
	//pt a doua fereastra
	private JPanel left;
	private JPanel right;
	private JPanel rightUp;
	private JPanel rightDown;
	private JPanel[] panels;
	private JLabel[] labels;
	private JTextField[] fields;
	private JLabel logLabel;
	private JTextArea logger;
	private JLabel time;
	private JButton simButton;
	private JButton statButton;
	private JScrollPane scroll;
	
	
	//pt a treia fereastra
	private JPanel stat1;
	private JPanel stat2;
	private JPanel stat3;
	private JPanel stat4;
	private JLabel averageWaitTime;
	private JLabel averageServTime;
	private JLabel peakTime;
	
	
	private Simulator s;
	
	public Windows() {
		inputWindow = new JFrame("Input window");
		inputWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputWindow.setSize(500, 300);
		inputWindow.setLocationRelativeTo(null);
		inputWindow.setLayout(new GridLayout(7, 1));
		
		p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		p1.setBackground(new Color(51, 204, 255));
		welcome = new JLabel("Welcome to the server-clients generator");
		p1.add(welcome);
		inputWindow.add(p1);
		
		p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		p2.setBackground(new Color(51, 204, 255));
		inputBetTime = new JLabel("Interval between clients: ");
		min1 = new JTextField(5);
		max1 = new JTextField(5);
		p2.add(inputBetTime);
		p2.add(min1);
		p2.add(max1);
		inputWindow.add(p2);
		
		p3 = new JPanel();
		p3.setLayout(new FlowLayout());
		p3.setBackground(new Color(51, 204, 255));
		inputSerTime = new JLabel("Interval for service time: ");
		min2 = new JTextField(5);
		max2 = new JTextField(5);
		p3.add(inputSerTime);
		p3.add(min2);
		p3.add(max2);
		inputWindow.add(p3);
		
		p4 = new JPanel();
		p4.setLayout(new FlowLayout());
		p4.setBackground(new Color(51, 204, 255));
		inputNrQueues = new JLabel("Number of queues: ");
		nrQueues = new JTextField(5);
		p4.add(inputNrQueues);
		p4.add(nrQueues);
		inputWindow.add(p4);
		
		p5 = new JPanel();
		p5.setLayout(new FlowLayout());
		p5.setBackground(new Color(51, 204, 255));
		inputNrClients = new JLabel("Number of clients: ");
		nrClients = new JTextField(5);
		p5.add(inputNrClients);
		p5.add(nrClients);
		inputWindow.add(p5);
		
		
		p6 = new JPanel();
		p6.setLayout(new FlowLayout());
		p6.setBackground(new Color(51, 204, 255));
		inputSimTime = new JLabel("Simulation time: ");
		simTime = new JTextField(5);
		p6.add(inputSimTime);
		p6.add(simTime);
		inputWindow.add(p6);
		
		
		p7 = new JPanel();
		p7.setLayout(new FlowLayout());
		p7.setBackground(new Color(51, 204, 255));
		openSimulation = new JButton("Let's go!");
		openSimulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					minBetweenTime = Integer.parseInt(min1.getText());
					maxBetweenTime = Integer.parseInt(max1.getText());
					minProcessingTime = Integer.parseInt(min2.getText());
					maxProcessingTime = Integer.parseInt(max2.getText());
					nrOfClients = Integer.parseInt(nrClients.getText());
					nrOfQueues = Integer.parseInt(nrQueues.getText());
					timeLimit = Integer.parseInt(simTime.getText());
				}catch(NumberFormatException e) {
					welcome.setText("Error at input!");
					return;//trebuie sa iasa din functie
				}
				inputWindow.dispose();
				
				simulationWindow = new JFrame("Simulation window");
				simulationWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				simulationWindow.setSize(800, 500);
				simulationWindow.setLocationRelativeTo(null);
				simulationWindow.setLayout(new GridLayout(1, 2));
				
				
				left = new JPanel();
				left.setLayout(new GridLayout(nrOfQueues, 1));
				
				panels = new JPanel[nrOfQueues];
				labels = new JLabel[nrOfQueues];
				fields = new JTextField[nrOfQueues];
				
				for(int i=0; i<nrOfQueues; i++) {
					panels[i] = new JPanel();
					panels[i].setBackground(new Color(51, 204, 255));
					panels[i].setLayout(new FlowLayout());
					labels[i] = new JLabel("Server #"+i);
					fields[i] = new JTextField(30);
					fields[i].setEditable(false);
					
					panels[i].add(labels[i]);
					panels[i].add(fields[i]);
					left.add(panels[i]);
				}
				simulationWindow.add(left);
				
				
				right = new JPanel();
				right.setLayout(new BorderLayout());
				rightUp = new JPanel();
				rightUp.setLayout(new GridLayout(2, 1));
				rightUp.setBackground(new Color(51, 204, 255));
				
				rightDown = new JPanel();
				rightDown.setLayout(new GridLayout(2, 1));
				rightDown.setBackground(new Color(51, 204, 255));
				
				
				logLabel = new JLabel("Logger");
				logger = new JTextArea(300, 30);
				logger.setEditable(false);
				scroll = new JScrollPane(logger);
				time = new JLabel();
				simButton = new JButton("Start");
				simButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						logger.setText("");
						s = new Simulator(timeLimit, minProcessingTime, maxProcessingTime, minBetweenTime, maxBetweenTime, nrOfQueues, nrOfClients, fields, logger, time, rightDown, statButton);
						s.start();
					}
				});
				
				statButton = new JButton("Statistics");
				statButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						simulationWindow.dispose();
						
						statisticsWindow = new JFrame("Statistics window");
						statisticsWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						statisticsWindow.setSize(700, 300);
						statisticsWindow.setLocationRelativeTo(null);
						statisticsWindow.setLayout(new GridLayout(4, 1));
						
						stat1 = new JPanel();
						stat1.setLayout(new FlowLayout());
						stat1.setBackground(new Color(51, 204, 255));
						stat1.add(new JLabel("The results are"));
						statisticsWindow.add(stat1);
						
						
						stat2 = new JPanel();
						stat2.setLayout(new FlowLayout());
						stat2.setBackground(new Color(51, 204, 255));
						String results = new String();
						for(int i=0; i<nrOfQueues; i++) {
							results += "For server #"+i+" is "+s.getServer(i).getAverageTime()+"   ";
						}
						averageWaitTime = new JLabel("Average waiting time: "+results);
						stat2.add(averageWaitTime);
						statisticsWindow.add(stat2);
						
						
						stat3 = new JPanel();
						stat3.setLayout(new FlowLayout());
						stat3.setBackground(new Color(51, 204, 255));
						results = new String();
						for(int i=0; i<nrOfQueues; i++) {
							results += "For server #"+i+" is "+s.getServer(i).getAverageSerTime()+"   ";
						}
						averageServTime = new JLabel("Average service time: "+results);
						stat3.add(averageServTime);
						statisticsWindow.add(stat3);
						
						
						stat4 = new JPanel();
						stat4.setLayout(new FlowLayout());
						stat4.setBackground(new Color(51, 204, 255));
						results = new String();
						for(int i=0; i<nrOfQueues; i++) {
							results += "For server #"+i+" is "+s.getServer(i).getPeakHour()+"   ";
						}
						peakTime = new JLabel("Peak time: "+results);
						stat4.add(peakTime);
						statisticsWindow.add(stat4);
						
						statisticsWindow.setVisible(true);
					}
				});
				
				rightUp.add(time);
				rightUp.add(logLabel);
				rightDown.add(simButton);
				right.add(rightUp ,BorderLayout.PAGE_START);
				right.add(scroll, BorderLayout.CENTER);
				right.add(rightDown, BorderLayout.PAGE_END);
				simulationWindow.add(right);
				
				simulationWindow.setVisible(true);
			}
		});
		p7.add(openSimulation);
		inputWindow.add(p7);
		inputWindow.setVisible(true);
	}
}
