package ee.ttu.kinect.view;

import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ButtonPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel selectedFileLabel;
	
	private JButton recordButton;
	
	private JButton playButton;
	
	private JButton stopRecordButton;

	private JButton stopPlayButton;
	
	private JCheckBox sensorOnCheckbox;
	
	public ButtonPanel(String title) {
		Border border = BorderFactory.createEtchedBorder();
		setBorder(BorderFactory.createTitledBorder(border, title));
		
		selectedFileLabel = new JLabel();
		recordButton = new JButton("Record");
		stopRecordButton = new JButton("Stop Record");
		playButton = new JButton("Play");
		stopPlayButton = new JButton("Stop Play");
		sensorOnCheckbox = new JCheckBox("Sensor ON");
		
		add(selectedFileLabel);
		add(sensorOnCheckbox);
		add(recordButton);
		add(stopRecordButton);
		add(playButton);
		add(stopPlayButton);
		
		setSize(1200, 100);
	}
	
	public void setSensorOn(boolean sensorRunning) {
		Logger logger = Logger.getLogger(getClass().getName());
		logger.info("sensor on: " + sensorRunning);
		sensorOnCheckbox.setSelected(sensorRunning);		
	}
	
	public void updateSelectedFileLabel(String name) {
		selectedFileLabel.setText(name);
	}
	
	public void addListenerForSensorOn(ActionListener lsitener) {
		sensorOnCheckbox.addActionListener(lsitener);
	}

	public void addListenerForStartRecord(ActionListener listener) {
		recordButton.addActionListener(listener);
	}

	public void addListenerForStopRecord(ActionListener listener) {
		stopRecordButton.addActionListener(listener);
	}

	public void addListenerForStartPlay(ActionListener listener) {
		playButton.addActionListener(listener);
	}

	public void addListenerForStopPlay(ActionListener listener) {
		stopPlayButton.addActionListener(listener);
	}
	
}
