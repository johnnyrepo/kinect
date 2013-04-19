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
	
	public void doStart() {
		logger.info("Starting MainModel");
		
		// starting sensor skeleton tracking
		sensorRunner.start();
		
//		this.doRun = true;
//		kinectThread = new KinectThread();
//		kinectThread.start();
	}

	public void doStop() {
		logger.info("Shutdown requested");
		//String msg = this.kinectSensorProxy.stop();

		//logger.info(msg);
	}

	public void readFile(File file) {
		fileRunner.readFile(file);
	}
	
	public void playFile() {
		if (sensorRunner.isRunning()) {
			sensorRunner.doStop();
			//sensorRunner = new SensorRunner(controller);
		}
		
		if (!fileRunner.isRunning()) {
			fileRunner.start();
		}
	}

	public void stopPlay() {
		if (fileRunner.isRunning()) {
			fileRunner.doStop();
			//fileRunner = new FileRunner(controller);
		}
		
		if (!sensorRunner.isRunning()) {
			sensorRunner.start();
		}
		//controller.clearChart();
	}

	public void startSaving() {
		if (fileRunner.isRunning()) {
			fileRunner.doStop();
			//fileRunner = new FileRunner(controller);
		}
		
		sensorRunner.startSaving();
	}

	public void stopSaving() {
		if (fileRunner.isRunning()) {
			fileRunner.doStop();
			//fileRunner = new FileRunner(controller);
		}
		
		sensorRunner.stopSaving();
	}

}
