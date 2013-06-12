package ee.ttu.kinect.view;

import java.awt.event.ActionListener;

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

	private JButton stopRecordButton;

	private JButton playButton;
	
	private JButton pausePlayButton;

	private JButton stopPlayButton;

	private JCheckBox sensorModeCheckbox;
	
	private JCheckBox movementAnalysisCheckbox;

	public ButtonPanel(String title) {
		Border border = BorderFactory.createEtchedBorder();
		setBorder(BorderFactory.createTitledBorder(border, title));

		selectedFileLabel = new JLabel();
		recordButton = new JButton("Record");
		stopRecordButton = new JButton("Stop Record");
		playButton = new JButton("Play");
		pausePlayButton = new JButton("Pause");
		stopPlayButton = new JButton("Stop Play");
		sensorModeCheckbox = new JCheckBox("Sensor mode");
		movementAnalysisCheckbox = new JCheckBox("Movement analysis");

		add(selectedFileLabel);
		add(sensorModeCheckbox);
		add(recordButton);
		add(stopRecordButton);
		add(playButton);
		add(pausePlayButton);
		add(stopPlayButton);
		add(movementAnalysisCheckbox);
	}

	public void setSensorEnabled(boolean enabled) {
		sensorModeCheckbox.setSelected(enabled);
	}

	public void setRecordingEnabled(boolean enabled) {
		recordButton.setEnabled(enabled);
		stopRecordButton.setEnabled(enabled);
	}
	
	public void setPlayingEnabled(boolean enabled) {
		playButton.setEnabled(enabled);
		pausePlayButton.setEnabled(enabled);
		stopPlayButton.setEnabled(enabled);
	}
	
	public void setMovementAnalysisEnabled(boolean enabled) {
		movementAnalysisCheckbox.setSelected(enabled);
	}
	
	public void updateSelectedFileLabel(String name) {
		selectedFileLabel.setText(name);
	}

	public void addListenerForSensorOn(ActionListener lsitener) {
		sensorModeCheckbox.addActionListener(lsitener);
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

	public void addListenerForPause(ActionListener listener) {
		pausePlayButton.addActionListener(listener);
	}
	
	public void addListenerForStopPlay(ActionListener listener) {
		stopPlayButton.addActionListener(listener);
	}
	
	public void addListenerForLifeMovementAnalysis(ActionListener listener) {
		movementAnalysisCheckbox.addActionListener(listener);
	}

}
