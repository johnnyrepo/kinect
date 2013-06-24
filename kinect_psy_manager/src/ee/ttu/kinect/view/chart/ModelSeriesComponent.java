package ee.ttu.kinect.view.chart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;

import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

import ee.ttu.kinect.model.JointType;

public class ModelSeriesComponent extends SeriesComponent {

	private static final long serialVersionUID = 1L;

	private JCheckBox velocityXCheckbox;

	private JCheckBox velocityYCheckbox;

	private JCheckBox velocityZCheckbox;

	private JCheckBox accelerationXCheckbox;

	private JCheckBox accelerationYCheckbox;

	private JCheckBox accelerationZCheckbox;

	private TimeSeries seriesVelocityX;
	private TimeSeries seriesVelocityY;
	private TimeSeries seriesVelocityZ;

	private TimeSeries seriesAccelerationX;
	private TimeSeries seriesAccelerationY;
	private TimeSeries seriesAccelerationZ;

	public ModelSeriesComponent(TimeSeriesCollection dataset) {
		super(dataset);

		seriesVelocityX = new TimeSeries("Velocity X");
		seriesVelocityY = new TimeSeries("Velocity Y");
		seriesVelocityZ = new TimeSeries("Velocity Z");
		seriesAccelerationX = new TimeSeries("Acceleration X");
		seriesAccelerationY = new TimeSeries("Acceleration Y");
		seriesAccelerationZ = new TimeSeries("Acceleration Z");

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

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		add(velocityXCheckbox);
		add(velocityYCheckbox);
		add(velocityZCheckbox);
		add(accelerationXCheckbox);
		add(accelerationYCheckbox);
		add(accelerationZCheckbox);
	}

	public void setLabels(JointType selectedType) {
		seriesVelocityX.setKey("Velocity X-" + selectedType.getName());
		seriesVelocityY.setKey("Velocity Y-" + selectedType.getName());
		seriesVelocityZ.setKey("Velocity Z-" + selectedType.getName());
		seriesAccelerationX.setKey("Acceleration X-" + selectedType.getName());
		seriesAccelerationY.setKey("Acceleration Y-" + selectedType.getName());
		seriesAccelerationZ.setKey("Acceleration Z-" + selectedType.getName());

		velocityXCheckbox.setText("Velocity X-" + selectedType.getName());
		velocityYCheckbox.setText("Velocity Y-" + selectedType.getName());
		velocityZCheckbox.setText("Velocity Z-" + selectedType.getName());
		accelerationXCheckbox.setText("Acceleration X-"
				+ selectedType.getName());
		accelerationYCheckbox.setText("Acceleration Y-"
				+ selectedType.getName());
		accelerationZCheckbox.setText("Acceleration Z-"
				+ selectedType.getName());
	}

	public void updateSeries(double velocityX, double velocityY, double velocityZ, double accelerationX, double accelerationY, double accelerationZ, long timestamp) {
		updateVelocity(timestamp, velocityX, velocityY, velocityZ);
		updateAcceleration(timestamp, accelerationX, accelerationY, accelerationZ);
	}

	public void clearSeries() {
		seriesVelocityX.clear();
		seriesVelocityY.clear();
		seriesVelocityZ.clear();
		seriesAccelerationX.clear();
		seriesAccelerationY.clear();
		seriesAccelerationZ.clear();
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

	private void changeSeriesVisibility(TimeSeriesCollection dataset,
			boolean showVelocityX, boolean showVelocityY,
			boolean showVelocityZ, boolean showAccelerationX,
			boolean showAccelerationY, boolean showAccelerationZ) {
		if (showVelocityX) {
			if (!dataset.getSeries().contains(seriesVelocityX)) {
				dataset.addSeries(seriesVelocityX);
			}
		} else {
			if (dataset.getSeries().contains(seriesVelocityX)) {
				dataset.removeSeries(seriesVelocityX);
			}
		}
		if (showVelocityY) {
			if (!dataset.getSeries().contains(seriesVelocityY)) {
				dataset.addSeries(seriesVelocityY);
			}
		} else {
			if (dataset.getSeries().contains(seriesVelocityY)) {
				dataset.removeSeries(seriesVelocityY);
			}
		}
		if (showVelocityZ) {
			if (!dataset.getSeries().contains(seriesVelocityZ)) {
				dataset.addSeries(seriesVelocityZ);
			}
		} else {
			if (dataset.getSeries().contains(seriesVelocityZ)) {
				dataset.removeSeries(seriesVelocityZ);
			}
		}

		// acceleration checkboxes
		if (showAccelerationX) {
			if (!dataset.getSeries().contains(seriesAccelerationX)) {
				dataset.addSeries(seriesAccelerationX);
			}
		} else {
			if (dataset.getSeries().contains(seriesAccelerationX)) {
				dataset.removeSeries(seriesAccelerationX);
			}
		}
		if (showAccelerationY) {
			if (!dataset.getSeries().contains(seriesAccelerationY)) {
				dataset.addSeries(seriesAccelerationY);
			}
		} else {
			if (dataset.getSeries().contains(seriesAccelerationY)) {
				dataset.removeSeries(seriesAccelerationY);
			}
		}
		if (showAccelerationZ) {
			if (!dataset.getSeries().contains(seriesAccelerationZ)) {
				dataset.addSeries(seriesAccelerationZ);
			}
		} else {
			if (dataset.getSeries().contains(seriesAccelerationZ)) {
				dataset.removeSeries(seriesAccelerationZ);
			}
		}
	}

	private class ChartControlChangeListener implements ActionListener {
		@Override
		public synchronized void actionPerformed(ActionEvent e) {
			changeSeriesVisibility(dataset, velocityXCheckbox.isSelected(),
					velocityYCheckbox.isSelected(),
					velocityZCheckbox.isSelected(),
					accelerationXCheckbox.isSelected(),
					accelerationYCheckbox.isSelected(),
					accelerationZCheckbox.isSelected());
		}
	}

}
