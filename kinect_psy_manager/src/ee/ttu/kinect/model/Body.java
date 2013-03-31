package ee.ttu.kinect.model;



public class Body {

	private Long frameNumber;
	private Long timestamp;
	private Long veryFirstTimestamp;
	
	private Joint head;
	private Joint hipCenter;
	private Joint spine;
	private Joint shoulderCenter;
	private Joint shoulderLeft;
	private Joint elbowLeft;
	private Joint wristLeft;
	private Joint handLeft;
	private Joint shoulderRight;
	private Joint elbowRight;
	private Joint wristRight;
	private Joint handRight;
	private Joint hipLeft;
	private Joint kneeLeft;
	private Joint ankleLeft;
	private Joint footLeft;
	private Joint hipRight;
	private Joint kneeRight;
	private Joint ankleRight;
	private Joint footRight;

	public Long getFrameNumber() {
		return frameNumber;
	}
	
	public void setFrameNumber(Long frameNumber) {
		this.frameNumber = frameNumber;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Long timestamp) {
		if (this.timestamp == null) {
			this.veryFirstTimestamp = timestamp;
		}
		this.timestamp = timestamp;
	}
	
	public Joint getHead() {
		return head;
	}

	public void setHead(Joint head) {
		this.head = head;
	}

	public Joint getHipCenter() {
		return hipCenter;
	}

	public void setHipCenter(Joint hipCenter) {
		this.hipCenter = hipCenter;
	}

	public Joint getSpine() {
		return spine;
	}

	public void setSpine(Joint spine) {
		this.spine = spine;
	}

	public Joint getShoulderCenter() {
		return shoulderCenter;
	}

	public void setShoulderCenter(Joint shoulderCenter) {
		this.shoulderCenter = shoulderCenter;
	}

	public Joint getShoulderLeft() {
		return shoulderLeft;
	}

	public void setShoulderLeft(Joint shoulderLeft) {
		this.shoulderLeft = shoulderLeft;
	}

	public Joint getElbowLeft() {
		return elbowLeft;
	}

	public void setElbowLeft(Joint elbowLeft) {
		this.elbowLeft = elbowLeft;
	}

	public Joint getWristLeft() {
		return wristLeft;
	}

	public void setWristLeft(Joint wristLeft) {
		this.wristLeft = wristLeft;
	}

	public Joint getHandLeft() {
		return handLeft;
	}

	public void setHandLeft(Joint handLeft) {
		this.handLeft = handLeft;
	}

	public Joint getShoulderRight() {
		return shoulderRight;
	}

	public void setShoulderRight(Joint shoulderRight) {
		this.shoulderRight = shoulderRight;
	}

	public Joint getElbowRight() {
		return elbowRight;
	}

	public void setElbowRight(Joint elbowRight) {
		this.elbowRight = elbowRight;
	}

	public Joint getWristRight() {
		return wristRight;
	}

	public void setWristRight(Joint wristRight) {
		this.wristRight = wristRight;
	}

	public Joint getHandRight() {
		return handRight;
	}

	public void setHandRight(Joint handRight) {
		this.handRight = handRight;
	}

	public Joint getHipLeft() {
		return hipLeft;
	}

	public void setHipLeft(Joint hipLeft) {
		this.hipLeft = hipLeft;
	}

	public Joint getKneeLeft() {
		return kneeLeft;
	}

	public void setKneeLeft(Joint kneeLeft) {
		this.kneeLeft = kneeLeft;
	}

	public Joint getAnkleLeft() {
		return ankleLeft;
	}

	public void setAnkleLeft(Joint ankleLeft) {
		this.ankleLeft = ankleLeft;
	}

	public Joint getFootLeft() {
		return footLeft;
	}

	public void setFootLeft(Joint footLeft) {
		this.footLeft = footLeft;
	}

	public Joint getHipRight() {
		return hipRight;
	}

	public void setHipRight(Joint hipRight) {
		this.hipRight = hipRight;
	}

	public Joint getKneeRight() {
		return kneeRight;
	}

