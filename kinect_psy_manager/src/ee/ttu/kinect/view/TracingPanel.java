package ee.ttu.kinect.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.Joint;

public abstract class TracingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	protected double spineX;

	protected double spineY;
	
	protected Graphics2D graphics;

	private JLabel coordLabel;

	private Body body;

	private boolean seatedMode;
	
	private int[] zoom = {100, 150, 200};
	
	private int zoomStep = zoom.length - 1;

	protected TracingPanel(String title) {
		Border border = BorderFactory.createEtchedBorder();
		setBorder(BorderFactory.createTitledBorder(border, title));
		coordLabel = new JLabel();
		add(coordLabel, BorderLayout.NORTH);
	}

	public void redrawSkeleton(Body body, boolean seatedMode) {
		this.body = body;
		this.seatedMode = seatedMode;
		
		setSpinePosition(body);
		
		repaint();
	}
	
	@Override
	public synchronized void paint(Graphics g) {
		super.paint(g);
		if (body != null && body.isBodyReady()) {
			// draw joint connection
			this.graphics = (Graphics2D) g;
			drawJointLine(body.getHead(), body.getShoulderCenter());
			drawJointLine(body.getShoulderCenter(), body.getShoulderLeft());
			drawJointLine(body.getShoulderLeft(), body.getElbowLeft());
			drawJointLine(body.getElbowLeft(), body.getWristLeft());
			drawJointLine(body.getWristLeft(), body.getHandLeft());
			drawJointLine(body.getShoulderCenter(), body.getShoulderRight());
			drawJointLine(body.getShoulderRight(), body.getElbowRight());
			drawJointLine(body.getElbowRight(), body.getWristRight());
			drawJointLine(body.getWristRight(), body.getHandRight());
			
			// draw joint
			drawJoint(body.getShoulderCenter());
			drawJoint(body.getShoulderLeft());
			drawJoint(body.getShoulderLeft());
			drawJoint(body.getShoulderRight());
			drawJoint(body.getElbowLeft());
			drawJoint(body.getElbowRight());
			drawJoint(body.getWristLeft());
			drawJoint(body.getWristRight());
			drawJoint(body.getHandLeft());
			drawJoint(body.getHandRight());
			
			if (!seatedMode) {
				drawJointLine(body.getShoulderCenter(), body.getSpine());
				drawJointLine(body.getSpine(), body.getHipCenter());
				drawJointLine(body.getHipCenter(), body.getHipLeft());
				drawJointLine(body.getHipLeft(), body.getKneeLeft());
				drawJointLine(body.getKneeLeft(), body.getAnkleLeft());
				drawJointLine(body.getAnkleLeft(), body.getFootLeft());
				drawJointLine(body.getHipCenter(), body.getHipRight());
				drawJointLine(body.getHipRight(), body.getKneeRight());
				drawJointLine(body.getKneeRight(), body.getAnkleRight());
				drawJointLine(body.getAnkleRight(), body.getFootRight());
				
				drawJoint(body.getSpine());
				drawJoint(body.getHipCenter());
				drawJoint(body.getHipLeft());
				drawJoint(body.getHipRight());
				drawJoint(body.getKneeLeft());
				drawJoint(body.getKneeRight());
				drawJoint(body.getAnkleLeft());
				drawJoint(body.getAnkleRight());
				drawJoint(body.getFootLeft());
				drawJoint(body.getFootRight());
			}

			drawHead(body.getHead());
			updateCoordLabel(body.getHead());
		}
	}

	public void clear() {
		this.body = null;
		repaint();
	}
	
	protected void drawJointLine(Joint joint1, Joint joint2) {
		if (joint1 != null && joint2 != null && graphics != null) {
			int x1 = getXForGraph(joint1);
			int y1 = getYForGraph(joint1);
			int x2 = getXForGraph(joint2);
			int y2 = getYForGraph(joint2);
			graphics.drawLine(x1, y1, x2, y2);
		}
	}

	protected void drawHead(Joint head)  {
		int x = getXForGraph(head);
		int y = getYForGraph(head);
		graphics.drawOval(x - 10, y - 10, 20, 20);
	}

	protected void drawJoint(Joint joint) {
		if (joint != null) {
			int x = getXForGraph(joint);
			int y = getYForGraph(joint);
			graphics.setColor(new Color(0, 160, 0));
			graphics.fillOval(x - 3, y - 3, 6, 6);
			graphics.setColor(Color.BLACK);
		}
	}

	abstract protected void setSpinePosition(Body body);
	
	abstract protected int getXForGraph(Joint joint);

	abstract protected int getYForGraph(Joint joint);
	
	protected void zoomIn() {
		if (++zoomStep >= zoom.length) {
			zoomStep = zoom.length - 1;
		}
	}
	
	protected void zoomOut() {
		if (--zoomStep < 0) {
			zoomStep = 0;
		}
	}
	
	protected int getZoomValue() {
		return zoom[zoomStep];
	}
	
	private void updateCoordLabel(Joint joint) {
		if (joint != null) {
			double x = (double) Math.round(joint.getPositionX() * 100) / 100;
			double y = (double) Math.round(joint.getPositionY() * 100) / 100;
			double z = (double) Math.round(joint.getPositionZ() * 100) / 100;
			coordLabel.setText("X = " + x + ", Y = " + y + ", Z = " + z);
		}
	}

}
