package ee.ttu.kinect.view.chart;

import java.util.ArrayList;
import java.util.List;

import ee.ttu.kinect.model.Frame;
import ee.ttu.kinect.model.JointType;
import ee.ttu.kinect.model.MotionProcessor;

public class MotionChart extends Chart {

	private static final long serialVersionUID = 1L;

	public MotionChart() {
		super("Trajectory", "Time");		
	}
	
	@Override
	public void drawChart(List<Frame> data, List<JointType> types,
			boolean seatedMode) {
		String chartTitle = "";
		for (JointType type : types) {
			MotionSeriesComponent sc = new MotionSeriesComponent(dataset);
			add(sc);
			sc.setLabels(type);
			
			// drawing chart
			drawMotionChart(sc, data, type);
			
			chartTitle += type.getName() + " ";
		}
		chart.setTitle(chartTitle);
	}

	private void drawMotionChart(MotionSeriesComponent sc, List<Frame> data, JointType type) {
		// Processing motions
		List<JointType> types = new ArrayList<JointType>();
		types.add(type);
		MotionProcessor processor = new MotionProcessor();
		processor.setTypes(types);
		for (Frame frame : data) {
			if (processor.process(frame)) {
				sc.updateSeries(processor.getTrajectoryMass(), frame.getTimestamp());
			}
		}
	}
	
}
