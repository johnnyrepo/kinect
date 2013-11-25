package ee.ttu.kinect.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ee.ttu.kinect.controller.MainController;
import ee.ttu.kinect.model.network.MarkersServer;

public class MainModel {
	
	private Runner activeRunner;
	
	private final SensorRunner sensorRunner;
	
	private final FileRunner fileRunner;
	
	private File fileToPlay;
	
	private Markers markers;
	
	private MarkersServer markersServer;
	
	private CoordinateCorrection coordinateCorrection;
	
	private MotionProcessor processor;
	
	private boolean motionAnalysisMode;
		
	public MainModel(MainController controller) {
		sensorRunner = new SensorRunner(controller);
		fileRunner = new FileRunner(controller);
		markers = new Markers();
		try {
			markersServer = new MarkersServer(markers);
		} catch(IOException ioe) {}
		coordinateCorrection = new CoordinateCorrection();
		processor = new MotionProcessor();
	}

	public void setFileToPlay(File file) {
		fileToPlay = file;
	}
	
	public File getFileToPlay() {
		return fileToPlay;
	}
	
	public void startRecord() {
		stopFileRun();

		markers.reset();
		
		sensorRunner.startRecord();
	}

	public void stopRecord() {
		stopFileRun();
		
		sensorRunner.stopRecord();
	}
	
	public void startFileRun() {
		fileRunner.readFile(fileToPlay);
		if (!fileRunner.isRunning()) {
			fileRunner.start();
			
			activeRunner = fileRunner;
		}
	}

	public void pauseFileRun() {
		if (fileRunner.isRunning()) {
			fileRunner.pause();
		}
	}
	
	public void unpauseFileRun() {
		if (fileRunner.isRunning()) {
			fileRunner.unpause();		}
	}
	
	public void stopFileRun() {
		if (fileRunner.isRunning()) {
			fileRunner.stop();
		}
	}
	
	public void startSensorRun() {
		if (!sensorRunner.isRunning()) {
			sensorRunner.start();
			
			activeRunner = sensorRunner;
		}
	}
	
	public void stopSensorRun() {
		if (sensorRunner.isRunning()) {
			sensorRunner.stop();
		}
	}
	
	public void startMarkersServer() {
		markersServer.run();
	}
	
	public boolean isFileRunPaused() {
		return fileRunner.isPaused();
	}

	public void setSeatedMode() {
		activeRunner.setSeatedMode();
	}
	
	public void setDefaultMode() {
		activeRunner.setDefaultMode();
	}

	public boolean isSeatedMode() {
		return activeRunner.isSeatedMode();
	}

	public List<Frame> getFileData() {
		fileRunner.readFile(fileToPlay);
		return fileRunner.getData();
	}

	public boolean[] getMarkersState() {
		return markers.getState();
	}
	
	public void setMarkersState(boolean[] state) {
		markers.setState(state);
	}
	
	public CoordinateCorrection getCoordinateCorrection() {
		return coordinateCorrection;
	}
	
	public void calculateSittingCorrection() {
		coordinateCorrection.calculateSittingCorrection(activeRunner.frame);
	}
	
	public void calculateStandingCorrection() {
		coordinateCorrection.calculateStandingCorrection(activeRunner.frame);
	}
	
	public void turnStandingCorrectionOff() {
		coordinateCorrection.turnStandingCorrectionOff();
	}
	
	public void turnSittingCorrectionOff() {
		coordinateCorrection.turnSittingCorrectionOff();
	}

	public void setMotionAnalysisMode(boolean enabled, long delay, List<JointType> types, double trajectoryMassMinValue) {
		motionAnalysisMode = enabled;
		if (enabled) {
			processor.setDelay(delay);
			processor.setTypes(types);
			processor.setTrajectoryMassMinValue(trajectoryMassMinValue);
			processor.reset();
		}
	}
	
	public boolean isMotionAnalysisMode() {
		return motionAnalysisMode;
	}
	
	public void setMotionAnalysisDelay(long delay) {
		processor.setDelay(delay);
	}
	
	public boolean isMotionEnded(Frame frame) {
		boolean isProcessed = false;
		boolean isMotionEnded = false;
		if (frame != null && frame.isFrameReady()) {
			isProcessed = processor.process(frame);
			if (isProcessed) {
				isMotionEnded = processor.isMotionEnded();
//				if (isMotionEnded) {
//					processor.outputSummaryToConsole(type);
//				}
//				processor.clean();
			}
		}
		
		return (isProcessed && isMotionEnded);
	}

	public List<Frame> getMotionData() {
		return processor.getDataSummary();
	}

	public List<JointType> getMotionDetectionJointTypes() {
		return processor.getTypes();
	}
	
	public double getTrajectoryMassSummary() {
		return processor.getTrajectoryMassSummary();
	}

	public double getAccelerationMassSummary() {
		return processor.getAccelerationMassSummary();
	}
	
}
