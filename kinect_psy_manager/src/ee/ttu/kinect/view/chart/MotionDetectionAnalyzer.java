package ee.ttu.kinect.view.chart;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ee.ttu.kinect.calc.Calculator;
import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.Joint;
import ee.ttu.kinect.model.JointType;

public class MotionDetectionAnalyzer {

	public void open(List<Body> data, List<JointType> types) {
		new MotionDetectionChartFrame(data, types);
	}

	private class MotionDetectionChartFrame extends JFrame {

		private static final long serialVersionUID = 1L;
		
		// Summaries
		private Summary summary = new Summary(); 
		
		private JFramePrinter printer;

		private MotionDetectionChart chart;

		private JPanel chartPanel;

		private JPanel summaryPanel;

		private JButton printButton;
		
		private JButton saveImgButton;

		private MotionDetectionChartFrame(List<Body> data, List<JointType> types) {
			setTitle("Motion has been detected!");
			setSize(800, 600);
			setResizable(false);

			getContentPane().setLayout(
					new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
			
			summary = new Summary();
			
			printer = new JFramePrinter(this);

			chart = new MotionDetectionChart();
			chart.setPreferredSize(new Dimension(800, 400));
			chartPanel = new JPanel();
			chartPanel.add(chart);

			summaryPanel = new JPanel();

			printButton = new JButton("Print");
			printButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					printer.printToPaper();
				}
			});
			
			saveImgButton = new JButton("Save");
			saveImgButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String fileName = System.getProperty("user.dir") + "/" 
							+ new SimpleDateFormat("yyyy.MM.dd_HH-mm-ss").format(new Date()) + ".png";
					printer.saveToImageFile(fileName);
				}
			});

			getContentPane().add(chartPanel);
			getContentPane().add(summaryPanel);

			calculateSummaries(data, types);

			drawChart(data, types);
			outputSummary();

			setVisible(true);
		}
		
		private void calculateSummaries(List<Body> data, List<JointType> types) {
			summary.frameStart = data.get(0).getFrameNumber();
			summary.frameEnd = data.get(data.size() - 1).getFrameNumber();
			summary.startTime = data.get(0).getTimestamp();
			summary.endTime = data.get(data.size() - 1).getTimestamp();
			summary.time = (summary.endTime - summary.startTime) / 1000;
			summary.trajectoryMassSummary = 0;
			summary.accelerationMassSummary = 0;
			summary.eucledianDistance = 0;
			for (JointType type : types) {
				summary.trajectoryMassSummary += calculateTrajectoryMass(data, type);
				summary.accelerationMassSummary += calculateAccelerationMass(data, type);
				summary.eucledianDistance += calculateEucledianDistance(data, type);
			}
			summary.ratio = summary.eucledianDistance / summary.trajectoryMassSummary;
		}

		private void drawChart(List<Body> data, List<JointType> types) {
			chart.drawChart(data, types, false);
		}

		private void outputSummary() {
			summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
			
			summaryPanel.add(createLabel("Frame start: " + summary.frameStart));
			summaryPanel.add(createLabel("Frame end: " + summary.frameEnd));
			summaryPanel.add(createLabel("Time elapsed: " + summary.time));
			summaryPanel
					.add(createLabel("Eucledian distance: "
							+ String.format(Locale.ENGLISH, "%-6.3f",
									summary.eucledianDistance)));
			summaryPanel.add(createLabel("Trajectory mass: "
					+ String.format(Locale.ENGLISH, "%-6.3f",
							summary.trajectoryMassSummary)));
			summaryPanel.add(createLabel("Acceleration mass: "
					+ String.format(Locale.ENGLISH, "%-6.3f",
							summary.accelerationMassSummary)));
			summaryPanel.add(createLabel("Ratio (Eucl. dist. / Traj. mass): "
					+ String.format(Locale.ENGLISH, "%-6.3f", summary.ratio)));
			
			summaryPanel.add(printButton);
			summaryPanel.add(saveImgButton);

			System.out.println(summary.toString());
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
				trajectoryMass += Calculator.calculateTrajectoryLength3D(
						joint1, joint2);
			}

			return trajectoryMass;
		}

		private double calculateAccelerationMass(List<Body> data, JointType type) {
			double accelerationMass = 0;
			for (int i = 0; i < data.size(); i++) {
				if (i + 2 >= data.size()) {
					break;
				}
				Joint joint1 = data.get(i).getJoint(type);
				Joint joint2 = data.get(i + 1).getJoint(type);
				Joint joint3 = data.get(i + 2).getJoint(type);
				long time1 = data.get(i).getTimestamp();
				long time2 = data.get(i + 1).getTimestamp();
				long time3 = data.get(i + 2).getTimestamp();
				accelerationMass += Math.abs(Calculator
						.calculateAcceleration3D(joint1, joint2, joint3, time1,
								time2, time3));
				System.out.println("frame nr. "
						+ data.get(i).getFrameNumber()
						+ " accel. "
						+ data.get(i).getFrameNumber()
						+ " "
						+ String.format(Locale.ENGLISH, "%-14.13f", Math
								.abs(Calculator.calculateAcceleration3D(joint1,
										joint2, joint3, time1, time2, time3))));
			}
			System.out.println("\n\n\n");

			return accelerationMass;
		}

		private double calculateEucledianDistance(List<Body> data,
				JointType type) {
			System.out.println("eucl size " + data.size());
			return Calculator.calculateTrajectoryLength3D(
					data.get(0).getJoint(type), data.get(data.size() - 1)
							.getJoint(type));
		}

	}
	
	private class Summary {
		
		public long frameStart;

		public long frameEnd;

		public long startTime;

		public long endTime;

		public long time;

		public double trajectoryMassSummary;

		public double accelerationMassSummary;

		public double eucledianDistance;

		public double ratio;
		
		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("Frame start: " + frameStart + "\n");
			sb.append("Frame end: " + frameEnd + "\n");
			sb.append("Time elapsed: " + time + "\n");
			sb.append("Eucledian distance: " + eucledianDistance + "\n");
			sb.append("Trajectory mass: " + trajectoryMassSummary + "\n");
			sb.append("Acceleration mass: " + accelerationMassSummary + "\n");
			sb.append("Eucl / Traj ratio: " + ratio + "\n");
			
			return sb.toString();
		}
		
	}

}
