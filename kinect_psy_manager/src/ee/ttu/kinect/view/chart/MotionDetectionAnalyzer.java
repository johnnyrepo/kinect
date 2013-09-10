package ee.ttu.kinect.view.chart;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PageRanges;
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

		private MotionDetectionChartFrame self;
		
		// Summaries
		private Summaries summaries = new Summaries(); 
		
		private MotionDetectionPrinter printer;

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

			self = this;
			
			summaries = new Summaries();
			
			printer = new MotionDetectionPrinter();

			chart = new MotionDetectionChart();
			chart.setPreferredSize(new Dimension(800, 400));
			chartPanel = new JPanel();
			chartPanel.add(chart);

			summaryPanel = new JPanel();

			printButton = new JButton("Print");
			printButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					printer.startPrint(self);
				}
			});
			
			saveImgButton = new JButton("Save");
			saveImgButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					saveImage("screen.png");
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
			summaries.frameStart = data.get(0).getFrameNumber();
			summaries.frameEnd = data.get(data.size() - 1).getFrameNumber();
			summaries.startTime = data.get(0).getTimestamp();
			summaries.endTime = data.get(data.size() - 1).getTimestamp();
			summaries.time = (summaries.endTime - summaries.startTime) / 1000;
			summaries.trajectoryMassSummary = 0;
			summaries.accelerationMassSummary = 0;
			summaries.eucledianDistance = 0;
			for (JointType type : types) {
				summaries.trajectoryMassSummary += calculateTrajectoryMass(data, type);
				summaries.accelerationMassSummary += calculateAccelerationMass(data, type);
				summaries.eucledianDistance += calculateEucledianDistance(data, type);
			}
			summaries.ratio = summaries.eucledianDistance / summaries.trajectoryMassSummary;
		}

		private void drawChart(List<Body> data, List<JointType> types) {
			chart.drawChart(data, types, false);
		}

		private void outputSummary() {
			summaryPanel
					.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
			summaryPanel.add(createLabel("Frame start: " + summaries.frameStart));
			summaryPanel.add(createLabel("Frame end: " + summaries.frameEnd));
			summaryPanel.add(createLabel("Time elapsed: " + summaries.time));
			summaryPanel
					.add(createLabel("Eucledian distance: "
							+ String.format(Locale.ENGLISH, "%-6.3f",
									summaries.eucledianDistance)));
			summaryPanel.add(createLabel("Trajectory mass: "
					+ String.format(Locale.ENGLISH, "%-6.3f",
							summaries.trajectoryMassSummary)));
			summaryPanel.add(createLabel("Acceleration mass: "
					+ String.format(Locale.ENGLISH, "%-6.3f",
							summaries.accelerationMassSummary)));
			summaryPanel.add(createLabel("Ratio (Eucl. dist. / Traj. mass): "
					+ String.format(Locale.ENGLISH, "%-6.3f", summaries.ratio)));
			
			summaryPanel.add(printButton);
			summaryPanel.add(saveImgButton);

			System.out.println(summaries.toString());
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
		
		private void saveImage(String name) {
			Rectangle rec = this.getBounds();
			BufferedImage bi = new BufferedImage(rec.width, rec.height, Transparency.TRANSLUCENT);
			this.paint(bi.getGraphics());
			
			File file = new File(name);
			try {
				ImageIO.write(bi, "png", file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private class MotionDetectionPrinter implements Printable {

		private MotionDetectionChartFrame frame;
		
		public void startPrint(MotionDetectionChartFrame frame) {
			this.frame = frame;
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintable(this);

			HashPrintRequestAttributeSet attrSet = new HashPrintRequestAttributeSet();
			attrSet.add(new PageRanges(1));
			attrSet.add(new Copies(1));
			attrSet.add(OrientationRequested.LANDSCAPE);
			attrSet.add(MediaSizeName.ISO_A4);
			
			boolean isPrint = job.printDialog(attrSet);
			if (isPrint) {
				try {
					job.print();
				} catch (PrinterException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
				throws PrinterException {
			// We have only one page, and 'page'
		    // is zero-based
		    if (pageIndex > 0) {
		         return NO_SUCH_PAGE;
		    }

		    // User (0,0) is typically outside the
		    // imageable area, so we must translate
		    // by the X and Y values in the PageFormat
		    // to avoid clipping.
		    Graphics2D g2d = (Graphics2D)graphics;
		    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

		    // Now we perform our rendering
		    frame.printAll(g2d);

		    // tell the caller that this page is part
		    // of the printed document
		    return PAGE_EXISTS;
		}

	}
	
	private class Summaries {
		
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
			sb.append("Frame start: " + frameStart + "\r\n");
			sb.append("Frame end: " + frameEnd + "\n\r");
			sb.append("Time elapsed: " + time + "\n");
			sb.append("Eucledian distance: " + eucledianDistance + "\n");
			sb.append("Trajectory mass: " + trajectoryMassSummary + "\n");
			sb.append("Acceleration mass: " + accelerationMassSummary + "\n");
			sb.append("Eucl / Traj ratio: " + ratio + "\n");
			
			return sb.toString();
		}
		
	}

}
