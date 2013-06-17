package ee.ttu.kinect.view.chart;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ee.ttu.kinect.calc.Calculator;
import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.Joint;
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
			setTitle("Movement has been detected!");
			
			setSize(1200, 400);

			getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
			
			chart = new MovementChart();
			chart.setPreferredSize(new Dimension(800, 400));
			chartPanel = new JPanel();
			chartPanel.add(chart);
			
			summaryPanel = new JPanel();
			
			getContentPane().add(summaryPanel);//, BorderLayout.SOUTH);
			getContentPane().add(chartPanel);//, BorderLayout.CENTER);
			
			drawChart(data, type);
			outputSummary(data, type);
			
			setVisible(true);
		}
		
		private void drawChart(List<Body> data, JointType type) {
			List<JointType> types = new ArrayList<JointType>();
			types.add(type);
			chart.drawChart(data, types, false);
		}
		
		private void outputSummary(List<Body> data, JointType type) {
			long frameStart = data.get(0).getFrameNumber();
			long frameEnd = data.get(data.size() - 1).getFrameNumber();
			double startTime = data.get(0).getTimestamp();
			double endTime = data.get(data.size() - 1).getTimestamp();
			double time = (endTime - startTime) / 1000;
			double trajectoryMassSummary = calculateTrajectoryMass(data, type);
			double accelerationMassSummary = calculateAccelerationMass(data, type);
			double eucledianDistance = calculateEucledianDistance(data, type);
			double ratio = eucledianDistance / trajectoryMassSummary;
			
			summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
			summaryPanel.add(createLabel("Frame start: " + frameStart));
			summaryPanel.add(createLabel("Frame end: " + frameEnd));
			summaryPanel.add(createLabel("Time elapsed: " + time));
			summaryPanel.add(createLabel("Eucledian distance: " + String.format(Locale.ENGLISH, "%-6.3f", eucledianDistance)));
			summaryPanel.add(createLabel("Trajectory mass: " + String.format(Locale.ENGLISH, "%-6.3f", trajectoryMassSummary)));
			summaryPanel.add(createLabel("Acceleration mass: " + String.format(Locale.ENGLISH, "%-6.3f", accelerationMassSummary)));
			summaryPanel.add(createLabel("Ratio (Eucl. dist. / Traj. mass): " + String.format(Locale.ENGLISH, "%-6.3f", ratio)));
			
			System.out.println("Frame start: " + frameStart);
			System.out.println("Frame end: " + frameEnd);
			System.out.println("Time elapsed: " + time);
			System.out.println("Eucledian distance: " + eucledianDistance);
			System.out.println("Trajectory mass: " + trajectoryMassSummary);
			System.out.println("Acceleration mass: " + accelerationMassSummary);
			System.out.println("Eucl / Traj ratio: " + ratio);
		}

		private JLabel createLabel(String text) {
			JLabel label = new JLabel(text);
			label.setFont(new Font("Serif", Font.BOLD, 16));
			
			return label;
		}
		
		private double calculateTrajectoryMass(List<Body> data, JointType type) {
			double trajectoryMass = 0;
			for (int i = 0; i < data.size(); i++) {
				if (i + 1 >= data.size()) {
					break;
				}
				Joint joint1 = data.get(i).getJoint(type);
				Joint joint2 = data.get(i + 1).getJoint(type);
				trajectoryMass += Calculator.calculateTrajectoryLength3D(joint1, joint2);
			}
			
			return trajectoryMass;
		}
		
		private double calculateAccelerationMass(List<Body> data, JointType type) {
			double accelerationMass = 0;
			for (int i = 0; i < data.size(); i++) {
				if (i + 1 >= data.size()) {
					break;
				}
				Joint joint1 = data.get(i).getJoint(type);
				Joint joint2 = data.get(i + 1).getJoint(type);
				long time1 = data.get(i).getTimestamp();
				long time2 = data.get(i + 1).getTimestamp();
				System.out.print(data.get(i).getFrameNumber());
				accelerationMass += Math.abs(Calculator.calculateAcceleration3D(joint1, joint2, time1, time2));
				System.out.println();
				System.out.println("accel " + data.get(i).getFrameNumber() + " " + String.format(Locale.ENGLISH, "%-14.13f", Math.abs(Calculator.calculateAcceleration3D(joint1, joint2, time1, time2))));
			}
			System.out.println("\n\n\n");
			
			return accelerationMass;
		}
		
		private double calculateEucledianDistance(List<Body> data,
				JointType type) {
			return Calculator.calculateTrajectoryLength3D(data.get(0).getJoint(type), 
					data.get(data.size() - 1).getJoint(type));
		}
		
	}

}
