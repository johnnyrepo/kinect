package ee.ttu.kinect.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
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
	
	private JButton drawButton;
	
	private JPanel controlPanel;
	
	private JPanel chartsPanel;
	
	private ChartComponent cc1;
	
	private ChartComponent cc2;
	
	private ChartComponent cc3;
	
	public ChartSelector() {
		setTitle("Analysis with charts");
		setSize(1600, 800);
		setLayout(new BorderLayout());
		
		jointsList = new JList<JointType>(JointType.values());
		JScrollPane scrollPane = new JScrollPane(jointsList);
		
		jointsList.addListSelectionListener(new JointsListChangeListener());
		
		drawButton = new JButton("Draw model");
		drawButton.addActionListener(new DrawButtonListener());
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
		controlPanel.add(scrollPane);
		controlPanel.add(drawButton);

		cc1 = new ChartComponent();
		cc2 = new ChartComponent();
		cc3 = new ChartComponent();
		
		chartsPanel = new JPanel();
		chartsPanel.setLayout(new BoxLayout(chartsPanel, BoxLayout.Y_AXIS));
		chartsPanel.add(cc1);
		chartsPanel.add(cc2);
		chartsPanel.add(cc3);
		
		add(controlPanel, BorderLayout.LINE_START);
		add(chartsPanel, BorderLayout.CENTER);
	}
	
	public void open(List<Body> data) {
		this.data = data;
		setVisible(true);
	}
	
	private class JointsListChangeListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			int[] indexes = jointsList.getSelectedIndices();
			if (indexes.length > 3) {
				jointsList.removeSelectionInterval(e.getFirstIndex(), e.getLastIndex());
			}
		}
	}
	
	private class DrawButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			List<JointType> selectedJoints = jointsList.getSelectedValuesList();
			for (JointType selectedJoint : selectedJoints) {
				cc1.drawChart(data, selectedJoint, false);
			}
		}
	}
	
}
