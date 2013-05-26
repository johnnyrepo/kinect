package ee.ttu.kinect.view.chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kohonen.WTALearningFunction;
import learningFactorFunctional.ConstantFunctionalFactor;
import metrics.EuclidesMetric;
import network.DefaultNetwork;
import network.KohonenNeuron;
import topology.MatrixTopology;
import activationFunction.ActivationFunctionModel;
import activationFunction.LinearActivationFunction;

import com.stromberglabs.cluster.Cluster;
import com.stromberglabs.cluster.Clusterable;
import com.stromberglabs.cluster.KClusterer;
import com.stromberglabs.cluster.KMeansClusterer;

import ee.ttu.kinect.calc.Calculator;
import ee.ttu.kinect.calc.KohonenLearningData;
import ee.ttu.kinect.calc.Step;
import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.Joint;
import ee.ttu.kinect.model.JointType;

public class SegmentationChart extends Chart {

	private static final long serialVersionUID = 1L;
	
	private static final int AMOUNT_OF_CLUSTERS = 5; // 8

	private static final int STEP_BETWEEN_POINTS = 5; // 7
	
	private static final int AMOUNT_OF_POINTS = 6; // 7
	
	private int clustersAmount;
	
	private int stepBetweenPoints;
	
	private int pointsAmount;
	
	public SegmentationChart(int clustersAmount, int stepBetweenPoints, int pointsAmount) {
		this.clustersAmount = clustersAmount;
		this.stepBetweenPoints = stepBetweenPoints;
		this.pointsAmount = pointsAmount;
	}
	
	@Override
	public void drawChart(List<Body> data, List<JointType> selectedTypes,
			boolean seatedMode) {
		// draw the result
		String chartTitle = "";
		for (JointType selectedType : selectedTypes) {
			SegmentationSeriesComponent sc = new SegmentationSeriesComponent(dataset);
			add(sc);
			sc.setLabels(selectedType);
			
			drawSegmentationChart(sc, data, selectedType, true);
			drawSegmentationChart(sc, data, selectedType, false);
			
			chartTitle += selectedType.getName() + " ";
		}
		chart.setTitle(chartTitle);
	}

	private void drawSegmentationChart(SegmentationSeriesComponent sc, List<Body> data, JointType selectedType, boolean isVelocity) {
		// organizing data into steps
		List<Step> stepData = organizeDataIntoSteps(data, selectedType, isVelocity);
		System.out.println("hojaaaaa " + data.size() + " " + stepData.size());
		// K-mean
		calculateKmean(stepData);
		// Kohonen
		//calculateKohonen(velocityData);
		
		reassignClusterIds(stepData);
		
		for (Step s : stepData) {
			if (isVelocity) {
				sc.updateVelocitySeries(s, s.getTimestamp());
			} else {
				sc.updateAccelerationSeries(s, s.getTimestamp());
			}
		}
	}

	private List<Step> organizeDataIntoSteps(List<Body> data, JointType selectedType, boolean isVelocityCalculation) {
		List<Step> velocityData = new ArrayList<Step>();
		
		for (int i = 0; i < data.size(); i++) {
			Step step = new Step();
			// making a big step forward:)
			for (int k = 0; k < pointsAmount; k++) {
				int x = i + k;
				if (x >= data.size()) {
					break;
				}
				double value1;
				double value2;
				Joint joint1;
				long time1;
				int j;
				
				joint1 = data.get(x).getJoint(selectedType);
				time1 = data.get(x).getTimestamp();
				j = x + stepBetweenPoints;
				if (j < data.size()) {
					Joint joint2 = data.get(j).getJoint(selectedType);
					long time2 = data.get(j).getTimestamp();
					if (isVelocityCalculation) {
						value1 = Calculator.calculateVelocity3D(joint1, joint2, time1, time2);
					} else {
						value1 = Calculator.calculateAcceleration3D(joint1, joint2, time1, time2);
					}
				} else {
					break;
				}
				
				// taking next joint on timeline			
				joint1 = data.get(x + 1).getJoint(selectedType);
				time1 = data.get(x + 1).getTimestamp();
				j = x + 1 + stepBetweenPoints;
				if (j < data.size()) {
					Joint joint2 = data.get(j).getJoint(selectedType);
					long time2 = data.get(j).getTimestamp();
					if (isVelocityCalculation) {
						value2 = Calculator.calculateVelocity3D(joint1, joint2, time1, time2);
					} else {
						value2 = Calculator.calculateAcceleration3D(joint1, joint2, time1, time2);
					}
				} else {
					break;
				}
				
				//System.out.println("hoj velocity " + velocity2 + "-" + velocity1);
				step.addElement(value2 - value1);
				
				if (step.getElements().size() == pointsAmount) {
					step.setTimestamp(data.get(i).getTimestamp());
					velocityData.add(step);
					step = new Step();
				}
			}
		}
		
		return velocityData;
	}
	
