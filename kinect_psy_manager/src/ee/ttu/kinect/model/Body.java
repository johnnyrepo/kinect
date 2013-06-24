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

	public void archive() {
		if (isBodyReady()) {
			try {
				oldBody = clone();
			} catch (CloneNotSupportedException e) {
				logger.info(e.getLocalizedMessage());
			}
		}
	}
	
	public Body getOldBody() {
		return oldBody;
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
