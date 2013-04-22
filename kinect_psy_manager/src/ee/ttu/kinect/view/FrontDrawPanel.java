package ee.ttu.kinect.view;

import java.awt.Color;

import ee.ttu.kinect.model.Joint;

public class FrontDrawPanel extends DrawPanel {


	private static final long serialVersionUID = 1L;

	public FrontDrawPanel() {
		super("Front View");
	}

	@Override
	protected void drawJointLine(Joint joint1, Joint joint2) {
		if (joint1 != null && joint2 != null && graphics != null) {
			int x1 = getXForGraph(joint1);
			int y1 = getYForGraph(joint1);
			int x2 = getXForGraph(joint2);
			int y2 = getYForGraph(joint2);
			graphics.drawLine(x1, y1, x2, y2);
		}
	}

	@Override
	protected void drawHead(Joint head)  {
		int x = getXForGraph(head);
		int y = getYForGraph(head);
		graphics.drawOval(x - 10, y - 20, 20, 20);
	}

	@Override
	protected void drawJoint(Joint joint) {
		int x = getXForGraph(joint);
		int y = getYForGraph(joint);
		graphics.setColor(Color.MAGENTA);
		graphics.fillOval(x - 3, y - 3, 6, 6);
		graphics.setColor(Color.BLACK);
	}		

}
