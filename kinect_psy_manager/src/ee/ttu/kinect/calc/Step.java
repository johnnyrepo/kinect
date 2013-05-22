package ee.ttu.kinect.calc;

import java.util.ArrayList;
import java.util.List;

import com.stromberglabs.cluster.Clusterable;

public class Step implements Clusterable {

	private List<Double> elements = new ArrayList<Double>();

	private int clusterId;

	private long timestamp;

	public void addElement(double element) {
		elements.add(element);
	}
	
	public List<Double> getElements() {
		return elements;
	}
	
	public double[] getElementsAsArr() {
		double[] elementsArr = new double[elements.size()];
		for (int i = 0; i < elements.size(); i++) {
			elementsArr[i] = elements.get(i).doubleValue();
		}
		return elementsArr;
	}
	
	public void setClusterId(int id) {
		this.clusterId = id;
	}

	public int getClusterId() {
		return clusterId;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getTimestamp() {
		return timestamp;
	}
	
	@Override
	public float[] getLocation() {
		float[] elementsArr = new float[elements.size()];
		for (int i = 0; i < elements.size(); i++) {
			elementsArr[i] = elements.get(i).floatValue();
		}
		return elementsArr;
	}

}
