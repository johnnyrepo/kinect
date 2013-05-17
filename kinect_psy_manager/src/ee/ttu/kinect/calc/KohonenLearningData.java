package ee.ttu.kinect.calc;

import java.util.ArrayList;
import java.util.List;

import kohonen.LearningDataModel;

public class KohonenLearningData implements LearningDataModel {

	private ArrayList <double[]> dataList = new ArrayList<double[]>();
	
	public KohonenLearningData(List<Vector> data) {
		for (Vector v : data) {
			dataList.add(v.getElementsAsArr());
		}
	}

	@Override
	public double[] getData(int index) {
		return dataList.get(index);
	}

	@Override
	public int getDataSize() {
		return dataList.size();
	}

}
