package ee.ttu.kinect.model;

import java.util.ArrayList;
import java.util.List;

import ee.ttu.kinect.calc.Calculator;

public class MovementProcessor {

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
		
	public MovementProcessor() {
		reset();
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
	}
	
	public boolean process(Body body, JointType type) {
		// clean if left from previous processor cycle
		if (data.size() == windowSize) {
			clean();
		}
		data.add(body);
		dataSummary.add(body);
		if (data.size() == windowSize) {
			analyze(data, type);
			return true;
		}
		
		return false;
	}
	
	private void analyze(List<Body> data, JointType type) {
		trajectoryMass = 0;
		velocityMass = 0;
		accelerationMass = 0;
		for (int i = 0; i < data.size(); i++) {
			if ((i + 1) < data.size()) {
				Joint joint1 = data.get(i).getJoint(type);
				Joint joint2 = data.get(i + 1).getJoint(type);
				long time1 = data.get(i).getTimestamp();
				long time2 = data.get(i + 1).getTimestamp();
				trajectoryMass += Calculator.calculateTrajectoryLength3D(joint1, joint2);
				velocityMass += Math.abs(Calculator.calculateVelocity3D(joint1, joint2, time1, time2));
				accelerationMass += Math.abs(Calculator.calculateAcceleration3D(joint1, joint2, time1, time2));
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
	
	public boolean isMovementEnded() {
		return trajectoryMass < MovementProcessor.TRAJECTORY_MASS_MIN_VALUE;
	}
	
	public void outputSummaryToConsole(JointType type) {
		long frameStart = dataSummary.get(0).getFrameNumber();
		long frameEnd = dataSummary.get(dataSummary.size() - 1).getFrameNumber();
		double startTime = dataSummary.get(0).getTimestamp() / 1000;
		double endTime = dataSummary.get(dataSummary.size() - 1).getTimestamp() / 1000;
		double time = endTime - startTime;
		
		analyze(dataSummary, type);
		double eucledianDistance = Calculator.calculateTrajectoryLength3D(dataSummary.get(0).getJoint(type), 
				dataSummary.get(dataSummary.size() - 1).getJoint(type));
		double ratio = eucledianDistance / trajectoryMass;
		
		System.out.println("Frame start: " + frameStart);
		System.out.println("Frame end: " + frameEnd);
		System.out.println("Time elapsed: " + time);
		System.out.println("Trajectory mass: " + trajectoryMass);
		System.out.println("Acceleration mass: " + accelerationMass);
		System.out.println("Eucledian distance: " + eucledianDistance);
		System.out.println("Eucl / Traj ratio: " + ratio);
	}
	
}