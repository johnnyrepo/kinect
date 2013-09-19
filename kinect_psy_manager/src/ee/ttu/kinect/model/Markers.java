package ee.ttu.kinect.model;

public class Markers {

	private boolean[] state = new boolean[0];

	public boolean[] getState() {
		return state;
	}

	public void setState(boolean[] stateArr) {
		state = new boolean[stateArr.length];

		for (int i = 0; i < state.length; i++) {
			if (i < stateArr.length) {
				state[i] = stateArr[i];
			}
		}
	}

}
