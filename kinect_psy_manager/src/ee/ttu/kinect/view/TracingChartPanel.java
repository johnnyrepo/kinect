package ee.ttu.kinect.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.JointType;

public class TracingChartPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JComboBox<JointType> jointCombo;

	private JCheckBox packedModeChekbox;
	
	private JCheckBox velocityXCheckbox;

	private JCheckBox velocityYCheckbox;

	private JCheckBox velocityZCheckbox;

	private JCheckBox accelerationXCheckbox;

	private JCheckBox accelerationYCheckbox;

	private JCheckBox accelerationZCheckbox;
	
	private JButton drawChartButton;

	private JFreeChart chart;

	private JPanel chartControlPanel;

	private ChartPanel chartPanel;

	private TimeSeriesCollection dataset;

	private TimeSeries seriesVelocityX;
	private TimeSeries seriesVelocityY;
	private TimeSeries seriesVelocityZ;

	private TimeSeries seriesAccelerationX;
	private TimeSeries seriesAccelerationY;
	private TimeSeries seriesAccelerationZ;

	private JointType selectedJoint;

	private ChartSelector chartSelector;

	//private List<Body> data = new ArrayList<Body>();

	public TracingChartPanel() {
		Border border = BorderFactory.createEtchedBorder();
		setBorder(BorderFactory.createTitledBorder(border, "Chart"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		jointCombo = new JComboBox<JointType>();
		for (JointType jt : JointType.values()) {
			jointCombo.addItem(jt);
		}
		packedModeChekbox = new JCheckBox("Packed mode");
		velocityXCheckbox = new JCheckBox("Velocity X");
		velocityYCheckbox = new JCheckBox("Velocity Y");
		velocityZCheckbox = new JCheckBox("Velocity Z");
		accelerationXCheckbox = new JCheckBox("Acceleration X");
		accelerationYCheckbox = new JCheckBox("Acceleration Y");
		accelerationZCheckbox = new JCheckBox("Acceleration Z");
		
		drawChartButton = new JButton("Draw chart");

		chartSelector = new ChartSelector();
		
		selectedJoint = (JointType) jointCombo.getSelectedItem();

		// listeners for controls
		ChartControlChangeListener chartControlListener = new ChartControlChangeListener();
		jointCombo.addActionListener(chartControlListener);
		velocityXCheckbox.addActionListener(chartControlListener);
		velocityYCheckbox.addActionListener(chartControlListener);
		velocityZCheckbox.addActionListener(chartControlListener);
		accelerationXCheckbox.addActionListener(chartControlListener);
		accelerationYCheckbox.addActionListener(chartControlListener);
		accelerationZCheckbox.addActionListener(chartControlListener);
		packedModeChekbox.addActionListener(chartControlListener);
		drawChartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});

		packedModeChekbox.setSelected(true);
		
		seriesVelocityX = new TimeSeries("Velocity X");
		seriesVelocityY = new TimeSeries("Velocity Y");
		seriesVelocityZ = new TimeSeries("Velocity Z");
		seriesAccelerationX = new TimeSeries("Acceleration X");
		seriesAccelerationY = new TimeSeries("Acceleration Y");
		seriesAccelerationZ = new TimeSeries("Acceleration Z");

		dataset = new TimeSeriesCollection();
		chart = ChartFactory.createTimeSeriesChart(
				"Velocity/Acceleration chart", "Time", "Velocity/Acceleration",
				dataset, true, true, false);
		XYPlot plot = (XYPlot) chart.getPlot();
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("mm:ss.SSS"));

		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(200, 330));

		chartControlPanel = new JPanel();

		chartControlPanel.setLayout(new BoxLayout(chartControlPanel,
				BoxLayout.X_AXIS));
		chartControlPanel.add(packedModeChekbox);
		chartControlPanel.add(jointCombo);
		chartControlPanel.add(velocityXCheckbox);
		chartControlPanel.add(velocityYCheckbox);
		chartControlPanel.add(velocityZCheckbox);
		chartControlPanel.add(accelerationXCheckbox);
		chartControlPanel.add(accelerationYCheckbox);
		chartControlPanel.add(accelerationZCheckbox);
		chartControlPanel.add(drawChartButton);

		add(chartControlPanel);
		add(chartPanel);
	}
	
	// XYPlot plot = (XYPlot) (chart.getPlot());
	// plot.getRenderer().setSeriesPaint(0, Color.RED);
	// plot.getRenderer().setSeriesPaint(1, Color.GREEN);
	// plot.getRenderer().setSeriesPaint(2, Color.BLUE);
	// plot.getRenderer().setSeriesPaint(3, Color.YELLOW);
	// plot.getRenderer().setSeriesPaint(4, Color.MAGENTA);
	// plot.getRenderer().setSeriesPaint(5, Color.CYAN);

	private class ChartControlChangeListener implements ActionListener {
		@Override
		public synchronized void actionPerformed(ActionEvent e) {
			if (!jointCombo.getSelectedItem().equals(selectedJoint)) {
				selectedJoint = (JointType) jointCombo.getSelectedItem();
				clearChart();
			}

			// packed mode checkbox
			togglePackedMode(packedModeChekbox.isSelected());
			
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

		private void togglePackedMode(boolean selected) {
			long period = selected ? Long.MAX_VALUE : 10000;
			seriesVelocityX.setMaximumItemAge(period);
			seriesVelocityY.setMaximumItemAge(period);
			seriesVelocityZ.setMaximumItemAge(period);
			seriesAccelerationX.setMaximumItemAge(period);
			seriesAccelerationY.setMaximumItemAge(period);
			seriesAccelerationZ.setMaximumItemAge(period);
		}
	}
	
	public void addListenerForDrawChart(ActionListener listener) {
		drawChartButton.addActionListener(listener);
	}
	
	public void openChartSelector(List<Body> data) {
		chartSelector.open(data);
	}

	public void clearChart() {
		//data = new ArrayList<Body>();
		
		seriesVelocityX.clear();
		seriesVelocityY.clear();
		seriesVelocityZ.clear();
		seriesAccelerationX.clear();
		seriesAccelerationY.clear();
		seriesAccelerationZ.clear();
	}
	
	public void updateChart(Body body, boolean seatedMode) {
		//data.add(body);
		
		if (!velocityXCheckbox.isSelected() 
				&& !velocityYCheckbox.isSelected()
				&& !velocityZCheckbox.isSelected()
				&& !accelerationXCheckbox.isSelected()
				&& !accelerationYCheckbox.isSelected()
				&& !accelerationZCheckbox.isSelected()) {
			return;
		}
		if (body == null || !body.isBodyReady()) {
			return;
		}

		JointType selectedType = (JointType) jointCombo.getSelectedItem();
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
			updateVelocity(body.getTimestamp(), 
					body.getElbowLeftXVelocity(),
					body.getElbowLeftYVelocity(), 
					body.getElbowLeftZVelocity());
			updateAcceleration(body.getTimestamp(),
					body.getElbowLeftXAcceleration(),
					body.getElbowLeftYAcceleration(),
					body.getElbowLeftZAcceleration());
			break;
		case ELBOW_RIGHT:
			updateVelocity(body.getTimestamp(), 
					body.getElbowRightXVelocity(),
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
			updateVelocity(body.getTimestamp(), 
					body.getHandLeftXVelocity(),
					body.getHandLeftYVelocity(), 
					body.getHandLeftZVelocity());
			updateAcceleration(body.getTimestamp(),
					body.getHandLeftXAcceleration(),
					body.getHandLeftYAcceleration(),
					body.getHandLeftZAcceleration());
			break;
		case HAND_RIGHT:
			updateVelocity(body.getTimestamp(), 
					body.getHandRightXVelocity(),
					body.getHandRightYVelocity(), 
					body.getHandRightZVelocity());
			updateAcceleration(body.getTimestamp(),
					body.getHandRightXAcceleration(),
					body.getHandRightYAcceleration(),
					body.getHandRightZAcceleration());
			break;
		case HEAD:
			updateVelocity(body.getTimestamp(), 
					body.getHeadXVelocity(),
					body.getHeadYVelocity(), 
					body.getHeadZVelocity());
			updateAcceleration(body.getTimestamp(),
					body.getHeadXAcceleration(), 
					body.getHeadYAcceleration(),
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
				updateVelocity(body.getTimestamp(), 
						body.getHipLeftXVelocity(),
						body.getHipLeftYVelocity(), 
						body.getHipLeftZVelocity());
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
				updateVelocity(body.getTimestamp(), 
						body.getSpineXVelocity(),
						body.getSpineYVelocity(), 
						body.getSpineZVelocity());
				updateAcceleration(body.getTimestamp(),
						body.getSpineXAcceleration(), 
						body.getSpineYAcceleration(),
						body.getSpineZAcceleration());
			}
			break;
		case WRIST_LEFT:
			updateVelocity(body.getTimestamp(), 
					body.getWristLeftXVelocity(),
					body.getWristLeftYVelocity(), 
					body.getWristLeftZVelocity());
			updateAcceleration(body.getTimestamp(),
					body.getWristLeftXAcceleration(), 
					body.getWristLeftYAcceleration(),
					body.getWristLeftZAcceleration());
			break;
		case WRIST_RIGHT:
			updateVelocity(body.getTimestamp(), 
					body.getWristRightXVelocity(),
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
