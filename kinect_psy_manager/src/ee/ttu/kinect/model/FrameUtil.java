package ee.ttu.kinect.model;

public class FrameUtil {

	public static String getHeader(boolean seatedMode, int markersAmount) {
		StringBuffer line = new StringBuffer();
		line = line.append("FrameId\t");
		line = line.append("Timestamp\t");
		
		line = line.append(Joint.getHeader(JointType.HEAD));
		line = line.append(Joint.getHeader(JointType.SHOULDER_CENTER));
		if (!seatedMode) {
			line = line.append(Joint.getHeader(JointType.SPINE));
			line = line.append(Joint.getHeader(JointType.HIP_CENTER));
		}
		line = line.append(Joint.getHeader(JointType.SHOULDER_LEFT));
		line = line.append(Joint.getHeader(JointType.ELBOW_LEFT));
		line = line.append(Joint.getHeader(JointType.WRIST_LEFT));
		line = line.append(Joint.getHeader(JointType.HAND_LEFT));
		line = line.append(Joint.getHeader(JointType.SHOULDER_RIGHT));
		line = line.append(Joint.getHeader(JointType.ELBOW_RIGHT));
		line = line.append(Joint.getHeader(JointType.WRIST_RIGHT));
		line = line.append(Joint.getHeader(JointType.HAND_RIGHT));
		if (!seatedMode) {
			line = line.append(Joint.getHeader(JointType.HIP_LEFT));
			line = line.append(Joint.getHeader(JointType.KNEE_LEFT));
			line = line.append(Joint.getHeader(JointType.ANKLE_LEFT));
			line = line.append(Joint.getHeader(JointType.FOOT_LEFT));
			line = line.append(Joint.getHeader(JointType.HIP_RIGHT));
			line = line.append(Joint.getHeader(JointType.KNEE_RIGHT));
			line = line.append(Joint.getHeader(JointType.ANKLE_RIGHT));
			line = line.append(Joint.getHeader(JointType.FOOT_RIGHT));
		}
		
		// Markers
		for (int i = 1; i <= markersAmount; i++) {
			line = line.append("Marker").append(i).append("\t");
		}
		
		return line.toString();
	}
	
	public static String getData(Frame frame, boolean seatedMode, boolean[] markersState) {
		return getJoints(frame, seatedMode) + getMarkers(markersState);
	}
	
	private static String getJoints(Frame frame, boolean seatedMode) {
		StringBuffer line = new StringBuffer();
		if (frame.isFrameReady()) {
			line = line.append(frame.getFrameNumber()).append("\t");
			line = line.append((frame.getTimestamp() - frame.getVeryFirstTimestamp())).append("\t");
			
			line = line.append(frame.getHead().toString());
			line = line.append(frame.getShoulderCenter().toString());
			if (!seatedMode) {
				line = line.append(frame.getSpine().toString());
				line = line.append(frame.getHipCenter().toString());
			}
			line = line.append(frame.getShoulderLeft().toString());
			line = line.append(frame.getElbowLeft().toString());
			line = line.append(frame.getWristLeft().toString());
			line = line.append(frame.getHandLeft().toString());
			line = line.append(frame.getShoulderRight().toString());
			line = line.append(frame.getElbowRight().toString());
			line = line.append(frame.getWristRight().toString());
			line = line.append(frame.getHandRight().toString());
			if (!seatedMode) {
				line = line.append(frame.getHipLeft().toString());
				line = line.append(frame.getKneeLeft().toString());
				line = line.append(frame.getAnkleLeft().toString());
				line = line.append(frame.getFootLeft().toString());
				line = line.append(frame.getHipRight().toString());
				line = line.append(frame.getKneeRight().toString());
				line = line.append(frame.getAnkleRight().toString());
				line = line.append(frame.getFootRight().toString());
			}
		}
		
		return line.toString();
	}

	private static String getMarkers(boolean[] markersState) {
		StringBuffer markers = new StringBuffer();
		for (boolean ms : markersState) {
			markers = markers.append((ms ? 1 : 0)).append("\t");
		}

		return markers.toString();
	}
	
}
