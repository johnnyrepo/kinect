package ee.ttu.kinect.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.JointType;
import ee.ttu.kinect.view.chart.TracingChartPanel;


public class MainView extends JFrame {

	private static final long serialVersionUID = 1L;

	private File selectedFile;
	
	private final JMenuBar menuBar;
	
	private final JMenu menu;
	
	private final JMenuItem menuItemOpen;
	
	private final JFileChooser fileChooser;
	
	private final ButtonPanel buttonPanel;
	
	private final MarkersPanel markersPanel;
	
	private final SeatedModePanel seatedModePanel;
	
	private final JPanel controlPanel;
		
	private final JPanel tracingPanel;
	
	private final FrontTracingPanel frontTracingPanel;

	private final SideTracingPanel sideTracingPanel;

	private final UpTracingPanel upTracingPanel;

	private final TracingChartPanel chartPanel;
	
	private boolean standUpCorrection;
	
	private boolean sitDownCorrection;
	
	
	public MainView() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setTitle("KinectPsyManager v0.9");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.exit(1);
		}
		
		this.menuBar = new JMenuBar();
		this.menu = new JMenu("File");
		this.menuItemOpen = new JMenuItem("Open");
		this.menu.add(this.menuItemOpen);
		this.menuBar.add(this.menu);
		this.setJMenuBar(this.menuBar);
		
		this.fileChooser = new JFileChooser();
		
		this.controlPanel = new JPanel();
		this.controlPanel.setLayout(new BoxLayout(this.controlPanel, BoxLayout.X_AXIS));
		
		this.buttonPanel = new ButtonPanel("Record / Play");
				
		this.markersPanel = new MarkersPanel();
		
		this.seatedModePanel = new SeatedModePanel();
		
		this.controlPanel.add(this.buttonPanel);
		this.controlPanel.add(this.markersPanel);
		this.controlPanel.add(this.seatedModePanel);

		this.tracingPanel = new JPanel();
		this.tracingPanel.setLayout(new BoxLayout(this.tracingPanel, BoxLayout.X_AXIS));
		this.frontTracingPanel = new FrontTracingPanel();
		this.sideTracingPanel = new SideTracingPanel();
		this.upTracingPanel = new UpTracingPanel();
		this.upTracingPanel.addCorrectionPanel(new CorrectionPanel(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainView.this.standUpCorrection = ((JCheckBox) e.getSource()).isSelected();
            }
        }, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainView.this.sitDownCorrection = ((JCheckBox) e.getSource()).isSelected();
			}
		}));
		this.sideTracingPanel.addZoomPanel(new ZoomPanel(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				MainView.this.frontTracingPanel.zoomIn();
				MainView.this.sideTracingPanel.zoomIn();
				MainView.this.upTracingPanel.zoomIn();
			}
		}, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainView.this.frontTracingPanel.zoomOut();
				MainView.this.sideTracingPanel.zoomOut();
				MainView.this.upTracingPanel.zoomOut();
			}
		}));
		this.tracingPanel.add(this.frontTracingPanel);
		this.tracingPanel.add(this.sideTracingPanel);
		this.tracingPanel.add(this.upTracingPanel);

		this.chartPanel = new TracingChartPanel();

		this.getContentPane().add(this.controlPanel, BorderLayout.NORTH);
		this.getContentPane().add(this.tracingPanel, BorderLayout.CENTER);
		this.getContentPane().add(this.chartPanel, BorderLayout.SOUTH);
		
		this.setSize(1200, 700);
		this.setVisible(true);
	}
	
	public void setSensorEnabled(boolean enabled) {
		this.buttonPanel.setSensorEnabled(enabled);
	}

	public void setRecordingEnabled(boolean enabled) {
		this.buttonPanel.setRecordingEnabled(enabled);
	}
	
	public void setPlayingEnabled(boolean enabled) {
		this.buttonPanel.setPlayingEnabled(enabled);
	}
	
	public File getSelectedFile() {
		return this.selectedFile;
	}
	
	public void redrawSkeleton(Body body, boolean seatedMode) {
		if (this.standUpCorrection) {
			this.performStandUpCorrection(body);
		}
		if (this.sitDownCorrection) {
			this.performSitDownCorrection(body);
		}
		
		this.frontTracingPanel.redrawSkeleton(body, seatedMode);
		this.sideTracingPanel.redrawSkeleton(body, seatedMode);
		this.upTracingPanel.redrawSkeleton(body, seatedMode);
	}

	public void redrawChart(Body body, boolean seatedMode) {
		this.chartPanel.updateChart(body, seatedMode);
	}

	public void clearChart() {
		this.chartPanel.clearChart();
	}
	
	public void clearTracing() {
		this.frontTracingPanel.clear();
		this.sideTracingPanel.clear();
		this.upTracingPanel.clear();
	}
	
	public void addListenerForMenuOpen(final ActionListener listener) {
		this.menuItemOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainView.this.fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int res = MainView.this.fileChooser.showDialog(null, null);
				if (res == JFileChooser.APPROVE_OPTION) {
					MainView.this.selectedFile = MainView.this.fileChooser.getSelectedFile();
					MainView.this.buttonPanel.updateSelectedFileLabel(MainView.this.selectedFile.getName());
					listener.actionPerformed(e);
				}
			}
		});
	}
	
	public void addListenerForSeatedModeCheckbox(ActionListener listener) {
		this.seatedModePanel.addLsitenerForCheckbox(listener);
	}
	
	public void addListenerForSensorOn(ActionListener listener) {
		this.buttonPanel.addListenerForSensorOn(listener);
	}

	public void addListenerForStartRecord(ActionListener listener) {
		this.buttonPanel.addListenerForStartRecord(listener);
	}

	public void addListenerForStopRecord(ActionListener listener) {
		this.buttonPanel.addListenerForStopRecord(listener);
	}

	public void addListenerForStartPlay(ActionListener listener) {
		this.buttonPanel.addListenerForStartPlay(listener);
	}

	public void addListenerForPause(ActionListener listener) {
		this.buttonPanel.addListenerForPause(listener);
	}
	
	public void addListenerForStopPlay(ActionListener listener) {
		this.buttonPanel.addListenerForStopPlay(listener);
	}

	public void addListenerForDrawChart(ActionListener listener) {
		this.chartPanel.addListenerForDrawChart(listener);
	}
	
	public void addListenerForSegmentChart(ActionListener listener) {
		this.chartPanel.addListenerForSegmentChart(listener);
	}
	
	public void showMessagePopup(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public boolean[] getMarkersState() {
		return this.markersPanel.getMarkersState();
	}

	public void openChartSelector(List<Body> data, boolean valuesCharts) {
		this.chartPanel.openChartSelector(data, valuesCharts);
	}
	
	private void performStandUpCorrection(Body body) {
		double referenceZ = body.getJoint(JointType.SHOULDER_CENTER).getPositionZ();
		body.getJoint(JointType.SPINE).setPositionZ(referenceZ);
		body.getJoint(JointType.HIP_CENTER).setPositionZ(referenceZ);
		body.getJoint(JointType.KNEE_LEFT).setPositionZ(referenceZ);
		body.getJoint(JointType.KNEE_RIGHT).setPositionZ(referenceZ);
		body.getJoint(JointType.ANKLE_LEFT).setPositionZ(referenceZ);
		body.getJoint(JointType.ANKLE_RIGHT).setPositionZ(referenceZ);
	}
	
	private void performSitDownCorrection(Body body) {
		double leftHipY = body.getJoint(JointType.HIP_LEFT).getPositionY();
		double rightHipY = body.getJoint(JointType.HIP_RIGHT).getPositionY();
		double leftKneeX = body.getJoint(JointType.KNEE_LEFT).getPositionX();
		double rightKneeX = body.getJoint(JointType.KNEE_RIGHT).getPositionX();
		
		body.getJoint(JointType.KNEE_LEFT).setPositionY(leftHipY);
		body.getJoint(JointType.KNEE_RIGHT).setPositionY(rightHipY);
		body.getJoint(JointType.ANKLE_LEFT).setPositionX(leftKneeX);
		body.getJoint(JointType.ANKLE_RIGHT).setPositionX(rightKneeX);
	}
	
}
