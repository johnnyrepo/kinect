package ee.ttu.kinect.model;

import java.util.logging.Logger;


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
	
	private final Logger logger = Logger.getLogger(getClass().getName());

	public void updated(boolean seatedMode) {
		if (oldBody != null) {
			calculateVelocities(seatedMode);
			calculateAccelerations(seatedMode);
		}
		
		if (isBodyReady()) {
			try {
				oldBody = clone();
			} catch (CloneNotSupportedException e) {
				logger.info(e.getLocalizedMessage());
			}
		}
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
			veryFirstTimestamp = timestamp;
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
	
	public Joint getJoint(JointType type) {
		Joint joint = null;
		switch (type) {
		case ANKLE_LEFT:
			joint = ankleLeft;
			break;
		case ANKLE_RIGHT:
			joint = ankleRight;
			break;
		case ELBOW_LEFT:
			joint = elbowLeft;
			break;
		case ELBOW_RIGHT:
			joint = elbowRight;
			break;
		case FOOT_LEFT:
			joint = footLeft;
			break;
		case FOOT_RIGHT:
			joint = footRight;
			break;
		case HAND_LEFT:
			joint = handLeft;
			break;
		case HAND_RIGHT:
			joint = handRight;
			break;
		case HEAD:
			joint = head;
			break;
		case HIP_CENTER:
			joint = hipCenter;
			break;
		case HIP_LEFT:
			joint = hipLeft;
			break;
		case HIP_RIGHT:
			joint = hipRight;
			break;
		case KNEE_LEFT:
			joint = kneeLeft;
			break;
		case KNEE_RIGHT:
			joint = kneeRight;
			break;
		case SHOULDER_CENTER:
			joint = shoulderCenter;
			break;
		case SHOULDER_LEFT:
			joint = shoulderLeft;
			break;
		case SHOULDER_RIGHT:
			joint = shoulderRight;
			break;
		case SPINE:
			joint = spine;
			break;
		case WRIST_LEFT:
			joint = wristLeft;
			break;
		case WRIST_RIGHT:
			joint = wristRight;
			break;
		}
		
		return joint;
	}

	public boolean isBodyReady() {
		return elbowLeft != null && elbowRight != null
				&& handLeft != null && handRight != null && head != null
				&& shoulderCenter != null && shoulderLeft != null
				&& shoulderRight != null && wristLeft != null && wristRight != null;
	}
	
//	public boolean isBodyReady() {
//		return ankleLeft != null && ankleRight != null && elbowLeft != null
//				&& elbowRight != null && footLeft != null && footRight != null
//				&& handLeft != null && handRight != null && head != null
//				&& hipCenter != null && hipLeft != null && hipRight != null
//				&& kneeLeft != null && kneeRight != null
//				&& shoulderCenter != null && shoulderLeft != null
//				&& shoulderRight != null && spine != null && wristLeft != null
//				&& wristRight != null;
//	}
	
	public boolean isBodyChanged() {
		return !equals(oldBody);
	}
	
	public static String getHeader(boolean seatedMode) {
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
		line = line.append("Marker1\t");
		line = line.append("Marker2\t");
		line = line.append("Marker3\t");
		line = line.append("Marker4\t");
		line = line.append("Marker5\t");
		
		return line.toString();
	}
	
	public String getJointString(boolean seatedMode) {
		StringBuffer line = new StringBuffer();
		if (isBodyReady()) {
			line = line.append(frameNumber).append("\t");
			line = line.append((timestamp - veryFirstTimestamp)).append("\t");
			
			line = line.append(head.toString());
			line = line.append(shoulderCenter.toString());
			if (!seatedMode) {
				line = line.append(spine.toString());
				line = line.append(hipCenter.toString());
			}
			line = line.append(shoulderLeft.toString());
			line = line.append(elbowLeft.toString());
			line = line.append(wristLeft.toString());
			line = line.append(handLeft.toString());
			line = line.append(shoulderRight.toString());
			line = line.append(elbowRight.toString());
			line = line.append(wristRight.toString());
			line = line.append(handRight.toString());
			if (!seatedMode) {
				line = line.append(hipLeft.toString());
				line = line.append(kneeLeft.toString());
				line = line.append(ankleLeft.toString());
				line = line.append(footLeft.toString());
				line = line.append(hipRight.toString());
				line = line.append(kneeRight.toString());
				line = line.append(ankleRight.toString());
				line = line.append(footRight.toString());
			}
		}
		
		return line.toString();
	}

//	public String getVelocityString() {
//		StringBuffer line = new StringBuffer();
//		if (isBodyReady() && oldBody != null) {
//			// head
//			line = line.append(headXVelocity).append("\t");
//			line = line.append(headYVelocity).append("\t");
//			line = line.append(headZVelocity).append("\t");
//			// shoulderCenter
//			line = line.append(shoulderCenterXVelocity).append("\t");
//			line = line.append(shoulderCenterYVelocity).append("\t");
//			line = line.append(shoulderCenterZVelocity).append("\t");
//			// spine
//			line = line.append(spineXVelocity).append("\t");
//			line = line.append(spineYVelocity).append("\t");
//			line = line.append(spineZVelocity).append("\t");
//			// hipCenter
//			line = line.append(hipCenterXVelocity).append("\t");
//			line = line.append(hipCenterYVelocity).append("\t");
//			line = line.append(hipCenterZVelocity).append("\t");
//			//shoulderLeft
//			line = line.append(shoulderLeftXVelocity).append("\t");
//			line = line.append(shoulderLeftYVelocity).append("\t");
//			line = line.append(shoulderLeftZVelocity).append("\t");
//			// elbowLeft
//			line = line.append(elbowLeftXVelocity).append("\t");
//			line = line.append(elbowLeftYVelocity).append("\t");
//			line = line.append(elbowLeftZVelocity).append("\t");
//			// wristLeft
//			line = line.append(wristLeftXVelocity).append("\t");
//			line = line.append(wristLeftYVelocity).append("\t");
//			line = line.append(wristLeftZVelocity).append("\t");
//			// handLeft
//			line = line.append(handLeftXVelocity).append("\t");
//			line = line.append(handLeftYVelocity).append("\t");
//			line = line.append(handLeftZVelocity).append("\t");
//			// shoulderRight
//			line = line.append(shoulderRightXVelocity).append("\t");
//			line = line.append(shoulderRightYVelocity).append("\t");
//			line = line.append(shoulderRightZVelocity).append("\t");
//			// elbowRight
//			line = line.append(elbowRightXVelocity).append("\t");
//			line = line.append(elbowRightYVelocity).append("\t");
//			line = line.append(elbowRightZVelocity).append("\t");
//			// wristRight
//			line = line.append(wristRightXVelocity).append("\t");
//			line = line.append(wristRightYVelocity).append("\t");
//			line = line.append(wristRightZVelocity).append("\t");
//			// handRight
//			line = line.append(handRightXVelocity).append("\t");
//			line = line.append(handRightYVelocity).append("\t");
//			line = line.append(handRightZVelocity).append("\t");
//			// hipLeft
//			line = line.append(hipLeftXVelocity).append("\t");
//			line = line.append(hipLeftYVelocity).append("\t");
//			line = line.append(hipLeftZVelocity).append("\t");
//			// kneeLeft
//			line = line.append(kneeLeftXVelocity).append("\t");
//			line = line.append(kneeLeftYVelocity).append("\t");
//			line = line.append(kneeLeftZVelocity).append("\t");
//			// ankleLeft
//			line = line.append(ankleLeftXVelocity).append("\t");
//			line = line.append(ankleLeftYVelocity).append("\t");
//			line = line.append(ankleLeftZVelocity).append("\t");
//			// footLeft
//			line = line.append(footLeftXVelocity).append("\t");
//			line = line.append(footLeftYVelocity).append("\t");
//			line = line.append(footLeftZVelocity).append("\t");
//			// hipRight
//			line = line.append(hipRightXVelocity).append("\t");
//			line = line.append(hipRightYVelocity).append("\t");
//			line = line.append(hipRightZVelocity).append("\t");
//			// kneeRight
//			line = line.append(kneeRightXVelocity).append("\t");
//			line = line.append(kneeRightYVelocity).append("\t");
//			line = line.append(kneeRightZVelocity).append("\t");
//			// ankleRight
//			line = line.append(ankleRightXVelocity).append("\t");
//			line = line.append(ankleRightYVelocity).append("\t");
//			line = line.append(ankleRightZVelocity).append("\t");
//			// footRight
//			line = line.append(footRightXVelocity).append("\t");
//			line = line.append(footRightYVelocity).append("\t");
//			line = line.append(footRightZVelocity).append("\t");
//		}
//		
//		return line.toString();
//	}
	
	private void calculateVelocities(boolean seatedMode) {
		// head
		head.setVelocityX(calculateVelocity(head.getPositionX(), oldBody.head.getPositionX(), timestamp, oldBody.getTimestamp()));
		head.setVelocityY(calculateVelocity(head.getPositionY(), oldBody.head.getPositionY(), timestamp, oldBody.getTimestamp()));
		head.setVelocityZ(calculateVelocity(head.getPositionZ(), oldBody.head.getPositionZ(), timestamp, oldBody.getTimestamp()));
		// shoulderCenter
		shoulderCenter.setVelocityX(calculateVelocity(shoulderCenter.getPositionX(), oldBody.shoulderCenter.getPositionX(), timestamp, oldBody.getTimestamp()));
		shoulderCenter.setVelocityY(calculateVelocity(shoulderCenter.getPositionY(), oldBody.shoulderCenter.getPositionY(), timestamp, oldBody.getTimestamp()));
		shoulderCenter.setVelocityZ(calculateVelocity(shoulderCenter.getPositionZ(), oldBody.shoulderCenter.getPositionZ(), timestamp, oldBody.getTimestamp()));
		if (!seatedMode) {
			// spine
			spine.setVelocityX(calculateVelocity(spine.getPositionX(), oldBody.spine.getPositionX(), timestamp, oldBody.getTimestamp()));
			spine.setVelocityY(calculateVelocity(spine.getPositionY(), oldBody.spine.getPositionY(), timestamp, oldBody.getTimestamp()));
			spine.setVelocityZ(calculateVelocity(spine.getPositionZ(), oldBody.spine.getPositionZ(), timestamp, oldBody.getTimestamp()));
			// hipCenter
			hipCenter.setVelocityX(calculateVelocity(hipCenter.getPositionX(), oldBody.hipCenter.getPositionX(), timestamp, oldBody.getTimestamp()));
			hipCenter.setVelocityY(calculateVelocity(hipCenter.getPositionY(), oldBody.hipCenter.getPositionY(), timestamp, oldBody.getTimestamp()));
			hipCenter.setVelocityZ(calculateVelocity(hipCenter.getPositionZ(), oldBody.hipCenter.getPositionZ(), timestamp, oldBody.getTimestamp()));
		}
		//shoulderLeft
		shoulderLeft.setVelocityX(calculateVelocity(shoulderLeft.getPositionX(), oldBody.shoulderLeft.getPositionX(), timestamp, oldBody.getTimestamp()));
		shoulderLeft.setVelocityY(calculateVelocity(shoulderLeft.getPositionY(), oldBody.shoulderLeft.getPositionY(), timestamp, oldBody.getTimestamp()));
		shoulderLeft.setVelocityZ(calculateVelocity(shoulderLeft.getPositionZ(), oldBody.shoulderLeft.getPositionZ(), timestamp, oldBody.getTimestamp()));
		// elbowLeft
		elbowLeft.setVelocityX(calculateVelocity(elbowLeft.getPositionX(), oldBody.elbowLeft.getPositionX(), timestamp, oldBody.getTimestamp()));
		elbowLeft.setVelocityY(calculateVelocity(elbowLeft.getPositionY(), oldBody.elbowLeft.getPositionY(), timestamp, oldBody.getTimestamp()));
		elbowLeft.setVelocityZ(calculateVelocity(elbowLeft.getPositionZ(), oldBody.elbowLeft.getPositionZ(), timestamp, oldBody.getTimestamp()));
		// wristLeft
		wristLeft.setVelocityX(calculateVelocity(wristLeft.getPositionX(), oldBody.wristLeft.getPositionX(), timestamp, oldBody.getTimestamp()));
		wristLeft.setVelocityY(calculateVelocity(wristLeft.getPositionY(), oldBody.wristLeft.getPositionY(), timestamp, oldBody.getTimestamp()));
		wristLeft.setVelocityZ(calculateVelocity(wristLeft.getPositionZ(), oldBody.wristLeft.getPositionZ(), timestamp, oldBody.getTimestamp()));
		// handLeft
		handLeft.setVelocityX(calculateVelocity(handLeft.getPositionX(), oldBody.handLeft.getPositionX(), timestamp, oldBody.getTimestamp()));
		handLeft.setVelocityY(calculateVelocity(handLeft.getPositionY(), oldBody.handLeft.getPositionY(), timestamp, oldBody.getTimestamp()));
		handLeft.setVelocityZ(calculateVelocity(handLeft.getPositionZ(), oldBody.handLeft.getPositionZ(), timestamp, oldBody.getTimestamp()));
		// shoulderRight
		shoulderRight.setVelocityX(calculateVelocity(shoulderRight.getPositionX(), oldBody.shoulderRight.getPositionX(), timestamp, oldBody.getTimestamp()));
		shoulderRight.setVelocityY(calculateVelocity(shoulderRight.getPositionY(), oldBody.shoulderRight.getPositionY(), timestamp, oldBody.getTimestamp()));
		shoulderRight.setVelocityZ(calculateVelocity(shoulderRight.getPositionZ(), oldBody.shoulderRight.getPositionZ(), timestamp, oldBody.getTimestamp()));
		// elbowRight
		elbowRight.setVelocityX(calculateVelocity(elbowRight.getPositionX(), oldBody.elbowRight.getPositionX(), timestamp, oldBody.getTimestamp()));
		elbowRight.setVelocityY(calculateVelocity(elbowRight.getPositionY(), oldBody.elbowRight.getPositionY(), timestamp, oldBody.getTimestamp()));
		elbowRight.setVelocityZ(calculateVelocity(elbowRight.getPositionZ(), oldBody.elbowRight.getPositionZ(), timestamp, oldBody.getTimestamp()));
		// wristRight
		wristRight.setVelocityX(calculateVelocity(wristRight.getPositionX(), oldBody.wristRight.getPositionX(), timestamp, oldBody.getTimestamp()));
		wristRight.setVelocityY(calculateVelocity(wristRight.getPositionY(), oldBody.wristRight.getPositionY(), timestamp, oldBody.getTimestamp()));
		wristRight.setVelocityZ(calculateVelocity(wristRight.getPositionZ(), oldBody.wristRight.getPositionZ(), timestamp, oldBody.getTimestamp()));
		// handRight
		handRight.setVelocityX(calculateVelocity(handRight.getPositionX(), oldBody.handRight.getPositionX(), timestamp, oldBody.getTimestamp()));
		handRight.setVelocityY(calculateVelocity(handRight.getPositionY(), oldBody.handRight.getPositionY(), timestamp, oldBody.getTimestamp()));
		handRight.setVelocityZ(calculateVelocity(handRight.getPositionZ(), oldBody.handRight.getPositionZ(), timestamp, oldBody.getTimestamp()));
		if (!seatedMode) {
			// hipLeft
			hipLeft.setVelocityX(calculateVelocity(hipLeft.getPositionX(), oldBody.hipLeft.getPositionX(), timestamp, oldBody.getTimestamp()));
			hipLeft.setVelocityY(calculateVelocity(hipLeft.getPositionY(), oldBody.hipLeft.getPositionY(), timestamp, oldBody.getTimestamp()));
			hipLeft.setVelocityZ(calculateVelocity(hipLeft.getPositionZ(), oldBody.hipLeft.getPositionZ(), timestamp, oldBody.getTimestamp()));
			// kneeLeft
			kneeLeft.setVelocityX(calculateVelocity(kneeLeft.getPositionX(), oldBody.kneeLeft.getPositionX(), timestamp, oldBody.getTimestamp()));
			kneeLeft.setVelocityY(calculateVelocity(kneeLeft.getPositionY(), oldBody.kneeLeft.getPositionY(), timestamp, oldBody.getTimestamp()));
			kneeLeft.setVelocityZ(calculateVelocity(kneeLeft.getPositionZ(), oldBody.kneeLeft.getPositionZ(), timestamp, oldBody.getTimestamp()));
			// ankleLeft
			ankleLeft.setVelocityX(calculateVelocity(ankleLeft.getPositionX(), oldBody.ankleLeft.getPositionX(), timestamp, oldBody.getTimestamp()));
			ankleLeft.setVelocityY(calculateVelocity(ankleLeft.getPositionY(), oldBody.ankleLeft.getPositionY(), timestamp, oldBody.getTimestamp()));
			ankleLeft.setVelocityZ(calculateVelocity(ankleLeft.getPositionZ(), oldBody.ankleLeft.getPositionZ(), timestamp, oldBody.getTimestamp()));
			// footLeft
			footLeft.setVelocityX(calculateVelocity(footLeft.getPositionX(), oldBody.footLeft.getPositionX(), timestamp, oldBody.getTimestamp()));
			footLeft.setVelocityY(calculateVelocity(footLeft.getPositionY(), oldBody.footLeft.getPositionY(), timestamp, oldBody.getTimestamp()));
			footLeft.setVelocityZ(calculateVelocity(footLeft.getPositionZ(), oldBody.footLeft.getPositionZ(), timestamp, oldBody.getTimestamp()));
			// hipRight
			hipRight.setVelocityX(calculateVelocity(hipRight.getPositionX(), oldBody.hipRight.getPositionX(), timestamp, oldBody.getTimestamp()));
			hipRight.setVelocityY(calculateVelocity(hipRight.getPositionY(), oldBody.hipRight.getPositionY(), timestamp, oldBody.getTimestamp()));
			hipRight.setVelocityZ(calculateVelocity(hipRight.getPositionZ(), oldBody.hipRight.getPositionZ(), timestamp, oldBody.getTimestamp()));
			// kneeRight
			kneeRight.setVelocityX(calculateVelocity(kneeRight.getPositionX(), oldBody.kneeRight.getPositionX(), timestamp, oldBody.getTimestamp()));
			kneeRight.setVelocityY(calculateVelocity(kneeRight.getPositionY(), oldBody.kneeRight.getPositionY(), timestamp, oldBody.getTimestamp()));
			kneeRight.setVelocityZ(calculateVelocity(kneeRight.getPositionZ(), oldBody.kneeRight.getPositionZ(), timestamp, oldBody.getTimestamp()));
			// ankleRight
			ankleRight.setVelocityX(calculateVelocity(ankleRight.getPositionX(), oldBody.ankleRight.getPositionX(), timestamp, oldBody.getTimestamp()));
			ankleRight.setVelocityY(calculateVelocity(ankleRight.getPositionY(), oldBody.ankleRight.getPositionY(), timestamp, oldBody.getTimestamp()));
			ankleRight.setVelocityZ(calculateVelocity(ankleRight.getPositionZ(), oldBody.ankleRight.getPositionZ(), timestamp, oldBody.getTimestamp()));
			// footRight
			footRight.setVelocityX(calculateVelocity(footRight.getPositionX(), oldBody.footRight.getPositionX(), timestamp, oldBody.getTimestamp()));
			footRight.setVelocityY(calculateVelocity(footRight.getPositionY(), oldBody.footRight.getPositionY(), timestamp, oldBody.getTimestamp()));
			footRight.setVelocityZ(calculateVelocity(footRight.getPositionZ(), oldBody.footRight.getPositionZ(), timestamp, oldBody.getTimestamp()));
		}
	}

	private void calculateAccelerations(boolean seatedMode) {
		// head
		head.setAccelerationX(calculateAcceleration(head.getPositionX(), oldBody.getHead().getPositionX(), timestamp, oldBody.getTimestamp()));
		head.setAccelerationY(calculateAcceleration(head.getPositionY(), oldBody.getHead().getPositionY(), timestamp, oldBody.getTimestamp()));
		head.setAccelerationZ(calculateAcceleration(head.getPositionZ(), oldBody.getHead().getPositionZ(), timestamp, oldBody.getTimestamp()));
		// shoulderCenter
		shoulderCenter.setAccelerationX(calculateAcceleration(shoulderCenter.getPositionX(), oldBody.getShoulderCenter().getPositionX(), timestamp, oldBody.getTimestamp()));
		shoulderCenter.setAccelerationY(calculateAcceleration(shoulderCenter.getPositionY(), oldBody.getShoulderCenter().getPositionY(), timestamp, oldBody.getTimestamp()));
		shoulderCenter.setAccelerationZ(calculateAcceleration(shoulderCenter.getPositionZ(), oldBody.getShoulderCenter().getPositionZ(), timestamp, oldBody.getTimestamp()));
		if (!seatedMode) {
			// spine
			spine.setAccelerationX(calculateAcceleration(spine.getPositionX(), oldBody.getSpine().getPositionX(), timestamp, oldBody.getTimestamp()));
			spine.setAccelerationY(calculateAcceleration(spine.getPositionY(), oldBody.getSpine().getPositionY(), timestamp, oldBody.getTimestamp()));
			spine.setAccelerationZ(calculateAcceleration(spine.getPositionZ(), oldBody.getSpine().getPositionZ(), timestamp, oldBody.getTimestamp()));
			// hipCenter
			hipCenter.setAccelerationX(calculateAcceleration(hipCenter.getPositionX(), oldBody.getHipCenter().getPositionX(), timestamp, oldBody.getTimestamp()));
			hipCenter.setAccelerationY(calculateAcceleration(hipCenter.getPositionY(), oldBody.getHipCenter().getPositionY(), timestamp, oldBody.getTimestamp()));
			hipCenter.setAccelerationZ(calculateAcceleration(hipCenter.getPositionZ(), oldBody.getHipCenter().getPositionZ(), timestamp, oldBody.getTimestamp()));
		}
		// shoulderLeft
		shoulderLeft.setAccelerationX(calculateAcceleration(shoulderLeft.getPositionX(), oldBody.getShoulderLeft().getPositionX(), timestamp, oldBody.getTimestamp()));
		shoulderLeft.setAccelerationY(calculateAcceleration(shoulderLeft.getPositionY(), oldBody.getShoulderLeft().getPositionY(), timestamp, oldBody.getTimestamp()));
		shoulderLeft.setAccelerationZ(calculateAcceleration(shoulderLeft.getPositionZ(), oldBody.getShoulderLeft().getPositionZ(), timestamp, oldBody.getTimestamp()));
		// elbowLeft
		elbowLeft.setAccelerationX(calculateAcceleration(elbowLeft.getPositionX(), oldBody.getElbowLeft().getPositionX(), timestamp, oldBody.getTimestamp()));
		elbowLeft.setAccelerationY(calculateAcceleration(elbowLeft.getPositionY(), oldBody.getElbowLeft().getPositionY(), timestamp, oldBody.getTimestamp()));
		elbowLeft.setAccelerationZ(calculateAcceleration(elbowLeft.getPositionZ(), oldBody.getElbowLeft().getPositionZ(), timestamp, oldBody.getTimestamp()));
		// wristLeft
		wristLeft.setAccelerationX(calculateAcceleration(wristLeft.getPositionX(), oldBody.getWristLeft().getPositionX(), timestamp, oldBody.getTimestamp()));
		wristLeft.setAccelerationY(calculateAcceleration(wristLeft.getPositionY(), oldBody.getWristLeft().getPositionY(), timestamp, oldBody.getTimestamp()));
		wristLeft.setAccelerationZ(calculateAcceleration(wristLeft.getPositionZ(), oldBody.getWristLeft().getPositionZ(), timestamp, oldBody.getTimestamp()));
		// handLeft
		handLeft.setAccelerationX(calculateAcceleration(handLeft.getPositionX(), oldBody.getHandLeft().getPositionX(), timestamp, oldBody.getTimestamp()));
		handLeft.setAccelerationY(calculateAcceleration(handLeft.getPositionY(), oldBody.getHandLeft().getPositionY(), timestamp, oldBody.getTimestamp()));
		handLeft.setAccelerationZ(calculateAcceleration(handLeft.getPositionZ(), oldBody.getHandLeft().getPositionZ(), timestamp, oldBody.getTimestamp()));
		// shoulderRight
		shoulderRight.setAccelerationX(calculateAcceleration(shoulderRight.getPositionX(), oldBody.getShoulderRight().getPositionX(), timestamp, oldBody.getTimestamp()));
		shoulderRight.setAccelerationY(calculateAcceleration(shoulderRight.getPositionY(), oldBody.getShoulderRight().getPositionY(), timestamp, oldBody.getTimestamp()));
		shoulderRight.setAccelerationZ(calculateAcceleration(shoulderRight.getPositionZ(), oldBody.getShoulderRight().getPositionZ(), timestamp, oldBody.getTimestamp()));
		// elbowRight
		elbowRight.setAccelerationX(calculateAcceleration(elbowRight.getPositionX(), oldBody.getElbowRight().getPositionX(), timestamp, oldBody.getTimestamp()));
		elbowRight.setAccelerationY(calculateAcceleration(elbowRight.getPositionY(), oldBody.getElbowRight().getPositionY(), timestamp, oldBody.getTimestamp()));
		elbowRight.setAccelerationZ(calculateAcceleration(elbowRight.getPositionZ(), oldBody.getElbowRight().getPositionZ(), timestamp, oldBody.getTimestamp()));
		// wristRight
		wristRight.setAccelerationX(calculateAcceleration(wristRight.getPositionX(), oldBody.getWristRight().getPositionX(), timestamp, oldBody.getTimestamp()));
		wristRight.setAccelerationY(calculateAcceleration(wristRight.getPositionY(), oldBody.getWristRight().getPositionY(), timestamp, oldBody.getTimestamp()));
		wristRight.setAccelerationZ(calculateAcceleration(wristRight.getPositionZ(), oldBody.getWristRight().getPositionZ(), timestamp, oldBody.getTimestamp()));
		// handRight
		handRight.setAccelerationX(calculateAcceleration(handRight.getPositionX(), oldBody.getHandRight().getPositionX(), timestamp, oldBody.getTimestamp()));
		handRight.setAccelerationY(calculateAcceleration(handRight.getPositionY(), oldBody.getHandRight().getPositionY(), timestamp, oldBody.getTimestamp()));
		handRight.setAccelerationZ(calculateAcceleration(handRight.getPositionZ(), oldBody.getHandRight().getPositionZ(), timestamp, oldBody.getTimestamp()));
		if (!seatedMode) {
			// hipLeft
			hipLeft.setAccelerationX(calculateAcceleration(hipLeft.getPositionX(), oldBody.hipLeft.getPositionX(), timestamp, oldBody.getTimestamp()));
			hipLeft.setAccelerationY(calculateAcceleration(hipLeft.getPositionY(), oldBody.hipLeft.getPositionY(), timestamp, oldBody.getTimestamp()));
			hipLeft.setAccelerationZ(calculateAcceleration(hipLeft.getPositionZ(), oldBody.hipLeft.getPositionZ(), timestamp, oldBody.getTimestamp()));
			// kneeLeft
			kneeLeft.setAccelerationX(calculateAcceleration(kneeLeft.getPositionX(), oldBody.kneeLeft.getPositionX(), timestamp, oldBody.getTimestamp()));
			kneeLeft.setAccelerationY(calculateAcceleration(kneeLeft.getPositionY(), oldBody.kneeLeft.getPositionY(), timestamp, oldBody.getTimestamp()));
			kneeLeft.setAccelerationZ(calculateAcceleration(kneeLeft.getPositionZ(), oldBody.kneeLeft.getPositionZ(), timestamp, oldBody.getTimestamp()));
			// ankleLeft
			ankleLeft.setAccelerationX(calculateAcceleration(ankleLeft.getPositionX(), oldBody.ankleLeft.getPositionX(), timestamp, oldBody.getTimestamp()));
			ankleLeft.setAccelerationY(calculateAcceleration(ankleLeft.getPositionY(), oldBody.ankleLeft.getPositionY(), timestamp, oldBody.getTimestamp()));
			ankleLeft.setAccelerationZ(calculateAcceleration(ankleLeft.getPositionZ(), oldBody.ankleLeft.getPositionZ(), timestamp, oldBody.getTimestamp()));
			// footLeft
			footLeft.setAccelerationX(calculateAcceleration(footLeft.getPositionX(), oldBody.footLeft.getPositionX(), timestamp, oldBody.getTimestamp()));
			footLeft.setAccelerationY(calculateAcceleration(footLeft.getPositionY(), oldBody.footLeft.getPositionY(), timestamp, oldBody.getTimestamp()));
			footLeft.setAccelerationZ(calculateAcceleration(footLeft.getPositionZ(), oldBody.footLeft.getPositionZ(), timestamp, oldBody.getTimestamp()));
			// hipRight
			hipRight.setAccelerationX(calculateAcceleration(hipRight.getPositionX(), oldBody.hipRight.getPositionX(), timestamp, oldBody.getTimestamp()));
			hipRight.setAccelerationY(calculateAcceleration(hipRight.getPositionY(), oldBody.hipRight.getPositionY(), timestamp, oldBody.getTimestamp()));
			hipRight.setAccelerationZ(calculateAcceleration(hipRight.getPositionZ(), oldBody.hipRight.getPositionZ(), timestamp, oldBody.getTimestamp()));
			// kneeRight
			kneeRight.setAccelerationX(calculateAcceleration(kneeRight.getPositionX(), oldBody.kneeRight.getPositionX(), timestamp, oldBody.getTimestamp()));
			kneeRight.setAccelerationY(calculateAcceleration(kneeRight.getPositionY(), oldBody.kneeRight.getPositionY(), timestamp, oldBody.getTimestamp()));
			kneeRight.setAccelerationZ(calculateAcceleration(kneeRight.getPositionZ(), oldBody.kneeRight.getPositionZ(), timestamp, oldBody.getTimestamp()));
			// ankleRight
			ankleRight.setAccelerationX(calculateAcceleration(ankleRight.getPositionX(), oldBody.ankleRight.getPositionX(), timestamp, oldBody.getTimestamp()));
			ankleRight.setAccelerationY(calculateAcceleration(ankleRight.getPositionY(), oldBody.ankleRight.getPositionY(), timestamp, oldBody.getTimestamp()));
			ankleRight.setAccelerationZ(calculateAcceleration(ankleRight.getPositionZ(), oldBody.ankleRight.getPositionZ(), timestamp, oldBody.getTimestamp()));
			// footRight
			footRight.setAccelerationX(calculateAcceleration(footRight.getPositionX(), oldBody.footRight.getPositionX(), timestamp, oldBody.getTimestamp()));
			footRight.setAccelerationY(calculateAcceleration(footRight.getPositionY(), oldBody.footRight.getPositionY(), timestamp, oldBody.getTimestamp()));
			footRight.setAccelerationZ(calculateAcceleration(footRight.getPositionZ(), oldBody.footRight.getPositionZ(), timestamp, oldBody.getTimestamp()));
		}
	}
	
	private double calculateVelocity(double pos1, double pos2, double time1, double time2) {
		// v = S / t
		return (pos1 - pos2) / (time1 - time2);
	}
	
	private double calculateAcceleration(double pos1, double pos2, Long time1, Long time2) {
		// a = v / t = S / (t * t)
		long time = time1 - time2;
		return (pos1 - pos2) / (time * time);
	}

	@Override
	public Body clone() throws CloneNotSupportedException {
		return (Body) super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Body other = (Body) obj;
		if (ankleLeft == null) {
			if (other.ankleLeft != null)
				return false;
		} else if (!ankleLeft.equals(other.ankleLeft))
			return false;
		if (ankleRight == null) {
			if (other.ankleRight != null)
				return false;
		} else if (!ankleRight.equals(other.ankleRight))
			return false;
		if (elbowLeft == null) {
			if (other.elbowLeft != null)
				return false;
		} else if (!elbowLeft.equals(other.elbowLeft))
			return false;
		if (elbowRight == null) {
			if (other.elbowRight != null)
				return false;
		} else if (!elbowRight.equals(other.elbowRight))
			return false;
		if (footLeft == null) {
			if (other.footLeft != null)
				return false;
		} else if (!footLeft.equals(other.footLeft))
			return false;
		if (footRight == null) {
			if (other.footRight != null)
				return false;
		} else if (!footRight.equals(other.footRight))
			return false;
		if (frameNumber == null) {
			if (other.frameNumber != null)
				return false;
		} else if (!frameNumber.equals(other.frameNumber))
			return false;
		if (handLeft == null) {
			if (other.handLeft != null)
				return false;
		} else if (!handLeft.equals(other.handLeft))
			return false;
		if (handRight == null) {
			if (other.handRight != null)
				return false;
		} else if (!handRight.equals(other.handRight))
			return false;
		if (head == null) {
			if (other.head != null)
				return false;
		} else if (!head.equals(other.head))
			return false;
		if (hipCenter == null) {
			if (other.hipCenter != null)
				return false;
		} else if (!hipCenter.equals(other.hipCenter))
			return false;
		if (hipLeft == null) {
			if (other.hipLeft != null)
				return false;
		} else if (!hipLeft.equals(other.hipLeft))
			return false;
		if (hipRight == null) {
			if (other.hipRight != null)
				return false;
		} else if (!hipRight.equals(other.hipRight))
			return false;
		if (kneeLeft == null) {
			if (other.kneeLeft != null)
				return false;
		} else if (!kneeLeft.equals(other.kneeLeft))
			return false;
		if (kneeRight == null) {
			if (other.kneeRight != null)
				return false;
		} else if (!kneeRight.equals(other.kneeRight))
			return false;
		if (logger == null) {
			if (other.logger != null)
				return false;
		} else if (!logger.equals(other.logger))
			return false;
		if (oldBody == null) {
			if (other.oldBody != null)
				return false;
		} else if (!oldBody.equals(other.oldBody))
			return false;
		if (shoulderCenter == null) {
			if (other.shoulderCenter != null)
				return false;
		} else if (!shoulderCenter.equals(other.shoulderCenter))
			return false;
		if (shoulderLeft == null) {
			if (other.shoulderLeft != null)
				return false;
		} else if (!shoulderLeft.equals(other.shoulderLeft))
			return false;
		if (shoulderRight == null) {
			if (other.shoulderRight != null)
				return false;
		} else if (!shoulderRight.equals(other.shoulderRight))
			return false;
		if (spine == null) {
			if (other.spine != null)
				return false;
		} else if (!spine.equals(other.spine))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (veryFirstTimestamp == null) {
			if (other.veryFirstTimestamp != null)
				return false;
		} else if (!veryFirstTimestamp.equals(other.veryFirstTimestamp))
			return false;
		if (wristLeft == null) {
			if (other.wristLeft != null)
				return false;
		} else if (!wristLeft.equals(other.wristLeft))
			return false;
		if (wristRight == null) {
			if (other.wristRight != null)
				return false;
		} else if (!wristRight.equals(other.wristRight))
			return false;
		return true;
	}

}
