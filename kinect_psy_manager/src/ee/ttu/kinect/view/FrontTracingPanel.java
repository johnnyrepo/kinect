package ee.ttu.kinect.view;

import ee.ttu.kinect.model.Joint;

public class FrontTracingPanel extends TracingPanel {

	private static final long serialVersionUID = 1L;

	public FrontTracingPanel() {
		super("Front View");
	}		

	protected int getXForGraph(Joint joint) {
		int centralPoint = getWidth() / 2;
		return centralPoint + (int) (joint.getPositionX() * getLongestSideSize());
	}

	protected int getYForGraph(Joint joint) {
		int centralPoint = getHeight() / 2;
		return centralPoint - (int) (joint.getPositionY() * getLongestSideSize());
	}
	
}
