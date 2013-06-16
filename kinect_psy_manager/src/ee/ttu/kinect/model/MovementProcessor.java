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
	
	private List<Body> allTimeData;
	
	public MovementProcessor() {
		data = new ArrayList<Body>();
		allTimeData = new ArrayList<Body>();
		clean();
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

	public void clean() {
		trajectoryMass = 0;
		velocityMass = 0;
		accelerationMass = 0;
	}
	
	public boolean process(Body body, JointType type) {
		allTimeData.add(body);
		data.add(body);
		if (data.size() == windowSize) {
			analyze(data, type);
			data.remove(0);
			return true;
		}
		
		return false;
	}
	
	public double getTrajectoryMass() {
		return trajectoryMass;
	}

	public boolean isMovementEnded() {
		return trajectoryMass < MovementProcessor.TRAJECTORY_MASS_MIN_VALUE;
	}
	
	public void outputSummaryToConsole(JointType type) {
		long frameStart = allTimeData.get(0).getFrameNumber();
		long frameEnd = allTimeData.get(allTimeData.size() - 1).getFrameNumber();
		double startTime = allTimeData.get(0).getTimestamp() / 1000;
		double endTime = allTimeData.get(allTimeData.size() - 1).getTimestamp() / 1000;
		double time = endTime - startTime;
		
		analyze(allTimeData, type);
		double eucledianDistance = Calculator.calculateTrajectoryLength3D(allTimeData.get(0).getJoint(type), 
				allTimeData.get(allTimeData.size() - 1).getJoint(type));
		double ratio = eucledianDistance / trajectoryMass;
		
		System.out.println("Frame start: " + frameStart);
		System.out.println("Frame end: " + frameEnd);
		System.out.println("Time elapsed: " + time);
		System.out.println("Trajectory mass: " + trajectoryMass);
		System.out.println("Acceleration mass: " + accelerationMass);
		System.out.println("Eucledian distance: " + eucledianDistance);
		System.out.println("Eucl / Traj ratio: " + ratio);
		
		// clean da data
		allTimeData = new ArrayList<Body>();
	}
	
}