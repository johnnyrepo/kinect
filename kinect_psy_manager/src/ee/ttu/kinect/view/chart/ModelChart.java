package ee.ttu.kinect.view.chart;

import java.util.List;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.JointType;
import ee.ttu.kinect.model.ModelProcessor;

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
			
			drawModelChart(sc, data, selectedType, seatedMode);
			
			chartTitle += selectedType.getName() + " ";
		}
		
		chart.setTitle(chartTitle);
	}

	private void drawModelChart(ModelSeriesComponent sc, List<Body> data, JointType type, boolean seatedMode) {
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
		
		for (Body body : data) {
			if (pr.process(body)) {
				sc.updateSeries(pr.getVelocityX(), pr.getVelocityY(), pr.getVelocityZ(), 
						pr.getAccelerationX(), pr.getAccelerationY(), pr.getAccelerationZ(), body.getTimestamp());
			}
		}
		
//		for (Body body : data) {
//			Body oldBody = body.getOldBody();
//			Body oldOldBody = (oldBody == null) ? null : oldBody.getOldBody();
//			if (body == null || !body.isBodyReady() 
//					|| oldBody == null || oldBody.isBodyReady() 
//					|| oldOldBody == null || oldOldBody.isBodyReady()) {
//				continue;
//			}
//
//			double velocityX = Calculator.calculateVelocity(oldBody.getJoint(type).getPositionX(), body.getJoint(type).getPositionX(), oldBody.getTimestamp(), body.getTimestamp());
//			double velocityY = Calculator.calculateVelocity(oldBody.getJoint(type).getPositionY(), body.getJoint(type).getPositionY(), oldBody.getTimestamp(), body.getTimestamp());
//			double velocityZ = Calculator.calculateVelocity(oldBody.getJoint(type).getPositionZ(), body.getJoint(type).getPositionZ(), oldBody.getTimestamp(), body.getTimestamp());
//			
//			double accelerationX = Calculator.calculateAcceleration(oldOldBody.getJoint(type).getPositionX(), oldBody.getJoint(type).getPositionX(), body.getJoint(type).getPositionX(), oldOldBody.getTimestamp(), oldBody.getTimestamp(), body.getTimestamp());
//			double accelerationY = Calculator.calculateAcceleration(oldOldBody.getJoint(type).getPositionY(), oldBody.getJoint(type).getPositionY(), body.getJoint(type).getPositionY(), oldOldBody.getTimestamp(), oldBody.getTimestamp(), body.getTimestamp());
//			double accelerationZ = Calculator.calculateAcceleration(oldOldBody.getJoint(type).getPositionZ(), oldBody.getJoint(type).getPositionZ(), body.getJoint(type).getPositionZ(), oldOldBody.getTimestamp(), oldBody.getTimestamp(), body.getTimestamp());
//			
//			sc.updateSeries(velocityX, velocityY, velocityZ, accelerationX, accelerationY, accelerationZ, body.getTimestamp());
//		}
	}
	
}
