package ee.ttu.kinect.view;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ZoomPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JButton zoomInBtn;
	
	private JButton zoomOutBtn;
	
	public ZoomPanel(ActionListener zoomInListener, ActionListener zoomOutListener) {
		zoomInBtn = new JButton("+");
		zoomOutBtn = new JButton("-");
		zoomInBtn.addActionListener(zoomInListener);
		zoomOutBtn.addActionListener(zoomOutListener);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(zoomInBtn);
		add(zoomOutBtn);
	}

}
