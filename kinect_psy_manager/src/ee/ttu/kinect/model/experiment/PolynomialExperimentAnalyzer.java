package ee.ttu.kinect.model.experiment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.Observation;
import net.sourceforge.openforecast.models.PolynomialRegressionModel;
import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.Markers;
import ee.ttu.kinect.model.parser.SkeletonParserFile;
import ee.ttu.kinect.util.FileUtil;

public class PolynomialExperimentAnalyzer {
	
	private static FileUtil fileUtil = new FileUtil();
	
	private static SkeletonParserFile parser = new SkeletonParserFile();
	
	private static Map<String, Experiment> experiments = new LinkedHashMap<String, Experiment>();
	
	private static Map<String, List<Boolean>> experimentsCorrectness = new LinkedHashMap<String, List<Boolean>>();
	
	static {
		// init experiments 
		initExperiment("2013.07.02_17-21-20.csv", new Boolean[] {false, false, false, true, false, false, false, false, false, false});
		initExperiment("2013.07.02_17-24-13.csv", new Boolean[] {false, false, false, false, false, false, true, true, true, false});
		initExperiment("2013.07.02_17-27-28.csv", new Boolean[] {false, false, false, false, false, true, false, true, true, false});
		initExperiment("2013.07.02_17-30-17.csv", new Boolean[] {false, false, false, true, false, false, false, false, false, false, false}); // 11
		initExperiment("2013.07.02_17-33-53.csv", new Boolean[] {true, true, false, true, false, false, true, true, false, false});
		initExperiment("2013.07.02_17-36-11.csv", new Boolean[] {false, false, false, false, true, true, false, false, true, true});
		initExperiment("2013.07.02_17-38-47.csv", new Boolean[] {false, false, true, false, false, true, false, true, false, true, false, false}); // 12
		initExperiment("2013.07.02_17-42-08.csv", new Boolean[] {false, false, false, false, false, false, false, false, false, false});
		initExperiment("2013.07.02_17-44-11.csv", new Boolean[] {false, true, true, false, true, false, true, false, true, true});
		initExperiment("2013.07.02_17-46-54.csv", new Boolean[] {false, true, false, false, false, true, true, true, false, true});
		initExperiment("2013.07.02_17-49-00.csv", new Boolean[] {false, false, false, false, false, false, false, false, false, false});
		initExperiment("2013.07.02_17-51-12.csv", new Boolean[] {false, true, false, true, false, false, true, false, true, true});
		initExperiment("2013.07.02_17-52-42.csv", new Boolean[] {true, true, true, true, false, false, false, true, true, true});
		initExperiment("2013.07.02_17-54-52.csv", new Boolean[] {false, true, false, false, true, false, false, false, true, true});
		initExperiment("2013.07.02_17-56-52.csv", new Boolean[] {false, false, true, false, true, false, false, false, true, true});
	}
	
	private static void initExperiment(String experimentId, Boolean[] correctness) {
		experiments.put(experimentId, new Experiment(experimentId));
		experimentsCorrectness.put(experimentId, Arrays.asList(correctness));
	}
	
	public static void analyze(File[] files) {
		for (File file : files) {
			System.out.println("file name: " + file.getName());
			try {
				fileUtil.readFile(file);
				List<String> data = fileUtil.readAllLines();
				Experiment experiment = prepareExperiment(file.getName(), data);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		analyzeExperiments();
	}

	private static Experiment prepareExperiment(String experimentId, List<String> data) {
		Experiment experiment = experiments.get(experimentId);
		List<Body> motionData = null;
		int motionCounter = 0;
		Body body = new Body();
		Markers markers = new Markers();
		for (String item : data) {
			parser.parseSkeleton(item, body);
			parser.parseMarkers(item, markers);
			
			if (markers.getState()[0]) {
				if (motionData == null) {
					motionData = new ArrayList<Body>();
				}
				
				try {
					motionData.add(body.clone());
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			} else {
				if (motionData != null) {
					experiment.addMotion(motionData);
					experiment.setLastMotionCorrect(experimentsCorrectness.get(experimentId).get(motionCounter));
					motionCounter++;
					motionData = null;
				}
			}
		}
		
		return experiment;
	}
	
	private static void analyzeExperiments() {
		DataSet ds = new DataSet();
		String firstExperimentId = null;
		int i = 1;
		for (Experiment experiment : experiments.values()) {
			if (firstExperimentId == null) {
				firstExperimentId = experiment.getId();
			}
			
//			ExperimentDataPoint edp = new ExperimentDataPoint();
			Observation edp = new Observation(experiment.getAverageTrajectoryMass());
			edp.setIndependentValue("Ind", i);
//			edp.setDependentValue(experiment.getAverageTrajectoryMass());
			
			ds.add(edp);
			i++;
		}
		System.out.println("hoj " + experiments.values().size() + " " + firstExperimentId);
		PolynomialRegressionModel model = new PolynomialRegressionModel("Ind", 5);
		model.init(ds);
		System.out.println(model);
		
		//model.forecast(ds.getIndependentVariables()[0]);
	}
	
}
