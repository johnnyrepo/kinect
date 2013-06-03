package ee.ttu.kinect.view;

import java.awt.BorderLayout;

import ee.ttu.kinect.model.Joint;

public class UpTracingPanel extends TracingPanel {

	private static final long serialVersionUID = 1L;
	
	public UpTracingPanel() {
		super("Up View");
	}

	@Override
	protected int getXForGraph(Joint joint) {
		int centralPoint = 0;//-getWidth() / 2;
		return centralPoint + (int) (joint.getPositionZ() * this.getZoomValue());
		
//		if (joint.getType() != JointType.SPINE) {
//			return (int) ((joint.getPositionZ() - spineX) * getLongestSideSize() + getWidth() / 2);
//		} else {
//			return (int) (getWidth() / 2);
//		}
	}

	@Override
	protected int getYForGraph(Joint joint) {
		int centralPoint = this.getHeight() / 2;
		return centralPoint - (int) (joint.getPositionX() * this.getZoomValue());
		
//		if (joint.getType() != JointType.SPINE) {
//			return (int) (-(joint.getPositionX() - spineY) * getLongestSideSize() + getHeight() / 2);
//		} else {
//			return (int) (getHeight() / 2);
//		}
	}

	public void addCorrectionPanel(CorrectionPanel correctionPanel) {
		this.add(correctionPanel, BorderLayout.NORTH);
	}

}
