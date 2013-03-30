package ee.ttu.kinect.model;

import java.util.HashMap;
import java.util.Map;

public enum JointType {

	HEAD("Head"), 
	HIP_CENTER("HipCenter"), 
	SPINE("Spine"), 
	SHOULDER_CENTER("ShoulderCenter"), 
	SHOULDER_LEFT("ShoulderLeft"), 
	ELBOW_LEFT("ElbowLeft"), 
	WRIST_LEFT("WristLeft"), 
	HAND_LEFT("HandLeft"), 
	SHOULDER_RIGHT("ShoulderRight"), 
	ELBOW_RIGHT("ElbowRight"), 
	WRIST_RIGHT("WristRight"), 
	HAND_RIGHT("HandRight"), 
	HIP_LEFT("HipLeft"), 
	KNEE_LEFT("KneeLeft"), 
	ANKLE_LEFT("AnkleLeft"), 
	FOOT_LEFT("FootLeft"), 
	HIP_RIGHT("HipRight"), 
	KNEE_RIGHT("KneeRight"), 
	ANKLE_RIGHT("AnkleRight"), 
	FOOT_RIGHT("FootRight");
	
	private String name;
	
	private static final Map<String, JointType> lookup = new HashMap<String, JointType>();
	
	static {
		for (JointType type : JointType.values()) {
			lookup.put(type.getName(), type);
		}
	}
	
	private JointType(final String name) {
		this.name = name;
	}
	
	public static final JointType getValueOf(String value) {
		return lookup.get(value);
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
