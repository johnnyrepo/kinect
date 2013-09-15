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
		
		List<Step> steps = new ArrayList<Step>();
		for (String experimentId : ExperimentPreparator.experiments.keySet()) {
			Experiment experiment = ExperimentPreparator.experiments.get(experimentId);
			int i = 0;
			for (Motion motion : experiment.getMotions()) {
				Step step = new Step();
				step.addElement(motion.getTrajectoryMass());
				step.addElement(motion.getAccelerationMass());
				//step.addElement(motion.getVelocityMass());
				//step.addElement(motion.getDurationTime());
				step.setTimestamp(i);
				steps.add(step);
				i++;
			}
			
			calculateKmean(steps);
		}
	}
	
	private static void calculateKmean(List<Step> data) {
		KClusterer clusterer = new KMeansClusterer();
		Cluster[] clusters = clusterer.cluster(data, 2);
		System.out.println("Clusters = " + clusters.length);
		for (Cluster c : clusters) {
			System.out.println("items = " + c.getItems().size() + " id = " + c.getId());
			for (Clusterable cl : c.getItems()) {
				((Step) cl).setClusterId(c.getId());
				System.out.println(((Step) cl).getTimestamp() + " = " + ((Step) cl).getElements().get(1));
			}
			System.out.print("=Centroid=");
			for (float centr : c.getClusterMean()) {
				System.out.print(centr + " ");
			}
			System.out.println();
		}
	}

}