package ee.ttu.kinect.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
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
	
	private JPanel buttonPanel;
	
	private JButton startTrackingButton;
	
	private JButton stopTrackingButton;
	
	private JButton recordButton;
	
	private JButton playButton;
	
	private JButton stopRecordButton;

	private JButton stopPlayButton;
		
	private JPanel drawPanel;
	
	private FrontDrawPanel frontDrawPanel;

	private SideDrawPanel sideDrawPanel;

	private UpDrawPanel upDrawPanel;

	private ChartPanel chartPanel;
	
	public MainView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		menuItemOpen = new JMenuItem("Open");
		menu.add(menuItemOpen);
		menuBar.add(menu);
		setJMenuBar(menuBar);
		
		fileChooser = new JFileChooser();
		
		buttonPanel = new JPanel();
		buttonPanel.setSize(1200, 100);
		startTrackingButton = new JButton("Start tracking");
		stopTrackingButton = new JButton("Stop tracking");
		recordButton = new JButton("Record");
		stopRecordButton = new JButton("Stop Record");
		playButton = new JButton("Play");
		stopPlayButton = new JButton("Stop Play");
		//buttonPanel.add(startTrackingButton);
		//buttonPanel.add(stopTrackingButton);
		buttonPanel.add(recordButton);
		buttonPanel.add(stopRecordButton);
		buttonPanel.add(playButton);
		buttonPanel.add(stopPlayButton);

		drawPanel = new JPanel();
		drawPanel.setSize(new Dimension(1200, 400));
		drawPanel.setLayout(new BoxLayout(drawPanel, BoxLayout.X_AXIS));
		frontDrawPanel = new FrontDrawPanel();
		sideDrawPanel = new SideDrawPanel();
		upDrawPanel = new UpDrawPanel();
		drawPanel.add(frontDrawPanel);
		drawPanel.add(sideDrawPanel);
		drawPanel.add(upDrawPanel);

		chartPanel = new ChartPanel();
		chartPanel.setSize(1200, 300);

		setTitle("KinectManager v0.2");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.exit(1);
		}

		// getContentPane().add(imagePanel, BorderLayout.NORTH);
		getContentPane().add(buttonPanel, BorderLayout.NORTH);
		getContentPane().add(drawPanel, BorderLayout.CENTER);
		getContentPane().add(chartPanel, BorderLayout.SOUTH);
		
		setSize(1200, 600);
		setVisible(true);
	}

	public File getSelectedFile() {
		return selectedFile;
	}
	
	public void redrawSkeleton(Body body) {
		frontDrawPanel.redrawSkeleton(body);
		sideDrawPanel.redrawSkeleton(body);
		upDrawPanel.redrawSkeleton(body);
	}

	public void addListenerForMenuOpen(final ActionListener listener) {
		menuItemOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int res = fileChooser.showDialog(null, null);
				if (res == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					listener.actionPerformed(e);
				}
			}
		});
	}
	
	public void addListenerForStartTracking(ActionListener listener) {
		startTrackingButton.addActionListener(listener);
	}

	public void addListenerForStopTracking(ActionListener listener) {
		stopTrackingButton.addActionListener(listener);
	}

	public void addListenerForStartRecord(ActionListener listener) {
		recordButton.addActionListener(listener);
	}

	public void addListenerForStopRecord(ActionListener listener) {
		stopRecordButton.addActionListener(listener);
	}

	public void addListenerForStartPlay(ActionListener listener) {
		playButton.addActionListener(listener);
	}

	public void addListenerForStopPlay(ActionListener listener) {
		stopPlayButton.addActionListener(listener);
	}

	public void showMessagePopup(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

}
