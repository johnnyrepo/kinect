package ee.ttu.kinect.model;

import java.util.ArrayList;
import java.util.List;

import ee.ttu.kinect.calc.Calculator;

public class MovementProcessor {

	private final static double TRAJECTORY_MIN_VALUE = 0.05;
	
	private final int windowSize = 30; 
	
	private double trajectorySummary;
	
	private double velocitySummary;
	
	private double accelerationSummary;
	
	private List<Body> data;
	
	public MovementProcessor() {
		this.clean();
	}
	
//	private void analyze(List<Body> data, JointType selectedType) {
//		this.trajectorySummary = new ArrayList<Double>();
//		this.velocitySummary = new ArrayList<Double>();
//		this.accelerationSummary = new ArrayList<Double>();
//		
//		for (int i = 0; i < data.size(); i++) {
//			double trajectorySummary = 0;
//			double velocitySummary = 0;
//			double accelerationSummary = 0;
//			for (int j = 0; j < this.windowSize; j++) {
//				if ((i + j) < data.size() && (i + j + 1) < data.size() && (j + 1) < this.windowSize) {
//					Joint joint1 = data.get(i + j).getJoint(selectedType);
//					Joint joint2 = data.get(i + j + 1).getJoint(selectedType);
//					long time1 = data.get(i + j).getTimestamp();
//					long time2 = data.get(i + j + 1).getTimestamp();
//					trajectorySummary += Calculator.calculateTrajectoryLength3D(joint1, joint2);
//					velocitySummary += Math.abs(Calculator.calculateVelocity3D(joint1, joint2, time1, time2));
//					accelerationSummary += Math.abs(Calculator.calculateAcceleration3D(joint1, joint2, time1, time2));
//				}
//			}
//			this.trajectorySummary.add(trajectorySummary);
//			this.velocitySummary.add(velocitySummary);
//			this.accelerationSummary.add(accelerationSummary);
//		}
//	}
	
	private void analyze(List<Body> data, JointType type) {
		this.trajectorySummary = 0;
		this.velocitySummary = 0;
		this.accelerationSummary = 0;
		for (int i = 0; i < data.size(); i++) {
			if ((i + 1) < data.size()) {
				Joint joint1 = data.get(i).getJoint(type);
				Joint joint2 = data.get(i + 1).getJoint(type);
				long time1 = data.get(i).getTimestamp();
				long time2 = data.get(i + 1).getTimestamp();
				this.trajectorySummary += Calculator.calculateTrajectoryLength3D(joint1, joint2);
				this.velocitySummary += Math.abs(Calculator.calculateVelocity3D(joint1, joint2, time1, time2));
				this.accelerationSummary += Math.abs(Calculator.calculateAcceleration3D(joint1, joint2, time1, time2));
			}
		}
	}

	public void clean() {
		this.data = new ArrayList<Body>();
		this.trajectorySummary = 0;
	}
	
	public boolean process(Body body, JointType type) {
		this.data.add(body);
		if (this.data.size() == this.windowSize) {
			this.analyze(this.data, type);
			return true;
		}
		
		return false;
	}
	
	public boolean isMovementEnded() {
		System.out.println(this.trajectorySummary);
		return this.trajectorySummary < MovementProcessor.TRAJECTORY_MIN_VALUE;
	}
	
}