package ee.ttu.kinect.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JCheckBox;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.MainModel;
import ee.ttu.kinect.view.MainView;


public class MainController {

	private final MainView view;

	private final MainModel model;

	public MainController() {
		this.model = new MainModel(this);

		this.view = new MainView();

		//SwingUtilities.invokeLater(view);
		
		this.view.addListenerForMenuOpen(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainController.this.model.setFileToPlay(MainController.this.view.getSelectedFile()); //TODO: fix this hack
				MainController.this.view.setPlayingEnabled(true);
			}
		});
		this.view.addListenerForStartRecord(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainController.this.model.startRecord();
			}
		});
		this.view.addListenerForStopRecord(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainController.this.model.stopRecord();
			}
		});
		this.view.addListenerForStartPlay(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (MainController.this.model.isFileRunPaused()) {
					MainController.this.model.unpauseFileRun();
				} else {
					MainController.this.view.clearChart();
					MainController.this.view.clearTracing();
					MainController.this.model.stopSensorRun();
					MainController.this.model.startFileRun();
				}
			}
		});
		this.view.addListenerForPause(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainController.this.model.pauseFileRun();
			}
		});
		this.view.addListenerForStopPlay(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainController.this.model.stopFileRun();
			}
		});
		this.view.addListenerForSensorOn(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean sensorOn = ((JCheckBox) e.getSource()).isSelected();
				Logger.getLogger(this.getClass().getName()).info("sensor mode changed: " + sensorOn);
				if (!sensorOn) {
					MainController.this.model.stopSensorRun();
				} else {
					MainController.this.view.clearChart();
					MainController.this.model.startSensorRun();
				}
			}
		});
		this.view.addListenerForSeatedMode(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean seatedMode = ((JCheckBox) e.getSource()).isSelected();
				if (!seatedMode) {
					MainController.this.model.setDefaultMode();
				} else {
					MainController.this.model.setSeatedMode();
				}
			}
		});
		this.view.addListenerForStandingCorrection(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					MainController.this.model.calculateStandingCorrection();
				} else {
					MainController.this.model.turnStandingCorrectionOff();
				}
			}
		});
		this.view.addListenerForSittingCorrection(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					MainController.this.model.calculateSittingCorrection();
				} else {
					MainController.this.model.turnSittingCorrectionOff();
				}
			}
		});
		this.view.addListenerForDrawChart(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainController.this.view.openChartSelector(MainController.this.model.getFileData(), true);
			}
		});
		
		this.view.addListenerForSegmentChart(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainController.this.view.openChartSelector(MainController.this.model.getFileData(), false);
			}
		});

		this.view.setRecordingEnabled(false);
		this.view.setPlayingEnabled(false);
		this.model.startSensorRun();
	}

	public void redrawSkeleton(Body body) {
		this.view.redrawSkeleton(body, this.model.isSeatedMode(), this.model.getCoordinateCorrection());
	}
	
	public void redrawChart(Body body) {
		this.view.redrawChart(body, this.model.isSeatedMode());
	}
	
	public void showMessagePopup(String message) {
		this.view.showMessagePopup(message);
	}
	
	public boolean[] getMarkersState() {
		return this.view.getMarkersState();
	}
	
	public void setSensorEnabled(boolean enabled) {
		this.view.setSensorEnabled(enabled);
		this.view.setRecordingEnabled(enabled);
	}
	
	public static void main(String... args) {
		new MainController();
	}

}
