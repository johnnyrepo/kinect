package ee.ttu.kinect.model.experiment;

import ee.ttu.kinect.calc.Step;
import ee.ttu.kinect.model.experiment.Experiment.Motion;

public class MotionStep extends Step {

	private Motion motion;
	
	public void setMotion(Motion motion) {
		this.motion = motion;
	}
	
	public Motion getMotion() {
		return motion;
	}
	
}
