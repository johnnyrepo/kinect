package ee.ttu.kinect.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.MainModel;
import ee.ttu.kinect.view.MainView;


public class MainController {

	private MainView view;

	private MainModel model;

	public MainController() {
		model = new MainModel(this);

		view = new MainView();

		view.addListenerForStartTracking(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.doStart();
			}
		});
		view.addListenerForStopTracking(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.doStop();
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

	public void showMessagePopup(String message) {
		view.showMessagePopup(message);
	}
	
	public static void main(String... args) {
		new MainController();
	}


}
