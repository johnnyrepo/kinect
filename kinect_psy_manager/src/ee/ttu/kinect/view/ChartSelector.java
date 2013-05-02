package ee.ttu.kinect.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.JointType;

public class ChartSelector extends JFrame {

	private static final long serialVersionUID = 1L;

	private List<Body> data;

	private JList<JointType> jointsList;

	private JCheckBox singleModeCheckbox;

	private JButton drawButton;

	private JPanel controlPanel;

	private JPanel chartsPanel;
	
	private List<ChartComponent> charts;

	public ChartSelector() {
		setTitle("Analysis with charts");
		setSize(1600, 800);
		setLayout(new BorderLayout());

		jointsList = new JList<JointType>(JointType.values());
		JScrollPane scrollPane = new JScrollPane(jointsList);

		jointsList.addListSelectionListener(new JointsListChangeListener());

		singleModeCheckbox = new JCheckBox("Single chart");

		drawButton = new JButton("Draw model");
		drawButton.addActionListener(new DrawButtonChangeListener());

		controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
		controlPanel.add(scrollPane);
		controlPanel.add(singleModeCheckbox);
		controlPanel.add(drawButton);
		
		charts = new ArrayList<ChartComponent>();
		for (int i = 0; i < 3; i++) {
			charts.add(new ChartComponent());
		}

		chartsPanel = new JPanel();
		chartsPanel.setLayout(new BoxLayout(chartsPanel, BoxLayout.Y_AXIS));

		add(controlPanel, BorderLayout.LINE_START);
		add(chartsPanel, BorderLayout.CENTER);
	}

	public void open(List<Body> data) {
		this.data = data;
		clearCharts();
		setVisible(true);
	}

	private void clearCharts() {
		for (ChartComponent cc : charts) {
			cc.clearChart();
			chartsPanel.remove(cc);
		}
		chartsPanel.validate();
		chartsPanel.repaint();
	}
	
	private class JointsListChangeListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			int[] indexes = jointsList.getSelectedIndices();
			if (indexes.length > 3) {
				jointsList.removeSelectionInterval(e.getFirstIndex(),
						e.getLastIndex());
			}
		}
	}

	private class DrawButtonChangeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			clearCharts();
			List<JointType> selectedJoints = jointsList.getSelectedValuesList();
			if (singleModeCheckbox.isSelected()) {
				ChartComponent cc = charts.get(0);
				cc.drawChart(data, selectedJoints, false);
				chartsPanel.add(cc);
			} else {
				for (int i = 0; i < selectedJoints.size(); i++) {
					ChartComponent cc = charts.get(i);
					cc.drawChart(data, selectedJoints.get(i), false);
					chartsPanel.add(cc);
				}
			}
			chartsPanel.validate();
			chartsPanel.repaint();
		}
	}

}
