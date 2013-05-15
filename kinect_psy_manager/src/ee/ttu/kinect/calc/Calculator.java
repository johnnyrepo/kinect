package ee.ttu.kinect.calc;

import ee.ttu.kinect.model.Joint;

public class Calculator {

	public static double calculateVelocity3D(Joint joint1, Joint joint2, long time1, long time2) {
		// Calculates velocity in 3D space
		// v = sqrt((x1-x2)^2 + (y1-y2)^2 + (z1-z2)^2) / t
		double s1 = joint1.getPositionX() - joint2.getPositionX();
		double s2 = joint1.getPositionY() - joint2.getPositionY();
		double s3 = joint1.getPositionZ() - joint2.getPositionZ();
		return Math.sqrt(s1*s1 + s2*s2 + s3*s3) / (time1 - time2);
	}
	
}
