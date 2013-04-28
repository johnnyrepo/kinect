package ee.ttu.kinect.view;

import java.awt.Color;

import ee.ttu.kinect.model.Joint;

public class SideTracingPanel extends TracingPanel {

	private static final long serialVersionUID = 1L;

	public SideTracingPanel() {
		super("Side View");
	}

	@Override
	protected void drawJointLine(Joint joint1, Joint joint2) {
		if (joint1 != null && joint2 != null && graphics != null) {
			int y1 = getYForGraph(joint1);
			int z1 = getZForGraph(joint1);
			int y2 = getYForGraph(joint2);
			int z2 = getZForGraph(joint2);
			graphics.drawLine(z1, y1, z2, y2);
		}
	}

	@Override
	protected void drawHead(Joint head) {
		int y = getYForGraph(head);
		int z = getZForGraph(head);
		graphics.drawOval(z - 10, y - 20, 20, 20);
	}
	
	@Override
	protected void drawJoint(Joint joint) {
		int y = getYForGraph(joint);
		int z = getZForGraph(joint);
		graphics.setColor(new Color(0, 160, 0));
		graphics.fillOval(z - 3, y - 3, 6, 6);
		graphics.setColor(Color.BLACK);
	}	

}
