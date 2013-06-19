package ee.ttu.kinect.view;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.Border;

import ee.ttu.kinect.model.JointType;

public class MotionDetectionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JCheckBox movementDetectionCheckbox;

	//private JLabel delayLabel;

	private JComboBox<Integer> delayCombo;

	private JComboBox<JointType> jointCombo;

	public MotionDetectionPanel() {
		Border border = BorderFactory.createEtchedBorder();
		setBorder(BorderFactory.createTitledBorder(border,
				"Movement detection mode"));

		movementDetectionCheckbox = new JCheckBox();
		// delayLabel = new JLabel("Delay");
		delayCombo = new JComboBox<Integer>(new Integer[] { 0, 1, 2, 3 });
		jointCombo = new JComboBox<JointType>(JointType.values());

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(movementDetectionCheckbox);
		// add(delayLabel);
		add(delayCombo);
		add(jointCombo);

	}

	public void setMovementDetectionEnabled(boolean enabled) {
		movementDetectionCheckbox.setSelected(enabled);
	}

	public void addListenerForCheckbox(ActionListener listener) {
		movementDetectionCheckbox.addActionListener(listener);
	}

	public long getDelay() {
		return delayCombo.getItemAt(delayCombo.getSelectedIndex()) * 1000;
	}
	
	public JointType getSelectedJoint() {
		return jointCombo.getItemAt(jointCombo.getSelectedIndex());
	}

}
