package ee.ttu.kinect.view;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JLabel;

import ee.ttu.kinect.model.Joint;

public class FrontTracingPanel extends TracingPanel {

	private static final long serialVersionUID = 1L;

	private final JLabel coordLabel;
	
	public FrontTracingPanel() {
		super("Front View");
		
	    this.coordLabel = new JLabel();
	    this.add(this.coordLabel, BorderLayout.NORTH);
	}

	@Override
	protected int getXForGraph(Joint joint) {		
		int centralPoint = this.getWidth() / 2;
		return centralPoint + (int) (joint.getPositionX() * this.getZoomValue());
		
//		if (joint.getType() != JointType.SPINE) {
//			return (int) ((joint.getPositionX() - spineX) * getLongestSideSize() + getWidth() / 2);
//		} else {
//			return (int) (getWidth() / 2);
//		}
	}

	@Override
	protected int getYForGraph(Joint joint) {
		int centralPoint = this.getHeight() / 2;
		return centralPoint - (int) (joint.getPositionY() * this.getZoomValue()); 

//		if (joint.getType() != JointType.SPINE) {
//			return (int) (-(joint.getPositionY() - spineY) * getLongestSideSize() + getHeight() / 2);
//		} else {
//			return (int) (getHeight() / 2);
//		}
	}
	
	@Override
    public synchronized void paint(Graphics g) {
	    super.paint(g);
	    
	    if (this.body != null && this.body.isBodyReady()) {
	        this.updateCoordLabel(this.body.getHead());
	    }
	}

   private void updateCoordLabel(Joint joint) {
        if (joint != null) {
            double x = (double) Math.round(joint.getPositionX() * 100) / 100;
            double y = (double) Math.round(joint.getPositionY() * 100) / 100;
            double z = (double) Math.round(joint.getPositionZ() * 100) / 100;
            this.coordLabel.setText("X = " + x + ", Y = " + y + ", Z = " + z);
        }
    }
	
}
