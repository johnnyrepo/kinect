package ee.ttu.kinect.view.chart;

import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

import ee.ttu.kinect.model.JointType;

public class MovementSeriesComponent extends SeriesComponent {

	private static final long serialVersionUID = 1L;

	private TimeSeries trajectorySeries;

	public MovementSeriesComponent(TimeSeriesCollection dataset) {
		super(dataset);
		trajectorySeries = new TimeSeries("Trajectory");
		dataset.addSeries(trajectorySeries);
	}

	public void updateSeries(double trajectory, long timestamp) {
		trajectorySeries.addOrUpdate(new TimeSeriesDataItem(
				new FixedMillisecond(timestamp), trajectory));
	}

	@Override
	public void setLabels(JointType selectedType) {
		trajectorySeries.setKey(selectedType.getName() + " trajectory");
	}

	@Override
	public void clearSeries() {
		trajectorySeries.clear();
	}

}
