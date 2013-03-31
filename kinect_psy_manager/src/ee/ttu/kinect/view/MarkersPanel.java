package ee.ttu.kinect.view;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class MarkersPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JCheckBox marker1;

	private JCheckBox marker2;

	private JCheckBox marker3;

	private JCheckBox marker4;

	private JCheckBox marker5;

	public MarkersPanel() {
		Border border = BorderFactory.createBevelBorder(BevelBorder.RAISED);
		setBorder(BorderFactory.createTitledBorder(border, "Markers"));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		marker1 = new JCheckBox("marker 1");
		marker2 = new JCheckBox("marker 2");
		marker3 = new JCheckBox("marker 3");
		marker4 = new JCheckBox("marker 4");
		marker5 = new JCheckBox("marker 5");

		add(marker1);
		add(marker2);
		add(marker3);
		add(marker4);
		add(marker5);
	}

	public boolean[] getMarkersState() {
		return new boolean[] { marker1.isSelected(), marker2.isSelected(),
				marker3.isSelected(), marker4.isSelected(),
				marker5.isSelected() };
	}
}
