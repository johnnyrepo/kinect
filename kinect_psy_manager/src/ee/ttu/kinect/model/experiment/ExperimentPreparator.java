package ee.ttu.kinect.model.experiment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.Markers;
import ee.ttu.kinect.model.parser.SkeletonParserFile;
import ee.ttu.kinect.util.FileUtil;

public class ExperimentPreparator {
	
	private static FileUtil fileUtil = new FileUtil();
	
	private static SkeletonParserFile parser = new SkeletonParserFile();
	
	public static Map<String, Experiment> experiments = new LinkedHashMap<String, Experiment>();
	
	public static Map<String, List<Boolean>> experimentsCorrectness = new LinkedHashMap<String, List<Boolean>>();
	
	private static Map<String, Experiment> allExperiments = new LinkedHashMap<String, Experiment>();
	
	private static Map<String, List<Boolean>> allExperimentsCorrectness = new LinkedHashMap<String, List<Boolean>>();
	
	private static Map<String, Experiment> experimentsV = new LinkedHashMap<String, Experiment>();
	
	private static Map<String, List<Boolean>> experimentsCorrectnessV = new LinkedHashMap<String, List<Boolean>>();
	
	private static Map<String, Experiment> experimentsS = new LinkedHashMap<String, Experiment>();
	
	private static Map<String, List<Boolean>> experimentsCorrectnessS = new LinkedHashMap<String, List<Boolean>>();
	
	static {
		// init experiments for V
		initExperimentV("2013.07.02_17-21-20.csv", new Boolean[] {false, false, false, true, false, false, false, false, false, false});
		initExperimentV("2013.07.02_17-24-13.csv", new Boolean[] {false, false, false, false, false, false, true, true, true, false});
		initExperimentV("2013.07.02_17-27-28.csv", new Boolean[] {false, false, false, false, false, true, false, true, true, false});
		initExperimentV("2013.07.02_17-30-17.csv", new Boolean[] {false, false, false, true, false, false, false, false, false, false, false}); // 11
		initExperimentV("2013.07.02_17-33-53.csv", new Boolean[] {true, true, false, true, false, false, true, true, false, false});
		initExperimentV("2013.07.02_17-36-11.csv", new Boolean[] {false, false, false, false, true, true, false, false, true, true});
		initExperimentV("2013.07.02_17-38-47.csv", new Boolean[] {false, false, true, false, false, true, false, true, false, true, false, false}); // 12
		initExperimentV("2013.07.02_17-42-08.csv", new Boolean[] {false, false, false, false, false, false, false, false, false, false});
		initExperimentV("2013.07.02_17-44-11.csv", new Boolean[] {false, true, true, false, true, false, true, false, true, true});
		initExperimentV("2013.07.02_17-46-54.csv", new Boolean[] {false, true, false, false, false, true, true, true, false, true});
		initExperimentV("2013.07.02_17-49-00.csv", new Boolean[] {false, false, false, false, false, false, false, false, false, false});
		initExperimentV("2013.07.02_17-51-12.csv", new Boolean[] {false, true, false, true, false, false, true, false, true, true});
		initExperimentV("2013.07.02_17-52-42.csv", new Boolean[] {true, true, true, true, false, false, false, true, true, true});
		initExperimentV("2013.07.02_17-54-52.csv", new Boolean[] {false, true, false, false, true, false, false, false, true, true});
		initExperimentV("2013.07.02_17-56-52.csv", new Boolean[] {false, false, true, false, true, false, false, false, true, true});
	}
	
