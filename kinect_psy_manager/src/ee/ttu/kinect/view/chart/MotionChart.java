package ee.ttu.kinect.view.chart;

import java.util.ArrayList;
import java.util.List;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.JointType;
import ee.ttu.kinect.model.MotionProcessor;

public class MotionChart extends Chart {

	private static final long serialVersionUID = 1L;

	public MotionChart() {
		super("Trajectory", "Time");		
	}
	
	@Override
	public void drawChart(List<Body> data, List<JointType> types,
			boolean seatedMode) {
		String chartTitle = "";
		for (JointType selectedType : types) {
			MotionSeriesComponent sc = new MotionSeriesComponent(dataset);
			add(sc);
			sc.setLabels(selectedType);
			
			// drawing chart
			drawMotionChart(sc, data, selectedType);
			
			chartTitle += selectedType.getName() + " ";
		}
		chart.setTitle(chartTitle);
	}

	private void drawMotionChart(MotionSeriesComponent sc, List<Body> data, JointType type) {
		// Processing motions
		List<JointType> types = new ArrayList<JointType>();
		types.add(type);
		MotionProcessor processor = new MotionProcessor();
		processor.setTypes(types);
		for (Body body : data) {
			if (processor.process(body)) {
				sc.updateSeries(processor.getTrajectoryMass(), body.getTimestamp());
			}
		}
	}
	
}
