package ee.ttu.kinect.model;

import java.util.Locale;

import com.stromberglabs.cluster.Clusterable;

public class Joint implements Clusterable {
	
	private double positionX;
	
	private double positionY;
	
	private double positionZ;
	
	private JointType type;
	
	private double velocityX;
	
	private double velocityY;
	
	private double velocityZ;
	
	private double accelerationX;
	
	private double accelerationY;
	
	private double accelerationZ;
	
	private double velocity;
	
	private int clusterId;

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
	
	public double getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	public double getVelocityZ() {
		return velocityZ;
	}

	public void setVelocityZ(double velocityZ) {
		this.velocityZ = velocityZ;
	}

	public double getAccelerationX() {
		return accelerationX;
	}

	public void setAccelerationX(double accelerationX) {
		this.accelerationX = accelerationX;
	}

	public double getAccelerationY() {
		return accelerationY;
	}

	public void setAccelerationY(double accelerationY) {
		this.accelerationY = accelerationY;
	}

	public double getAccelerationZ() {
		return accelerationZ;
	}

	public void setAccelerationZ(double accelerationZ) {
		this.accelerationZ = accelerationZ;
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	
	public int getClusterId() {
		return clusterId;
	}

	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}
	
	@Override
	public float[] getLocation() {
		return new float[]{(float) velocityX, (float) velocityY, (float) velocityZ};
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Joint other = (Joint) obj;
		if (Double.doubleToLongBits(accelerationX) != Double
				.doubleToLongBits(other.accelerationX))
			return false;
		if (Double.doubleToLongBits(accelerationY) != Double
				.doubleToLongBits(other.accelerationY))
			return false;
		if (Double.doubleToLongBits(accelerationZ) != Double
				.doubleToLongBits(other.accelerationZ))
			return false;
		if (clusterId != other.clusterId)
			return false;
		if (Double.doubleToLongBits(positionX) != Double
				.doubleToLongBits(other.positionX))
			return false;
		if (Double.doubleToLongBits(positionY) != Double
				.doubleToLongBits(other.positionY))
			return false;
		if (Double.doubleToLongBits(positionZ) != Double
				.doubleToLongBits(other.positionZ))
			return false;
		if (type != other.type)
			return false;
		if (Double.doubleToLongBits(velocityX) != Double
				.doubleToLongBits(other.velocityX))
			return false;
		if (Double.doubleToLongBits(velocityY) != Double
				.doubleToLongBits(other.velocityY))
			return false;
		if (Double.doubleToLongBits(velocityZ) != Double
				.doubleToLongBits(other.velocityZ))
			return false;
		return true;
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

}
