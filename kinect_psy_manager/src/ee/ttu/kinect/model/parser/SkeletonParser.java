package ee.ttu.kinect.model.parser;

import ee.ttu.kinect.model.Body;

public interface SkeletonParser {
	
	void parseSkeleton(String input, Body body);
	
}
