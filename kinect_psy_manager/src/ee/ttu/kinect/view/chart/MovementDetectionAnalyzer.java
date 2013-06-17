package ee.ttu.kinect.view.chart;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ee.ttu.kinect.calc.Calculator;
import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.JointType;


public class MovementDetectionAnalyzer {
	
	public void open(List<Body> data, JointType type, 
			double trajectoryMassSummary, double accelerationMassSummary) {
		new MovementDetectionChartFrame(data, type, trajectoryMassSummary, accelerationMassSummary);
	}
	
	private class MovementDetectionChartFrame extends JFrame {

		private static final long serialVersionUID = 1L;
				
		private MovementChart chart;

		private JPanel chartPanel;
		
		private JPanel summaryPanel;
		
		private MovementDetectionChartFrame(List<Body> data, JointType type, 
				double trajectoryMassSummary, double accelerationMassSummary) {		
			//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			setSize(600, 500);
			
			chart = new MovementChart();
			chart.setPreferredSize(new Dimension(600, 260));
			chartPanel = new JPanel();
			chartPanel.add(chart);
			
			summaryPanel = new JPanel();
			
			add(chartPanel, BorderLayout.CENTER);
			add(summaryPanel, BorderLayout.SOUTH);
			
			drawChart(data, type);
			outputSummary(data, type, trajectoryMassSummary, accelerationMassSummary);
			
			setVisible(true);
		}
		
		private void drawChart(List<Body> data, JointType type) {
			List<JointType> types = new ArrayList<JointType>();
			types.add(type);
			chart.drawChart(data, types, false);
		}
		
		private void outputSummary(List<Body> data, JointType type, double trajectoryMassSummary, double accelerationMassSummary) {
			long frameStart = data.get(0).getFrameNumber();
			long frameEnd = data.get(data.size() - 1).getFrameNumber();
			double startTime = data.get(0).getTimestamp() / 1000;
			double endTime = data.get(data.size() - 1).getTimestamp() / 1000;
			double time = endTime - startTime;
			double eucledianDistance = Calculator.calculateTrajectoryLength3D(data.get(0).getJoint(type), 
					data.get(data.size() - 1).getJoint(type));
			double ratio = eucledianDistance / trajectoryMassSummary;
			
			summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
			summaryPanel.add(new JLabel("Frame start: " + frameStart));
			summaryPanel.add(new JLabel("Frame end: " + frameEnd));
			summaryPanel.add(new JLabel("Time elapsed: " + time));
			summaryPanel.add(new JLabel("Trajectory mass: " + trajectoryMassSummary));
			summaryPanel.add(new JLabel("Acceleration mass: " + accelerationMassSummary));
			summaryPanel.add(new JLabel("(Eucledian distance / Trajecory mass) ratio: " + ratio));
			
			System.out.println("Frame start: " + frameStart);
			System.out.println("Frame end: " + frameEnd);
			System.out.println("Time elapsed: " + time);
			System.out.println("Trajectory mass: " + trajectoryMassSummary);
			System.out.println("Acceleration mass: " + accelerationMassSummary);
			System.out.println("Eucledian distance: " + eucledianDistance);
			System.out.println("Eucl / Traj ratio: " + ratio);
		}
		
	}

}
