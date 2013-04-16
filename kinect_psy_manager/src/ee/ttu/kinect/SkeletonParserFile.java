package ee.ttu.kinect;

import java.util.Arrays;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.Joint;
import ee.ttu.kinect.model.JointIndexInFile;
import ee.ttu.kinect.model.JointType;


public class SkeletonParserFile implements SkeletonParser {
	
	@Override
	public void parseSkeleton(String input, Body body) {
		if (input.length() > 50) {
			String jointCoords[] = input.split("\\s+");
			try {
				Double.parseDouble(jointCoords[2]);
			} catch(NumberFormatException e) {
				return; // not a row with coords
			}
			
			body.setTimestamp(Long.parseLong(jointCoords[0]));
			body.setFrameNumber(Long.parseLong(jointCoords[1]));
			
			// Joints coords starting from third column
			jointCoords = Arrays.copyOfRange(jointCoords, 2, (2 + 3 * JointIndexInFile.values().length)); // markers are ignored
			int jointsCount = 0;
			double coordX = 0;
			double coordY = 0;
			double coordZ = 0;
			for (int i = 0; i < jointCoords.length; i += 3) {
				coordX = Double.parseDouble(jointCoords[i]);
				coordY = Double.parseDouble(jointCoords[i + 1]);
				coordZ = Double.parseDouble(jointCoords[i + 2]);
				parseJoint(body, JointIndexInFile.getValueOf(jointsCount), coordX, coordY, coordZ);
				jointsCount++;
			}
			
			// trigger BodyUpdated action
			body.updated();
		}
	}
	
	private void parseJoint(Body body, JointIndexInFile index, double positionX, double positionY, double positionZ) {
		Joint joint = new Joint();
		joint.setPositionX(positionX);
		joint.setPositionY(positionY);
		joint.setPositionZ(positionZ);
		switch (index) {
		case ANKLE_LEFT:
			joint.setType(JointType.ANKLE_LEFT);
			body.setAnkleLeft(joint);
			break;
		case ANKLE_RIGHT: 
			joint.setType(JointType.ANKLE_RIGHT);
			body.setAnkleRight(joint);
			break;
			
		case ELBOW_LEFT: 
			joint.setType(JointType.ELBOW_LEFT);
			body.setElbowLeft(joint);
			break;
		case ELBOW_RIGHT: 
			joint.setType(JointType.ELBOW_RIGHT);
			body.setElbowRight(joint);
			break;
		case FOOT_LEFT: 
			joint.setType(JointType.FOOT_LEFT);
			body.setFootLeft(joint);
			break;
		case FOOT_RIGHT: 
			joint.setType(JointType.FOOT_RIGHT);
			body.setFootRight(joint);
			break;
		case HAND_LEFT: 
			joint.setType(JointType.HAND_LEFT);
			body.setHandLeft(joint);
			break;
		case HAND_RIGHT: 
			joint.setType(JointType.HAND_RIGHT);
			body.setHandRight(joint);
			break;
		case HEAD: 
			joint.setType(JointType.HEAD);
			body.setHead(joint);
			break;
		case HIP_CENTER: 
			joint.setType(JointType.HIP_CENTER);
			body.setHipCenter(joint);
			break;
		case HIP_LEFT: 
			joint.setType(JointType.HIP_LEFT);
			body.setHipLeft(joint);
			break;
		case HIP_RIGHT: 
			joint.setType(JointType.HIP_RIGHT);
			body.setHipRight(joint);
			break;
		case KNEE_LEFT: 
			joint.setType(JointType.KNEE_LEFT);
			body.setKneeLeft(joint);
			break;
		case KNEE_RIGHT: 
			joint.setType(JointType.KNEE_RIGHT);
			body.setKneeRight(joint);
			break;
		case SHOULDER_CENTER: 
			joint.setType(JointType.SHOULDER_CENTER);
			body.setShoulderCenter(joint);
			break;
		case SHOULDER_LEFT: 
			joint.setType(JointType.SHOULDER_LEFT);
			body.setShoulderLeft(joint);
			break;
		case SHOULDER_RIGHT: 
			joint.setType(JointType.SHOULDER_RIGHT);
			body.setShoulderRight(joint);
			break;
		case SPINE: 
			joint.setType(JointType.SPINE);
			body.setSpine(joint);
			break;
		case WRIST_LEFT: 
			joint.setType(JointType.WRIST_LEFT);
			body.setWristLeft(joint);
			break;
		case WRIST_RIGHT: 
			joint.setType(JointType.WRIST_RIGHT);
			body.setWristRight(joint);
			break;
		}
		
	}


}