	private void reassignClusterIds(List<Step> steps) {
		// getting sequence of clusterId-s
		Map<Integer, Integer> oldToNewClusterIdMap = new HashMap<Integer, Integer>();
		int newClusterId = 0;
		for (Step step : steps) {
			Integer oldClusterId = step.getClusterId();
			if (!oldToNewClusterIdMap.containsKey(oldClusterId)) {
				oldToNewClusterIdMap.put(oldClusterId, newClusterId);
				newClusterId++;
			}
			
			if (oldToNewClusterIdMap.size() == clustersAmount) {
				break;
			}
		}
		for (Integer k : oldToNewClusterIdMap.keySet()) {
			System.out.println("newids " + k + " = " + oldToNewClusterIdMap.get(k));
		}
		// reassigning clusterId-s for readability
		for (Step step : steps) {
			step.setClusterId(oldToNewClusterIdMap.get(step.getClusterId()));
		}
	}
	
	private void calculateKmean(List<Step> data) {
		KClusterer clusterer = new KMeansClusterer();
		Cluster[] clusters = clusterer.cluster(data, clustersAmount);
		System.out.println("Clusters = " + clusters.length);
		for (Cluster c : clusters) {
//			System.out.println("items = " + c.getItems().size() + " id = " + c.getId());
			for (Clusterable cl : c.getItems()) {
				((Step) cl).setClusterId(c.getId());
			}
//			System.out.print("-=Centroid=-");
//			for (float centr : c.getClusterMean()) {
//				System.out.print(centr + " ");
//			}
//			System.out.println();
		}
	}

//	private List<Joint> getListOfJoints(List<Body> data, JointType type) {
//		List<Joint> jointData = new ArrayList<Joint>();
//		for (Body body : data) {
//			Joint joint = body.getJoint(type);
//			jointData.add(joint);
//		}
//		
//		return jointData;
//	}
	
	private void calculateKohonen(List<Step> data) {
		MatrixTopology topology = new MatrixTopology(3, 3, 3);
		double[] maxWeight = {8, 8, 8};
		DefaultNetwork network = new DefaultNetwork(3, maxWeight, topology);
		ConstantFunctionalFactor constantFactor = new ConstantFunctionalFactor(0.8);
		KohonenLearningData learningData = new KohonenLearningData(data);
		WTALearningFunction learning = new WTALearningFunction(network, 20, new EuclidesMetric(), learningData, constantFactor);
		learning.learn();
		System.out.println(network);
		for (int i = 0; i < network.getNumbersOfNeurons(); i++) {
			System.out.println("Neuron= " + network.getNeuron(i));
			for (double d : network.getNeuron(i).getWeight()) {
				System.out.println("weight= " + d);
			}
		}
		ActivationFunctionModel function = new LinearActivationFunction();
		KohonenNeuron neuron = new KohonenNeuron(7, maxWeight, function);
		double d = neuron.getValue(data.get(0).getElementsAsArr());
		System.out.println("Double d= " + d);
	}
	
}
