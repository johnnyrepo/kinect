package ee.ttu.kinect.view;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.Joint;
import ee.ttu.kinect.model.JointType;

public class UpTracingPanel extends TracingPanel {

	private static final long serialVersionUID = 1L;

	public UpTracingPanel() {
		super("Up View");
	}

	@Override
	protected void setSpinePosition(Body body) {
		Joint spine = body.getJoint(JointType.SPINE);
		spineX = spine.getPositionZ();
		spineY = spine.getPositionX();
	}
	
	@Override
	protected int getXForGraph(Joint joint) {
//		int centralPoint = -getWidth() / 2;
//		return (int) (centralPoint + joint.getPositionZ() * getLongestSideSize());
		
		if (joint.getType() != JointType.SPINE) {
			return (int) ((joint.getPositionZ() - spineX) * getLongestSideSize() + getWidth() / 2);
		} else {
			return (int) (getWidth() / 2);
		}
	}

	@Override
	protected int getYForGraph(Joint joint) {
//		int centralPoint = getHeight() / 2;
//		return centralPoint - (int) (joint.getPositionX() * getLongestSideSize());
		
		if (joint.getType() != JointType.SPINE) {
			return (int) (-(joint.getPositionX() - spineY) * getLongestSideSize() + getHeight() / 2);
		} else {
			return (int) (getHeight() / 2);
		}
	}

}
