package ee.ttu.kinect.model;

import java.util.ArrayList;
import java.util.List;

import ee.ttu.kinect.calc.Calculator;

public class ModelProcessor {

	private static final int DATA_SIZE_FOR_CALCULATION = 3;

	private List<Frame> data;

	private double velocityX;
	private double velocityY;
	private double velocityZ;

	private double accelerationX;
	private double accelerationY;
	private double accelerationZ;

	private JointType type;

	public ModelProcessor(JointType type) {
		setType(type);
		reset();
	}
	
	public void setType(JointType type) {
		this.type = type;
	}
	
	public void reset() {
		data = new ArrayList<Frame>();
	}
	
	public boolean process(Frame frame) {
		if (data.size() == DATA_SIZE_FOR_CALCULATION) {
			clean();
		}
		
		data.add(frame);
		
		if (data.size() == DATA_SIZE_FOR_CALCULATION) {
			analyze();
			return true;
		}
		
		return false;
	}
	
	private void clean() {
		data.remove(0);
	}

	private void analyze() {
		velocityX = Calculator.calculateVelocity(data.get(1).getJoint(type).getPositionX(), data.get(2).getJoint(type).getPositionX(), data.get(1).getTimestamp(), data.get(2).getTimestamp());
		velocityY = Calculator.calculateVelocity(data.get(1).getJoint(type).getPositionY(), data.get(2).getJoint(type).getPositionY(), data.get(1).getTimestamp(), data.get(2).getTimestamp());
		velocityZ = Calculator.calculateVelocity(data.get(1).getJoint(type).getPositionZ(), data.get(2).getJoint(type).getPositionZ(), data.get(1).getTimestamp(), data.get(2).getTimestamp());
		
		accelerationX = Calculator.calculateAcceleration(data.get(0).getJoint(type).getPositionX(), data.get(1).getJoint(type).getPositionX(), data.get(2).getJoint(type).getPositionX(), data.get(0).getTimestamp(), data.get(1).getTimestamp(), data.get(2).getTimestamp());
		accelerationY = Calculator.calculateAcceleration(data.get(0).getJoint(type).getPositionY(), data.get(1).getJoint(type).getPositionY(), data.get(2).getJoint(type).getPositionY(), data.get(0).getTimestamp(), data.get(1).getTimestamp(), data.get(2).getTimestamp());
		accelerationZ = Calculator.calculateAcceleration(data.get(0).getJoint(type).getPositionZ(), data.get(1).getJoint(type).getPositionZ(), data.get(2).getJoint(type).getPositionZ(), data.get(0).getTimestamp(), data.get(1).getTimestamp(), data.get(2).getTimestamp());		
	}

	public double getVelocityX() {
		return velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public double getVelocityZ() {
		return velocityZ;
	}

	public double getAccelerationX() {
		return accelerationX;
	}

	public double getAccelerationY() {
		return accelerationY;
	}

	public double getAccelerationZ() {
		return accelerationZ;
	}

}
