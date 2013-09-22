package ee.ttu.kinect.model.parser;

import ee.ttu.kinect.model.Frame;

public interface SkeletonParser {
	
	void parseSkeleton(String input, Frame frame);
	
}
