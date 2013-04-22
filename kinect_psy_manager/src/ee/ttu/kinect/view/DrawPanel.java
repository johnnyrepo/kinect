package ee.ttu.kinect.view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.Joint;

public abstract class DrawPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	protected Graphics2D graphics;

	private JLabel coordLabel;

	private Body body;

	private boolean seatedMode;

	protected DrawPanel(String title) {
		Border border = BorderFactory.createEtchedBorder();
		setBorder(BorderFactory.createTitledBorder(border, title));
		coordLabel = new JLabel();
		add(coordLabel);
	}

	public void redrawSkeleton(Body body, boolean seatedMode) {
		this.body = body;
		this.seatedMode = seatedMode;
		
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

	abstract protected void drawJointLine(Joint joint1, Joint joint2);
	
	abstract protected void drawJoint(Joint joint);

	abstract protected void drawHead(Joint head);

	protected int getXForGraph(Joint joint) {
		int centralPoint = getWidth() / 2;
		return centralPoint - (int) (joint.getPositionX() * 200);
	}

	protected int getYForGraph(Joint joint) {
		int centralPoint = getWidth() / 2;
		return centralPoint - (int) (joint.getPositionY() * 200);
	}

	protected int getZForGraph(Joint joint) {
		return (int) (joint.getPositionZ() * 200);
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
