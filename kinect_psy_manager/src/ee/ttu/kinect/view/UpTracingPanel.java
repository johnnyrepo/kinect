package ee.ttu.kinect.view;

import ee.ttu.kinect.model.Joint;

public class UpTracingPanel extends TracingPanel {

	private static final long serialVersionUID = 1L;

	public UpTracingPanel() {
		super("Up View");
	}

	@Override
	protected int getXForGraph(Joint joint) {
		int centralPoint = -getWidth() / 2;
		return (int) (centralPoint + joint.getPositionZ() * getLongestSideSize());
	}

	@Override
	protected int getYForGraph(Joint joint) {
		int centralPoint = getHeight() / 2;
		return centralPoint - (int) (joint.getPositionX() * getLongestSideSize());
	}

}
