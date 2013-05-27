package ee.ttu.kinect.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

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
import ee.ttu.kinect.view.chart.TracingChartPanel;


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
		
	private JPanel tracingPanel;
	
	private FrontTracingPanel frontTracingPanel;

	private SideTracingPanel sideTracingPanel;

	private UpTracingPanel upTracingPanel;

	private TracingChartPanel chartPanel;
	
	
	public MainView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setTitle("KinectPsyManager v0.9");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.exit(1);
		}
		
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		menuItemOpen = new JMenuItem("Open");
		menu.add(menuItemOpen);
		menuBar.add(menu);
		setJMenuBar(menuBar);
		
		fileChooser = new JFileChooser();
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
		
		buttonPanel = new ButtonPanel("Record / Play");
				
		markersPanel = new MarkersPanel();
		
		seatedModePanel = new SeatedModePanel();
		
		controlPanel.add(buttonPanel);
		controlPanel.add(markersPanel);
		controlPanel.add(seatedModePanel);

		tracingPanel = new JPanel();
		tracingPanel.setLayout(new BoxLayout(tracingPanel, BoxLayout.X_AXIS));
		frontTracingPanel = new FrontTracingPanel();
		sideTracingPanel = new SideTracingPanel();
		upTracingPanel = new UpTracingPanel();
		sideTracingPanel.addZoomPanel(new ZoomPanel(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				frontTracingPanel.zoomIn();
				sideTracingPanel.zoomIn();
				upTracingPanel.zoomIn();
			}
		}, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frontTracingPanel.zoomOut();
				sideTracingPanel.zoomOut();
				upTracingPanel.zoomOut();
			}
		}));
		tracingPanel.add(frontTracingPanel);
		tracingPanel.add(sideTracingPanel);
		tracingPanel.add(upTracingPanel);

		chartPanel = new TracingChartPanel();

		getContentPane().add(controlPanel, BorderLayout.NORTH);
		getContentPane().add(tracingPanel, BorderLayout.CENTER);
		getContentPane().add(chartPanel, BorderLayout.SOUTH);
		
		setSize(1200, 700);
		setVisible(true);
	}
	
	public void setSensorEnabled(boolean enabled) {
		buttonPanel.setSensorEnabled(enabled);
	}

	public void setRecordingEnabled(boolean enabled) {
		buttonPanel.setRecordingEnabled(enabled);
	}
	
	public void setPlayingEnabled(boolean enabled) {
		buttonPanel.setPlayingEnabled(enabled);
	}
	
	public File getSelectedFile() {
		return selectedFile;
	}
	
	public void redrawSkeleton(Body body, boolean seatedMode) {
		frontTracingPanel.redrawSkeleton(body, seatedMode);
		sideTracingPanel.redrawSkeleton(body, seatedMode);
		upTracingPanel.redrawSkeleton(body, seatedMode);
	}
	
	public void redrawChart(Body body, boolean seatedMode) {
		chartPanel.updateChart(body, seatedMode);
	}

	public void clearChart() {
		chartPanel.clearChart();
	}
	
	public void clearDraw() {
		frontTracingPanel.clear();
		sideTracingPanel.clear();
		upTracingPanel.clear();
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

	public void addListenerForPause(ActionListener listener) {
		buttonPanel.addListenerForPause(listener);
	}
	
	public void addListenerForStopPlay(ActionListener listener) {
		buttonPanel.addListenerForStopPlay(listener);
	}

	public void addListenerForDrawChart(ActionListener listener) {
		chartPanel.addListenerForDrawChart(listener);
	}
	
	public void addListenerForSegmentChart(ActionListener listener) {
		chartPanel.addListenerForSegmentChart(listener);
	}
	
	public void showMessagePopup(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public boolean[] getMarkersState() {
		return markersPanel.getMarkersState();
	}

	public void openChartSelector(List<Body> data, boolean valuesCharts) {
		chartPanel.openChartSelector(data, valuesCharts);
	}
	
}
