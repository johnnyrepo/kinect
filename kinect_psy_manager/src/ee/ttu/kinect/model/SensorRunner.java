package ee.ttu.kinect.model;

import java.io.File;
import java.io.IOException;

import kinectsensorproxy.KinectSensorProxy;
import net.sf.jni4net.Bridge;
import ee.ttu.kinect.controller.MainController;
import ee.ttu.kinect.model.parser.SkeletonParserKinect;

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
		frame = new Frame();
		
		initKinectSensor();

		logger.info("Starting skeleton tracking");

		try {
			String msg = this.kinectSensorProxy.initialize();
			if (msg.contains("FAIL")) {
				throw new Exception(msg.replaceAll("FAIL,", ""));
			}			
			msg = this.kinectSensorProxy.start();
			if (msg.contains("FAIL")) {
				throw new Exception(msg.replaceAll("FAIL,", ""));
			}	
			
			super.start();
		} catch (Exception e) {
			controller.showMessagePopup(e.getMessage());
		}
		controller.setSensorEnabled(isRunning());
	}

	@Override
	public void stop() {
		try {
			String msg = this.kinectSensorProxy.stop();
			logger.info("Stopping kinect tracking: " + msg);
			super.stop();
			controller.setSensorEnabled(isRunning());
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
					skeletonParserKinect.parseSkeleton(inputParts[i], frame);
					break; // TODO Do we want all skeleton frames
					// collected
					// in 50ms or only one frame?
				}
			}
		} else { // Lost skeleton
			skeletonParserKinect.parseSkeleton(inputParts[0], frame);
		}
	}

	@Override
	protected void saveSkeleton() {
		if (savingToFile) {
			try {
				fileUtil.addToSave(FrameUtil.getData(frame, seatedMode, controller.getMarkersState()));
			} catch (IOException e) {
				logger.info(e.getLocalizedMessage());
			}
		}
	}
	
	public void startRecord() {
		savingToFile = true;

		frame = new Frame();
	}

	public void stopRecord() {
		try {
			savingToFile = false;

			fileUtil.dumpFile(FrameUtil.getHeader(seatedMode, controller.getMarkersState().length));
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
