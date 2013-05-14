package ee.ttu.kinect.model;

import java.io.File;
import java.util.List;

import ee.ttu.kinect.controller.MainController;

public class MainModel {
	
	private Runner activeRunner;
	
	private SensorRunner sensorRunner;
	
	private FileRunner fileRunner;
	
	private File fileToPlay;
		
	public MainModel(MainController controller) {
		this.sensorRunner = new SensorRunner(controller);
		this.fileRunner = new FileRunner(controller);
	}

	public void setFileToPlay(File file) {
		fileToPlay = file;
	}
	
	public File getFileToPlay() {
		return fileToPlay;
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

	public List<Body> getFileData() {
		fileRunner.readFile(fileToPlay);
		return fileRunner.getData();
	}
	
}
