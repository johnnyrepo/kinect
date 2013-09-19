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
	
	public static String getData(Body body, boolean seatedMode, boolean[] markersState) {
		return getJoints(body, seatedMode) + getMarkers(markersState);
	}
	
	private static String getJoints(Body body, boolean seatedMode) {
		StringBuffer line = new StringBuffer();
		if (body.isBodyReady()) {
			line = line.append(body.getFrameNumber()).append("\t");
			line = line.append((body.getTimestamp() - body.getVeryFirstTimestamp())).append("\t");
			
			line = line.append(body.getHead().toString());
			line = line.append(body.getShoulderCenter().toString());
			if (!seatedMode) {
				line = line.append(body.getSpine().toString());
				line = line.append(body.getHipCenter().toString());
			}
			line = line.append(body.getShoulderLeft().toString());
			line = line.append(body.getElbowLeft().toString());
			line = line.append(body.getWristLeft().toString());
			line = line.append(body.getHandLeft().toString());
			line = line.append(body.getShoulderRight().toString());
			line = line.append(body.getElbowRight().toString());
			line = line.append(body.getWristRight().toString());
			line = line.append(body.getHandRight().toString());
			if (!seatedMode) {
				line = line.append(body.getHipLeft().toString());
				line = line.append(body.getKneeLeft().toString());
				line = line.append(body.getAnkleLeft().toString());
				line = line.append(body.getFootLeft().toString());
				line = line.append(body.getHipRight().toString());
				line = line.append(body.getKneeRight().toString());
				line = line.append(body.getAnkleRight().toString());
				line = line.append(body.getFootRight().toString());
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
