package ee.ttu.kinect.view.chart;

import java.util.List;

import ee.ttu.kinect.model.Frame;
import ee.ttu.kinect.model.JointType;
import ee.ttu.kinect.model.ModelProcessor;

public class ModelChart extends Chart {

	private static final long serialVersionUID = 1L;

	public ModelChart() {
		super("Velocity/Acceleration", "Time");
	}
	
	public void drawChart(List<Frame> data, List<JointType> selectedTypes,
			boolean seatedMode) {
		String chartTitle = "";
		for (JointType selectedType : selectedTypes) {
			ModelSeriesComponent sc = new ModelSeriesComponent(dataset);
			add(sc);
			sc.setLabels(selectedType);
			
			drawModelChart(sc, data, selectedType, seatedMode);
			
			chartTitle += selectedType.getName() + " ";
		}
		
		chart.setTitle(chartTitle);
	}

	private void drawModelChart(ModelSeriesComponent sc, List<Frame> data, JointType type, boolean seatedMode) {
		if (seatedMode
				&& (type == JointType.ANKLE_LEFT
						|| type == JointType.ANKLE_RIGHT
						|| type == JointType.FOOT_LEFT
						|| type == JointType.FOOT_RIGHT
						|| type == JointType.HIP_CENTER
						|| type == JointType.HIP_LEFT
						|| type == JointType.HIP_RIGHT
						|| type == JointType.KNEE_LEFT
						|| type == JointType.KNEE_RIGHT 
						|| type == JointType.SPINE)) {
			return;
		}
		
		ModelProcessor pr = new ModelProcessor(type);
		
		for (Frame frame : data) {
			if (pr.process(frame)) {
				sc.updateSeries(pr.getVelocityX(), pr.getVelocityY(), pr.getVelocityZ(), 
						pr.getAccelerationX(), pr.getAccelerationY(), pr.getAccelerationZ(), frame.getTimestamp());
			}
		}
	}
	
}
