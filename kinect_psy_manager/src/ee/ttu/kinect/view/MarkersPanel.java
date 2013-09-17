package ee.ttu.kinect.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class MarkersPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField markersAmountField;
	
	private JButton markersAmountButton;
	
	private JComboBox<String> markersCombo;
	
	private int markersAmount = 0;

	public MarkersPanel() {
		Border border = BorderFactory.createEtchedBorder();
		setBorder(BorderFactory.createTitledBorder(border, "Markers"));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		markersAmountField = new JTextField(5);
		markersAmountField.setMaximumSize(new Dimension(50, 25));
		markersAmountButton = new JButton("Set amount");
		markersCombo = new JComboBox<String>();
		markersCombo.setMaximumSize(new Dimension(50, 25));
		initMarkersCombo(0);
		
		markersAmountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					markersAmount = Integer.parseInt(markersAmountField.getText());
					initMarkersCombo(markersAmount);
				} catch (NumberFormatException nfe) {}
			}
		});
		
		add(markersAmountField);
		add(markersAmountButton);
		add(markersCombo);
	}
	
	public void addListenerForStateChange(ActionListener listener) {
		markersCombo.addActionListener(listener);
	}
	
	public boolean[] getMarkersState() {
		boolean[] state = new boolean[markersAmount];
		try {
			int selectedMarker = Integer.parseInt((String) markersCombo.getSelectedItem());
			state[selectedMarker - 1] = true;
		} catch (NumberFormatException nfe) {}
		
		return state;
	}
	
	private void initMarkersCombo(int markersAmount) {
		markersCombo.removeAllItems();
		
		markersCombo.addItem("none");
		for (int i = 0; i < markersAmount; i++) {
			markersCombo.addItem("" + (i + 1));
		}
	}

}
