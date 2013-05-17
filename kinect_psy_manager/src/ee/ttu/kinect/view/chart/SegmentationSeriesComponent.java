package ee.ttu.kinect.view.chart;

import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

import ee.ttu.kinect.calc.Vector;
import ee.ttu.kinect.model.Joint;
import ee.ttu.kinect.model.JointType;

public class SegmentationSeriesComponent extends SeriesComponent {

	private static final long serialVersionUID = 1L;

	private TimeSeries series;
	

	public SegmentationSeriesComponent(TimeSeriesCollection dataset) {
		super(dataset);
		series = new TimeSeries("Segmentation");
		dataset.addSeries(series);
	}
	
	@Override
	public void setLabels(JointType selectedType) {
		series.setKey(selectedType.getName());
	}

	@Override
	public void updateSeries(Joint joint, long timestamp, boolean seatedMode) {
		series.addOrUpdate(new TimeSeriesDataItem(
				new FixedMillisecond(timestamp), joint.getClusterId()));
	}
	
	public void updateSeries(Vector vector, long timestamp, boolean seatedMode) {
		//System.out.println("hoj " + vector.getClusterId() + " " + vector.getLocation() + " " + timestamp);
		series.addOrUpdate(new TimeSeriesDataItem(
				new FixedMillisecond(timestamp), vector.getClusterId()));
	}

	@Override
	public void clearSeries() {
		series.clear();
	}

}
