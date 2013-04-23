package ee.ttu.kinect.model;

import java.io.File;

import ee.ttu.kinect.controller.MainController;

public class MainModel {
	
	private Runner activeRunner;
	
	private SensorRunner sensorRunner;
	
	private FileRunner fileRunner;
		
	public MainModel(MainController controller) {
		this.sensorRunner = new SensorRunner(controller);
		this.fileRunner = new FileRunner(controller);
	}

	public void readFile(File file) {
		fileRunner.readFile(file);
	}

	public void startRecord() {
		stopFileRun();
		
		sensorRunner.startRecord();
	}

	public void stopRecord() {
		stopFileRun();
		
		sensorRunner.stopRecord();
	}
	
	public void startFileRun() {
		if (!fileRunner.isRunning()) {
			fileRunner.start();
			
			activeRunner = fileRunner;
		}
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
	
	public boolean isSensorRunning() {
		return sensorRunner.isRunning();
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
	
}