	public void setKneeRight(Joint kneeRight) {
		this.kneeRight = kneeRight;
	}

	public Joint getAnkleRight() {
		return ankleRight;
	}

	public void setAnkleRight(Joint ankleRight) {
		this.ankleRight = ankleRight;
	}

	public Joint getFootRight() {
		return footRight;
	}

	public void setFootRight(Joint footRight) {
		this.footRight = footRight;
	}

	public boolean isBodyReady() {
		return ankleLeft != null && ankleRight != null && elbowLeft != null
				&& elbowRight != null && footLeft != null && footRight != null
				&& handLeft != null && handRight != null && head != null
				&& hipCenter != null && hipLeft != null && hipRight != null
				&& kneeLeft != null && kneeRight != null
				&& shoulderCenter != null && shoulderLeft != null
				&& shoulderRight != null && spine != null && wristLeft != null
				&& wristRight != null;
	}
	
	public static String getHeader() {
		StringBuffer line = new StringBuffer();
		line = line.append("FrameId").append("\t");
		line = line.append("Timestamp").append("\t");
		
		line = line.append(Joint.getHeader(JointType.HEAD));
		line = line.append(Joint.getHeader(JointType.SHOULDER_CENTER));
		line = line.append(Joint.getHeader(JointType.SPINE));
		line = line.append(Joint.getHeader(JointType.HIP_CENTER));
		line = line.append(Joint.getHeader(JointType.SHOULDER_LEFT));
		line = line.append(Joint.getHeader(JointType.ELBOW_LEFT));
		line = line.append(Joint.getHeader(JointType.WRIST_LEFT));
		line = line.append(Joint.getHeader(JointType.HAND_LEFT));
		line = line.append(Joint.getHeader(JointType.SHOULDER_RIGHT));
		line = line.append(Joint.getHeader(JointType.ELBOW_RIGHT));
		line = line.append(Joint.getHeader(JointType.WRIST_RIGHT));
		line = line.append(Joint.getHeader(JointType.HAND_RIGHT));
		line = line.append(Joint.getHeader(JointType.HIP_LEFT));
		line = line.append(Joint.getHeader(JointType.KNEE_LEFT));
		line = line.append(Joint.getHeader(JointType.ANKLE_LEFT));
		line = line.append(Joint.getHeader(JointType.FOOT_LEFT));
		line = line.append(Joint.getHeader(JointType.HIP_RIGHT));
		line = line.append(Joint.getHeader(JointType.KNEE_RIGHT));
		line = line.append(Joint.getHeader(JointType.ANKLE_RIGHT));
		line = line.append(Joint.getHeader(JointType.FOOT_RIGHT));
		
		// Markers
		line = line.append("Marker1\t");
		line = line.append("Marker2\t");
		line = line.append("Marker3\t");
		line = line.append("Marker4\t");
		line = line.append("Marker5\t");
		
		return line.toString();
	}
	
	@Override
	public String toString() {
		StringBuffer line = new StringBuffer();
		if (isBodyReady()) {
			line = line.append(frameNumber).append("\t");
			line = line.append((timestamp - veryFirstTimestamp)).append("\t");
			
			line = line.append(head.toString());
			line = line.append(shoulderCenter.toString());
			line = line.append(spine.toString());
			line = line.append(hipCenter.toString());
			line = line.append(shoulderLeft.toString());
			line = line.append(elbowLeft.toString());
			line = line.append(wristLeft.toString());
			line = line.append(handLeft.toString());
			line = line.append(shoulderRight.toString());
			line = line.append(elbowRight.toString());
			line = line.append(wristRight.toString());
			line = line.append(handRight.toString());
			line = line.append(hipLeft.toString());
			line = line.append(kneeLeft.toString());
			line = line.append(ankleLeft.toString());
			line = line.append(footLeft.toString());
			line = line.append(hipRight.toString());
			line = line.append(kneeRight.toString());
			line = line.append(ankleRight.toString());
			line = line.append(footRight.toString());
		}
		
		return line.toString();
	}
	
}
