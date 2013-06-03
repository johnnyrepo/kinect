package ee.ttu.kinect.view;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class CorrectionPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JCheckBox standUpCorrectionBox;
    
    private final JCheckBox sitDownCorrectionBox;
    
    public CorrectionPanel(ActionListener standUpListener, ActionListener sitDownListener) {
        this.standUpCorrectionBox = new JCheckBox("Stand up correction");
        this.sitDownCorrectionBox = new JCheckBox("Sit down correction");
        this.standUpCorrectionBox.addActionListener(standUpListener);
        this.sitDownCorrectionBox.addActionListener(sitDownListener);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(this.standUpCorrectionBox);
        this.add(this.sitDownCorrectionBox);
    }

}
