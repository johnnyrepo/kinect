package ee.ttu.kinect.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import ee.ttu.kinect.model.Frame;
import ee.ttu.kinect.model.Joint;

public abstract class TracingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected Graphics2D graphics;

	protected Frame frame;

	private boolean seatedMode;
	
	private final int[] zoom = {100, 125, 150, 175, 200};
	
	private int zoomStep = this.zoom.length - 1;

	protected TracingPanel(String title) {
		Border border = BorderFactory.createEtchedBorder();
		this.setBorder(BorderFactory.createTitledBorder(border, title));
	}

	public void redrawSkeleton(Frame frame, boolean seatedMode) {
		this.frame = frame;
		this.seatedMode = seatedMode;
		
		this.repaint();
	}
	
	@Override
	public synchronized void paint(Graphics g) {
		super.paint(g);
		if (this.frame != null && this.frame.isFrameReady()) {
			// draw joint connection
			this.graphics = (Graphics2D) g;
			this.drawJointLine(this.frame.getHead(), this.frame.getShoulderCenter());
			this.drawJointLine(this.frame.getShoulderCenter(), this.frame.getShoulderLeft());
			this.drawJointLine(this.frame.getShoulderLeft(), this.frame.getElbowLeft());
			this.drawJointLine(this.frame.getElbowLeft(), this.frame.getWristLeft());
			this.drawJointLine(this.frame.getWristLeft(), this.frame.getHandLeft());
			this.drawJointLine(this.frame.getShoulderCenter(), this.frame.getShoulderRight());
			this.drawJointLine(this.frame.getShoulderRight(), this.frame.getElbowRight());
			this.drawJointLine(this.frame.getElbowRight(), this.frame.getWristRight());
			this.drawJointLine(this.frame.getWristRight(), this.frame.getHandRight());
			
			// draw joint
			this.drawJoint(this.frame.getShoulderCenter());
			this.drawJoint(this.frame.getShoulderLeft());
			this.drawJoint(this.frame.getShoulderRight());
			this.drawJoint(this.frame.getElbowLeft());
			this.drawJoint(this.frame.getElbowRight());
			this.drawJoint(this.frame.getWristLeft());
			this.drawJoint(this.frame.getWristRight());
			this.drawJoint(this.frame.getHandLeft());
			this.drawJoint(this.frame.getHandRight());
			
			if (!this.seatedMode) {
				this.drawJointLine(this.frame.getShoulderCenter(), this.frame.getSpine());
				this.drawJointLine(this.frame.getSpine(), this.frame.getHipCenter());
				this.drawJointLine(this.frame.getHipCenter(), this.frame.getHipLeft());
				this.drawJointLine(this.frame.getHipLeft(), this.frame.getKneeLeft());
				this.drawJointLine(this.frame.getKneeLeft(), this.frame.getAnkleLeft());
				this.drawJointLine(this.frame.getAnkleLeft(), this.frame.getFootLeft());
				this.drawJointLine(this.frame.getHipCenter(), this.frame.getHipRight());
				this.drawJointLine(this.frame.getHipRight(), this.frame.getKneeRight());
				this.drawJointLine(this.frame.getKneeRight(), this.frame.getAnkleRight());
				this.drawJointLine(this.frame.getAnkleRight(), this.frame.getFootRight());
				
				this.drawJoint(this.frame.getSpine());
				this.drawJoint(this.frame.getHipCenter());
				this.drawJoint(this.frame.getHipLeft());
				this.drawJoint(this.frame.getHipRight());
				this.drawJoint(this.frame.getKneeLeft());
				this.drawJoint(this.frame.getKneeRight());
				this.drawJoint(this.frame.getAnkleLeft());
				this.drawJoint(this.frame.getAnkleRight());
				this.drawJoint(this.frame.getFootLeft());
				this.drawJoint(this.frame.getFootRight());
			}

			this.drawHead(this.frame.getHead());
		}
	}

	public void clear() {
		this.frame = null;
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
