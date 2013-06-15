package ee.ttu.kinect.view.chart;

import java.util.List;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.JointType;
import ee.ttu.kinect.model.MovementProcessor;

public class MovementChart extends Chart {

	private static final long serialVersionUID = 1L;

	public MovementChart() {
		super("Trajectory", "Time");		
	}
	
	@Override
	public void drawChart(List<Body> data, List<JointType> selectedTypes,
			boolean seatedMode) {
		String chartTitle = "";
		for (JointType selectedType : selectedTypes) {
			MovementSeriesComponent sc = new MovementSeriesComponent(dataset);
			add(sc);
			sc.setLabels(selectedType);
			
			drawMovementChart(sc, data, selectedType);
			
			chartTitle += selectedType.getName() + " ";
		}
		chart.setTitle(chartTitle);
	}

	private void drawMovementChart(MovementSeriesComponent sc, List<Body> data, JointType selectedType) {
		// Processing movements
		MovementProcessor processor = new MovementProcessor();
		for (Body body : data) {
			if (processor.process(body, selectedType)) {
				sc.updateSeries(processor.getTrajectorySummary(), body.getTimestamp());
			}
		}
		
	}
	
}
