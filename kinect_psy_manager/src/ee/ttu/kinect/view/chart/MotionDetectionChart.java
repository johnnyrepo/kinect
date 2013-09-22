package ee.ttu.kinect.view.chart;

import java.util.List;

import ee.ttu.kinect.model.Frame;
import ee.ttu.kinect.model.JointType;
import ee.ttu.kinect.model.MotionProcessor;

public class MotionDetectionChart extends Chart{

	private static final long serialVersionUID = 1L;
	
	public MotionDetectionChart() {
		super("Trajectory", "Time");
	}

	@Override
	public void drawChart(List<Frame> data, List<JointType> types,
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

	private void drawMotionChart(MotionSeriesComponent sc, List<Frame> data, List<JointType> types) {
		// Processing motions
		MotionProcessor processor = new MotionProcessor();
		processor.setTypes(types);
		for (Frame frame : data) {
			if (processor.process(frame)) {
				sc.updateSeries(processor.getTrajectoryMass(), frame.getTimestamp());
			}
		}
	}

}
