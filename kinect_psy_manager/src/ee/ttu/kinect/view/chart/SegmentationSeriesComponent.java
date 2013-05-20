package ee.ttu.kinect.view.chart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;

import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

import ee.ttu.kinect.calc.Vector;
import ee.ttu.kinect.model.Joint;
import ee.ttu.kinect.model.JointType;

public class SegmentationSeriesComponent extends SeriesComponent {

	private static final long serialVersionUID = 1L;

	private TimeSeries velocitySeries;

	private TimeSeries accelerationSeries;
	
	private JCheckBox velocityCheckbox;
	
	private JCheckBox accelerationCheckbox;
	
	public SegmentationSeriesComponent(TimeSeriesCollection dataset) {
		super(dataset);
		velocitySeries = new TimeSeries("Velocity segmentation");
		accelerationSeries = new TimeSeries("Acceleration segmentation");
		
		velocityCheckbox = new JCheckBox();
		accelerationCheckbox = new JCheckBox();
		
		// control listeners
		ChartControlChangeListener controlListener = new ChartControlChangeListener();
		velocityCheckbox.addActionListener(controlListener);
		accelerationCheckbox.addActionListener(controlListener);
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(velocityCheckbox);
		add(accelerationCheckbox);
	}
	
	@Override
	public void setLabels(JointType selectedType) {
		velocitySeries.setKey(selectedType.getName() + " velocity");
		accelerationSeries.setKey(selectedType.getName() + " acceleration");
		
		velocityCheckbox.setText("Velocity " + selectedType.getName());
		accelerationCheckbox.setText("Acceleration " + selectedType.getName());
	}

	@Override
	public void updateSeries(Joint joint, long timestamp, boolean seatedMode) {
//		series.addOrUpdate(new TimeSeriesDataItem(
//				new FixedMillisecond(timestamp), joint.getClusterId()));
	}
	
	public void updateVelocitySeries(Vector vector, long timestamp) {
		velocitySeries.addOrUpdate(new TimeSeriesDataItem(
				new FixedMillisecond(timestamp), vector.getClusterId()));
	}
	
	public void updateAccelerationSeries(Vector vector, long timestamp) {
		accelerationSeries.addOrUpdate(new TimeSeriesDataItem(
				new FixedMillisecond(timestamp), vector.getClusterId()));
	}

	@Override
	public void clearSeries() {
		velocitySeries.clear();
		accelerationSeries.clear();
	}
	
	private void changeSeriesVisibility(TimeSeriesCollection dataset,
			boolean showVelocity, boolean showAcceleration) {
		if (showVelocity) {
			if (!dataset.getSeries().contains(velocitySeries)) {
				dataset.addSeries(velocitySeries);
			}
		} else {
			if (dataset.getSeries().contains(velocitySeries)) {
				dataset.removeSeries(velocitySeries);
			}
		}
		if (showAcceleration) {
			if (!dataset.getSeries().contains(accelerationSeries)) {
				dataset.addSeries(accelerationSeries);
			}
		} else {
			if (dataset.getSeries().contains(accelerationSeries)) {
				dataset.removeSeries(accelerationSeries);
			}
		}
	}
	
	private class ChartControlChangeListener implements ActionListener {
		@Override
		public synchronized void actionPerformed(ActionEvent e) {
			changeSeriesVisibility(dataset, velocityCheckbox.isSelected(), accelerationCheckbox.isSelected());
		}
	}

}
