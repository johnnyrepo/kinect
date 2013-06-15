package ee.ttu.kinect.view.chart;

import java.util.List;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.JointType;

public class ModelChart extends Chart {

	private static final long serialVersionUID = 1L;

	public ModelChart() {
		super("Velocity/Acceleration", "Time");
	}
	
	public void drawChart(List<Body> data, List<JointType> selectedTypes,
			boolean seatedMode) {
		String chartTitle = "";
		for (JointType selectedType : selectedTypes) {
			ModelSeriesComponent sc = new ModelSeriesComponent(dataset);
			add(sc);
			sc.setLabels(selectedType);
			
			for (Body body : data) {
				if (body == null || !body.isBodyReady()) {
					continue;
				}
				sc.updateSeries(body.getJoint(selectedType), body.getTimestamp(), seatedMode);
			}
			
			chartTitle += selectedType.getName() + " ";
		}
		chart.setTitle(chartTitle);
	}
	
}
