package ee.ttu.kinect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.Joint;
import ee.ttu.kinect.model.JointType;


public class SkeletonParserFile implements SkeletonParser {
	
	private int jointsAmount = 0;
	
	private List<JointType> jointTypes = new ArrayList<JointType>();
	
	@Override
	public synchronized void parseSkeleton(String input, Body body) {
		if (input.length() > 50) {
			String jointCoords[] = input.split("\\s+");
			try {
				Double.parseDouble(jointCoords[2]);
			} catch(NumberFormatException e) {
				// We are dealing with header(not a row with coords)
				jointsAmount = (jointCoords.length - 2 - 5) / 3; // -FrameId -Timestamp -5x markers
				jointTypes = parseJointTypes(input, jointsAmount);
				return;
			}
			
			body.setFrameNumber(Long.parseLong(jointCoords[0]));
			body.setTimestamp(Long.parseLong(jointCoords[1]));
			
			// Joints coords starting from third column
			jointCoords = Arrays.copyOfRange(jointCoords, 2, (2 + 3 * jointsAmount)); // markers are ignored
			int jointCounter = 0;
			double coordX = 0;
			double coordY = 0;
			double coordZ = 0;
			for (int i = 0; i < jointCoords.length; i += 3) {
				coordX = Double.parseDouble(jointCoords[i]);
				coordY = Double.parseDouble(jointCoords[i + 1]);
				coordZ = Double.parseDouble(jointCoords[i + 2]);
				parseJoint(body, jointTypes.get(jointCounter), coordX, coordY, coordZ);
				jointCounter++;
			}
		}
	}

	private void parseJoint(Body body, JointType type, double positionX, double positionY, double positionZ) {
		Joint joint = new Joint();
		joint.setPositionX(positionX);
		joint.setPositionY(positionY);
		joint.setPositionZ(positionZ);
		joint.setType(type);
		switch (type) {
		case ANKLE_LEFT:
			body.setAnkleLeft(joint);
			break;
		case ANKLE_RIGHT: 
			body.setAnkleRight(joint);
			break;
		case ELBOW_LEFT: 
			body.setElbowLeft(joint);
			break;
		case ELBOW_RIGHT: 
			body.setElbowRight(joint);
			break;
		case FOOT_LEFT: 
			body.setFootLeft(joint);
			break;
		case FOOT_RIGHT: 
			body.setFootRight(joint);
			break;
		case HAND_LEFT: 
			body.setHandLeft(joint);
			break;
		case HAND_RIGHT: 
			body.setHandRight(joint);
			break;
		case HEAD: 
			body.setHead(joint);
			break;
		case HIP_CENTER: 
			body.setHipCenter(joint);
			break;
		case HIP_LEFT: 
			body.setHipLeft(joint);
			break;
		case HIP_RIGHT: 
			body.setHipRight(joint);
			break;
		case KNEE_LEFT: 
			body.setKneeLeft(joint);
			break;
		case KNEE_RIGHT: 
			body.setKneeRight(joint);
			break;
		case SHOULDER_CENTER: 
			body.setShoulderCenter(joint);
			break;
		case SHOULDER_LEFT: 
			body.setShoulderLeft(joint);
			break;
		case SHOULDER_RIGHT: 
			body.setShoulderRight(joint);
			break;
		case SPINE: 
			body.setSpine(joint);
			break;
		case WRIST_LEFT: 
			body.setWristLeft(joint);
			break;
		case WRIST_RIGHT: 
			body.setWristRight(joint);
			break;
		}
	}

	private List<JointType> parseJointTypes(String input, int typesAmount) {
		List<JointType> types = new ArrayList<JointType>();
		String typesArr[] = input.split("\\s+");
		typesArr = Arrays.copyOfRange(typesArr, 2, (2 + 3 * typesAmount));
		for (int i = 0; i < typesArr.length; i+=3) {
			String typeHeader = typesArr[i].substring(0, typesArr[i].length() - 1);
			types.add(JointType.getValueOf(typeHeader));
		}
		
		return types;
	}

	public boolean isSeatedMode() {
		return jointTypes.size() == 10;
	}
	
}
