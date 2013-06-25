package ee.ttu.kinect.view.chart;

import java.util.List;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.JointType;
import ee.ttu.kinect.model.MotionProcessor;

public class MotionDetectionChart extends Chart{

	private static final long serialVersionUID = 1L;
	
	public MotionDetectionChart() {
		super("Trajectory", "Time");
	}

	@Override
	public void drawChart(List<Body> data, List<JointType> types,
			boolean seatedMode) {
		String chartTitle = "";
		MotionSeriesComponent sc = new MotionSeriesComponent(dataset);
		add(sc);
		//sc.setLabels(type);
		
		// drawing chart
		drawMotionChart(sc, data, types);
		
		for (JointType type : types) {
			chartTitle += type.getName() + " ";
		}
		
		chart.setTitle(chartTitle);
	}

	private void drawMotionChart(MotionSeriesComponent sc, List<Body> data, List<JointType> types) {
		// Processing motions
		MotionProcessor processor = new MotionProcessor();
		processor.setTypes(types);
		for (Body body : data) {
			if (processor.process(body)) {
				sc.updateSeries(processor.getTrajectoryMass(), body.getTimestamp());
			}
		}
	}

}
