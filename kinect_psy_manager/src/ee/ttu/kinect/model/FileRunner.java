package ee.ttu.kinect.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ee.ttu.kinect.controller.MainController;
import ee.ttu.kinect.model.parser.SkeletonParserFile;

public class FileRunner extends Runner {

	private SkeletonParserFile skeletonParserFile;

	public FileRunner(MainController controller) {
		super(controller);
		skeletonParserFile = new SkeletonParserFile();
	}

	@Override
	public synchronized void start() {
		body = new Body();
		
		super.start();
	}

	@Override
	protected synchronized String getSkeletonData() {
		String input = null;
		try {
			input = fileUtil.readNextLine();
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
			fileUtil.readFile(file);
		} catch (FileNotFoundException e) {
			logger.info(e.getLocalizedMessage());
		}
	}

	@Override
	public boolean isSeatedMode() {
		return skeletonParserFile.isSeatedMode();
	}

	public List<Body> getData() {
		List<Body> data = new ArrayList<Body>();
		Body body = new Body();
		List<String> textData = fileUtil.readAllLines();
		skeletonParserFile.reset();
		for (String text : textData) {
			try {
				skeletonParserFile.parseSkeleton(text, body);
				if (body.isBodyReady() && body.isBodyChanged()) {
					Body clone = body.clone();
					data.add(clone);
				}
			} catch (CloneNotSupportedException e) {
				logger.info(e.getLocalizedMessage());
			}
		}
		
		return data;
	}

}
