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
	
	// velocities for each joint's X, Y, Z coordinate
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
	
	// accelerations for each joint's X, Y, Z coordinate
	private double headXAcceleration;
	private double headYAcceleration;
	private double headZAcceleration;
	
	private double hipCenterXAcceleration;
	private double hipCenterYAcceleration;
	private double hipCenterZAcceleration;
	
	private double spineXAcceleration;
	private double spineYAcceleration;
	private double spineZAcceleration;
	
	private double shoulderCenterXAcceleration;
	private double shoulderCenterYAcceleration;
	private double shoulderCenterZAcceleration;
	
	private double shoulderLeftXAcceleration;
	private double shoulderLeftYAcceleration;
	private double shoulderLeftZAcceleration;
	
	private double elbowLeftXAcceleration;
	private double elbowLeftYAcceleration;
	private double elbowLeftZAcceleration;
	
	private double wristLeftXAcceleration;
	private double wristLeftYAcceleration;
	private double wristLeftZAcceleration;
	
	private double handLeftXAcceleration;
	private double handLeftYAcceleration;
	private double handLeftZAcceleration;
	
	private double shoulderRightXAcceleration;
	private double shoulderRightYAcceleration;
	private double shoulderRightZAcceleration;
	
	private double elbowRightXAcceleration;
	private double elbowRightYAcceleration;
	private double elbowRightZAcceleration;
	
	private double wristRightXAcceleration;
	private double wristRightYAcceleration;
	private double wristRightZAcceleration;
	
	private double handRightXAcceleration;
	private double handRightYAcceleration;
	private double handRightZAcceleration;
	
	private double hipLeftXAcceleration;
	private double hipLeftYAcceleration;
	private double hipLeftZAcceleration;
	
	private double kneeLeftXAcceleration;
	private double kneeLeftYAcceleration;
	private double kneeLeftZAcceleration;
	
	private double ankleLeftXAcceleration;
	private double ankleLeftYAcceleration;
	private double ankleLeftZAcceleration;
	
	private double footLeftXAcceleration;
	private double footLeftYAcceleration;
	private double footLeftZAcceleration;
	
	private double hipRightXAcceleration;
	private double hipRightYAcceleration;
	private double hipRightZAcceleration;
	
	private double kneeRightXAcceleration;
	private double kneeRightYAcceleration;
	private double kneeRightZAcceleration;
	
	private double ankleRightXAcceleration;
	private double ankleRightYAcceleration;
	private double ankleRightZAcceleration;
	
	private double footRightXAcceleration;
	private double footRightYAcceleration;
	private double footRightZAcceleration;

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
	
	public double getHeadXAcceleration() {
		return headXAcceleration;
	}

	public double getHeadYAcceleration() {
		return headYAcceleration;
	}

	public double getHeadZAcceleration() {
		return headZAcceleration;
	}
	
	public double getHipCenterXAcceleration() {
		return hipCenterXAcceleration;
	}

	public double getHipCenterYAcceleration() {
		return hipCenterYAcceleration;
	}

	public double getHipCenterZAcceleration() {
		return hipCenterZAcceleration;
	}

	public double getElbowLeftXAcceleration() {
		return elbowLeftXAcceleration;
	}

	public double getElbowLeftYAcceleration() {
		return elbowLeftYAcceleration;
	}

	public double getElbowLeftZAcceleration() {
		return elbowLeftZAcceleration;
	}

	public double getHandLeftXAcceleration() {
		return handLeftXAcceleration;
	}

	public double getHandLeftYAcceleration() {
		return handLeftYAcceleration;
	}

	public double getHandLeftZAcceleration() {
		return handLeftZAcceleration;
	}

	public double getElbowRightXAcceleration() {
		return elbowRightXAcceleration;
	}

	public double getElbowRightYAcceleration() {
		return elbowRightYAcceleration;
	}

	public double getElbowRightZAcceleration() {
		return elbowRightZAcceleration;
	}

	public double getHandRightXAcceleration() {
		return handRightXAcceleration;
	}

	public double getHandRightYAcceleration() {
		return handRightYAcceleration;
	}

	public double getHandRightZAcceleration() {
		return handRightZAcceleration;
	}

	public double getHipLeftXAcceleration() {
		return hipLeftXAcceleration;
	}

	public double getHipLeftYAcceleration() {
		return hipLeftYAcceleration;
	}

	public double getHipLeftZAcceleration() {
		return hipLeftZAcceleration;
	}

	public double getKneeLeftXAcceleration() {
		return kneeLeftXAcceleration;
	}

	public double getKneeLeftYAcceleration() {
		return kneeLeftYAcceleration;
	}

	public double getKneeLeftZAcceleration() {
		return kneeLeftZAcceleration;
	}

	public double getAnkleLeftYAcceleration() {
		return ankleLeftYAcceleration;
	}

	public double getAnkleLeftZAcceleration() {
		return ankleLeftZAcceleration;
	}

	public double getFootLeftXAcceleration() {
		return footLeftXAcceleration;
	}

	public double getFootLeftYAcceleration() {
		return footLeftYAcceleration;
	}

	public double getFootLeftZAcceleration() {
		return footLeftZAcceleration;
	}

	public double getHipRightXAcceleration() {
		return hipRightXAcceleration;
	}

	public double getHipRightYAcceleration() {
		return hipRightYAcceleration;
	}

	public double getHipRightZAcceleration() {
		return hipRightZAcceleration;
	}

	public double getKneeRightXAcceleration() {
		return kneeRightXAcceleration;
	}

	public double getKneeRightYAcceleration() {
		return kneeRightYAcceleration;
	}

	public double getKneeRightZAcceleration() {
		return kneeRightZAcceleration;
	}

	public double getAnkleRightXAcceleration() {
		return ankleRightXAcceleration;
	}

	public double getAnkleRightYAcceleration() {
		return ankleRightYAcceleration;
	}

	public double getAnkleRightZAcceleration() {
		return ankleRightZAcceleration;
	}

	public double getFootRightXAcceleration() {
		return footRightXAcceleration;
	}

	public double getFootRightYAcceleration() {
		return footRightYAcceleration;
	}

	public double getFootRightZAcceleration() {
		return footRightZAcceleration;
	}

	public double getSpineXAcceleration() {
		return spineXAcceleration;
	}

	public double getSpineYAcceleration() {
		return spineYAcceleration;
	}

	public double getSpineZAcceleration() {
		return spineZAcceleration;
	}

	public double getWristLeftXAcceleration() {
		return wristLeftXAcceleration;
	}

	public double getWristLeftYAcceleration() {
		return wristLeftYAcceleration;
	}

	public double getWristLeftZAcceleration() {
		return wristLeftZAcceleration;
	}

	public double getShoulderRightZAcceleration() {
		return shoulderRightZAcceleration;
	}

	public double getWristRightXAcceleration() {
		return wristRightXAcceleration;
	}

	public double getWristRightYAcceleration() {
		return wristRightYAcceleration;
	}

	public double getWristRightZAcceleration() {
		return wristRightZAcceleration;
	}

	public double getShoulderCenterXAcceleration() {
		return shoulderCenterXAcceleration;
	}

	public double getShoulderCenterYAcceleration() {
		return shoulderCenterYAcceleration;
	}

	public double getShoulderCenterZAcceleration() {
		return shoulderCenterZAcceleration;
	}

	public double getShoulderLeftXAcceleration() {
		return shoulderLeftXAcceleration;
	}

	public double getShoulderLeftYAcceleration() {
		return shoulderLeftYAcceleration;
	}

	public double getShoulderLeftZAcceleration() {
		return shoulderLeftZAcceleration;
	}

	public double getShoulderRightXAcceleration() {
		return shoulderRightXAcceleration;
	}

	public double getShoulderRightYAcceleration() {
		return shoulderRightYAcceleration;
	}

	public double getAnkleLeftXAcceleration() {
		return ankleLeftXAcceleration;
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
		return !this.equals(oldBody);
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
	
	private void calculateVelocities(boolean seatedMode) {
		// head
		headXVelocity = calculateVelocity(head.getPositionX(), oldBody.head.getPositionX(), timestamp, oldBody.getTimestamp());
		headYVelocity = calculateVelocity(head.getPositionY(), oldBody.head.getPositionY(), timestamp, oldBody.getTimestamp());
		headZVelocity = calculateVelocity(head.getPositionZ(), oldBody.head.getPositionZ(), timestamp, oldBody.getTimestamp());
		// shoulderCenter
		shoulderCenterXVelocity = calculateVelocity(shoulderCenter.getPositionX(), oldBody.shoulderCenter.getPositionX(), timestamp, oldBody.getTimestamp());
		shoulderCenterYVelocity = calculateVelocity(shoulderCenter.getPositionY(), oldBody.shoulderCenter.getPositionY(), timestamp, oldBody.getTimestamp());
		shoulderCenterZVelocity = calculateVelocity(shoulderCenter.getPositionZ(), oldBody.shoulderCenter.getPositionZ(), timestamp, oldBody.getTimestamp());
		if (!seatedMode) {
			// spine
			spineXVelocity = calculateVelocity(spine.getPositionX(), oldBody.spine.getPositionX(), timestamp, oldBody.getTimestamp());
			spineYVelocity = calculateVelocity(spine.getPositionY(), oldBody.spine.getPositionY(), timestamp, oldBody.getTimestamp());
			spineZVelocity = calculateVelocity(spine.getPositionZ(), oldBody.spine.getPositionZ(), timestamp, oldBody.getTimestamp());
			// hipCenter
			hipCenterXVelocity = calculateVelocity(hipCenter.getPositionX(), oldBody.hipCenter.getPositionX(), timestamp, oldBody.getTimestamp());
			hipCenterYVelocity = calculateVelocity(hipCenter.getPositionY(), oldBody.hipCenter.getPositionY(), timestamp, oldBody.getTimestamp());
			hipCenterZVelocity = calculateVelocity(hipCenter.getPositionZ(), oldBody.hipCenter.getPositionZ(), timestamp, oldBody.getTimestamp());
		}
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
		if (!seatedMode) {
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
	}
	
	private void calculateAccelerations(boolean seatedMode) {
		// head
		headXAcceleration = calculateAcceleration(head.getPositionX(), oldBody.getHead().getPositionX(), timestamp, oldBody.getTimestamp());
		headYAcceleration = calculateAcceleration(head.getPositionY(), oldBody.getHead().getPositionY(), timestamp, oldBody.getTimestamp());
		headZAcceleration = calculateAcceleration(head.getPositionZ(), oldBody.getHead().getPositionZ(), timestamp, oldBody.getTimestamp());
		// shoulderCenter
		shoulderCenterXAcceleration = calculateAcceleration(shoulderCenter.getPositionX(), oldBody.getShoulderCenter().getPositionX(), timestamp, oldBody.getTimestamp());
		shoulderCenterXAcceleration = calculateAcceleration(shoulderCenter.getPositionY(), oldBody.getShoulderCenter().getPositionY(), timestamp, oldBody.getTimestamp());
		shoulderCenterXAcceleration = calculateAcceleration(shoulderCenter.getPositionZ(), oldBody.getShoulderCenter().getPositionZ(), timestamp, oldBody.getTimestamp());
		if (!seatedMode) {
			// spine
			spineXAcceleration = calculateAcceleration(spine.getPositionX(), oldBody.getSpine().getPositionX(), timestamp, oldBody.getTimestamp());
			spineYAcceleration = calculateAcceleration(spine.getPositionY(), oldBody.getSpine().getPositionY(), timestamp, oldBody.getTimestamp());
			spineZAcceleration = calculateAcceleration(spine.getPositionZ(), oldBody.getSpine().getPositionZ(), timestamp, oldBody.getTimestamp());
			// hipCenter
			hipCenterXAcceleration = calculateAcceleration(hipCenter.getPositionX(), oldBody.getHipCenter().getPositionX(), timestamp, oldBody.getTimestamp());
			hipCenterYAcceleration = calculateAcceleration(hipCenter.getPositionY(), oldBody.getHipCenter().getPositionY(), timestamp, oldBody.getTimestamp());
			hipCenterZAcceleration = calculateAcceleration(hipCenter.getPositionZ(), oldBody.getHipCenter().getPositionZ(), timestamp, oldBody.getTimestamp());
		}
		// shoulderLeft
		shoulderLeftXAcceleration = calculateAcceleration(shoulderLeft.getPositionX(), oldBody.getShoulderLeft().getPositionX(), timestamp, oldBody.getTimestamp());
		shoulderLeftYAcceleration = calculateAcceleration(shoulderLeft.getPositionY(), oldBody.getShoulderLeft().getPositionY(), timestamp, oldBody.getTimestamp());
		shoulderLeftZAcceleration = calculateAcceleration(shoulderLeft.getPositionZ(), oldBody.getShoulderLeft().getPositionZ(), timestamp, oldBody.getTimestamp());
		// elbowLeft
		elbowLeftXVelocity = calculateAcceleration(elbowLeft.getPositionX(), oldBody.getElbowLeft().getPositionX(), timestamp, oldBody.getTimestamp());
		elbowLeftYVelocity = calculateAcceleration(elbowLeft.getPositionY(), oldBody.getElbowLeft().getPositionY(), timestamp, oldBody.getTimestamp());
		elbowLeftZVelocity = calculateAcceleration(elbowLeft.getPositionZ(), oldBody.getElbowLeft().getPositionZ(), timestamp, oldBody.getTimestamp());
		// wristLeft
		wristLeftXVelocity = calculateAcceleration(wristLeft.getPositionX(), oldBody.getWristLeft().getPositionX(), timestamp, oldBody.getTimestamp());
		wristLeftYVelocity = calculateAcceleration(wristLeft.getPositionY(), oldBody.getWristLeft().getPositionY(), timestamp, oldBody.getTimestamp());
		wristLeftZVelocity = calculateAcceleration(wristLeft.getPositionZ(), oldBody.getWristLeft().getPositionZ(), timestamp, oldBody.getTimestamp());
		// handLeft
		handLeftXAcceleration = calculateAcceleration(handLeft.getPositionX(), oldBody.getHandLeft().getPositionX(), timestamp, oldBody.getTimestamp());
		handLeftYAcceleration = calculateAcceleration(handLeft.getPositionY(), oldBody.getHandLeft().getPositionY(), timestamp, oldBody.getTimestamp());
		handLeftZAcceleration = calculateAcceleration(handLeft.getPositionZ(), oldBody.getHandLeft().getPositionZ(), timestamp, oldBody.getTimestamp());
		// shoulderRight
		shoulderRightXAcceleration = calculateAcceleration(shoulderRight.getPositionX(), oldBody.getShoulderRight().getPositionX(), timestamp, oldBody.getTimestamp());
		shoulderRightYAcceleration = calculateAcceleration(shoulderRight.getPositionY(), oldBody.getShoulderRight().getPositionY(), timestamp, oldBody.getTimestamp());
		shoulderRightZAcceleration = calculateAcceleration(shoulderRight.getPositionZ(), oldBody.getShoulderRight().getPositionZ(), timestamp, oldBody.getTimestamp());
		// elbowRight
		elbowRightXAcceleration = calculateAcceleration(elbowRight.getPositionX(), oldBody.getElbowRight().getPositionX(), timestamp, oldBody.getTimestamp());
		elbowRightYAcceleration = calculateAcceleration(elbowRight.getPositionY(), oldBody.getElbowRight().getPositionY(), timestamp, oldBody.getTimestamp());
		elbowRightZAcceleration = calculateAcceleration(elbowRight.getPositionZ(), oldBody.getElbowRight().getPositionZ(), timestamp, oldBody.getTimestamp());
		// wristRight
		wristRightXAcceleration = calculateAcceleration(wristRight.getPositionX(), oldBody.getWristRight().getPositionX(), timestamp, oldBody.getTimestamp());
		wristRightYAcceleration = calculateAcceleration(wristRight.getPositionY(), oldBody.getWristRight().getPositionY(), timestamp, oldBody.getTimestamp());
		wristRightZAcceleration = calculateAcceleration(wristRight.getPositionZ(), oldBody.getWristRight().getPositionZ(), timestamp, oldBody.getTimestamp());
		// handRight
		handRightXAcceleration = calculateAcceleration(handRight.getPositionX(), oldBody.getHandRight().getPositionX(), timestamp, oldBody.getTimestamp());
		handRightYAcceleration = calculateAcceleration(handRight.getPositionY(), oldBody.getHandRight().getPositionY(), timestamp, oldBody.getTimestamp());
		handRightZAcceleration = calculateAcceleration(handRight.getPositionZ(), oldBody.getHandRight().getPositionZ(), timestamp, oldBody.getTimestamp());
		if (!seatedMode) {
			// hipLeft
			hipLeftXAcceleration = calculateAcceleration(hipLeft.getPositionX(), oldBody.hipLeft.getPositionX(), timestamp, oldBody.getTimestamp());
			hipLeftYAcceleration = calculateAcceleration(hipLeft.getPositionY(), oldBody.hipLeft.getPositionY(), timestamp, oldBody.getTimestamp());
			hipLeftZAcceleration = calculateAcceleration(hipLeft.getPositionZ(), oldBody.hipLeft.getPositionZ(), timestamp, oldBody.getTimestamp());
			// kneeLeft
			kneeLeftXAcceleration = calculateAcceleration(kneeLeft.getPositionX(), oldBody.kneeLeft.getPositionX(), timestamp, oldBody.getTimestamp());
			kneeLeftYAcceleration = calculateAcceleration(kneeLeft.getPositionY(), oldBody.kneeLeft.getPositionY(), timestamp, oldBody.getTimestamp());
			kneeLeftZAcceleration = calculateAcceleration(kneeLeft.getPositionZ(), oldBody.kneeLeft.getPositionZ(), timestamp, oldBody.getTimestamp());
			// ankleLeft
			ankleLeftXAcceleration = calculateAcceleration(ankleLeft.getPositionX(), oldBody.ankleLeft.getPositionX(), timestamp, oldBody.getTimestamp());
			ankleLeftYAcceleration = calculateAcceleration(ankleLeft.getPositionY(), oldBody.ankleLeft.getPositionY(), timestamp, oldBody.getTimestamp());
			ankleLeftZAcceleration = calculateAcceleration(ankleLeft.getPositionZ(), oldBody.ankleLeft.getPositionZ(), timestamp, oldBody.getTimestamp());
			// footLeft
			footLeftXAcceleration = calculateAcceleration(footLeft.getPositionX(), oldBody.footLeft.getPositionX(), timestamp, oldBody.getTimestamp());
			footLeftYAcceleration = calculateAcceleration(footLeft.getPositionY(), oldBody.footLeft.getPositionY(), timestamp, oldBody.getTimestamp());
			footLeftZAcceleration = calculateAcceleration(footLeft.getPositionZ(), oldBody.footLeft.getPositionZ(), timestamp, oldBody.getTimestamp());
			// hipRight
			hipRightXAcceleration = calculateAcceleration(hipRight.getPositionX(), oldBody.hipRight.getPositionX(), timestamp, oldBody.getTimestamp());
			hipRightYAcceleration = calculateAcceleration(hipRight.getPositionY(), oldBody.hipRight.getPositionY(), timestamp, oldBody.getTimestamp());
			hipRightZAcceleration = calculateAcceleration(hipRight.getPositionZ(), oldBody.hipRight.getPositionZ(), timestamp, oldBody.getTimestamp());
			// kneeRight
			kneeRightXAcceleration = calculateAcceleration(kneeRight.getPositionX(), oldBody.kneeRight.getPositionX(), timestamp, oldBody.getTimestamp());
			kneeRightYAcceleration = calculateAcceleration(kneeRight.getPositionY(), oldBody.kneeRight.getPositionY(), timestamp, oldBody.getTimestamp());
			kneeRightZAcceleration = calculateAcceleration(kneeRight.getPositionZ(), oldBody.kneeRight.getPositionZ(), timestamp, oldBody.getTimestamp());
			// ankleRight
			ankleRightXAcceleration = calculateAcceleration(ankleRight.getPositionX(), oldBody.ankleRight.getPositionX(), timestamp, oldBody.getTimestamp());
			ankleRightYAcceleration = calculateAcceleration(ankleRight.getPositionY(), oldBody.ankleRight.getPositionY(), timestamp, oldBody.getTimestamp());
			ankleRightZAcceleration = calculateAcceleration(ankleRight.getPositionZ(), oldBody.ankleRight.getPositionZ(), timestamp, oldBody.getTimestamp());
			// footRight
			footRightXAcceleration = calculateAcceleration(footRight.getPositionX(), oldBody.footRight.getPositionX(), timestamp, oldBody.getTimestamp());
			footRightYAcceleration = calculateAcceleration(footRight.getPositionY(), oldBody.footRight.getPositionY(), timestamp, oldBody.getTimestamp());
			footRightZAcceleration = calculateAcceleration(footRight.getPositionZ(), oldBody.footRight.getPositionZ(), timestamp, oldBody.getTimestamp());
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
		if (Double.doubleToLongBits(ankleLeftXVelocity) != Double
				.doubleToLongBits(other.ankleLeftXVelocity))
			return false;
		if (Double.doubleToLongBits(ankleLeftYVelocity) != Double
				.doubleToLongBits(other.ankleLeftYVelocity))
			return false;
		if (Double.doubleToLongBits(ankleLeftZVelocity) != Double
				.doubleToLongBits(other.ankleLeftZVelocity))
			return false;
		if (ankleRight == null) {
			if (other.ankleRight != null)
				return false;
		} else if (!ankleRight.equals(other.ankleRight))
			return false;
		if (Double.doubleToLongBits(ankleRightXVelocity) != Double
				.doubleToLongBits(other.ankleRightXVelocity))
			return false;
		if (Double.doubleToLongBits(ankleRightYVelocity) != Double
				.doubleToLongBits(other.ankleRightYVelocity))
			return false;
		if (Double.doubleToLongBits(ankleRightZVelocity) != Double
				.doubleToLongBits(other.ankleRightZVelocity))
			return false;
		if (elbowLeft == null) {
			if (other.elbowLeft != null)
				return false;
		} else if (!elbowLeft.equals(other.elbowLeft))
			return false;
		if (Double.doubleToLongBits(elbowLeftXVelocity) != Double
				.doubleToLongBits(other.elbowLeftXVelocity))
			return false;
		if (Double.doubleToLongBits(elbowLeftYVelocity) != Double
				.doubleToLongBits(other.elbowLeftYVelocity))
			return false;
		if (Double.doubleToLongBits(elbowLeftZVelocity) != Double
				.doubleToLongBits(other.elbowLeftZVelocity))
			return false;
		if (elbowRight == null) {
			if (other.elbowRight != null)
				return false;
		} else if (!elbowRight.equals(other.elbowRight))
			return false;
		if (Double.doubleToLongBits(elbowRightXVelocity) != Double
				.doubleToLongBits(other.elbowRightXVelocity))
			return false;
		if (Double.doubleToLongBits(elbowRightYVelocity) != Double
				.doubleToLongBits(other.elbowRightYVelocity))
			return false;
		if (Double.doubleToLongBits(elbowRightZVelocity) != Double
				.doubleToLongBits(other.elbowRightZVelocity))
			return false;
		if (footLeft == null) {
			if (other.footLeft != null)
				return false;
		} else if (!footLeft.equals(other.footLeft))
			return false;
		if (Double.doubleToLongBits(footLeftXVelocity) != Double
				.doubleToLongBits(other.footLeftXVelocity))
			return false;
		if (Double.doubleToLongBits(footLeftYVelocity) != Double
				.doubleToLongBits(other.footLeftYVelocity))
			return false;
		if (Double.doubleToLongBits(footLeftZVelocity) != Double
				.doubleToLongBits(other.footLeftZVelocity))
			return false;
		if (footRight == null) {
			if (other.footRight != null)
				return false;
		} else if (!footRight.equals(other.footRight))
			return false;
		if (Double.doubleToLongBits(footRightXVelocity) != Double
				.doubleToLongBits(other.footRightXVelocity))
			return false;
		if (Double.doubleToLongBits(footRightYVelocity) != Double
				.doubleToLongBits(other.footRightYVelocity))
			return false;
		if (Double.doubleToLongBits(footRightZVelocity) != Double
				.doubleToLongBits(other.footRightZVelocity))
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
		if (Double.doubleToLongBits(handLeftXVelocity) != Double
				.doubleToLongBits(other.handLeftXVelocity))
			return false;
		if (Double.doubleToLongBits(handLeftYVelocity) != Double
				.doubleToLongBits(other.handLeftYVelocity))
			return false;
		if (Double.doubleToLongBits(handLeftZVelocity) != Double
				.doubleToLongBits(other.handLeftZVelocity))
			return false;
		if (handRight == null) {
			if (other.handRight != null)
				return false;
		} else if (!handRight.equals(other.handRight))
			return false;
		if (Double.doubleToLongBits(handRightXVelocity) != Double
				.doubleToLongBits(other.handRightXVelocity))
			return false;
		if (Double.doubleToLongBits(handRightYVelocity) != Double
				.doubleToLongBits(other.handRightYVelocity))
			return false;
		if (Double.doubleToLongBits(handRightZVelocity) != Double
				.doubleToLongBits(other.handRightZVelocity))
			return false;
		if (head == null) {
			if (other.head != null)
				return false;
		} else if (!head.equals(other.head))
			return false;
		if (Double.doubleToLongBits(headXVelocity) != Double
				.doubleToLongBits(other.headXVelocity))
			return false;
		if (Double.doubleToLongBits(headYVelocity) != Double
				.doubleToLongBits(other.headYVelocity))
			return false;
		if (Double.doubleToLongBits(headZVelocity) != Double
				.doubleToLongBits(other.headZVelocity))
			return false;
		if (hipCenter == null) {
			if (other.hipCenter != null)
				return false;
		} else if (!hipCenter.equals(other.hipCenter))
			return false;
		if (Double.doubleToLongBits(hipCenterXVelocity) != Double
				.doubleToLongBits(other.hipCenterXVelocity))
			return false;
		if (Double.doubleToLongBits(hipCenterYVelocity) != Double
				.doubleToLongBits(other.hipCenterYVelocity))
			return false;
		if (Double.doubleToLongBits(hipCenterZVelocity) != Double
				.doubleToLongBits(other.hipCenterZVelocity))
			return false;
		if (hipLeft == null) {
			if (other.hipLeft != null)
				return false;
		} else if (!hipLeft.equals(other.hipLeft))
			return false;
		if (Double.doubleToLongBits(hipLeftXVelocity) != Double
				.doubleToLongBits(other.hipLeftXVelocity))
			return false;
		if (Double.doubleToLongBits(hipLeftYVelocity) != Double
				.doubleToLongBits(other.hipLeftYVelocity))
			return false;
		if (Double.doubleToLongBits(hipLeftZVelocity) != Double
				.doubleToLongBits(other.hipLeftZVelocity))
			return false;
		if (hipRight == null) {
			if (other.hipRight != null)
				return false;
		} else if (!hipRight.equals(other.hipRight))
			return false;
		if (Double.doubleToLongBits(hipRightXVelocity) != Double
				.doubleToLongBits(other.hipRightXVelocity))
			return false;
		if (Double.doubleToLongBits(hipRightYVelocity) != Double
				.doubleToLongBits(other.hipRightYVelocity))
			return false;
		if (Double.doubleToLongBits(hipRightZVelocity) != Double
				.doubleToLongBits(other.hipRightZVelocity))
			return false;
		if (kneeLeft == null) {
			if (other.kneeLeft != null)
				return false;
		} else if (!kneeLeft.equals(other.kneeLeft))
			return false;
		if (Double.doubleToLongBits(kneeLeftXVelocity) != Double
				.doubleToLongBits(other.kneeLeftXVelocity))
			return false;
		if (Double.doubleToLongBits(kneeLeftYVelocity) != Double
				.doubleToLongBits(other.kneeLeftYVelocity))
			return false;
		if (Double.doubleToLongBits(kneeLeftZVelocity) != Double
				.doubleToLongBits(other.kneeLeftZVelocity))
			return false;
		if (kneeRight == null) {
			if (other.kneeRight != null)
				return false;
		} else if (!kneeRight.equals(other.kneeRight))
			return false;
		if (Double.doubleToLongBits(kneeRightXVelocity) != Double
				.doubleToLongBits(other.kneeRightXVelocity))
			return false;
		if (Double.doubleToLongBits(kneeRightYVelocity) != Double
				.doubleToLongBits(other.kneeRightYVelocity))
			return false;
		if (Double.doubleToLongBits(kneeRightZVelocity) != Double
				.doubleToLongBits(other.kneeRightZVelocity))
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
		if (Double.doubleToLongBits(shoulderCenterXVelocity) != Double
				.doubleToLongBits(other.shoulderCenterXVelocity))
			return false;
		if (Double.doubleToLongBits(shoulderCenterYVelocity) != Double
				.doubleToLongBits(other.shoulderCenterYVelocity))
			return false;
		if (Double.doubleToLongBits(shoulderCenterZVelocity) != Double
				.doubleToLongBits(other.shoulderCenterZVelocity))
			return false;
		if (shoulderLeft == null) {
			if (other.shoulderLeft != null)
				return false;
		} else if (!shoulderLeft.equals(other.shoulderLeft))
			return false;
		if (Double.doubleToLongBits(shoulderLeftXVelocity) != Double
				.doubleToLongBits(other.shoulderLeftXVelocity))
			return false;
		if (Double.doubleToLongBits(shoulderLeftYVelocity) != Double
				.doubleToLongBits(other.shoulderLeftYVelocity))
			return false;
		if (Double.doubleToLongBits(shoulderLeftZVelocity) != Double
				.doubleToLongBits(other.shoulderLeftZVelocity))
			return false;
		if (shoulderRight == null) {
			if (other.shoulderRight != null)
				return false;
		} else if (!shoulderRight.equals(other.shoulderRight))
			return false;
		if (Double.doubleToLongBits(shoulderRightXVelocity) != Double
				.doubleToLongBits(other.shoulderRightXVelocity))
			return false;
		if (Double.doubleToLongBits(shoulderRightYVelocity) != Double
				.doubleToLongBits(other.shoulderRightYVelocity))
			return false;
		if (Double.doubleToLongBits(shoulderRightZVelocity) != Double
				.doubleToLongBits(other.shoulderRightZVelocity))
			return false;
		if (spine == null) {
			if (other.spine != null)
				return false;
		} else if (!spine.equals(other.spine))
			return false;
		if (Double.doubleToLongBits(spineXVelocity) != Double
				.doubleToLongBits(other.spineXVelocity))
			return false;
		if (Double.doubleToLongBits(spineYVelocity) != Double
				.doubleToLongBits(other.spineYVelocity))
			return false;
		if (Double.doubleToLongBits(spineZVelocity) != Double
				.doubleToLongBits(other.spineZVelocity))
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
		if (Double.doubleToLongBits(wristLeftXVelocity) != Double
				.doubleToLongBits(other.wristLeftXVelocity))
			return false;
		if (Double.doubleToLongBits(wristLeftYVelocity) != Double
				.doubleToLongBits(other.wristLeftYVelocity))
			return false;
		if (Double.doubleToLongBits(wristLeftZVelocity) != Double
				.doubleToLongBits(other.wristLeftZVelocity))
			return false;
		if (wristRight == null) {
			if (other.wristRight != null)
				return false;
		} else if (!wristRight.equals(other.wristRight))
			return false;
		if (Double.doubleToLongBits(wristRightXVelocity) != Double
				.doubleToLongBits(other.wristRightXVelocity))
			return false;
		if (Double.doubleToLongBits(wristRightYVelocity) != Double
				.doubleToLongBits(other.wristRightYVelocity))
			return false;
		if (Double.doubleToLongBits(wristRightZVelocity) != Double
				.doubleToLongBits(other.wristRightZVelocity))
			return false;
		return true;
	}
	
	public Body clone() throws CloneNotSupportedException {
		return (Body) super.clone();
	}
	
}
