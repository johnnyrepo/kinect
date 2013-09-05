package ee.ttu.kinect.model.experiment;

import java.io.File;
import java.util.Iterator;

import net.sourceforge.openforecast.DataPoint;
import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.Observation;
import net.sourceforge.openforecast.models.PolynomialRegressionModel;

public class PolynomialExperimentAnalyzer {
	
	private static enum ExperimentMeasure {
		AVG_TRAJECTORY_MASS, AVG_ACCELERATION_MASS, 
		BEST_TRAJECTORY_MASS, BEST_ACCELERATION_MASS
	};
	
	public static void analyze(File[] files) {
		ExperimentPreparator.prepareExperiments(files);
		
		analyzeExperiments(ExperimentMeasure.AVG_TRAJECTORY_MASS);
		analyzeExperiments(ExperimentMeasure.BEST_TRAJECTORY_MASS);
		analyzeExperiments(ExperimentMeasure.AVG_ACCELERATION_MASS);
		analyzeExperiments(ExperimentMeasure.BEST_ACCELERATION_MASS);
	}
	
	private static void analyzeExperiments(ExperimentMeasure measure) {
		System.out.println("Analyzing with " + measure);
		
		DataSet ds = new DataSet();
		int i = 1;
		for (Experiment experiment : ExperimentPreparator.experiments.values()) {	
			Observation obs = new Observation(getExperimentRealValue(experiment, measure));
			obs.setIndependentValue("Ind", i);
			
			ds.add(obs);
			i++;
		}
		
		PolynomialRegressionModel model = new PolynomialRegressionModel("Ind", 7);
		model.init(ds);
		
		System.out.println(model);
		System.out.println(ds);
		Iterator<DataPoint> dpIterator = ds.iterator();
		Iterator<Experiment> expIterator = ExperimentPreparator.experiments.values().iterator();
		while (dpIterator.hasNext()) {
			DataPoint dp = dpIterator.next();
			Experiment exp = expIterator.next();
			double forecast = model.forecast(dp);
			double real = getExperimentRealValue(exp, measure);
			System.out.println("Forecast with independent variable " + dp.getIndependentValue("Ind") + ": " 
					+ forecast + ", but Real: " + real + " and Diff(Forecast-Real): " + (forecast-real));
		}
		System.out.println("==============================\n");
	}
	
	private static double getExperimentRealValue(Experiment experiment, ExperimentMeasure measure) {
		double value = 0;
		switch (measure) {
			case AVG_TRAJECTORY_MASS:
				value = experiment.getAverageTrajectoryMass();
				break;
			case BEST_TRAJECTORY_MASS:
				value = experiment.getBestTrajectoryMass();
				break;
			case AVG_ACCELERATION_MASS:
				value = experiment.getAverageAccelerationMass();
				break;
			case BEST_ACCELERATION_MASS:
				value = experiment.getBestAccelerationMass();
				break;
		}
		
		return value;
	}
	
}
