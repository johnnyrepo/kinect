package ee.ttu.kinect.model;

import java.io.File;
import java.io.IOException;

import kinectsensorproxy.KinectSensorProxy;
import net.sf.jni4net.Bridge;
import ee.ttu.kinect.SkeletonParserKinect;
import ee.ttu.kinect.controller.MainController;

public class SensorRunner extends Runner {

	private boolean savingToFile;

	private KinectSensorProxy kinectSensorProxy;

	private SkeletonParserKinect skeletonParserKinect;

	public SensorRunner(MainController controller) {
		super(controller);
		this.skeletonParserKinect = new SkeletonParserKinect();
	}

	@Override
	public synchronized void start() {
		body = new Body();
		
		initKinectSensor();

		logger.info("Starting skeleton tracking");

		try {
			String msg = this.kinectSensorProxy.initialize(); // Ignoring the return value. "Setup Done!"
			logger.info(msg);
			
			msg = this.kinectSensorProxy.start();
			logger.info(msg);
			
			super.start();
			controller.setSensorOn(isRunning());
		} catch (Exception e) {
			controller.showMessagePopup(e.getMessage());
		}
	}

	@Override
	public void stop() {
		try {
			String msg = this.kinectSensorProxy.stop();
			logger.info("Stopping kinect tracking: " + msg);
			super.stop();
			controller.setSensorOn(isRunning());
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void initKinectSensor() {
		try {
			// Bundle bundle =
			// org.eclipse.core.runtime.Platform.getBundle("org.jnect.core");
			// URL locationUrl = FileLocator.find(bundle, new Path("/"), null);
			// URL fileUrl = FileLocator.toFileURL(locationUrl);
			// String parentPath = fileUrl.getFile();

			Bridge.init(new File(System.getProperty("user.dir"), "lib"));
			File path = new File(System.getProperty("user.dir"),
					"lib/KinectSensorProxy.j4n.dll").getCanonicalFile();
			Bridge.LoadAndRegisterAssemblyFrom(path);

			this.kinectSensorProxy = new KinectSensorProxy();
		} catch (IOException ioe) {
			logger.info(ioe.getLocalizedMessage());
		}
	}

	@Override
	protected String getSkeletonData() {
		return kinectSensorProxy.getFrames(); // This seems to be non-blocking
	}

	@Override
	protected void parseSkeleton(String input) {
		// Parse skeleton to Body model
		// The parts are separated with a '*'.
		String[] inputParts = input.split("\\*");
		if (input.contains("skeletonData")) {
			// Found skeleton
			for (int i = 0; i < inputParts.length; i++) {
				if (inputParts[i].contains("skeletonData")) {
					skeletonParserKinect.parseSkeleton(inputParts[i], body);
					break; // TODO Do we want all skeleton frames
					// collected
					// in 50ms or only one frame?
				}
			}
		} else { // Lost skeleton
			skeletonParserKinect.parseSkeleton(inputParts[0], body);
		}
	}

	@Override
	protected void saveSkeleton() {
		if (savingToFile) {
			try {
				fileWorker.addToSave(body.getJointString(seatedMode) + getMarkers());
			} catch (IOException e) {
				logger.info(e.getLocalizedMessage());
			}
		}
	}

	private String getMarkers() {
		StringBuffer markers = new StringBuffer();
		boolean[] markersBool = controller.getMarkersState();
		for (boolean mb : markersBool) {
			markers = markers.append((mb ? 1 : 0)).append("\t");
		}

		return markers.toString();
	}
	
	public void startRecord() {
		savingToFile = true;

		body = new Body();
	}

	public void stopRecord() {
		try {
			savingToFile = false;

			fileWorker.dumpFile(seatedMode);
		} catch (IOException e) {
			logger.info(e.getLocalizedMessage());
		}
	}

	@Override
	public void setSeatedMode() {
		super.setSeatedMode();
		String msg = kinectSensorProxy.setSeatedTrackingMode();
		logger.info(msg);
	}

	@Override
	public void setDefaultMode() {
		super.setDefaultMode();
		String msg = kinectSensorProxy.setDefaultTrackingMode();
		logger.info(msg);
	}
	
}
