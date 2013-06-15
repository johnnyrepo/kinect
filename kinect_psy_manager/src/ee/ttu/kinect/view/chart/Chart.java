package ee.ttu.kinect.view.chart;

import java.awt.Component;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeriesCollection;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.JointType;

public abstract class Chart extends JPanel {

	private static final long serialVersionUID = 1L;

	protected TimeSeriesCollection dataset;

	protected JFreeChart chart;

	public Chart(String valueAxisLabel, String timeAxisValues) {
		dataset = new TimeSeriesCollection();
		chart = ChartFactory.createTimeSeriesChart(null, timeAxisValues,
				valueAxisLabel, dataset, true, true, false);

		XYPlot plot = (XYPlot) chart.getPlot();
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("mm:ss.SSS"));

		setPreferredSize(new Dimension(1500, 260));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(new ChartPanel(chart));
	}

	public void clearChart() {
		for (Component comp : getComponents()) {
			if (comp instanceof ModelSeriesComponent) {
				((ModelSeriesComponent) comp).clearSeries();
			}
		}
	}

//	public void drawChart(List<Body> data, JointType selectedType,
//			boolean seatedMode) {
//		List<JointType> types = new ArrayList<JointType>();
//		types.add(selectedType);
//		drawChart(data, types, seatedMode);
//	}

	public abstract void drawChart(List<Body> data,
			List<JointType> selectedTypes, boolean seatedMode);

}
