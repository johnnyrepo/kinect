package ee.ttu.kinect.model;

import java.util.ArrayList;
import java.util.List;

import ee.ttu.kinect.calc.Calculator;

public class MotionProcessor {

	private final static double TRAJECTORY_MASS_MIN_VALUE = 0.15;

	private final int windowSize = 30;

	private double trajectoryMass;

	private double accelerationMass;

	private double velocityMass;

	private List<Body> data;

	private List<Body> dataSummary;

	private double trajectoryMassSummary;

	private double accelerationMassSummary;

	private double velocityMassSummary;

	private long firstTimestamp;

	private long delay;

	private List<JointType> types;

	public MotionProcessor() {
		reset();
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public void setTypes(List<JointType> types) {
		this.types = types;
	}

	public void reset() {
		trajectoryMassSummary = 0;
		accelerationMassSummary = 0;
		velocityMassSummary = 0;

		trajectoryMass = 0;
		accelerationMass = 0;
		velocityMass = 0;

		dataSummary = new ArrayList<Body>();
		data = new ArrayList<Body>();

		firstTimestamp = 0;

	}

	public boolean process(Body body) {
		// set very first timestamp if processing has been just started
		if (data.size() == 0) {
			firstTimestamp = body.getTimestamp();
		}
		// clean if left from previous processor cycle
		if (data.size() == windowSize) {
			clean();
		}
		data.add(body);
		dataSummary.add(body);
		if (data.size() == windowSize) {
			analyze();
			return true;
		}

		return false;
	}

	private void analyze() {
		trajectoryMass = 0;
		velocityMass = 0;
		accelerationMass = 0;
		for (int i = 0; i < data.size(); i++) {
			if ((i + 2) < data.size()) {
				for (JointType type : types) {
					Joint joint1 = data.get(i).getJoint(type);
					Joint joint2 = data.get(i + 1).getJoint(type);
					Joint joint3 = data.get(i + 2).getJoint(type);
					long time1 = data.get(i).getTimestamp();
					long time2 = data.get(i + 1).getTimestamp();
					long time3 = data.get(i + 2).getTimestamp();
					trajectoryMass += Calculator.calculateTrajectoryLength3D(
							joint2, joint3);
					velocityMass += Math.abs(Calculator.calculateVelocity3D(joint2,
							joint3, time2, time3));
					accelerationMass += Math.abs(Calculator
							.calculateAcceleration3D(joint1, joint2, joint3, time1,
									time2, time3));
				}
			}
		}
	}

	private void clean() {
		trajectoryMassSummary += trajectoryMass;
		accelerationMassSummary += accelerationMass;
		velocityMassSummary += velocityMass;

		trajectoryMass = 0;
		velocityMass = 0;
		accelerationMass = 0;

		data.remove(0);
	}

	public double getTrajectoryMass() {
		return trajectoryMass;
	}

	public double getAccelerationMass() {
		return accelerationMass;
	}

	public List<Body> getData() {
		return data;
	}

	public List<Body> getDataSummary() {
		return dataSummary;
	}

	public double getTrajectoryMassSummary() {
		return trajectoryMassSummary;
	}

	public double getAccelerationMassSummary() {
		return accelerationMassSummary;
	}

	public boolean isMotionEnded() {
		if ((data.get(data.size() - 1).getTimestamp() - firstTimestamp) >= delay) {
			return trajectoryMass < MotionProcessor.TRAJECTORY_MASS_MIN_VALUE;
		}

		return false;
	}

}