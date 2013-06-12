package ee.ttu.kinect.view.chart;

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
import ee.ttu.kinect.model.Joint;
import ee.ttu.kinect.model.JointType;
import ee.ttu.kinect.view.ChartType;

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
	
	private JButton valuesAnalysisButton;
	
	private JButton segmentationAnalysisButton;
	
	private JButton movementAnalysisButton;

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
		
		valuesAnalysisButton = new JButton("Values analysis");
		
		segmentationAnalysisButton = new JButton("Segmentation analysis");
		
		movementAnalysisButton = new JButton("Movement analysis");

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
		chartControlPanel.add(valuesAnalysisButton);
		chartControlPanel.add(segmentationAnalysisButton);
		chartControlPanel.add(movementAnalysisButton);

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
	
	public void addListenerForValuesAnalysis(ActionListener listener) {
		valuesAnalysisButton.addActionListener(listener);
	}
	
	public void addListenerForSegmentationAnalysis(ActionListener listener) {
		segmentationAnalysisButton.addActionListener(listener);
	}
	
	public void addListenerForMovementAnalysis(ActionListener listener) {
		movementAnalysisButton.addActionListener(listener);
	}
	
	public void openChartSelector(List<Body> data, ChartType type) {
		chartSelector.open(data, type);
	}

	public void clearChart() {		
		seriesVelocityX.clear();
		seriesVelocityY.clear();
		seriesVelocityZ.clear();
		seriesAccelerationX.clear();
		seriesAccelerationY.clear();
		seriesAccelerationZ.clear();
	}
	
	public void updateChart(Body body, boolean seatedMode) {		
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
		Joint joint = body.getJoint(selectedType);
		if (seatedMode
				&& (joint.getType() == JointType.ANKLE_LEFT
						|| joint.getType() == JointType.ANKLE_RIGHT
						|| joint.getType() == JointType.FOOT_LEFT
						|| joint.getType() == JointType.FOOT_RIGHT
						|| joint.getType() == JointType.HIP_CENTER
						|| joint.getType() == JointType.HIP_LEFT
						|| joint.getType() == JointType.HIP_RIGHT
						|| joint.getType() == JointType.KNEE_LEFT
						|| joint.getType() == JointType.KNEE_RIGHT 
						|| joint.getType() == JointType.SPINE)) {
			return;
		}
		
		updateVelocity(body.getTimestamp(),
				joint.getVelocityX(),
				joint.getVelocityY(),
				joint.getVelocityZ());
		updateAcceleration(body.getTimestamp(),
				joint.getAccelerationX(),
				joint.getAccelerationY(),
				joint.getAccelerationZ());
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
