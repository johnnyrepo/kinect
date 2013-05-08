package ee.ttu.kinect.view;

import java.awt.Component;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class ChartComponent extends JPanel {

	private static final long serialVersionUID = 1L;

	private TimeSeriesCollection dataset;

	private JFreeChart chart;

	private ChartPanel chartPanel;

//	private List<SeriesComponent> seriesList;
//	
//	private int seriesCount;
	
	
	public ChartComponent() {
		dataset = new TimeSeriesCollection();

		chart = ChartFactory.createTimeSeriesChart(null, null,
				"Velocity/Acceleration", dataset, true, true, false);
		XYPlot plot = (XYPlot) chart.getPlot();
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("mm:ss.SSS"));

		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(1500, 260));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(chartPanel);
	}

	public void clearChart() {
		for (Component comp : getComponents()) {
			if (comp instanceof SeriesComponent) {
				((SeriesComponent) comp).clearSeries();
			}
		}
	}

	public void drawChart(List<Body> data, List<JointType> selectedTypes,
			boolean seatedMode) {
		String chartTitle = "";
		for (JointType selectedType : selectedTypes) {
			SeriesComponent sc = new SeriesComponent(dataset);
			add(sc);
			sc.setLabels(selectedType);
			
			for (Body body : data) {
				if (body == null || !body.isBodyReady()) {
					continue;
				}
				sc.updateSeries(body, selectedType, seatedMode);
			}
			
			chartTitle += selectedType.getName() + " ";
		}
		chart.setTitle(chartTitle);
	}

	public void drawChart(List<Body> data, JointType selectedType,
			boolean seatedMode) {
		List<JointType> types = new ArrayList<JointType>();
		types.add(selectedType);
		drawChart(data, types , seatedMode);
//		SeriesComponent sc = new SeriesComponent(dataset);
//		for (Body body : data) {
//			if (body == null || !body.isBodyReady()) {
//				continue;
//			}
//			sc.updateSeries(body, selectedType, seatedMode);
//		}
//		chart.setTitle(new TextTitle(selectedType.getName()));
	}

}
