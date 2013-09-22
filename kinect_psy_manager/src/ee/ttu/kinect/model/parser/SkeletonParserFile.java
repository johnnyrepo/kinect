package ee.ttu.kinect.model.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ee.ttu.kinect.model.Frame;
import ee.ttu.kinect.model.Joint;
import ee.ttu.kinect.model.JointType;


public class SkeletonParserFile implements SkeletonParser {
	
	private int jointsAmount = 0;
	
	private int markersAmount = 0;
	
	private List<JointType> jointTypes = new ArrayList<JointType>();
	
	@Override
	public synchronized void parseSkeleton(String input, Frame frame) {
		if (input.length() > 50) {
			String[] data = input.split("\\s+");
			if (!isDataString(data)) {
				// We are dealing with header(not a row with coords)
				markersAmount = countMarkers(data);
				jointsAmount = (data.length - 2 - markersAmount) / 3; // -FrameId -Timestamp - markers
				jointTypes = parseJointTypes(input, jointsAmount);
				return;
			}
			
			frame.setFrameNumber(Long.parseLong(data[0]));
			frame.setTimestamp(Long.parseLong(data[1]));
			
			// Joints coords starting from third column
			String[] joints = Arrays.copyOfRange(data, 2, (data.length - markersAmount)); // markers are ignored
			int jointCounter = 0;
			double coordX = 0;
			double coordY = 0;
			double coordZ = 0;
			for (int i = 0; i < joints.length; i += 3) {
				coordX = Double.parseDouble(joints[i]);
				coordY = Double.parseDouble(joints[i + 1]);
				coordZ = Double.parseDouble(joints[i + 2]);
				parseJoint(frame, jointTypes.get(jointCounter), coordX, coordY, coordZ);
				jointCounter++;
			}
			
			if (markersAmount > 0) {
				// markers are taken
				String[] markers = Arrays.copyOfRange(data, (2 + 3 * jointsAmount), data.length);
				parseMarkers(markers, frame);
			}
		}
	}
	
	private void parseMarkers(String[] data, Frame frame) {
		// Parsing markers state
		boolean[] markersBool = new boolean[data.length];
		for (int i = 0; i < data.length; i++) {
			markersBool[i] = Integer.parseInt(data[i]) == 1 ? true : false;
		}
		frame.setMarkersState(markersBool);
	}
	
	public void reset() {
		jointsAmount = 0;
		markersAmount = 0;
		jointTypes = new ArrayList<JointType>();
	}
	
	private boolean isDataString(String[] data) {
		try {
			Double.parseDouble(data[2]);
		} catch(Exception e) {
			// We are dealing with header(not a row with coords)
			return false;
		}
		return true;
	}

	private int countMarkers(String[] data) {
		int counter = 0;
		for (String d : data) {
			if (d.startsWith("Marker")) {
				counter++;
			}
		}
		return counter;
	}
	
	private void parseJoint(Frame frame, JointType type, double positionX, double positionY, double positionZ) {
		Joint joint = new Joint();
		joint.setPositionX(positionX);
		joint.setPositionY(positionY);
		joint.setPositionZ(positionZ);
		joint.setType(type);
		switch (type) {
		case ANKLE_LEFT:
			frame.setAnkleLeft(joint);
			break;
		case ANKLE_RIGHT: 
			frame.setAnkleRight(joint);
			break;
		case ELBOW_LEFT: 
			frame.setElbowLeft(joint);
			break;
		case ELBOW_RIGHT: 
			frame.setElbowRight(joint);
			break;
		case FOOT_LEFT: 
			frame.setFootLeft(joint);
			break;
		case FOOT_RIGHT: 
			frame.setFootRight(joint);
			break;
		case HAND_LEFT: 
			frame.setHandLeft(joint);
			break;
		case HAND_RIGHT: 
			frame.setHandRight(joint);
			break;
		case HEAD: 
			frame.setHead(joint);
			break;
		case HIP_CENTER: 
			frame.setHipCenter(joint);
			break;
		case HIP_LEFT: 
			frame.setHipLeft(joint);
			break;
		case HIP_RIGHT: 
			frame.setHipRight(joint);
			break;
		case KNEE_LEFT: 
			frame.setKneeLeft(joint);
			break;
		case KNEE_RIGHT: 
			frame.setKneeRight(joint);
			break;
		case SHOULDER_CENTER: 
			frame.setShoulderCenter(joint);
			break;
		case SHOULDER_LEFT: 
			frame.setShoulderLeft(joint);
			break;
		case SHOULDER_RIGHT: 
			frame.setShoulderRight(joint);
			break;
		case SPINE: 
			frame.setSpine(joint);
			break;
		case WRIST_LEFT: 
			frame.setWristLeft(joint);
			break;
		case WRIST_RIGHT: 
			frame.setWristRight(joint);
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
