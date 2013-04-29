package ee.ttu.kinect.view;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class SeatedModePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JCheckBox seatedModeCheckbox;
	
	public SeatedModePanel() {
		Border border = BorderFactory.createEtchedBorder();
		setBorder(BorderFactory.createTitledBorder(border, "Tracking mode"));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		seatedModeCheckbox = new JCheckBox("Seated mode");
		add(seatedModeCheckbox);
	}

	public void addLsitenerForCheckbox(ActionListener listener) {
		seatedModeCheckbox.addActionListener(listener);
	}

}
