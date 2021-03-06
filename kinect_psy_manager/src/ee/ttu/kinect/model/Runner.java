package ee.ttu.kinect.model;

import java.util.List;
import java.util.logging.Logger;

import javax.swing.SwingWorker;

import ee.ttu.kinect.controller.MainController;
import ee.ttu.kinect.util.FileUtil;

public abstract class Runner {

	protected final Logger logger = Logger.getLogger(this.getClass().getName());

	protected MainController controller;

	protected Frame frame;

	protected FileUtil fileUtil;
	
	protected boolean seatedMode = false;

	private Worker worker;

	private boolean running = false;
	
	private boolean paused = false;

	
	public Runner(MainController controller) {
		this.controller = controller;
		frame = new Frame();
		fileUtil = new FileUtil();
	}

	public synchronized void start() {
		logger.info("Starting " + this.getClass().getName());
		running = true;
		unpause();
		worker = new Worker();
		worker.execute();
	}
	
	public synchronized void stop() {
		logger.info("Stopping " + this.getClass().getName());
		running = false;
		unpause();
	}

	public synchronized void pause() {
		logger.info("Pausing " + this.getClass().getName());
		paused = true;
	}
	
	public synchronized void unpause() {
		logger.info("Unpausing " + this.getClass().getName());
		paused = false;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public void setSeatedMode() {
		seatedMode = true;
	}

	public void setDefaultMode() {
		seatedMode = false;
	}

	public boolean isSeatedMode() {
		return seatedMode;
	}

	protected abstract String getSkeletonData();

	protected abstract void parseSkeleton(String input);

	protected abstract void saveSkeleton();

	private class Worker extends SwingWorker<Void, Frame> {

		@Override
		public synchronized Void doInBackground() {
			// logger.info("Running: " + getClass().getName() + " " +
			while (running) {
				if (paused) {
					continue;
				}
				// get data
				String input = null;
				input = getSkeletonData();
				if (input != null) {
					parseSkeleton(input);
					// render frame
					if (frame != null && frame.isFrameReady()
							&& frame.isFrameChanged()) {
						try {
							Frame clone = frame.clone();
							publish(clone);
						} catch (CloneNotSupportedException e) {
							logger.info(e.getLocalizedMessage());
						}
						// Save skeleton, if needed
						saveSkeleton();
					}
				}

				// archive frame
				frame.archive();
				
				// Sleep for X ms
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					logger.info(e.getLocalizedMessage());
				}
			}
			return null;
		}

		@Override
		protected void process(List<Frame> chunks) {
			for (Frame chunk : chunks) {
				// Redraw the skeleton
				controller.redrawSkeleton(chunk);
				// Redraw chart
				controller.redrawChart(chunk);
				// analyze motion
				controller.analyzeMotion(chunk);
			}
		}

		@Override
		protected void done() {
			logger.info(this.getClass().getName() + " sucessfully stopped");
		}

	}

}
