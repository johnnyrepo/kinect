package ee.ttu.kinect.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
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
					int parsed = Integer.parseInt(markersAmountField
							.getText());
					if (parsed < 0) {
						markersAmountField.setText("");
						return;
					}
					markersAmount = parsed;
					initMarkersCombo(markersAmount);
				} catch (NumberFormatException nfe) {
					markersAmountField.setText("");
				}
			}
		});

		add(markersAmountField);
		add(markersAmountButton);
		add(markersCombo);
	}

	public void setAmountOfMarkersChangeEnabled(boolean enabled) {
		markersAmountButton.setEnabled(enabled);
		markersAmountField.setEnabled(enabled);
	}

	public void addListenerForStateChange(ActionListener listener) {
		markersCombo.addActionListener(listener);
		addKeyBidings();
	}
	
	public boolean[] getMarkersState() {
		boolean[] state = new boolean[markersAmount];
		try {
			int selectedMarker = Integer.parseInt((String) markersCombo
					.getSelectedItem());
			state[selectedMarker - 1] = true;
		} catch (NumberFormatException nfe) {
		}

		return state;
	}

	private void addKeyBidings() {
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0, false), "PAGE_DOWN");
		getActionMap().put("PAGE_DOWN", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	markersCombo.setSelectedIndex(getNextMarkerIndex());
	        }
	    });
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0, false), "PAGE_UP");
		getActionMap().put("PAGE_UP", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	markersCombo.setSelectedIndex(getPreviousMarkerIndex());
	        }
	    });
	}
	
	private void initMarkersCombo(int markersAmount) {
		markersCombo.removeAllItems();

		markersCombo.addItem("none");
		for (int i = 0; i < markersAmount; i++) {
			markersCombo.addItem("" + (i + 1));
		}
	}
	
	private int getNextMarkerIndex() {
		int newIndex = markersCombo.getSelectedIndex() + 1;
		if (newIndex == markersCombo.getItemCount()) {
			newIndex = 0;
		}
		
		return newIndex;
	}
	
	private int getPreviousMarkerIndex() {
		int newIndex = markersCombo.getSelectedIndex() - 1;
		if (newIndex < 0) {
			newIndex = markersCombo.getItemCount() - 1;
		}
		
		return newIndex;
	}

}
