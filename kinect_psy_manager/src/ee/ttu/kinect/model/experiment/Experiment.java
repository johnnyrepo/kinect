package ee.ttu.kinect.model.experiment;

import java.util.ArrayList;
import java.util.List;

import ee.ttu.kinect.calc.Calculator;
import ee.ttu.kinect.model.Frame;
import ee.ttu.kinect.model.JointType;

public class Experiment {

	private String id;
		
	private List<Motion> motions;
	
	public Experiment(String id) {
		this.id = id;
		this.motions = new ArrayList<Motion>();
	}
	
	public String getId() {
		return id;
	}
	
	public List<Motion> getMotions() {
		return motions;
	}
	
	public void addMotion(List<Frame> motionData) {
		Motion motion = new Motion(motionData);
		motions.add(motion);
	}
	
	public void setLastMotionCorrect(boolean correct) {
		motions.get(motions.size() - 1).setCorrect(correct);		
	}
	
	public double getAverageTrajectoryMass() {
		double mass = 0;
		for (Motion motion : motions) {
			mass += motion.getTrajectoryMass();
		}
		
		return mass / motions.size();
	}
	
	public double getAverageAccelerationMass() {
		double mass = 0;
		for (Motion motion : motions) {
			mass += motion.getAccelerationMass();
		}
		
		return mass / motions.size();
	}
	
	public double getBestTrajectoryMass() {
		double best = motions.get(0).getTrajectoryMass();
		for (Motion motion : motions) {
			if (motion.isCorrect()) {
				if (best > motion.getTrajectoryMass()) {
					best = motion.getTrajectoryMass();
				}
			}
		}
		
		return best;
	}
	
	public double getBestAccelerationMass() {
		double best = motions.get(0).getAccelerationMass();
		for (Motion motion : motions) {
			if (motion.isCorrect()) {
				if (best > motion.getAccelerationMass()) {
					best = motion.getAccelerationMass();
				}
			}
		}
		
		return best;
	}
	
	class Motion {
		
		private List<Frame> data;
		
		private boolean correct;
		
		private double trajectoryMass;
		
		private double velocityMass;
		
		private double accelerationMass;
		
		private long time;

		public Motion(List<Frame> data) {
			this.data = data;
			calculateMasses();
			time = (data.get(data.size() - 1)).getTimestamp() - data.get(0).getTimestamp();
		}
		
		private void calculateMasses() {
			trajectoryMass = 0;
			accelerationMass = 0;
			for (int i = 0; i < data.size(); i++) {
				for (JointType type : JointType.values()) {
					if (i + 2 < data.size()) {
						trajectoryMass += Calculator.calculateTrajectoryLength3D(data.get(i).getJoint(type), 
								data.get(i + 1).getJoint(type));
						velocityMass += Math.abs(Calculator.calculateVelocity3D(data.get(i).getJoint(type), data.get(i + 1).getJoint(type), 
								data.get(i).getTimestamp(), data.get(i + 1).getTimestamp()));
						accelerationMass += Math.abs(Calculator.calculateAcceleration3D(data.get(i).getJoint(type), 
								data.get(i + 1).getJoint(type), data.get(i + 2).getJoint(type), 
								data.get(i).getTimestamp(), data.get(i + 1).getTimestamp(), data.get(i + 2).getTimestamp()));
						
					}
				}
			}
		}

		public void setCorrect(boolean correct) {
			this.correct = correct;
		}
		
		public boolean isCorrect() {
			return correct;
		}

		public double getTrajectoryMass() {
			return trajectoryMass;
		}

		public double getVelocityMass() {
			return velocityMass;
		}
		
		public double getAccelerationMass() {
			return accelerationMass;
		}
		
		public long getDurationTime() {
			return time;
		}
		
	}
	
}
