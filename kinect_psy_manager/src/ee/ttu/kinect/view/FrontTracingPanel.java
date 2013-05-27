package ee.ttu.kinect.view;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.Joint;
import ee.ttu.kinect.model.JointType;

public class FrontTracingPanel extends TracingPanel {

	private static final long serialVersionUID = 1L;
	
	public FrontTracingPanel() {
		super("Front View");
	}		

	@Override
	protected void setSpinePosition(Body body) {
		Joint spine = body.getJoint(JointType.SPINE);
		spineX = spine.getPositionX();
		spineY = spine.getPositionY();
	}
	
	@Override
	protected int getXForGraph(Joint joint) {	
		if (joint.getType() != JointType.SPINE) {
			return (int) ((joint.getPositionX() - spineX) * getLongestSideSize() + getWidth() / 2);
		} else {
			return (int) (getWidth() / 2);
		}
	}

	@Override
	protected int getYForGraph(Joint joint) {
		if (joint.getType() != JointType.SPINE) {
			return (int) (-(joint.getPositionY() - spineY) * getLongestSideSize() + getHeight() / 2);
		} else {
			return (int) (getHeight() / 2);
		}
	}
	
}
