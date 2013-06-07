package ee.ttu.kinect.view;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class CorrectionPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JCheckBox standUpCorrectionBox;
    
    private final JCheckBox sitDownCorrectionBox;
    
    public CorrectionPanel() {
        this.standUpCorrectionBox = new JCheckBox("Stand up correction");
        this.sitDownCorrectionBox = new JCheckBox("Sit down correction");
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(this.standUpCorrectionBox);
        this.add(this.sitDownCorrectionBox);
    }
    
    public void addStandingCorrectionListener(ActionListener listener) {
    	this.standUpCorrectionBox.addActionListener(listener);
    }

	public void addSittingCorrectionListener(ActionListener listener) {
		this.sitDownCorrectionBox.addActionListener(listener);
	}

}
