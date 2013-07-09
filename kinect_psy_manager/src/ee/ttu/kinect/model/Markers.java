package ee.ttu.kinect.model;

public class Markers {

	private boolean[] state = new boolean[5];
	
	public void setState(boolean[] stateArr) {
		for (int i = 0; i < state.length; i++) {
			if (i < stateArr.length) {
				state[i] = stateArr[i];
			}
		}
	}
	
	public boolean[] getState() {
		return state;
	}
	
}
