package ee.ttu.kinect.model;

import java.io.File;
import java.util.List;

import ee.ttu.kinect.controller.MainController;

public class MainModel {
	
	private Runner activeRunner;
	
	private final SensorRunner sensorRunner;
	
	private final FileRunner fileRunner;
	
	private File fileToPlay;
	
	private final CoordinateCorrection coordinateCorrection;
		
	public MainModel(MainController controller) {
		this.sensorRunner = new SensorRunner(controller);
		this.fileRunner = new FileRunner(controller);
		this.coordinateCorrection = new CoordinateCorrection();
	}

	public void setFileToPlay(File file) {
		this.fileToPlay = file;
	}
	
	public File getFileToPlay() {
		return this.fileToPlay;
	}
	
	public void startRecord() {
		this.stopFileRun();
		
		this.sensorRunner.startRecord();
	}

	public void stopRecord() {
		this.stopFileRun();
		
		this.sensorRunner.stopRecord();
	}
	
	public void startFileRun() {
		this.fileRunner.readFile(this.fileToPlay);
		if (!this.fileRunner.isRunning()) {
			this.fileRunner.start();
			
			this.activeRunner = this.fileRunner;
		}
	}

	public void pauseFileRun() {
		if (this.fileRunner.isRunning()) {
			this.fileRunner.pause();
		}
	}
	
	public void unpauseFileRun() {
		if (this.fileRunner.isRunning()) {
			this.fileRunner.unpause();		}
	}
	
	public void stopFileRun() {
		if (this.fileRunner.isRunning()) {
			this.fileRunner.stop();
		}
	}
	
	public void startSensorRun() {
		if (!this.sensorRunner.isRunning()) {
			this.sensorRunner.start();
			
			this.activeRunner = this.sensorRunner;
		}
	}
	
	public void stopSensorRun() {
		if (this.sensorRunner.isRunning()) {
			this.sensorRunner.stop();
		}
	}
	
	public boolean isFileRunPaused() {
		return this.fileRunner.isPaused();
	}

	public void setSeatedMode() {
		this.activeRunner.setSeatedMode();
	}
	
	public void setDefaultMode() {
		this.activeRunner.setDefaultMode();
	}

	public boolean isSeatedMode() {
		return this.activeRunner.isSeatedMode();
	}

	public List<Body> getFileData() {
		this.fileRunner.readFile(this.fileToPlay);
		return this.fileRunner.getData();
	}

	public CoordinateCorrection getCoordinateCorrection() {
		return this.coordinateCorrection;
	}
	
	public void calculateSittingCorrection() {
		this.coordinateCorrection.calculateSittingCorrection(this.activeRunner.body);
	}
	
	public void calculateStandingCorrection() {
		this.coordinateCorrection.calculateStandingCorrection(this.activeRunner.body);
	}
	
	public void turnStandingCorrectionOff() {
		this.coordinateCorrection.turnStandingCorrectionOff();
	}
	
	public void turnSittingCorrectionOff() {
		this.coordinateCorrection.turnSittingCorrectionOff();
	}
	
}
