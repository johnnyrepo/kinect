package ee.ttu.kinect.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JCheckBox;

import ee.ttu.kinect.model.Frame;
import ee.ttu.kinect.model.MainModel;
import ee.ttu.kinect.model.experiment.SegmentationExperimentAnalyzer;
import ee.ttu.kinect.view.ChartType;
import ee.ttu.kinect.view.MainView;


public class MainController {

	private final MainView view;

	private final MainModel model;

	public MainController() {
		model = new MainModel(this);

		view = new MainView();

		//SwingUtilities.invokeLater(view);
		
		view.addListenerForMenuOpen(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setFileToPlay(view.getFileToPlay()); //TODO: fix this hack
				view.setPlayingEnabled(true);
			}
		});
		view.addListenerForMenuPolynomialAnalyzer(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//PolynomialExperimentAnalyzer.analyze(view.getFilesForAnalysis());
				SegmentationExperimentAnalyzer.analyze(view.getFilesForAnalysis());
			}
		});
		view.addListenerForStartRecord(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.startRecord();
				view.setMarkersAmountChangeEnabled(false);
			}
		});
		view.addListenerForStopRecord(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.stopRecord();
				view.setMarkersAmountChangeEnabled(true);
			}
		});
		view.addListenerForStartPlay(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.isFileRunPaused()) {
					model.unpauseFileRun();
				} else {
					view.clearChart();
					view.clearTracing();
					model.stopSensorRun();
					model.startFileRun();
				}
			}
		});
		view.addListenerForPause(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.pauseFileRun();
			}
		});
		view.addListenerForStopPlay(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.stopFileRun();
			}
		});
		view.addListenerForSensorOn(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean sensorOn = ((JCheckBox) e.getSource()).isSelected();
				Logger.getLogger(this.getClass().getName()).info("sensor mode changed: " + sensorOn);
				if (!sensorOn) {
					model.stopSensorRun();
				} else {
					view.clearChart();
					model.startSensorRun();
				}
			}
		});
		view.addListenerForLifeMotionDetection(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setMotionAnalysisMode(((JCheckBox) e.getSource()).isSelected(), 
						view.getMotionDetectionDelay(), view.getMotionDetectionJoints(), 
						view.getTrajectoryMassMinValue());
			}
		});
		view.addListenerForMarkerStateChange(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setMarkersState(view.getMarkersState());
			}
		});
		view.addListenerForSeatedMode(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean seatedMode = ((JCheckBox) e.getSource()).isSelected();
				if (!seatedMode) {
					model.setDefaultMode();
				} else {
					model.setSeatedMode();
				}
			}
		});
		view.addListenerForStandingCorrection(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					model.calculateStandingCorrection();
				} else {
					model.turnStandingCorrectionOff();
				}
			}
		});
		view.addListenerForSittingCorrection(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					model.calculateSittingCorrection();
				} else {
					model.turnSittingCorrectionOff();
				}
			}
		});
		view.addListenerForValuesAnalysis(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.openChartSelector(model.getFileData(), ChartType.VALUES);
			}
		});
		
		view.addListenerForSegmentationAnalysis(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.openChartSelector(model.getFileData(), ChartType.SEGMENTATION);
			}
		});
		
		view.addListenerForMotionAnalysis(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.openChartSelector(model.getFileData(), ChartType.MOTION);
			}
		});

		view.setRecordingEnabled(false);
		view.setPlayingEnabled(false);
		model.startSensorRun();
	}

	public void redrawSkeleton(Frame frame) {
		view.redrawSkeleton(frame, model.isSeatedMode(), model.getCoordinateCorrection());
	}
	
	public void redrawChart(Frame frame) {
		view.redrawChart(frame, model.isSeatedMode());
	}
	
	public void showMessagePopup(String message) {
		view.showMessagePopup(message);
	}
	
	public boolean[] getMarkersState() {
		return model.getMarkersState();
	}
	
	public void setSensorEnabled(boolean enabled) {
		view.setSensorEnabled(enabled);
		view.setRecordingEnabled(enabled);
	}

	public void analyzeMotion(Frame frame) {		
		if (!model.isMotionAnalysisMode()) {
			return;
		}
		
		if (model.isMotionEnded(frame)) {
			view.setMotionDetectionEnabled(false); // TODO: make the controller and model in sync
			model.setMotionAnalysisMode(false, view.getMotionDetectionDelay(), view.getMotionDetectionJoints(), view.getTrajectoryMassMinValue());
			//view.showMessagePopup("Motion has ended");
			view.openMotionDetectionChart(model.getMotionData(), model.getMotionDetectionJointTypes(), model.getTrajectoryMassSummary(), model.getAccelerationMassSummary());
		}
	}
	
	public static void main(String... args) {
		new MainController();
	}

}
