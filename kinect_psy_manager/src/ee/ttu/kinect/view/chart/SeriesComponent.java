package ee.ttu.kinect.view.chart;

import javax.swing.JPanel;

import org.jfree.data.time.TimeSeriesCollection;

import ee.ttu.kinect.model.Joint;
import ee.ttu.kinect.model.JointType;


public abstract class SeriesComponent extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected TimeSeriesCollection dataset;

	public SeriesComponent(TimeSeriesCollection dataset) {
		this.dataset = dataset;
	}
	
	public abstract void setLabels(JointType selectedType);
	
	public abstract void updateSeries(Joint joint, long timestamp,
			boolean seatedMode);
	
	public abstract void clearSeries();

}
