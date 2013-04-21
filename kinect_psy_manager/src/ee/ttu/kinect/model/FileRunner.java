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
	protected String getSkeletonData() {
		String input = null;
		try {
			input = fileWorker.readNextLine();
			if (input == null) { // end of file reached
				stop();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return input;
	}

	@Override
	protected void parseSkeleton(String input) {
		skeletonParserFile.parseSkeleton(input, body);
	}

	@Override
	protected void saveSkeleton() {
		// TODO Auto-generated method stub
	}
	
	public void readFile(File file) {
		try {
			fileWorker.readFile(file);
		} catch (FileNotFoundException e) {
			logger.info(e.getLocalizedMessage());
		}
	}

}
