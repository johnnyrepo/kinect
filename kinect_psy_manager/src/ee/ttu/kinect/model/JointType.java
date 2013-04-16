package ee.ttu.kinect.model;

import java.util.HashMap;
import java.util.Map;

public enum JointType {

	ANKLE_LEFT("AnkleLeft"), 
	ANKLE_RIGHT("AnkleRight"), 
	ELBOW_LEFT("ElbowLeft"), 
	ELBOW_RIGHT("ElbowRight"), 
	FOOT_LEFT("FootLeft"), 
	FOOT_RIGHT("FootRight"), 
	HAND_LEFT("HandLeft"), 
	HAND_RIGHT("HandRight"), 
	HEAD("Head"), 
	HIP_CENTER("HipCenter"), 
	HIP_LEFT("HipLeft"), 
	HIP_RIGHT("HipRight"), 
	KNEE_LEFT("KneeLeft"), 
	KNEE_RIGHT("KneeRight"), 
	SHOULDER_CENTER("ShoulderCenter"), 
	SHOULDER_LEFT("ShoulderLeft"), 
	SHOULDER_RIGHT("ShoulderRight"), 
	SPINE("Spine"), 
	WRIST_LEFT("WristLeft"), 
	WRIST_RIGHT("WristRight");
	
	private static final Map<String, JointType> lookup = new HashMap<String, JointType>();
	
	private String name;

	static {
		for (JointType type : JointType.values()) {
			lookup.put(type.getName(), type);
		}
	}
	
	public static final JointType getValueOf(String value) {
		return lookup.get(value);
	}
		
	private JointType(final String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
