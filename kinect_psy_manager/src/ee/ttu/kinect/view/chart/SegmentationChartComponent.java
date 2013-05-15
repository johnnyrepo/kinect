package ee.ttu.kinect.view.chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stromberglabs.cluster.Cluster;
import com.stromberglabs.cluster.Clusterable;
import com.stromberglabs.cluster.KClusterer;
import com.stromberglabs.cluster.KMeansClusterer;

import ee.ttu.kinect.calc.Calculator;
import ee.ttu.kinect.calc.Vector;
import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.Joint;
import ee.ttu.kinect.model.JointType;

public class SegmentationChartComponent extends ChartComponent {

	private static final int AMOUNT_OF_CLUSTERS = 8;
	
	private static final long serialVersionUID = 1L;

	private static final int STEP_BETWEEN_POINTS = 7;
	
	private static final int AMOUNT_OF_POINTS = 7;
	
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
			List<Vector> velocityData = new ArrayList<Vector>();
			Vector velocityVector = new Vector();
			
			for (int i = 0; i < data.size(); i++) {
				// making a big step forward:)
				for (int k = 0; k < AMOUNT_OF_POINTS; k++) {
					int x = i + k;
					if (x >= data.size()) {
						break;
					}
					double velocity1;
					double velocity2;
					Joint joint1;
					long time1;
					int j;
					
					joint1 = data.get(x).getJoint(selectedType);
					time1 = data.get(x).getTimestamp();
					j = x + STEP_BETWEEN_POINTS;
					if (j < data.size()) {
						Joint joint2 = data.get(j).getJoint(selectedType);
						long time2 = data.get(j).getTimestamp();
						velocity1 = Calculator.calculateVelocity3D(joint1, joint2, time1, time2);
					} else {
						break;
					}
					
					// taking next joint on timeline			
					joint1 = data.get(x + 1).getJoint(selectedType);
					time1 = data.get(x + 1).getTimestamp();
					j = x + 1 + STEP_BETWEEN_POINTS;
					if (j < data.size()) {
						Joint joint2 = data.get(j).getJoint(selectedType);
						long time2 = data.get(j).getTimestamp();
						velocity2 = Calculator.calculateVelocity3D(joint1, joint2, time1, time2);
					} else {
						break;
					}
					//System.out.println("hoj velocity " + velocity2 + "-" + velocity1);
					velocityVector.addElement(velocity2 - velocity1);
					
					if (velocityVector.getElements().size() == AMOUNT_OF_POINTS) {
						velocityVector.setTimestamp(data.get(i).getTimestamp());
						velocityData.add(velocityVector);
						velocityVector = new Vector();
					}
				}
			}
			
			System.out.println("hojaaaaa " + data.size() + " " + velocityData.size());
			calculateClusters(velocityData, clusterer);
			//calculateClusters(jointData, clusterer);
			
			// getting sequence of clusterId-s
			Map<Integer, Integer> oldToNewClusterIdMap = new HashMap<Integer, Integer>();
			int newClusterId = 0;
			for (Vector vector : velocityData) {
				Integer oldClusterId = vector.getClusterId();
				if (!oldToNewClusterIdMap.containsKey(oldClusterId)) {
					oldToNewClusterIdMap.put(oldClusterId, newClusterId);
					newClusterId++;
				}
				
				if (oldToNewClusterIdMap.size() == AMOUNT_OF_CLUSTERS) {
					break;
				}
			}
			for (Integer k : oldToNewClusterIdMap.keySet()) {
				System.out.println("newids " + k + " = " + oldToNewClusterIdMap.get(k));
			}
			// reassigning clusterId-s for readability
			for (Vector vector : velocityData) {
				vector.setClusterId(oldToNewClusterIdMap.get(vector.getClusterId()));
			}
			
			
			SegmentationSeriesComponent sc = new SegmentationSeriesComponent(dataset);
			add(sc);
			sc.setLabels(selectedType);
			
			for (Vector v : velocityData) {
				sc.updateSeries(v, v.getTimestamp(), seatedMode);
			}
			
			chartTitle += selectedType.getName() + " ";
		}
		chart.setTitle(chartTitle);
	}
	
	private <T> Cluster[] calculateClusters(List<Vector> data, KClusterer clusterer) {
		Cluster[] clusters = clusterer.cluster(data, AMOUNT_OF_CLUSTERS);
		System.out.println("Clusters = " + clusters.length);
		for (Cluster c : clusters) {
			System.out.println("items = " + c.getItems().size() + " id = " + c.getId());
			for (Clusterable cl : c.getItems()) {
				((Vector) cl).setClusterId(c.getId());
			}
			System.out.print("-=Centroid=-");
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
