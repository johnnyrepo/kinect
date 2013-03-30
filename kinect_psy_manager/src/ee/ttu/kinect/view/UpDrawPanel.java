package ee.ttu.kinect.view;

import ee.ttu.kinect.model.Joint;

public class UpDrawPanel extends DrawPanel {

	private static final long serialVersionUID = 1L;

	public UpDrawPanel() {
		super("Up View");
	}

	@Override
	protected void drawJointLine(Joint joint1, Joint joint2) {
		if (joint1 != null && joint2 != null && graphics != null) {
			int x1 = getXForGraph(joint1);
			int z1 = getZForGraph(joint1);
			int x2 = getXForGraph(joint2);
			int z2 = getZForGraph(joint2);
			graphics.drawLine(z1, x1, z2, x2);
		}
	}

	@Override
	protected void drawHead(Joint head) {
		int x = getXForGraph(head);
		int z = getZForGraph(head);
		graphics.drawOval(z - 10, x - 10, 20, 20);
	}

}
