package ee.ttu.kinect.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map;

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
import ee.ttu.kinect.model.CoordinateCorrection;
import ee.ttu.kinect.model.JointType;
import ee.ttu.kinect.view.chart.MotionDetectionAnalyzer;
import ee.ttu.kinect.view.chart.TracingChartPanel;

public class MainView extends JFrame {

	private static final long serialVersionUID = 1L;

	private File fileToPlay;
	
	private File[] filesForAnalysis;

	private final MotionDetectionAnalyzer motionDetectionChartOpener;
	
	private final JMenuBar menuBar;

	private final JMenu menu;

	private final JMenuItem menuItemOpen;

	private final JMenuItem menuItemPolynomialAnalyzer;

	private final JFileChooser fileChooser;

	private final ButtonPanel buttonPanel;
	
	private final MotionDetectionPanel motionDetectionPanel;

	private final MarkersPanel markersPanel;

	private final SeatedModePanel seatedModePanel;

	private final JPanel controlPanel;

	private final JPanel tracingPanel;

	private final FrontTracingPanel frontTracingPanel;

	private final SideTracingPanel sideTracingPanel;

	private final UpTracingPanel upTracingPanel;

	private final TracingChartPanel chartPanel;

	public MainView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setTitle("KinectPsyManager v1.0");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.exit(1);
		}

		motionDetectionChartOpener = new MotionDetectionAnalyzer();
		
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		menuItemOpen = new JMenuItem("Open");
		menuItemPolynomialAnalyzer = new JMenuItem("Polynomial experiment analyzer");
		menu.add(menuItemOpen);
		menu.add(menuItemPolynomialAnalyzer);
		menuBar.add(menu);
		setJMenuBar(menuBar);

		fileChooser = new JFileChooser();

		controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel,
				BoxLayout.X_AXIS));

		buttonPanel = new ButtonPanel();

		motionDetectionPanel = new MotionDetectionPanel();
		
		markersPanel = new MarkersPanel();

		seatedModePanel = new SeatedModePanel();

		controlPanel.add(buttonPanel);
		controlPanel.add(motionDetectionPanel);
		controlPanel.add(markersPanel);
		controlPanel.add(seatedModePanel);

		tracingPanel = new JPanel();
		tracingPanel.setLayout(new BoxLayout(tracingPanel,
				BoxLayout.X_AXIS));
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

		this.setSize(1200, 700);
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

	public void setMotionDetectionEnabled(boolean enabled) {
		motionDetectionPanel.setMotionDetectionEnabled(enabled);
	}
	
	public File getFileToPlay() {
		return fileToPlay;
	}
	
	public File[] getFilesForAnalysis() {
		return filesForAnalysis;
	}

	public void redrawSkeleton(Body body, boolean seatedMode,
			CoordinateCorrection correction) {
		// perform coordinate correction, if needed
		performCorrection(body, correction);

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

	public void clearTracing() {
		frontTracingPanel.clear();
		sideTracingPanel.clear();
		upTracingPanel.clear();
	}

	public void addListenerForMenuOpen(final ActionListener listener) {
		menuItemOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File[] files = retrieveSelectedFiles(false);
				if (files != null) {
					fileToPlay = files[0];
					buttonPanel.updateSelectedFileLabel(fileToPlay.getName());
					listener.actionPerformed(e);
				}
			}
		});
	}

	public void addListenerForMenuPolynomialAnalyzer(final ActionListener listener) {
		menuItemPolynomialAnalyzer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filesForAnalysis = retrieveSelectedFiles(true);
				listener.actionPerformed(e);
			}
		});
	}
	
	private File[] retrieveSelectedFiles(boolean multiSelection) {
		fileChooser.setMultiSelectionEnabled(multiSelection);
		fileChooser.setCurrentDirectory(new File(System
				.getProperty("user.dir")));
		int res = fileChooser.showDialog(null, null);
		if (res == JFileChooser.APPROVE_OPTION) {
			if (multiSelection) {
				return fileChooser.getSelectedFiles();
			} else {
				return new File[] {fileChooser.getSelectedFile()};
			}
		}
		
		return null;
	}
	
	public void addListenerForSeatedMode(ActionListener listener) {
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

	public void addListenerForLifeMotionDetection(ActionListener listener) {
		motionDetectionPanel.addListenerForCheckbox(listener);
	}
	
	public void addListenerForStandingCorrection(ActionListener listener) {
		upTracingPanel.addStandingCorrectionListener(listener);
	}

	public void addListenerForSittingCorrection(ActionListener listener) {
		upTracingPanel.addSittingCorrectionListener(listener);
	}

	public void addListenerForValuesAnalysis(ActionListener listener) {
		chartPanel.addListenerForValuesAnalysis(listener);
	}

	public void addListenerForSegmentationAnalysis(ActionListener listener) {
		chartPanel.addListenerForSegmentationAnalysis(listener);
	}
	
	public void addListenerForMotionAnalysis(ActionListener listener) {
		chartPanel.addListenerForMotionAnalysis(listener);
	}

	public void showMessagePopup(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public boolean[] getMarkersState() {
		return markersPanel.getMarkersState();
	}
	
	public long getMotionDetectionDelay() {
		return motionDetectionPanel.getDelay();
	}

	public double getTrajectoryMassMinValue() {
		return motionDetectionPanel.getTrajectoryMassMinValue();
	}
	
	public List<JointType> getMotionDetectionJoints() {
		return motionDetectionPanel.getSelectedJoints();
	}

	public void openChartSelector(List<Body> data, ChartType type) {
		chartPanel.openChartSelector(data, type);
	}

	public void openMotionDetectionChart(List<Body> data, List<JointType> types, 
			double trajectoryMassSummary, double accelerationMassSummary) {
		motionDetectionChartOpener.open(data, types);
	}
	
	private void performCorrection(Body body, CoordinateCorrection correction) {
		if (correction.areCorrectionsZOn()) {
			Map<JointType, Double> correctionsZ = correction.getCorrectionsZ();
			for (JointType type : correctionsZ.keySet()) {
				double oldPositionZ = body.getJoint(type).getPositionZ();
				body.getJoint(type).setPositionZ(
						oldPositionZ + correctionsZ.get(type));
			}
		}

		if (correction.areCorrectionsYOn()) {
			Map<JointType, Double> correctionsY = correction.getCorrectionsY();
			for (JointType type : correctionsY.keySet()) {
				double oldPositionY = body.getJoint(type).getPositionY();
				body.getJoint(type).setPositionY(
						oldPositionY + correctionsY.get(type));
			}
		}
	}

}
