package ee.ttu.kinect.calc;

import java.math.BigDecimal;
import java.math.RoundingMode;

import ee.ttu.kinect.model.Joint;

public class Calculator {

	private static final BigDecimal MILLIS_IN_SEC = new BigDecimal(1000);

	// Calculates trajectory in 3D space (m)
	// t = sqrt((x1-x2)^2 + (y1-y2)^2 + (z1-z2)^2)
	public static double calculateTrajectoryLength3D(Joint joint1, Joint joint2) {
		double s1 = joint1.getPositionX() - joint2.getPositionX();
		double s2 = joint1.getPositionY() - joint2.getPositionY();
		double s3 = joint1.getPositionZ() - joint2.getPositionZ();
		return Math.sqrt(s1 * s1 + s2 * s2 + s3 * s3);
	}

	// Calculates velocity in 3D space (m/s)
	// v = sqrt((x1-x2)^2 + (y1-y2)^2 + (z1-z2)^2) / (t2 - t1)
	public static double calculateVelocity3D(Joint joint1, Joint joint2,
			long time1, long time2) {
		double s1 = joint2.getPositionX() - joint1.getPositionX();
		double s2 = joint2.getPositionY() - joint1.getPositionY();
		double s3 = joint2.getPositionZ() - joint1.getPositionZ();
		return new BigDecimal(Math.sqrt(s1 * s1 + s2 * s2 + s3 * s3)).divide(
				countTimeDeltaInSecs(time1, time2)).doubleValue();
	}

	// Calculates acceleration in 3D space (m/s^2)
	// a = (v2 - v1) / (t2 - t1)
	public static double calculateAcceleration3D(Joint joint1, Joint joint2,
			Joint joint3, long time1, long time2, long time3) {

		return countDelta(
				Calculator.calculateVelocity3D(joint1, joint2, time1, time2),
				Calculator.calculateVelocity3D(joint2, joint3, time2, time3))
				.divide(countTimeDeltaInSecs(time2, time3), 5,
						RoundingMode.CEILING).doubleValue();
	}

	// Calculates velocity between 2 positions
	// v = s / t
	public static double calculateVelocity(double position1, double position2,
			long time1, long time2) {

		return countDelta(position1, position2).divide(
				countTimeDeltaInSecs(time1, time2), 5, RoundingMode.CEILING)
				.doubleValue();
	}

	// Calculates acceleration between 2 positions (position2 and position3)
	// having a velocities
	// a = (v2 - v1) / (t2-t1)
	public static double calculateAcceleration(double position1,
			double position2, double position3, long time1, long time2,
			long time3) {

		return countDelta(
				Calculator
						.calculateVelocity(position1, position2, time1, time2),
				Calculator
						.calculateVelocity(position2, position3, time2, time3))
				.divide(countTimeDeltaInSecs(time2, time3), 5,
						RoundingMode.CEILING).doubleValue();
	}

	private static BigDecimal countDelta(double value1, double value2) {
		return new BigDecimal(value2).subtract(new BigDecimal(value1));
	}

	private static BigDecimal countTimeDeltaInSecs(long time1, long time2) {
		return new BigDecimal((double) time2 - time1).divide(MILLIS_IN_SEC, 3,
				RoundingMode.CEILING);
	}

}
