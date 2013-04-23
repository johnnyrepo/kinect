package ee.ttu.kinect.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import ee.ttu.kinect.SkeletonParserFile;
import ee.ttu.kinect.controller.MainController;

public class FileRunner extends Runner {

	private SkeletonParserFile skeletonParserFile;

	public FileRunner(MainController controller) {
		super(controller);
		skeletonParserFile = new SkeletonParserFile();
	}

	@Override
	public void start() {
		body = new Body();
		
		super.start();
	}

	@Override
	protected synchronized String getSkeletonData() {
		String input = null;
		try {
			input = fileWorker.readNextLine();
			if (input == null) { // end of file reached
				stop();
			}
		} catch (IOException e) {
			logger.info(e.getLocalizedMessage());
		}

		return input;
	}

	@Override
	protected synchronized void parseSkeleton(String input) {
		skeletonParserFile.parseSkeleton(input, body);
	}

	@Override
	protected synchronized void saveSkeleton() {
		// do nothing
	}
	
	public void readFile(File file) {
		try {
			fileWorker.readFile(file);
		} catch (FileNotFoundException e) {
			logger.info(e.getLocalizedMessage());
		}
	}

	@Override
	public boolean isSeatedMode() {
		return skeletonParserFile.isSeatedMode();
	}
	
}
