package ee.ttu.kinect.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JCheckBox;

import kohonen.LearningData;
import kohonen.WTALearningFunction;
import learningFactorFunctional.ConstantFunctionalFactor;
import metrics.EuclidesMetric;
import network.DefaultNetwork;
import topology.MatrixTopology;

import com.stromberglabs.cluster.Cluster;
import com.stromberglabs.cluster.Clusterable;
import com.stromberglabs.cluster.ElkanKMeansClusterer;
import com.stromberglabs.cluster.KClusterer;
import com.stromberglabs.cluster.KMeansClusterer;
import com.stromberglabs.cluster.KMeansForestClusterer;
import com.stromberglabs.cluster.KMeansTreeClusterer;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.Joint;
import ee.ttu.kinect.model.JointType;
import ee.ttu.kinect.model.MainModel;
import ee.ttu.kinect.view.MainView;


public class MainController {

	private MainView view;

	private MainModel model;

	public MainController() {
		model = new MainModel(this);

		view = new MainView();

		//SwingUtilities.invokeLater(view);
		
		view.addListenerForMenuOpen(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setFileToPlay(view.getSelectedFile()); //TODO: fix this hack
				view.setPlayingEnabled(true);
			}
		});
		view.addListenerForStartRecord(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.startRecord();
			}
		});
		view.addListenerForStopRecord(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.stopRecord();
			}
		});
		view.addListenerForStartPlay(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.isFileRunPaused()) {
					model.unpauseFileRun();
				} else {
					view.clearChart();
					view.clearDraw();
					model.stopSensorRun();
					model.startFileRun();
				}
			}
		});
		view.addListenerForPause(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.pauseFileRun();
			}
		});
		view.addListenerForStopPlay(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.stopFileRun();
			}
		});
		view.addListenerForSensorOn(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean sensorOn = ((JCheckBox) e.getSource()).isSelected();
				Logger.getLogger(getClass().getName()).info("sensor mode changed: " + sensorOn);
				if (!sensorOn) {
					model.stopSensorRun();
				} else {
					view.clearChart();
					model.startSensorRun();
				}
			}
		});
		view.addListenerForSeatedModeCheckbox(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean seatedMode = ((JCheckBox) e.getSource()).isSelected();
				if (!seatedMode) {
					model.setDefaultMode();
				} else {
					model.setSeatedMode();
				}
			}
		});
		view.addListenerForDrawChart(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.openChartSelector(model.getFileData());
			}
		});
		
		view.addListenerForSegmentChart(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Body> data = model.getFileData();
				// K-mean
				KClusterer clusterer1 = new KMeansClusterer();
				//KClusterer clusterer2 = new KMeansForestClusterer();
				//KClusterer clusterer3 = new KMeansTreeClusterer();
				//KClusterer clusterer4 = new ElkanKMeansClusterer();
				calculateClusters(data, clusterer1);
				//calculateClusters(data, clusterer2);
				//calculateClusters(data, clusterer3);
				//calculateClusters(data, clusterer4);
				
				// Kohonen
				//calculateKohonen(model.getFileToPlay());
			}

			private void calculateClusters(List<Body> data, KClusterer clusterer) {
				List<Joint> jointData = getListOfJoints(data, JointType.HIP_CENTER);
				Cluster[] clusters = clusterer.cluster(jointData, 8);
				System.out.println("Clusters = " + clusters.length);
				for (Cluster c : clusters) {
					System.out.println("items = " + c.getItems().size() + " id = " + c.getId());
//					for (Clusterable cl : c.getItems()) {
//						for (float coord : cl.getLocation()) {
//							System.out.print(coord + "\t");
//						}
//						System.out.println();
//					}
					System.out.print("Centroid = ");
					for (float centr : c.getClusterMean()) {
						System.out.print(centr + " ");
					}
					System.out.println();
				}
			}

			private List<Joint> getListOfJoints(List<Body> data, JointType type) {
				List<Joint> jointData = new ArrayList<Joint>();
				for (Body body : data) {
					Joint joint = body.getJoint(type);
					//System.out.println("hoj " + joint);
					if (joint != null) {
						jointData.add(joint);
					}
				}
				
				return jointData;
			}
			
			private void calculateKohonen(File file) {
				MatrixTopology topology = new MatrixTopology(10, 10, 10);
				double[] maxWeight = {100, 100, 100};
				DefaultNetwork network = new DefaultNetwork(3, maxWeight, topology);
				ConstantFunctionalFactor constantFactor = new ConstantFunctionalFactor(0.8);
				LearningData data = new LearningData(file.getAbsolutePath());
				WTALearningFunction learning = new WTALearningFunction(network, 20, new EuclidesMetric(), data, constantFactor);
				learning.learn();
				System.out.println(network);
			}
		});

		view.setRecordingEnabled(false);
		view.setPlayingEnabled(false);
		model.startSensorRun();
	}

	public void redrawSkeleton(Body body) {
		view.redrawSkeleton(body, model.isSeatedMode());
	}
	
	public void redrawChart(Body body) {
		view.redrawChart(body, model.isSeatedMode());
	}
	
	public void showMessagePopup(String message) {
		view.showMessagePopup(message);
	}
	
	public boolean[] getMarkersState() {
		return view.getMarkersState();
	}
	
	public void setSensorEnabled(boolean enabled) {
		view.setSensorEnabled(enabled);
		view.setRecordingEnabled(enabled);
	}
	
	public static void main(String... args) {
		new MainController();
	}

}
