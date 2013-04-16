package ee.ttu.kinect.model;



public class Body implements Cloneable {

	private Body oldBody;
	
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
	
	private double headXVelocity;
	private double headYVelocity;
	private double headZVelocity;
	
	private double hipCenterXVelocity;
	private double hipCenterYVelocity;
	private double hipCenterZVelocity;
	
	private double spineXVelocity;
	private double spineYVelocity;
	private double spineZVelocity;
	
	private double shoulderCenterXVelocity;
	private double shoulderCenterYVelocity;
	private double shoulderCenterZVelocity;
	
	private double shoulderLeftXVelocity;
	private double shoulderLeftYVelocity;
	private double shoulderLeftZVelocity;
	
	private double elbowLeftXVelocity;
	private double elbowLeftYVelocity;
	private double elbowLeftZVelocity;
	
	private double wristLeftXVelocity;
	private double wristLeftYVelocity;
	private double wristLeftZVelocity;
	
	private double handLeftXVelocity;
	private double handLeftYVelocity;
	private double handLeftZVelocity;
	
	private double shoulderRightXVelocity;
	private double shoulderRightYVelocity;
	private double shoulderRightZVelocity;
	
	private double elbowRightXVelocity;
	private double elbowRightYVelocity;
	private double elbowRightZVelocity;
	
	private double wristRightXVelocity;
	private double wristRightYVelocity;
	private double wristRightZVelocity;
	
	private double handRightXVelocity;
	private double handRightYVelocity;
	private double handRightZVelocity;
	
	private double hipLeftXVelocity;
	private double hipLeftYVelocity;
	private double hipLeftZVelocity;
	
	private double kneeLeftXVelocity;
	private double kneeLeftYVelocity;
	private double kneeLeftZVelocity;
	
	private double ankleLeftXVelocity;
	private double ankleLeftYVelocity;
	private double ankleLeftZVelocity;
	
	private double footLeftXVelocity;
	private double footLeftYVelocity;
	private double footLeftZVelocity;
	
	private double hipRightXVelocity;
	private double hipRightYVelocity;
	private double hipRightZVelocity;
	
	private double kneeRightXVelocity;
	private double kneeRightYVelocity;
	private double kneeRightZVelocity;
	
	private double ankleRightXVelocity;
	private double ankleRightYVelocity;
	private double ankleRightZVelocity;
	
	private double footRightXVelocity;
	private double footRightYVelocity;
	private double footRightZVelocity;

