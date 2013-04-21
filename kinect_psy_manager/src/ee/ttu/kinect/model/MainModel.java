package ee.ttu.kinect.model;

import java.io.File;
import java.util.logging.Logger;

import ee.ttu.kinect.controller.MainController;

public class MainModel {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private MainController controller;
	
	private SensorRunner sensorRunner;
	
	private FileRunner fileRunner;
		
	public MainModel(MainController controller) {
		this.controller = controller;
		this.sensorRunner = new SensorRunner(controller);
		this.fileRunner = new FileRunner(controller);
	}

	public void readFile(File file) {
		fileRunner.readFile(file);
	}
	
	public void playFile() {
		stopSensor();
		
		if (!fileRunner.isRunning()) {
			fileRunner.start();
		}
	}

	public void stopPlay() {
		if (fileRunner.isRunning()) {
			fileRunner.stop();
			//fileRunner = new FileRunner(controller);
		}
		
		startSensor();
		//controller.clearChart();
	}

	public void startRecord() {
		if (fileRunner.isRunning()) {
			fileRunner.stop();
			//fileRunner = new FileRunner(controller);
		}
		
		sensorRunner.startRecord();
	}

	public void stopRecord() {
		if (fileRunner.isRunning()) {
			fileRunner.stop();
			//fileRunner = new FileRunner(controller);
		}
		
		sensorRunner.stopRecord();
	}
	
	public void startSensor() {
		if (!sensorRunner.isRunning()) {
			sensorRunner.start();
		}
	}
	
	public void stopSensor() {
		if (sensorRunner.isRunning()) {
			sensorRunner.stop();
		}
	}
	
	public boolean isSensorRunning() {
		return sensorRunner.isRunning();
	}

	public void setSeatedSkeletonTrackingMode() {
		sensorRunner.setSeatedSkeletonTrackingMode();
	}
	
	public void setDefaultSkeletonTrackingMode() {
		sensorRunner.setDefaultSkeletonTrackingMode();
	}
	
}
