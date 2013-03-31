package ee.ttu.kinect.model;

public enum JointIndexInFile {
	
	HEAD, 
	SHOULDER_CENTER, 
	SPINE, 
	HIP_CENTER, 
	SHOULDER_LEFT, 
	ELBOW_LEFT, 
	WRIST_LEFT, 
	HAND_LEFT, 
	SHOULDER_RIGHT, 
	ELBOW_RIGHT, 
	WRIST_RIGHT, 
	HAND_RIGHT, 
	HIP_LEFT, 
	KNEE_LEFT, 
	ANKLE_LEFT, 
	FOOT_LEFT, 
	HIP_RIGHT, 
	KNEE_RIGHT, 
	ANKLE_RIGHT, 
	FOOT_RIGHT;
	
	public static final JointIndexInFile getValueOf(int index) {
		for (JointIndexInFile jointIndex : JointIndexInFile.values()) {
			if (jointIndex.ordinal() == index) {
				return jointIndex;
			}
		}
		
		return null;
	}
	
}
