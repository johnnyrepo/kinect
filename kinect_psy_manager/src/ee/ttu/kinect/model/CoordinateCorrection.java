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
	public void calculateStandingCorrection(Body body) {		
		for (JointType type : JointType.values()) {
			if (type != JointType.SPINE && type != JointType.HEAD) {
				double correctionZ = 0;
				switch (type) {
					case ANKLE_LEFT: 
						correctionZ = body.getJoint(JointType.KNEE_LEFT).getPositionZ() - body.getJoint(type).getPositionZ();
						break;
					case ANKLE_RIGHT:
						correctionZ = body.getJoint(JointType.KNEE_RIGHT).getPositionZ() - body.getJoint(type).getPositionZ();
						break;
//					case FOOT_LEFT: 
//						correctionZ = body.getJoint(JointType.KNEE_LEFT).getPositionZ() - body.getJoint(type).getPositionZ();
//						break;
//					case FOOT_RIGHT:
//						correctionZ = body.getJoint(JointType.KNEE_RIGHT).getPositionZ() - body.getJoint(type).getPositionZ();
//						break;
					default:
						correctionZ = body.getJoint(JointType.SPINE).getPositionZ() - body.getJoint(type).getPositionZ();
						break;
				}
				this.correctionsZ.put(type, correctionZ);
			}
		}
	}
	
	// joint coordinates corrections for "sitting" body
	public void calculateSittingCorrection(Body body) {
		for (JointType type : JointType.values()) {
			double correctionY = 0;
			switch (type) {
				case KNEE_LEFT:
					correctionY = body.getJoint(JointType.HIP_LEFT).getPositionY() - body.getJoint(type).getPositionY();
					break;
				case KNEE_RIGHT:
					correctionY = body.getJoint(JointType.HIP_RIGHT).getPositionY() - body.getJoint(type).getPositionY();
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
