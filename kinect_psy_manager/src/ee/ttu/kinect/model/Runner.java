package ee.ttu.kinect.model;

import java.util.List;
import java.util.logging.Logger;

import javax.swing.SwingWorker;

import ee.ttu.kinect.controller.MainController;
import ee.ttu.kinect.util.FileUtil;

public abstract class Runner {

	protected final Logger logger = Logger.getLogger(this.getClass().getName());

	protected MainController controller;

	protected Body body;

	protected FileUtil fileUtil;
	
	protected boolean seatedMode = false;

	private Worker worker;

	private boolean running = false;
	
	private boolean paused = false;

	
	public Runner(MainController controller) {
		this.controller = controller;
		this.body = new Body();
		this.fileUtil = new FileUtil();
	}

	public synchronized void start() {
		this.logger.info("Starting " + this.getClass().getName());
		this.running = true;
		this.unpause();
		this.worker = new Worker();
		this.worker.execute();
	}
	
	public synchronized void stop() {
		this.logger.info("Stopping " + this.getClass().getName());
		this.running = false;
		this.unpause();
	}

	public synchronized void pause() {
		this.logger.info("Pausing " + this.getClass().getName());
		this.paused = true;
	}
	
	public synchronized void unpause() {
		this.logger.info("Unpausing " + this.getClass().getName());
		this.paused = false;
	}
	
	public boolean isRunning() {
		return this.running;
	}
	
	public boolean isPaused() {
		return this.paused;
	}
	
	public void setSeatedMode() {
		this.seatedMode = true;
	}

	public void setDefaultMode() {
		this.seatedMode = false;
	}

	public boolean isSeatedMode() {
		return this.seatedMode;
	}

	protected abstract String getSkeletonData();

	protected abstract void parseSkeleton(String input);

	protected abstract void saveSkeleton();

	private class Worker extends SwingWorker<Void, Body> {

		@Override
		public synchronized Void doInBackground() {
			// logger.info("Running: " + getClass().getName() + " " +
			// isCancelled());
			while (Runner.this.running) {
				if (Runner.this.paused) {
					continue;
				}
				// get data
				String input = null;
				input = Runner.this.getSkeletonData();
				if (input != null) {
					Runner.this.parseSkeleton(input);
					// render body
					if (Runner.this.body != null && Runner.this.body.isBodyReady()
							&& Runner.this.body.isBodyChanged()) {
						try {
							Body clone = Runner.this.body.clone();
							this.publish(clone);
						} catch (CloneNotSupportedException e) {
							Runner.this.logger.info(e.getLocalizedMessage());
						}
						// Save skeleton, if needed
						Runner.this.saveSkeleton();
					}
				}

				// Sleep for X ms
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					Runner.this.logger.info(e.getLocalizedMessage());
				}
			}
			return null;
		}

		@Override
		protected void process(List<Body> chunks) {
			for (Body chunk : chunks) {
				// Redraw the skeleton
				Runner.this.controller.redrawSkeleton(chunk);
				// Redraw chart
				Runner.this.controller.redrawChart(chunk);
				// Analyze movement
				Runner.this.controller.analyzeMovement(chunk);
			}
		}

		@Override
		protected void done() {
			Runner.this.logger.info(this.getClass().getName() + " sucessfully stopped");
		}

	}

}
