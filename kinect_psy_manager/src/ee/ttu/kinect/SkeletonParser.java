package ee.ttu.kinect;

import ee.ttu.kinect.model.Body;

public interface SkeletonParser {
	
	void parseSkeleton(String input, Body body);
	
}
