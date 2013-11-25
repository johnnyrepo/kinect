package ee.ttu.kinect.model;

public class Markers {

	private boolean[] state;

	private int amount;

	public void reset() {
		state = new boolean[0];
		amount = state.length;
	}

	public boolean[] getState() {
		return state;
	}

	public void setState(boolean[] stateArr) {
		adjustMarkersAmount(stateArr.length);

		for (int i = 0; i < state.length; i++) {
			if (i < stateArr.length) {
				state[i] = stateArr[i];
			} else {
				state[i] = false;
			}
		}
	}

	private void adjustMarkersAmount(int newAmount) {
		if (amount < newAmount) {
			amount = newAmount;
		}
		
		state = new boolean[amount];
	}

}
