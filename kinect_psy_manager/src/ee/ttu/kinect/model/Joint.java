package ee.ttu.kinect.model;

import java.util.Locale;

public class Joint {
	
	private double positionX;
	
	private double positionY;
	
	private double positionZ;
	
	private JointType type;

	public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public double getPositionZ() {
		return positionZ;
	}

	public void setPositionZ(double positionZ) {
		this.positionZ = positionZ;
	}

	public JointType getType() {
		return type;
	}

	public void setType(JointType type) {
		this.type = type;
	}
	
	public static String getHeader(JointType jointType) {
		StringBuffer line = new StringBuffer();
		line = line.append(jointType).append("X\t");
		line = line.append(jointType).append("Y\t");
		line = line.append(jointType).append("Z\t");
		
		return line.toString();
	}
	
	public static String getVelocityHeader(JointType jointType) {
		StringBuffer line = new StringBuffer();
		line = line.append(jointType).append("XVelocity\t");
		line = line.append(jointType).append("YVelocity\t");
		line = line.append(jointType).append("ZVelocity\t");
		
		return line.toString();
	}
	
	public static String getAccelerationHeader(JointType jointType) {
		StringBuffer line = new StringBuffer();
		line = line.append(jointType).append("XAcceleration\t");
		line = line.append(jointType).append("YAcceleration\t");
		line = line.append(jointType).append("ZAcceleration\t");
		
		return line.toString();
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(roundCoord(positionX)).append(roundCoord(positionY)).append(roundCoord(positionZ));
		
		return sb.toString();
	}
	
	private String roundCoord(double coord) {
		return String.format(Locale.ENGLISH, "%-13.12f", coord) + "\t";
	}
	
}
