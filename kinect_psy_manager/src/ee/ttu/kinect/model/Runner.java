package ee.ttu.kinect.model;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingWorker;

import ee.ttu.kinect.controller.MainController;
import ee.ttu.kinect.util.FileWorker;

public abstract class Runner {

	protected final Logger logger = Logger.getLogger(this.getClass().getName());

	protected MainController controller;

	protected Body body;

	protected FileWorker fileWorker;
	
	private Worker worker;

	private boolean running = false;

	public Runner(MainController controller) {
		this.controller = controller;
		this.body = new Body();
		this.fileWorker = new FileWorker();
	}
	
	public synchronized void start() {
		logger.info("Starting " + getClass().getName());
		running = true;
		worker = new Worker();
		worker.execute();
	}

	public void stop() {
		logger.info("Stopping " + getClass().getName());
		running = false;
	}

	public boolean isRunning() {
		return running;
	}
	
	protected abstract String getSkeletonData();

	protected abstract void parseSkeleton(String input);

	protected abstract void saveSkeleton();

	private class Worker extends SwingWorker<Void, Body> {

		@Override
		public Void doInBackground() {
			//logger.info("Running: " + getClass().getName() + " " + isCancelled());
			while (running) {
				// get data
				String input = null;
				input = getSkeletonData();
				if (input != null) {
					parseSkeleton(input);
				}

				if (input != null) {
					if (body != null && body.isBodyReady() && body.isBodyChanged()) {
						try {
							Body clone = body.clone();
							publish(clone);
						} catch (CloneNotSupportedException e) {
							logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
						}
					}
					// Save skeleton, if needed
					saveSkeleton();
				}

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
		protected void process(List<Body> chunks) {
			//logger.info("processing: " + getClass().getName() + " " + chunks);
			for (Body chunk : chunks) {
				// Redraw the skeleton
				controller.redrawSkeleton(chunk);
				// Redraw chart
				controller.redrawChart(chunk);
			}
		}
		
		@Override
		protected void done() {
			logger.info(getClass().getName() + " sucessfully stopped");
		}
		
	}
	
}
