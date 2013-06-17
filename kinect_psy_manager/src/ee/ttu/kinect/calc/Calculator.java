package ee.ttu.kinect.calc;

import java.util.Locale;

import ee.ttu.kinect.model.Joint;

public class Calculator {

	// Calculates velocity in 3D space (m/s)
	// v = sqrt((x1-x2)^2 + (y1-y2)^2 + (z1-z2)^2) / t
	public static double calculateVelocity3D(Joint joint1, Joint joint2, long time1, long time2) {
		double s1 = joint1.getPositionX() - joint2.getPositionX();
		double s2 = joint1.getPositionY() - joint2.getPositionY();
		double s3 = joint1.getPositionZ() - joint2.getPositionZ();
		return (Math.sqrt(s1*s1 + s2*s2 + s3*s3) / (time1 - time2) * 1000);
	}
	
	// Calculates acceleration in 3D space (m/s^2)
	// a = v / t
	public static double calculateAcceleration3D(Joint joint1, Joint joint2, long time1, long time2) {
		System.out.print(" " + String.format(Locale.ENGLISH, "%-14.13f", Calculator.calculateVelocity3D(joint1, joint2, time1, time2)));
		return (Calculator.calculateVelocity3D(joint1, joint2, time1, time2) / (time1 - time2) * 1000);
	}
	
	// Calculates trajectory in 3D space
	// t = sqrt((x1-x2)^2 + (y1-y2)^2 + (z1-z2)^2)
	public static double calculateTrajectoryLength3D(Joint joint1, Joint joint2) {
		double s1 = joint1.getPositionX() - joint2.getPositionX();
		double s2 = joint1.getPositionY() - joint2.getPositionY();
		double s3 = joint1.getPositionZ() - joint2.getPositionZ();
		return Math.sqrt(s1*s1 + s2*s2 + s3*s3);
	}
	
}
