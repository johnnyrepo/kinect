package ee.ttu.kinect.view;

import java.awt.BorderLayout;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.Joint;
import ee.ttu.kinect.model.JointType;

public class SideTracingPanel extends TracingPanel {

	private static final long serialVersionUID = 1L;

	public SideTracingPanel() {
		super("Side View");
	}

	@Override
	protected void setSpinePosition(Body body) {
		Joint spine = body.getJoint(JointType.SPINE);
		spineX = spine.getPositionZ();
		spineY = spine.getPositionY();
	}
	
	@Override
	protected int getXForGraph(Joint joint) {
		int centralPoint = 0;//-getWidth() / 2;
		return centralPoint + (int) (joint.getPositionZ() * getZoomValue());
		
//		if (joint.getType() != JointType.SPINE) {
//			return (int) ((joint.getPositionZ() - spineX) * getLongestSideSize() + getWidth() / 2);
//		} else {
//			return (int) (getWidth() / 2);
//		}
	}

	@Override
	protected int getYForGraph(Joint joint) {
		int centralPoint = getHeight() / 2;
		return centralPoint - (int) (joint.getPositionY() * getZoomValue());
		
//		if (joint.getType() != JointType.SPINE) {
//			return (int) (-(joint.getPositionY() - spineY) * getLongestSideSize() + getHeight() / 2);
//		} else {
//			return (int) (getHeight() / 2);
//		}
	}
	
	public void addZoomPanel(ZoomPanel zoomPanel) {
		add(zoomPanel, BorderLayout.SOUTH);
	}

}
