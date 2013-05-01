package ee.ttu.kinect.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.JointType;

public class ChartComponent extends JPanel {

	private static final long serialVersionUID = 1L;

	private TimeSeries seriesVelocityX;
	private TimeSeries seriesVelocityY;
	private TimeSeries seriesVelocityZ;

	private TimeSeries seriesAccelerationX;
	private TimeSeries seriesAccelerationY;
	private TimeSeries seriesAccelerationZ;

	private TimeSeriesCollection dataset;

	private JFreeChart chart;

	private ChartPanel chartPanel;

	private JCheckBox velocityXCheckbox;

	private JCheckBox velocityYCheckbox;

	private JCheckBox velocityZCheckbox;

	private JCheckBox accelerationXCheckbox;

	private JCheckBox accelerationYCheckbox;

	private JCheckBox accelerationZCheckbox;

	private JPanel chartControlPanel;

	public ChartComponent() {
		seriesVelocityX = new TimeSeries("Velocity X");
		seriesVelocityY = new TimeSeries("Velocity Y");
		seriesVelocityZ = new TimeSeries("Velocity Z");
		seriesAccelerationX = new TimeSeries("Acceleration X");
		seriesAccelerationY = new TimeSeries("Acceleration Y");
		seriesAccelerationZ = new TimeSeries("Acceleration Z");

		dataset = new TimeSeriesCollection();

		chart = ChartFactory.createTimeSeriesChart(null, null,
				"Velocity/Acceleration", dataset, true, true, false);
		XYPlot plot = (XYPlot) chart.getPlot();
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("mm:ss.SSS"));

		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(1500, 260));

		velocityXCheckbox = new JCheckBox("Velocity X");
		velocityYCheckbox = new JCheckBox("Velocity Y");
		velocityZCheckbox = new JCheckBox("Velocity Z");
		accelerationXCheckbox = new JCheckBox("Acceleration X");
		accelerationYCheckbox = new JCheckBox("Acceleration Y");
		accelerationZCheckbox = new JCheckBox("Acceleration Z");

		// listeners for controls
		ChartControlChangeListener chartControlListener = new ChartControlChangeListener();
		velocityXCheckbox.addActionListener(chartControlListener);
		velocityYCheckbox.addActionListener(chartControlListener);
		velocityZCheckbox.addActionListener(chartControlListener);
		accelerationXCheckbox.addActionListener(chartControlListener);
		accelerationYCheckbox.addActionListener(chartControlListener);
		accelerationZCheckbox.addActionListener(chartControlListener);

		chartControlPanel = new JPanel();
		chartControlPanel.setLayout(new BoxLayout(chartControlPanel,
				BoxLayout.X_AXIS));

		chartControlPanel.add(velocityXCheckbox);
		chartControlPanel.add(velocityYCheckbox);
		chartControlPanel.add(velocityZCheckbox);
		chartControlPanel.add(accelerationXCheckbox);
		chartControlPanel.add(accelerationYCheckbox);
		chartControlPanel.add(accelerationZCheckbox);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(chartPanel);
		add(chartControlPanel);
	}

	private class ChartControlChangeListener implements ActionListener {
		@Override
		public synchronized void actionPerformed(ActionEvent e) {
			// velocity checkboxes
			if (velocityXCheckbox.isSelected()) {
				if (!dataset.getSeries().contains(seriesVelocityX)) {
					dataset.addSeries(seriesVelocityX);
				}
			} else {
				if (dataset.getSeries().contains(seriesVelocityX)) {
					// velocitySeriesX.clear();
					dataset.removeSeries(seriesVelocityX);
				}
			}
			if (velocityYCheckbox.isSelected()) {
				if (!dataset.getSeries().contains(seriesVelocityY)) {
					dataset.addSeries(seriesVelocityY);
				}
			} else {
				if (dataset.getSeries().contains(seriesVelocityY)) {
					// velocitySeriesY.clear();
					dataset.removeSeries(seriesVelocityY);
				}
			}
			if (velocityZCheckbox.isSelected()) {
				if (!dataset.getSeries().contains(seriesVelocityZ)) {
					dataset.addSeries(seriesVelocityZ);
				}
			} else {
				if (dataset.getSeries().contains(seriesVelocityZ)) {
					// velocitySeriesZ.clear();
					dataset.removeSeries(seriesVelocityZ);
				}
			}

			// acceleration checkboxes
			if (accelerationXCheckbox.isSelected()) {
				if (!dataset.getSeries().contains(seriesAccelerationX)) {
					dataset.addSeries(seriesAccelerationX);
				}
			} else {
				if (dataset.getSeries().contains(seriesAccelerationX)) {
					dataset.removeSeries(seriesAccelerationX);
				}
			}
			if (accelerationYCheckbox.isSelected()) {
				if (!dataset.getSeries().contains(seriesAccelerationY)) {
					dataset.addSeries(seriesAccelerationY);
				}
			} else {
				if (dataset.getSeries().contains(seriesAccelerationY)) {
					dataset.removeSeries(seriesAccelerationY);
				}
			}
			if (accelerationZCheckbox.isSelected()) {
				if (!dataset.getSeries().contains(seriesAccelerationZ)) {
					dataset.addSeries(seriesAccelerationZ);
				}
			} else {
				if (dataset.getSeries().contains(seriesAccelerationZ)) {
					dataset.removeSeries(seriesAccelerationZ);
				}
			}
		}
	}

	public void clearChart() {
		seriesVelocityX.clear();
		seriesVelocityY.clear();
		seriesVelocityZ.clear();
		seriesAccelerationX.clear();
		seriesAccelerationY.clear();
		seriesAccelerationZ.clear();
	}

	public void drawChart(List<Body> data, List<JointType> selectedTypes,
			boolean seatedMode) {
		for (JointType selectedType : selectedTypes) {
			for (Body body : data) {
				if (body == null || !body.isBodyReady()) {
					continue;
				}
				updateChart(body, selectedType, seatedMode);
			}
		}
	}

	public void drawChart(List<Body> data, JointType selectedType,
			boolean seatedMode) {
		chart.setTitle(new TextTitle(selectedType.getName()));
		for (Body body : data) {
			if (body == null || !body.isBodyReady()) {
				continue;
			}
			updateChart(body, selectedType, seatedMode);
		}
	}

	public void updateChart(Body body, JointType selectedType,
			boolean seatedMode) {
		if (body == null || !body.isBodyReady()) {
			return;
		}

		switch (selectedType) {
		case ANKLE_LEFT:
			if (!seatedMode) {
				updateVelocity(body.getTimestamp(),
						body.getAnkleLeftXVelocity(),
						body.getAnkleLeftYVelocity(),
						body.getAnkleLeftZVelocity());
				updateAcceleration(body.getTimestamp(),
						body.getAnkleLeftXAcceleration(),
						body.getAnkleLeftYAcceleration(),
						body.getAnkleLeftZAcceleration());
			}
			break;
		case ANKLE_RIGHT:
			if (!seatedMode) {
				updateVelocity(body.getTimestamp(),
						body.getAnkleRightXVelocity(),
						body.getAnkleRightYVelocity(),
						body.getAnkleRightZVelocity());
				updateAcceleration(body.getTimestamp(),
						body.getAnkleRightXAcceleration(),
						body.getAnkleRightYAcceleration(),
						body.getAnkleRightZAcceleration());
			}
			break;
		case ELBOW_LEFT:
			updateVelocity(body.getTimestamp(), body.getElbowLeftXVelocity(),
					body.getElbowLeftYVelocity(), body.getElbowLeftZVelocity());
			updateAcceleration(body.getTimestamp(),
					body.getElbowLeftXAcceleration(),
					body.getElbowLeftYAcceleration(),
					body.getElbowLeftZAcceleration());
			break;
		case ELBOW_RIGHT:
			updateVelocity(body.getTimestamp(), body.getElbowRightXVelocity(),
					body.getElbowRightYVelocity(),
					body.getElbowRightZVelocity());
			updateAcceleration(body.getTimestamp(),
					body.getElbowRightXAcceleration(),
					body.getElbowRightYAcceleration(),
					body.getElbowRightZAcceleration());
			break;
		case FOOT_LEFT:
			if (!seatedMode) {
				updateVelocity(body.getTimestamp(),
						body.getFootLeftXVelocity(),
						body.getFootLeftYVelocity(),
						body.getFootLeftZVelocity());
				updateAcceleration(body.getTimestamp(),
						body.getFootLeftXAcceleration(),
						body.getFootLeftYAcceleration(),
						body.getFootLeftZAcceleration());
			}
			break;
		case FOOT_RIGHT:
			if (!seatedMode) {
				updateVelocity(body.getTimestamp(),
						body.getFootRightXVelocity(),
						body.getFootRightYVelocity(),
						body.getFootRightZVelocity());
				updateAcceleration(body.getTimestamp(),
						body.getFootRightXAcceleration(),
						body.getFootRightYAcceleration(),
						body.getFootRightZAcceleration());
			}
			break;
		case HAND_LEFT:
			updateVelocity(body.getTimestamp(), body.getHandLeftXVelocity(),
					body.getHandLeftYVelocity(), body.getHandLeftZVelocity());
			updateAcceleration(body.getTimestamp(),
					body.getHandLeftXAcceleration(),
					body.getHandLeftYAcceleration(),
					body.getHandLeftZAcceleration());
			break;
		case HAND_RIGHT:
			updateVelocity(body.getTimestamp(), body.getHandRightXVelocity(),
					body.getHandRightYVelocity(), body.getHandRightZVelocity());
			updateAcceleration(body.getTimestamp(),
					body.getHandRightXAcceleration(),
					body.getHandRightYAcceleration(),
					body.getHandRightZAcceleration());
			break;
		case HEAD:
			updateVelocity(body.getTimestamp(), body.getHeadXVelocity(),
					body.getHeadYVelocity(), body.getHeadZVelocity());
			updateAcceleration(body.getTimestamp(),
					body.getHeadXAcceleration(), body.getHeadYAcceleration(),
					body.getHeadZAcceleration());
			break;
		case HIP_CENTER:
			if (!seatedMode) {
				updateVelocity(body.getTimestamp(),
						body.getHipCenterXVelocity(),
						body.getHipCenterYVelocity(),
						body.getHipCenterZVelocity());
				updateAcceleration(body.getTimestamp(),
						body.getHipCenterXAcceleration(),
						body.getHipCenterYAcceleration(),
						body.getHipCenterZAcceleration());
			}
			break;
		case HIP_LEFT:
			if (!seatedMode) {
				updateVelocity(body.getTimestamp(), body.getHipLeftXVelocity(),
						body.getHipLeftYVelocity(), body.getHipLeftZVelocity());
				updateAcceleration(body.getTimestamp(),
						body.getHipLeftXAcceleration(),
						body.getHipLeftYAcceleration(),
						body.getHipLeftZAcceleration());
			}
			break;
		case HIP_RIGHT:
			if (!seatedMode) {
				updateVelocity(body.getTimestamp(),
						body.getHipRightXVelocity(),
						body.getHipRightYVelocity(),
						body.getHipRightZVelocity());
				updateAcceleration(body.getTimestamp(),
						body.getHipRightXAcceleration(),
						body.getHipRightYAcceleration(),
						body.getHipRightZAcceleration());
			}
			break;
		case KNEE_LEFT:
			if (!seatedMode) {
				updateVelocity(body.getTimestamp(),
						body.getKneeLeftXVelocity(),
						body.getKneeLeftYVelocity(),
						body.getKneeLeftZVelocity());
				updateAcceleration(body.getTimestamp(),
						body.getKneeLeftXAcceleration(),
						body.getKneeLeftYAcceleration(),
						body.getKneeLeftZAcceleration());
			}
			break;
		case KNEE_RIGHT:
			if (!seatedMode) {
				updateVelocity(body.getTimestamp(),
						body.getKneeRightXVelocity(),
						body.getKneeRightYVelocity(),
						body.getKneeRightZVelocity());
				updateAcceleration(body.getTimestamp(),
						body.getKneeRightXAcceleration(),
						body.getKneeRightYAcceleration(),
						body.getKneeRightZAcceleration());
			}
			break;
		case SHOULDER_CENTER:
			updateVelocity(body.getTimestamp(),
					body.getShoulderCenterXVelocity(),
					body.getShoulderCenterYVelocity(),
					body.getShoulderCenterZVelocity());
			updateAcceleration(body.getTimestamp(),
					body.getShoulderCenterXAcceleration(),
					body.getShoulderCenterYAcceleration(),
					body.getShoulderCenterZAcceleration());
			break;
		case SHOULDER_LEFT:
			updateVelocity(body.getTimestamp(),
					body.getShoulderLeftXVelocity(),
					body.getShoulderLeftYVelocity(),
					body.getShoulderLeftZVelocity());
			updateAcceleration(body.getTimestamp(),
					body.getShoulderLeftXAcceleration(),
					body.getShoulderLeftYAcceleration(),
					body.getShoulderLeftZAcceleration());
			break;
		case SHOULDER_RIGHT:
			updateVelocity(body.getTimestamp(),
					body.getShoulderRightXVelocity(),
					body.getShoulderRightYVelocity(),
					body.getShoulderRightZVelocity());
			updateAcceleration(body.getTimestamp(),
					body.getShoulderRightXAcceleration(),
					body.getShoulderRightYAcceleration(),
					body.getShoulderRightZAcceleration());
			break;
		case SPINE:
			if (!seatedMode) {
				updateVelocity(body.getTimestamp(), body.getSpineXVelocity(),
						body.getSpineYVelocity(), body.getSpineZVelocity());
				updateAcceleration(body.getTimestamp(),
						body.getSpineXAcceleration(),
						body.getSpineYAcceleration(),
						body.getSpineZAcceleration());
			}
			break;
		case WRIST_LEFT:
			updateVelocity(body.getTimestamp(), body.getWristLeftXVelocity(),
					body.getWristLeftYVelocity(), body.getWristLeftZVelocity());
			updateAcceleration(body.getTimestamp(),
					body.getWristLeftXAcceleration(),
					body.getWristLeftYAcceleration(),
					body.getWristLeftZAcceleration());
			break;
		case WRIST_RIGHT:
			updateVelocity(body.getTimestamp(), body.getWristRightXVelocity(),
					body.getWristRightYVelocity(),
					body.getWristRightZVelocity());
			updateAcceleration(body.getTimestamp(),
					body.getWristRightXAcceleration(),
					body.getWristRightYAcceleration(),
					body.getWristRightZAcceleration());
			break;
		}
	}

	private void updateVelocity(long timestamp, double velocityX,
			double velocityY, double velocityZ) {
		seriesVelocityX.addOrUpdate(new TimeSeriesDataItem(
				new FixedMillisecond(timestamp), velocityX));
		seriesVelocityY.addOrUpdate(new TimeSeriesDataItem(
				new FixedMillisecond(timestamp), velocityY));
		seriesVelocityZ.addOrUpdate(new TimeSeriesDataItem(
				new FixedMillisecond(timestamp), velocityZ));
	}

	private void updateAcceleration(long timestamp, double accelerationX,
			double accelerationY, double accelerationZ) {
		seriesAccelerationX.addOrUpdate(new TimeSeriesDataItem(
				new FixedMillisecond(timestamp), accelerationX));
		seriesAccelerationY.addOrUpdate(new TimeSeriesDataItem(
				new FixedMillisecond(timestamp), accelerationY));
		seriesAccelerationZ.addOrUpdate(new TimeSeriesDataItem(
				new FixedMillisecond(timestamp), accelerationZ));
	}

}
