package ee.ttu.kinect.view;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ButtonPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JButton recordButton;
	
	private JButton playButton;
	
	private JButton stopRecordButton;

	private JButton stopPlayButton;
	
	public ButtonPanel(String title) {
		Border border = BorderFactory.createEtchedBorder();
		setBorder(BorderFactory.createTitledBorder(border, title));
		
		recordButton = new JButton("Record");
		stopRecordButton = new JButton("Stop Record");
		playButton = new JButton("Play");
		stopPlayButton = new JButton("Stop Play");
				
		add(recordButton);
		add(stopRecordButton);
		add(playButton);
		add(stopPlayButton);
		
		setSize(1200, 100);
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
