package ee.ttu.kinect.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JCheckBox;

import com.stromberglabs.cluster.Cluster;
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
				List<Body> data = model.getFileData();
				view.openChartSelector(data);

				KClusterer clusterer1 = new KMeansClusterer();
				KClusterer clusterer2 = new KMeansForestClusterer();
				KClusterer clusterer3 = new KMeansTreeClusterer();
				KClusterer clusterer4 = new ElkanKMeansClusterer();
				calculateClusters(data, clusterer1);
				calculateClusters(data, clusterer2);
				calculateClusters(data, clusterer3);
				calculateClusters(data, clusterer4);
			}		
		
			private void calculateClusters(List<Body> data, KClusterer clusterer) {
				List<Joint> jointData = getListOfJoints(data, JointType.HAND_LEFT);
				Cluster[] clusters = clusterer.cluster(jointData, 8);
				System.out.println("Clusters = " + clusters.length);
				for (Cluster c : clusters){
					System.out.println(c.getItems().size());
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
