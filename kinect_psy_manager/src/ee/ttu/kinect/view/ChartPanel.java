package ee.ttu.kinect.view;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class ChartPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JCheckBox xCheckbox;

	private JCheckBox yCheckbox;

	private JCheckBox zCheckbox;

	public ChartPanel() {
		Border border = BorderFactory.createBevelBorder(BevelBorder.RAISED);
		setBorder(BorderFactory.createTitledBorder(border, "Chart"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		xCheckbox = new JCheckBox("X");
		yCheckbox = new JCheckBox("Y");
		zCheckbox = new JCheckBox("Z");
		add(xCheckbox);
		add(yCheckbox);
		add(zCheckbox);
	}

}
