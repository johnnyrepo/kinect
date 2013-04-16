package ee.ttu.kinect.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.MainModel;
import ee.ttu.kinect.view.MainView;


public class MainController {

	private MainView view;

	private MainModel model;

	public MainController() {
		model = new MainModel(this);

		view = new MainView();

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
				model.startSaving();
			}
		});
		view.addListenerForStopRecord(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.stopSaving();
			}
		});
		view.addListenerForStartPlay(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.startReading();
			}
		});
		view.addListenerForStopPlay(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.stopReading();
			}
		});

		model.init();
		model.doStart();
	}

	public void redrawSkeleton(Body body) {
		view.redrawSkeleton(body);
	}
	
	public void redrawChart(Body body) {
		view.redrawChart(body);
	}

	public void clearChart() {
		view.clearChart();
	}
	
	public void showMessagePopup(String message) {
		view.showMessagePopup(message);
	}
	
	public boolean[] getMarkersState() {
		return view.getMarkersState();
	}
	
	public static void main(String... args) {
		new MainController();
	}

}