	static {
		// init experiments for S
		initExperimentS("2013.07.10_17-58-22.csv" , new Boolean[] {false, false, false, false, false, false, false, true, false, false});
		initExperimentS("2013.07.10_18-01-08.csv" , new Boolean[] {true, false, false, true, true, false, false, false, true, false});
		initExperimentS("2013.07.10_18-03-14.csv" , new Boolean[] {false, false, false, true, true, false, true, false, true, false});
		initExperimentS("2013.07.10_18-04-59.csv" , new Boolean[] {false, false, false, false, false, false, false, false, false, true});
		initExperimentS("2013.07.10_18-06-50.csv" , new Boolean[] {false, true, false, false, false, true, true, false, true, true});
		initExperimentS("2013.07.10_18-08-27.csv" , new Boolean[] {false, true, true, false, true, false, false, false, true, true});
		initExperimentS("2013.07.10_18-10-43.csv" , new Boolean[] {false, false, false, false, true, false, true, false, true, false});
		initExperimentS("2013.07.10_18-12-28.csv" , new Boolean[] {false, true, true, false, true, false, true, true, true, false});
		initExperimentS("2013.07.10_18-14-21.csv" , new Boolean[] {false, true, true, false, true, false, true, true, true, false});
		initExperimentS("2013.07.10_18-17-32.csv" , new Boolean[] {false, false, false, true, false, false, true, false, false, false});
		initExperimentS("2013.07.10_18-21-05.csv" , new Boolean[] {false, false, false, false, false, true, false, false, true, false});
		initExperimentS("2013.07.10_18-23-02.csv" , new Boolean[] {true, true, false, false, true, true, false, false, false, true});
		initExperimentS("2013.07.10_18-24-39.csv" , new Boolean[] {false, false, true, true, true, false, false, false, true, false});
		initExperimentS("2013.07.10_18-25-55.csv" , new Boolean[] {true, true, false, false, false, false, false, false, true, false});
		initExperimentS("2013.07.10_18-27-40.csv" , new Boolean[] {false, true, true, false, false, false, false, false, false, false});
		initExperimentS("2013.07.10_18-30-17.csv" , new Boolean[] {true, true, true, true, true, true, true, true, true, true});
		initExperimentS("2013.07.10_18-31-57.csv" , new Boolean[] {false, true, true, true, true, false, false, true, false, true});
		initExperimentS("2013.07.10_18-33-32.csv" , new Boolean[] {false, false, false, false, true, false, true, false, true, true});
		initExperimentS("2013.07.10_18-35-18.csv" , new Boolean[] {false, true, false, false, false, false, false, false, true, false});
		initExperimentS("2013.07.10_18-36-51.csv" , new Boolean[] {true, true, true, true, true, true, true, true, true, false});

	}
	
	static {
		allExperiments.putAll(experimentsV);
		allExperiments.putAll(experimentsS);
		allExperimentsCorrectness.putAll(experimentsCorrectnessV);
		allExperimentsCorrectness.putAll(experimentsCorrectnessS);
	}
	
	private static void initExperimentV(String experimentId, Boolean[] correctness) {
		experimentsV.put(experimentId, new Experiment(experimentId));
		experimentsCorrectnessV.put(experimentId, Arrays.asList(correctness));
	}
	
	private static void initExperimentS(String experimentId, Boolean[] correctness) {
		experimentsS.put(experimentId, new Experiment(experimentId));
		experimentsCorrectnessS.put(experimentId, Arrays.asList(correctness));
	}
	
	private static void resetExperiments() {
		experiments.clear();
		experimentsCorrectness.clear();
	}
	
	private static void initExperiments(File[] files) {
		for (File file : files) {
			String experimentId = file.getName();
			experiments.put(experimentId , allExperiments.get(experimentId));
			experimentsCorrectness.put(experimentId , allExperimentsCorrectness.get(experimentId));
		}
	}
	
	public static void prepareExperiments(File[] files) {
		resetExperiments();
		initExperiments(files);
		
		for (File file : files) {
			System.out.println("Preparing experiment from file: \n" + file.getName());
			try {
				fileUtil.readFile(file);
				List<String> data = fileUtil.readAllLines();
				prepareExperiment(file.getName(), data);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
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
	
}
