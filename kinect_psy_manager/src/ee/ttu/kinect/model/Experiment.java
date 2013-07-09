package ee.ttu.kinect.model;

import java.util.ArrayList;
import java.util.List;

public class Experiment {

	private String id;
		
	private List<Motion> motions;
	
	public Experiment(String id) {
		this.id = id;
		this.motions = new ArrayList<Experiment.Motion>();
	}
	
	public String getId() {
		return id;
	}
	
	public void addMotion(List<Body> motionData) {
		Motion motion = new Motion();
		motion.setData(motionData);
		motions.add(motion);
	}
	
	public void setLastMotionCorrect(boolean correct) {
		motions.get(motions.size() - 1).setCorrect(correct);		
	}
	
	private class Motion {
		
		private List<Body> data;
		
		private boolean correct;

		public List<Body> getData() {
			return data;
		}

		public void setData(List<Body> data) {
			this.data = data;
		}

		public boolean isCorrect() {
			return correct;
		}

		public void setCorrect(boolean correct) {
			this.correct = correct;
		}
		
	}
	
}