	public void updated() {
		if (oldBody != null) {
			headXVelocity = calculateVelocity(head.getPositionX(), oldBody.head.getPositionX(), timestamp, oldBody.getTimestamp());
			headYVelocity = calculateVelocity(head.getPositionY(), oldBody.head.getPositionY(), timestamp, oldBody.getTimestamp());
			headZVelocity = calculateVelocity(head.getPositionZ(), oldBody.head.getPositionZ(), timestamp, oldBody.getTimestamp());
			// shoulderCenter
			shoulderCenterXVelocity = calculateVelocity(shoulderCenter.getPositionX(), oldBody.shoulderCenter.getPositionX(), timestamp, oldBody.getTimestamp());
			shoulderCenterYVelocity = calculateVelocity(shoulderCenter.getPositionY(), oldBody.shoulderCenter.getPositionY(), timestamp, oldBody.getTimestamp());
			shoulderCenterZVelocity = calculateVelocity(shoulderCenter.getPositionZ(), oldBody.shoulderCenter.getPositionZ(), timestamp, oldBody.getTimestamp());
			// spine
			spineXVelocity = calculateVelocity(spine.getPositionX(), oldBody.spine.getPositionX(), timestamp, oldBody.getTimestamp());
			spineYVelocity = calculateVelocity(spine.getPositionY(), oldBody.spine.getPositionY(), timestamp, oldBody.getTimestamp());
			spineZVelocity = calculateVelocity(spine.getPositionZ(), oldBody.spine.getPositionZ(), timestamp, oldBody.getTimestamp());
			// hipCenter
			hipCenterXVelocity = calculateVelocity(hipCenter.getPositionX(), oldBody.hipCenter.getPositionX(), timestamp, oldBody.getTimestamp());
			hipCenterYVelocity = calculateVelocity(hipCenter.getPositionY(), oldBody.hipCenter.getPositionY(), timestamp, oldBody.getTimestamp());
			hipCenterZVelocity = calculateVelocity(hipCenter.getPositionZ(), oldBody.hipCenter.getPositionZ(), timestamp, oldBody.getTimestamp());
			//shoulderLeft
			shoulderLeftXVelocity = calculateVelocity(shoulderLeft.getPositionX(), oldBody.shoulderLeft.getPositionX(), timestamp, oldBody.getTimestamp());
			shoulderLeftYVelocity = calculateVelocity(shoulderLeft.getPositionY(), oldBody.shoulderLeft.getPositionY(), timestamp, oldBody.getTimestamp());
			shoulderLeftZVelocity = calculateVelocity(shoulderLeft.getPositionZ(), oldBody.shoulderLeft.getPositionZ(), timestamp, oldBody.getTimestamp());
			// elbowLeft
			elbowLeftXVelocity = calculateVelocity(elbowLeft.getPositionX(), oldBody.elbowLeft.getPositionX(), timestamp, oldBody.getTimestamp());
			elbowLeftYVelocity = calculateVelocity(elbowLeft.getPositionY(), oldBody.elbowLeft.getPositionY(), timestamp, oldBody.getTimestamp());
			elbowLeftZVelocity = calculateVelocity(elbowLeft.getPositionZ(), oldBody.elbowLeft.getPositionZ(), timestamp, oldBody.getTimestamp());
			// wristLeft
			wristLeftXVelocity = calculateVelocity(wristLeft.getPositionX(), oldBody.wristLeft.getPositionX(), timestamp, oldBody.getTimestamp());
			wristLeftYVelocity = calculateVelocity(wristLeft.getPositionY(), oldBody.wristLeft.getPositionY(), timestamp, oldBody.getTimestamp());
			wristLeftZVelocity = calculateVelocity(wristLeft.getPositionZ(), oldBody.wristLeft.getPositionZ(), timestamp, oldBody.getTimestamp());
			// handLeft
			handLeftXVelocity = calculateVelocity(handLeft.getPositionX(), oldBody.handLeft.getPositionX(), timestamp, oldBody.getTimestamp());
			handLeftYVelocity = calculateVelocity(handLeft.getPositionY(), oldBody.handLeft.getPositionY(), timestamp, oldBody.getTimestamp());
			handLeftZVelocity = calculateVelocity(handLeft.getPositionZ(), oldBody.handLeft.getPositionZ(), timestamp, oldBody.getTimestamp());
			// shoulderRight
			shoulderRightXVelocity = calculateVelocity(shoulderRight.getPositionX(), oldBody.shoulderRight.getPositionX(), timestamp, oldBody.getTimestamp());
			shoulderRightYVelocity = calculateVelocity(shoulderRight.getPositionY(), oldBody.shoulderRight.getPositionY(), timestamp, oldBody.getTimestamp());
			shoulderRightZVelocity = calculateVelocity(shoulderRight.getPositionZ(), oldBody.shoulderRight.getPositionZ(), timestamp, oldBody.getTimestamp());
			// elbowRight
			elbowRightXVelocity = calculateVelocity(elbowRight.getPositionX(), oldBody.elbowRight.getPositionX(), timestamp, oldBody.getTimestamp());
			elbowRightYVelocity = calculateVelocity(elbowRight.getPositionY(), oldBody.elbowRight.getPositionY(), timestamp, oldBody.getTimestamp());
			elbowRightZVelocity = calculateVelocity(elbowRight.getPositionZ(), oldBody.elbowRight.getPositionZ(), timestamp, oldBody.getTimestamp());
			// wristRight
			wristRightXVelocity = calculateVelocity(wristRight.getPositionX(), oldBody.wristRight.getPositionX(), timestamp, oldBody.getTimestamp());
			wristRightYVelocity = calculateVelocity(wristRight.getPositionY(), oldBody.wristRight.getPositionY(), timestamp, oldBody.getTimestamp());
			wristRightZVelocity = calculateVelocity(wristRight.getPositionZ(), oldBody.wristRight.getPositionZ(), timestamp, oldBody.getTimestamp());
			// handRight
			handRightXVelocity = calculateVelocity(handRight.getPositionX(), oldBody.handRight.getPositionX(), timestamp, oldBody.getTimestamp());
			handRightYVelocity = calculateVelocity(handRight.getPositionY(), oldBody.handRight.getPositionY(), timestamp, oldBody.getTimestamp());
			handRightZVelocity = calculateVelocity(handRight.getPositionZ(), oldBody.handRight.getPositionZ(), timestamp, oldBody.getTimestamp());
			// hipLeft
			hipLeftXVelocity = calculateVelocity(hipLeft.getPositionX(), oldBody.hipLeft.getPositionX(), timestamp, oldBody.getTimestamp());
			hipLeftYVelocity = calculateVelocity(hipLeft.getPositionY(), oldBody.hipLeft.getPositionY(), timestamp, oldBody.getTimestamp());
			hipLeftZVelocity = calculateVelocity(hipLeft.getPositionZ(), oldBody.hipLeft.getPositionZ(), timestamp, oldBody.getTimestamp());
			// kneeLeft
			kneeLeftXVelocity = calculateVelocity(kneeLeft.getPositionX(), oldBody.kneeLeft.getPositionX(), timestamp, oldBody.getTimestamp());
			kneeLeftYVelocity = calculateVelocity(kneeLeft.getPositionY(), oldBody.kneeLeft.getPositionY(), timestamp, oldBody.getTimestamp());
			kneeLeftZVelocity = calculateVelocity(kneeLeft.getPositionZ(), oldBody.kneeLeft.getPositionZ(), timestamp, oldBody.getTimestamp());
			// ankleLeft
			ankleLeftXVelocity = calculateVelocity(ankleLeft.getPositionX(), oldBody.ankleLeft.getPositionX(), timestamp, oldBody.getTimestamp());
			ankleLeftYVelocity = calculateVelocity(ankleLeft.getPositionY(), oldBody.ankleLeft.getPositionY(), timestamp, oldBody.getTimestamp());
			ankleLeftZVelocity = calculateVelocity(ankleLeft.getPositionZ(), oldBody.ankleLeft.getPositionZ(), timestamp, oldBody.getTimestamp());
			// footLeft
			footLeftXVelocity = calculateVelocity(footLeft.getPositionX(), oldBody.footLeft.getPositionX(), timestamp, oldBody.getTimestamp());
			footLeftYVelocity = calculateVelocity(footLeft.getPositionY(), oldBody.footLeft.getPositionY(), timestamp, oldBody.getTimestamp());
			footLeftZVelocity = calculateVelocity(footLeft.getPositionZ(), oldBody.footLeft.getPositionZ(), timestamp, oldBody.getTimestamp());
			// hipRight
			hipRightXVelocity = calculateVelocity(hipRight.getPositionX(), oldBody.hipRight.getPositionX(), timestamp, oldBody.getTimestamp());
			hipRightYVelocity = calculateVelocity(hipRight.getPositionY(), oldBody.hipRight.getPositionY(), timestamp, oldBody.getTimestamp());
			hipRightZVelocity = calculateVelocity(hipRight.getPositionZ(), oldBody.hipRight.getPositionZ(), timestamp, oldBody.getTimestamp());
			// kneeRight
			kneeRightXVelocity = calculateVelocity(kneeRight.getPositionX(), oldBody.kneeRight.getPositionX(), timestamp, oldBody.getTimestamp());
			kneeRightYVelocity = calculateVelocity(kneeRight.getPositionY(), oldBody.kneeRight.getPositionY(), timestamp, oldBody.getTimestamp());
			kneeRightZVelocity = calculateVelocity(kneeRight.getPositionZ(), oldBody.kneeRight.getPositionZ(), timestamp, oldBody.getTimestamp());
			// ankleRight
			ankleRightXVelocity = calculateVelocity(ankleRight.getPositionX(), oldBody.ankleRight.getPositionX(), timestamp, oldBody.getTimestamp());
			ankleRightYVelocity = calculateVelocity(ankleRight.getPositionY(), oldBody.ankleRight.getPositionY(), timestamp, oldBody.getTimestamp());
			ankleRightZVelocity = calculateVelocity(ankleRight.getPositionZ(), oldBody.ankleRight.getPositionZ(), timestamp, oldBody.getTimestamp());
			// footRight
			footRightXVelocity = calculateVelocity(footRight.getPositionX(), oldBody.footRight.getPositionX(), timestamp, oldBody.getTimestamp());
			footRightYVelocity = calculateVelocity(footRight.getPositionY(), oldBody.footRight.getPositionY(), timestamp, oldBody.getTimestamp());
			footRightZVelocity = calculateVelocity(footRight.getPositionZ(), oldBody.footRight.getPositionZ(), timestamp, oldBody.getTimestamp());
		}
		
		if (isBodyReady()) {
			try {
				oldBody = clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public Body clone() throws CloneNotSupportedException {
		Body clone = (Body) super.clone();
//		clone.ankleLeft = ankleLeft;
//		clone.ankleRight = ankleRight;
//		clone.elbowLeft = elbowLeft;
//		clone.elbowRight = elbowRight;
//		clone.footLeft = footLeft;
//		clone.footRight = footRight;
//		clone.frameNumber = frameNumber;
//		clone.handLeft = handLeft;
//		clone.handRight = handRight;
//		clone.head = head;
//		clone.hipCenter = hipCenter;
//		clone.hipLeft = hipLeft;
//		clone.hipRight = hipRight;
//		clone.kneeLeft = kneeLeft;
//		clone.kneeRight = kneeRight;
//		clone.shoulderCenter = shoulderCenter;
//		clone.shoulderLeft = shoulderLeft;
//		clone.shoulderRight = shoulderRight;
//		clone.spine = spine;
//		clone.timestamp = timestamp;
//		clone.veryFirstTimestamp = veryFirstTimestamp;
//		clone.wristLeft = wristLeft;
//		clone.wristRight = wristRight;
		
		return clone;
	}
	
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
	
	public double getHeadXVelocity() {
		return headXVelocity;
	}

	public double getHeadYVelocity() {
		return headYVelocity;
	}

	public double getHeadZVelocity() {
		return headZVelocity;
	}

	public double getHipCenterXVelocity() {
		return hipCenterXVelocity;
	}

	public double getHipCenterYVelocity() {
		return hipCenterYVelocity;
	}

	public double getHipCenterZVelocity() {
		return hipCenterZVelocity;
	}

	public double getSpineXVelocity() {
		return spineXVelocity;
	}

	public double getSpineYVelocity() {
		return spineYVelocity;
	}

	public double getSpineZVelocity() {
		return spineZVelocity;
	}

	public double getShoulderCenterXVelocity() {
		return shoulderCenterXVelocity;
	}

	public double getShoulderCenterYVelocity() {
		return shoulderCenterYVelocity;
	}

	public double getShoulderCenterZVelocity() {
		return shoulderCenterZVelocity;
	}

	public double getShoulderLeftXVelocity() {
		return shoulderLeftXVelocity;
	}

	public double getShoulderLeftYVelocity() {
		return shoulderLeftYVelocity;
	}

	public double getShoulderLeftZVelocity() {
		return shoulderLeftZVelocity;
	}

	public double getElbowLeftXVelocity() {
		return elbowLeftXVelocity;
	}

	public double getElbowLeftYVelocity() {
		return elbowLeftYVelocity;
	}

	public double getElbowLeftZVelocity() {
		return elbowLeftZVelocity;
	}

	public double getWristLeftXVelocity() {
		return wristLeftXVelocity;
	}

	public double getWristLeftYVelocity() {
		return wristLeftYVelocity;
	}

	public double getWristLeftZVelocity() {
		return wristLeftZVelocity;
	}

	public double getHandLeftXVelocity() {
		return handLeftXVelocity;
	}

	public double getHandLeftYVelocity() {
		return handLeftYVelocity;
	}

	public double getHandLeftZVelocity() {
		return handLeftZVelocity;
	}

	public double getShoulderRightXVelocity() {
		return shoulderRightXVelocity;
	}

	public double getShoulderRightYVelocity() {
		return shoulderRightYVelocity;
	}

	public double getShoulderRightZVelocity() {
		return shoulderRightZVelocity;
	}

	public double getElbowRightXVelocity() {
		return elbowRightXVelocity;
	}

	public double getElbowRightYVelocity() {
		return elbowRightYVelocity;
	}

	public double getElbowRightZVelocity() {
		return elbowRightZVelocity;
	}

	public double getWristRightXVelocity() {
		return wristRightXVelocity;
	}

	public double getWristRightYVelocity() {
		return wristRightYVelocity;
	}

	public double getWristRightZVelocity() {
		return wristRightZVelocity;
	}

	public double getHandRightXVelocity() {
		return handRightXVelocity;
	}

	public double getHandRightYVelocity() {
		return handRightYVelocity;
	}

	public double getHandRightZVelocity() {
		return handRightZVelocity;
	}

	public double getHipLeftXVelocity() {
		return hipLeftXVelocity;
	}

	public double getHipLeftYVelocity() {
		return hipLeftYVelocity;
	}

	public double getHipLeftZVelocity() {
		return hipLeftZVelocity;
	}

	public double getKneeLeftXVelocity() {
		return kneeLeftXVelocity;
	}

	public double getKneeLeftYVelocity() {
		return kneeLeftYVelocity;
	}

	public double getKneeLeftZVelocity() {
		return kneeLeftZVelocity;
	}

	public double getAnkleLeftXVelocity() {
		return ankleLeftXVelocity;
	}

	public double getAnkleLeftYVelocity() {
		return ankleLeftYVelocity;
	}

	public double getAnkleLeftZVelocity() {
		return ankleLeftZVelocity;
	}

	public double getFootLeftXVelocity() {
		return footLeftXVelocity;
	}

	public double getFootLeftYVelocity() {
		return footLeftYVelocity;
	}

	public double getFootLeftZVelocity() {
		return footLeftZVelocity;
	}

	public double getHipRightXVelocity() {
		return hipRightXVelocity;
	}

	public double getHipRightYVelocity() {
		return hipRightYVelocity;
	}

	public double getHipRightZVelocity() {
		return hipRightZVelocity;
	}

	public double getKneeRightXVelocity() {
		return kneeRightXVelocity;
	}

	public double getKneeRightYVelocity() {
		return kneeRightYVelocity;
	}

	public double getKneeRightZVelocity() {
		return kneeRightZVelocity;
	}

	public double getAnkleRightXVelocity() {
		return ankleRightXVelocity;
	}

	public double getAnkleRightYVelocity() {
		return ankleRightYVelocity;
	}

	public double getAnkleRightZVelocity() {
		return ankleRightZVelocity;
	}

	public double getFootRightXVelocity() {
		return footRightXVelocity;
	}

	public double getFootRightYVelocity() {
		return footRightYVelocity;
	}

	public double getFootRightZVelocity() {
		return footRightZVelocity;
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
		line = line.append("FrameId\t");
		line = line.append("Timestamp\t");
		
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
		
		// Velocities
		line = line.append(Joint.getVelocityHeader(JointType.HEAD));

		// Accelerations
		//line = line.append(Joint.getAccelerationHeader(JointType.HEAD));
		
		// Markers
		line = line.append("Marker1\t");
		line = line.append("Marker2\t");
		line = line.append("Marker3\t");
		line = line.append("Marker4\t");
		line = line.append("Marker5\t");
		
		return line.toString();
	}
	
	public String getJointString() {
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

	public String getVelocityString() {
		StringBuffer line = new StringBuffer();
		if (isBodyReady() && oldBody != null) {
			// head
			line = line.append(headXVelocity).append("\t");
			line = line.append(headYVelocity).append("\t");
			line = line.append(headZVelocity).append("\t");
			// shoulderCenter
			line = line.append(shoulderCenterXVelocity).append("\t");
			line = line.append(shoulderCenterYVelocity).append("\t");
			line = line.append(shoulderCenterZVelocity).append("\t");
			// spine
			line = line.append(spineXVelocity).append("\t");
			line = line.append(spineYVelocity).append("\t");
			line = line.append(spineZVelocity).append("\t");
			// hipCenter
			line = line.append(hipCenterXVelocity).append("\t");
			line = line.append(hipCenterYVelocity).append("\t");
			line = line.append(hipCenterZVelocity).append("\t");
			//shoulderLeft
			line = line.append(shoulderLeftXVelocity).append("\t");
			line = line.append(shoulderLeftYVelocity).append("\t");
			line = line.append(shoulderLeftZVelocity).append("\t");
			// elbowLeft
			line = line.append(elbowLeftXVelocity).append("\t");
			line = line.append(elbowLeftYVelocity).append("\t");
			line = line.append(elbowLeftZVelocity).append("\t");
			// wristLeft
			line = line.append(wristLeftXVelocity).append("\t");
			line = line.append(wristLeftYVelocity).append("\t");
			line = line.append(wristLeftZVelocity).append("\t");
			// handLeft
			line = line.append(handLeftXVelocity).append("\t");
			line = line.append(handLeftYVelocity).append("\t");
			line = line.append(handLeftZVelocity).append("\t");
			// shoulderRight
			line = line.append(shoulderRightXVelocity).append("\t");
			line = line.append(shoulderRightYVelocity).append("\t");
			line = line.append(shoulderRightZVelocity).append("\t");
			// elbowRight
			line = line.append(elbowRightXVelocity).append("\t");
			line = line.append(elbowRightYVelocity).append("\t");
			line = line.append(elbowRightZVelocity).append("\t");
			// wristRight
			line = line.append(wristRightXVelocity).append("\t");
			line = line.append(wristRightYVelocity).append("\t");
			line = line.append(wristRightZVelocity).append("\t");
			// handRight
			line = line.append(handRightXVelocity).append("\t");
			line = line.append(handRightYVelocity).append("\t");
			line = line.append(handRightZVelocity).append("\t");
			// hipLeft
			line = line.append(hipLeftXVelocity).append("\t");
			line = line.append(hipLeftYVelocity).append("\t");
			line = line.append(hipLeftZVelocity).append("\t");
			// kneeLeft
			line = line.append(kneeLeftXVelocity).append("\t");
			line = line.append(kneeLeftYVelocity).append("\t");
			line = line.append(kneeLeftZVelocity).append("\t");
			// ankleLeft
			line = line.append(ankleLeftXVelocity).append("\t");
			line = line.append(ankleLeftYVelocity).append("\t");
			line = line.append(ankleLeftZVelocity).append("\t");
			// footLeft
			line = line.append(footLeftXVelocity).append("\t");
			line = line.append(footLeftYVelocity).append("\t");
			line = line.append(footLeftZVelocity).append("\t");
			// hipRight
			line = line.append(hipRightXVelocity).append("\t");
			line = line.append(hipRightYVelocity).append("\t");
			line = line.append(hipRightZVelocity).append("\t");
			// kneeRight
			line = line.append(kneeRightXVelocity).append("\t");
			line = line.append(kneeRightYVelocity).append("\t");
			line = line.append(kneeRightZVelocity).append("\t");
			// ankleRight
			line = line.append(ankleRightXVelocity).append("\t");
			line = line.append(ankleRightYVelocity).append("\t");
			line = line.append(ankleRightZVelocity).append("\t");
			// footRight
			line = line.append(footRightXVelocity).append("\t");
			line = line.append(footRightYVelocity).append("\t");
			line = line.append(footRightZVelocity).append("\t");
		}
		
		return line.toString();
	}
	
	private double calculateVelocity(double pos1, double pos2, double time1, double time2) {
		// v = S / t
		return (pos1 - pos2) / (time1 - time2);
	}

	public String getAccelerationString() {
		if (isBodyReady() && oldBody != null) {
			
		}
		
		return null;
	}
	
}
