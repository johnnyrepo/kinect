package ee.ttu.kinect.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.Joint;

public abstract class TracingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected Graphics2D graphics;

	protected Body body;

	private boolean seatedMode;
	
	private final int[] zoom = {100, 125, 150, 175, 200};
	
	private int zoomStep = this.zoom.length - 1;

	protected TracingPanel(String title) {
		Border border = BorderFactory.createEtchedBorder();
		this.setBorder(BorderFactory.createTitledBorder(border, title));
	}

	public void redrawSkeleton(Body body, boolean seatedMode) {
		this.body = body;
		this.seatedMode = seatedMode;
		
		this.repaint();
	}
	
	@Override
	public synchronized void paint(Graphics g) {
		super.paint(g);
		if (this.body != null && this.body.isBodyReady()) {
			// draw joint connection
			this.graphics = (Graphics2D) g;
			this.drawJointLine(this.body.getHead(), this.body.getShoulderCenter());
			this.drawJointLine(this.body.getShoulderCenter(), this.body.getShoulderLeft());
			this.drawJointLine(this.body.getShoulderLeft(), this.body.getElbowLeft());
			this.drawJointLine(this.body.getElbowLeft(), this.body.getWristLeft());
			this.drawJointLine(this.body.getWristLeft(), this.body.getHandLeft());
			this.drawJointLine(this.body.getShoulderCenter(), this.body.getShoulderRight());
			this.drawJointLine(this.body.getShoulderRight(), this.body.getElbowRight());
			this.drawJointLine(this.body.getElbowRight(), this.body.getWristRight());
			this.drawJointLine(this.body.getWristRight(), this.body.getHandRight());
			
			// draw joint
			this.drawJoint(this.body.getShoulderCenter());
			this.drawJoint(this.body.getShoulderLeft());
			this.drawJoint(this.body.getShoulderRight());
			this.drawJoint(this.body.getElbowLeft());
			this.drawJoint(this.body.getElbowRight());
			this.drawJoint(this.body.getWristLeft());
			this.drawJoint(this.body.getWristRight());
			this.drawJoint(this.body.getHandLeft());
			this.drawJoint(this.body.getHandRight());
			
			if (!this.seatedMode) {
				this.drawJointLine(this.body.getShoulderCenter(), this.body.getSpine());
				this.drawJointLine(this.body.getSpine(), this.body.getHipCenter());
				this.drawJointLine(this.body.getHipCenter(), this.body.getHipLeft());
				this.drawJointLine(this.body.getHipLeft(), this.body.getKneeLeft());
				this.drawJointLine(this.body.getKneeLeft(), this.body.getAnkleLeft());
				this.drawJointLine(this.body.getAnkleLeft(), this.body.getFootLeft());
				this.drawJointLine(this.body.getHipCenter(), this.body.getHipRight());
				this.drawJointLine(this.body.getHipRight(), this.body.getKneeRight());
				this.drawJointLine(this.body.getKneeRight(), this.body.getAnkleRight());
				this.drawJointLine(this.body.getAnkleRight(), this.body.getFootRight());
				
				this.drawJoint(this.body.getSpine());
				this.drawJoint(this.body.getHipCenter());
				this.drawJoint(this.body.getHipLeft());
				this.drawJoint(this.body.getHipRight());
				this.drawJoint(this.body.getKneeLeft());
				this.drawJoint(this.body.getKneeRight());
				this.drawJoint(this.body.getAnkleLeft());
				this.drawJoint(this.body.getAnkleRight());
				this.drawJoint(this.body.getFootLeft());
				this.drawJoint(this.body.getFootRight());
			}

			this.drawHead(this.body.getHead());
		}
	}

	public void clear() {
		this.body = null;
		this.repaint();
	}
	
	protected void drawJointLine(Joint joint1, Joint joint2) {
		if (joint1 != null && joint2 != null && this.graphics != null) {
			int x1 = this.getXForGraph(joint1);
			int y1 = this.getYForGraph(joint1);
			int x2 = this.getXForGraph(joint2);
			int y2 = this.getYForGraph(joint2);
			this.graphics.drawLine(x1, y1, x2, y2);
		}
	}

	protected void drawHead(Joint head)  {
		int x = this.getXForGraph(head);
		int y = this.getYForGraph(head);
		this.graphics.drawOval(x - 10, y - 10, 20, 20);
	}

	protected void drawJoint(Joint joint) {
		if (joint != null) {
			int x = this.getXForGraph(joint);
			int y = this.getYForGraph(joint);
			this.graphics.setColor(new Color(0, 160, 0));
			this.graphics.fillOval(x - 3, y - 3, 6, 6);
			this.graphics.setColor(Color.BLACK);
		}
	}
	
	abstract protected int getXForGraph(Joint joint);

	abstract protected int getYForGraph(Joint joint);
	
	protected void zoomIn() {
		if (++this.zoomStep >= this.zoom.length) {
			this.zoomStep = this.zoom.length - 1;
		}
	}
	
	protected void zoomOut() {
		if (--this.zoomStep < 0) {
			this.zoomStep = 0;
		}
	}
	
	protected int getZoomValue() {
		return this.zoom[this.zoomStep];
	}

}
