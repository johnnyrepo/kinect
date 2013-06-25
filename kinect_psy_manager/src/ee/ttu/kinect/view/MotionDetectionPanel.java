package ee.ttu.kinect.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.Border;

import ee.ttu.kinect.model.JointType;

public class MotionDetectionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JCheckBox motionDetectionCheckbox;

	private JComboBox<Integer> delayCombo;
		
	private JButton jointSelectorButton;
	
	private JointSelector jointSelector;

	public MotionDetectionPanel() {
		Border border = BorderFactory.createEtchedBorder();
		setBorder(BorderFactory.createTitledBorder(border,
				"Motion detection mode"));

		jointSelector = new JointSelector();
		
		motionDetectionCheckbox = new JCheckBox();
		delayCombo = new JComboBox<Integer>(new Integer[] { 0, 1, 2, 3 });
		jointSelectorButton = new JButton("Joints");
		jointSelectorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				jointSelector.open();
			}
		});

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(motionDetectionCheckbox);
		add(delayCombo);
		add(jointSelectorButton);
	}

	public void setMotionDetectionEnabled(boolean enabled) {
		motionDetectionCheckbox.setSelected(enabled);
	}

	public void addListenerForCheckbox(ActionListener listener) {
		motionDetectionCheckbox.addActionListener(listener);
	}

	public long getDelay() {
		return delayCombo.getItemAt(delayCombo.getSelectedIndex()) * 1000;
	}
	
	public List<JointType> getSelectedJoints() {
		return jointSelector.getSelectedJoints();
	}

}
