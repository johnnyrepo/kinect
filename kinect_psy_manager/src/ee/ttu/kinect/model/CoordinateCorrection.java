package ee.ttu.kinect.model;

import java.util.HashMap;
import java.util.Map;

public class CoordinateCorrection {

	private Map<JointType, Double> correctionsY;

	private Map<JointType, Double> correctionsZ;
	
	public CoordinateCorrection() {
		this.correctionsY = new HashMap<JointType, Double>();
		this.correctionsZ = new HashMap<JointType, Double>();
	}
	
	// joint coordinates corrections for "standing" body
	public void calculateStandingCorrection(Frame frame) {		
		for (JointType type : JointType.values()) {
			if (type != JointType.SPINE && type != JointType.HEAD) {
				double correctionZ = 0;
				switch (type) {
					case ANKLE_LEFT: 
						correctionZ = frame.getJoint(JointType.KNEE_LEFT).getPositionZ() - frame.getJoint(type).getPositionZ();
						break;
					case ANKLE_RIGHT:
						correctionZ = frame.getJoint(JointType.KNEE_RIGHT).getPositionZ() - frame.getJoint(type).getPositionZ();
						break;
//					case FOOT_LEFT: 
//						correctionZ = frame.getJoint(JointType.KNEE_LEFT).getPositionZ() - frame.getJoint(type).getPositionZ();
//						break;
//					case FOOT_RIGHT:
//						correctionZ = frame.getJoint(JointType.KNEE_RIGHT).getPositionZ() - frame.getJoint(type).getPositionZ();
//						break;
					default:
						correctionZ = frame.getJoint(JointType.SPINE).getPositionZ() - frame.getJoint(type).getPositionZ();
						break;
				}
				this.correctionsZ.put(type, correctionZ);
			}
		}
	}
	
	// joint coordinates corrections for "sitting" body
	public void calculateSittingCorrection(Frame frame) {
		for (JointType type : JointType.values()) {
			double correctionY = 0;
			switch (type) {
				case KNEE_LEFT:
					correctionY = frame.getJoint(JointType.HIP_LEFT).getPositionY() - frame.getJoint(type).getPositionY();
					break;
				case KNEE_RIGHT:
					correctionY = frame.getJoint(JointType.HIP_RIGHT).getPositionY() - frame.getJoint(type).getPositionY();
					break;
				default:
					break;
			}
			this.correctionsY.put(type, correctionY);
		}
	}
	
	public void turnStandingCorrectionOff() {
		this.correctionsZ = new HashMap<JointType, Double>();
	}
	
	public void turnSittingCorrectionOff() {
		this.correctionsY = new HashMap<JointType, Double>();
	}
	
	public Map<JointType, Double> getCorrectionsY() {
		return this.correctionsY;
	}

	public Map<JointType, Double> getCorrectionsZ() {
		return this.correctionsZ;
	}
	
	public boolean areCorrectionsZOn() {
		return !this.correctionsZ.isEmpty();
	}
	
	public boolean areCorrectionsYOn() {
		return !this.correctionsY.isEmpty();
	}

}
