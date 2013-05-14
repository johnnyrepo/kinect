package ee.ttu.kinect.view.chart;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kohonen.LearningData;
import kohonen.WTALearningFunction;
import learningFactorFunctional.ConstantFunctionalFactor;
import metrics.EuclidesMetric;
import network.DefaultNetwork;
import topology.MatrixTopology;

import com.stromberglabs.cluster.Cluster;
import com.stromberglabs.cluster.Clusterable;
import com.stromberglabs.cluster.KClusterer;
import com.stromberglabs.cluster.KMeansClusterer;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.Joint;
import ee.ttu.kinect.model.JointType;

public class SegmentationChartComponent extends ChartComponent {

	private static final long serialVersionUID = 1L;

	@Override
	public void drawChart(List<Body> data, List<JointType> selectedTypes,
			boolean seatedMode) {
		// draw the result
		String chartTitle = "";
		for (JointType selectedType : selectedTypes) {
			// Kohonen
			//calculateKohonen(model.getFileToPlay());
			// K-mean
			KClusterer clusterer = new KMeansClusterer();
			List<Joint> jointData = getListOfJoints(data, selectedType);
			calculateClusters(jointData, clusterer);
			
			SegmentationSeriesComponent sc = new SegmentationSeriesComponent(dataset);
			add(sc);
			sc.setLabels(selectedType);
			
			for (Body body : data) {
				if (body == null || !body.isBodyReady()) {
					continue;
				}
				sc.updateSeries(body.getJoint(selectedType), body.getTimestamp(), false);
			}
			
			chartTitle += selectedType.getName() + " ";
		}
		chart.setTitle(chartTitle);
	}

	private Cluster[] calculateClusters(List<Joint> data, KClusterer clusterer) {
		Cluster[] clusters = clusterer.cluster(data, 8);
		System.out.println("Clusters = " + clusters.length);
		for (Cluster c : clusters) {
			System.out.println("items = " + c.getItems().size() + " id = " + c.getId());
			for (Clusterable cl : c.getItems()) {
				((Joint) cl).setClusterId(c.getId());
			}
			System.out.print("-=Centroid=- ");
			for (float centr : c.getClusterMean()) {
				System.out.print(centr + " ");
			}
			System.out.println();
		}
		
		return clusters;
	}

	private List<Joint> getListOfJoints(List<Body> data, JointType type) {
		List<Joint> jointData = new ArrayList<Joint>();
		for (Body body : data) {
			Joint joint = body.getJoint(type);
			jointData.add(joint);
		}
		
		return jointData;
	}
	
//	private void calculateKohonen(File file) {
//		MatrixTopology topology = new MatrixTopology(10, 10, 10);
//		double[] maxWeight = {100, 100, 100};
//		DefaultNetwork network = new DefaultNetwork(3, maxWeight, topology);
//		ConstantFunctionalFactor constantFactor = new ConstantFunctionalFactor(0.8);
//		LearningData data = new LearningData(file.getAbsolutePath());
//		WTALearningFunction learning = new WTALearningFunction(network, 20, new EuclidesMetric(), data, constantFactor);
//		learning.learn();
//		System.out.println(network);
//	}
	
}
