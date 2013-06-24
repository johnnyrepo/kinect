package ee.ttu.kinect.view.chart;

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
	public void drawChart(List<Body> data, List<JointType> selectedTypes,
			boolean seatedMode) {
		String chartTitle = "";
		for (JointType selectedType : selectedTypes) {
			MotionSeriesComponent sc = new MotionSeriesComponent(dataset);
			add(sc);
			sc.setLabels(selectedType);
			
			drawMotionChart(sc, data, selectedType);
			
			chartTitle += selectedType.getName() + " ";
		}
		chart.setTitle(chartTitle);
	}

	private void drawMotionChart(MotionSeriesComponent sc, List<Body> data, JointType selectedType) {
		// Processing motions
		MotionProcessor processor = new MotionProcessor();
		processor.setType(selectedType);
		for (Body body : data) {
			if (processor.process(body)) {
				sc.updateSeries(processor.getTrajectoryMass(), body.getTimestamp());
			}
		}
	}
	
}
