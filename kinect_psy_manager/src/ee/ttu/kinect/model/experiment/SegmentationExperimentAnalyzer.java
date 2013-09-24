package ee.ttu.kinect.model.experiment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.stromberglabs.cluster.Cluster;
import com.stromberglabs.cluster.Clusterable;
import com.stromberglabs.cluster.KClusterer;
import com.stromberglabs.cluster.KMeansClusterer;

import ee.ttu.kinect.calc.Step;
import ee.ttu.kinect.model.experiment.Experiment.Motion;

public class SegmentationExperimentAnalyzer {

	public static void analyze(File[] files) {
		ExperimentPreparator.prepareExperiments(files);
		
		List<MotionStep> steps;
		for (String experimentId : ExperimentPreparator.experiments.keySet()) {
			steps = new ArrayList<MotionStep>();
			Experiment experiment = ExperimentPreparator.experiments.get(experimentId);
			int i = 0;
			for (Motion motion : experiment.getMotions()) {
				MotionStep step = new MotionStep();
				step.setMotion(motion);
				step.addElement(motion.getTrajectoryMass());
				step.addElement(motion.getAccelerationMass());
				//step.addElement(motion.getVelocityMass());
				//step.addElement(motion.getDurationTime());
				step.setTimestamp(i);
				steps.add(step);
				i++;
			}
			
			System.out.println(experimentId);
			
			calculateKmean(steps);
		}
	}
	
	private static void calculateKmean(List<MotionStep> data) {
		KClusterer clusterer = new KMeansClusterer();
		Cluster[] clusters = clusterer.cluster(data, 3);
		for (Cluster c : clusters) {
			for (Clusterable cl : c.getItems()) {
				((Step) cl).setClusterId(c.getId());
			}
		}
		
		//printResult(clusters);
		printSortedResult(clusters);
	}

	private static void printResult(Cluster[] clusters) {
		System.out.println("Clusters = " + clusters.length);
		for (Cluster c : clusters) {
			System.out.println("items = " + c.getItems().size() + " id = " + c.getId());
			for (Clusterable cl : c.getItems()) {
				System.out.println(((Step) cl).getTimestamp() + " = " + ((Step) cl).getElements().get(1));
			}
			System.out.print("=Centroid=");
			for (float centr : c.getClusterMean()) {
				System.out.print(centr + " ");
			}
			System.out.println();
		}
	}
	
	private static void printSortedResult(Cluster[] clusters) {
		List<MotionStep> unsortedSteps = new ArrayList<MotionStep>();
		for (Cluster c : clusters) {
			for (Clusterable cl : c.getItems()) {
				unsortedSteps.add(((MotionStep) cl));
			}
		}
		List<MotionStep> sortedSteps = new ArrayList<MotionStep>();
		for (int i = 0; i < unsortedSteps.size(); i++) {
			for (MotionStep step : unsortedSteps) {
				if (step.getTimestamp() == i) {
					sortedSteps.add(step);
				}
			}
		}
		
//		int clusterWithLessAccelMass = 0;
//		double accelMass = clusters[0].getClusterMean()[1];
//		for (int i = 0; i < clusters.length; i++) {
//			Cluster c = clusters[i];
//			double meanAccelMass = c.getClusterMean()[1];
//			if (accelMass > meanAccelMass) {
//				accelMass = meanAccelMass;
//				clusterWithLessAccelMass = i;
//			}
//		}
		
		System.out.print("Index, ");
		for (MotionStep step : sortedSteps) {
			System.out.print(step.getTimestamp() + ", ");
		}
		System.out.println();
		System.out.print("Expected, ");
		for (MotionStep step : sortedSteps) {
			System.out.print(step.getMotion().isCorrect() + ", ");
		}
		System.out.println();
		System.out.print("Actual, ");
		for (MotionStep step : sortedSteps) {
			//System.out.print((step.getClusterId() == clusterWithLessAccelMass ? true : false) + ", ");
			System.out.print(step.getClusterId() + ", ");
		}
		System.out.println();
//		System.out.print("Accel. mass, ");
//		for (MotionStep step : sortedSteps) {
//			System.out.print(step.getMotion().getAccelerationMass() + ", ");
//		}
//		System.out.println();
	}

}