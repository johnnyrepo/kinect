package ee.ttu.kinect.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import ee.ttu.kinect.model.Body;


public class MainView extends JFrame {

	private static final long serialVersionUID = 1L;

	private File selectedFile;
	
	private JMenuBar menuBar;
	
	private JMenu menu;
	
	private JMenuItem menuItemOpen;
	
	private JFileChooser fileChooser;
	
	private ButtonPanel buttonPanel;
	
	private MarkersPanel markersPanel;
	
	private SeatedModePanel seatedModePanel;
	
	private JPanel controlPanel;
		
	private JPanel drawPanel;
	
	private FrontTracingPanel frontDrawPanel;

	private SideTracingPanel sideDrawPanel;

	private UpTracingPanel upDrawPanel;

	private TracingChartPanel chartPanel;
	
	
	public MainView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		menuItemOpen = new JMenuItem("Open");
		menu.add(menuItemOpen);
		menuBar.add(menu);
		setJMenuBar(menuBar);
		
		fileChooser = new JFileChooser();
		
		controlPanel = new JPanel();
		
		buttonPanel = new ButtonPanel("Record / Play");
				
		markersPanel = new MarkersPanel();
		
		seatedModePanel = new SeatedModePanel();
		
		controlPanel.add(buttonPanel);
		controlPanel.add(markersPanel);
		controlPanel.add(seatedModePanel);

		drawPanel = new JPanel();
		//drawPanel.setSize(new Dimension(1200, 400));
		drawPanel.setLayout(new BoxLayout(drawPanel, BoxLayout.X_AXIS));
		frontDrawPanel = new FrontTracingPanel();
		sideDrawPanel = new SideTracingPanel();
		upDrawPanel = new UpTracingPanel();
		drawPanel.add(frontDrawPanel);
		drawPanel.add(sideDrawPanel);
		drawPanel.add(upDrawPanel);

		chartPanel = new TracingChartPanel();
		//chartPanel.setSize(1200, 300);
		

		setTitle("KinectManager v0.7");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.exit(1);
		}

		getContentPane().add(controlPanel, BorderLayout.NORTH);
		getContentPane().add(drawPanel, BorderLayout.CENTER);
		getContentPane().add(chartPanel, BorderLayout.SOUTH);
		
		setSize(1200, 700);
		setVisible(true);
	}
	
	public void setSensorOn(boolean sensorOn) {
		buttonPanel.setSensorOn(sensorOn);
	}

	public File getSelectedFile() {
		return selectedFile;
	}
	
	public void redrawSkeleton(Body body, boolean seatedMode) {
		frontDrawPanel.redrawSkeleton(body, seatedMode);
		sideDrawPanel.redrawSkeleton(body, seatedMode);
		upDrawPanel.redrawSkeleton(body, seatedMode);
	}
	
	public void redrawChart(Body body, boolean seatedMode) {
		chartPanel.updateChart(body, seatedMode);
	}

	public void clearChart() {
		chartPanel.clearChart();
	}
	
	public void addListenerForMenuOpen(final ActionListener listener) {
		menuItemOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int res = fileChooser.showDialog(null, null);
				if (res == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					buttonPanel.updateSelectedFileLabel(selectedFile.getName());
					listener.actionPerformed(e);
				}
			}
		});
	}
	
	public void addListenerForSeatedModeCheckbox(ActionListener listener) {
		seatedModePanel.addLsitenerForCheckbox(listener);
	}
	
	public void addListenerForSensorOn(ActionListener listener) {
		buttonPanel.addListenerForSensorOn(listener);
	}

	public void addListenerForStartRecord(ActionListener listener) {
		buttonPanel.addListenerForStartRecord(listener);
	}

	public void addListenerForStopRecord(ActionListener listener) {
		buttonPanel.addListenerForStopRecord(listener);
	}

	public void addListenerForStartPlay(ActionListener listener) {
		buttonPanel.addListenerForStartPlay(listener);
	}

	public void addListenerForStopPlay(ActionListener listener) {
		buttonPanel.addListenerForStopPlay(listener);
	}

	public void showMessagePopup(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public boolean[] getMarkersState() {
		return markersPanel.getMarkersState();
	}
	
}
