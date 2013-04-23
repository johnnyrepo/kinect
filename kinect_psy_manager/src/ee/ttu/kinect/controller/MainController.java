package ee.ttu.kinect.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Logger;

import javax.swing.JCheckBox;

import ee.ttu.kinect.model.Body;
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
				File file = view.getSelectedFile(); //TODO: fix this hack
				if (file != null) {
					model.readFile(file);
				}
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
				view.clearChart();
				model.stopSensorRun();
				model.startFileRun();
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
				Logger.getLogger(getClass().getName()).info("sensor on canged: " + sensorOn);
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
	
	public void setSensorOn(boolean sensorOn) {
		view.setSensorOn(sensorOn);
	}
	
	public static void main(String... args) {
		new MainController();
	}

}
