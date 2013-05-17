package ee.ttu.kinect.view.chart;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class ChartSelector {
	
	private ChartSelectorFrame frame;
	
	public void open(List<Body> data, boolean valuesCharts) {
		this.frame = new ChartSelectorFrame(data, valuesCharts);
		//clearCharts();
	}
	
	private class ChartSelectorFrame extends JFrame {
		
		private static final long serialVersionUID = 1L;
	
		private List<Body> data;
		
		private boolean valuesCharts;
	
		private JList<JointType> jointsList;
	
		private SegmentationChartConfPanel segmentationConfPanel;
		
		private JCheckBox singleModeCheckbox;
	
		private JButton drawButton;
	
		private JPanel controlPanel;
	
		private JPanel chartsPanel;
	
		public ChartSelectorFrame(List<Body> data, boolean valuesCharts) {
			this.data = data;
			this.valuesCharts = valuesCharts;
			
			setSize(1600, 800);
			setLayout(new BorderLayout());
			
			if (valuesCharts) {
				setTitle("Analysis with velocities/accelerations VALUES");
			} else {
				setTitle("Analysis with velocities/accelerations SEGMENTATION");
			}
	
			jointsList = new JList<JointType>(JointType.values());
			JScrollPane scrollPane = new JScrollPane(jointsList);
	
			jointsList.addListSelectionListener(new JointsListChangeListener());
			
			segmentationConfPanel = new SegmentationChartConfPanel();
	
			singleModeCheckbox = new JCheckBox("Single chart");
	
			drawButton = new JButton("Draw model");
			drawButton.addActionListener(new DrawButtonChangeListener());
	
			controlPanel = new JPanel();
			controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
			controlPanel.add(scrollPane);
			if (!valuesCharts) {
				controlPanel.add(segmentationConfPanel);
			}
			controlPanel.add(singleModeCheckbox);
			controlPanel.add(drawButton);
	
			chartsPanel = new JPanel();
			chartsPanel.setLayout(new BoxLayout(chartsPanel, BoxLayout.Y_AXIS));
	
			add(controlPanel, BorderLayout.LINE_START);
			add(chartsPanel, BorderLayout.CENTER);
			
			setVisible(true);
		}
		
		private void clearCharts() {
			for (Component comp : chartsPanel.getComponents()) {
				if (comp instanceof ChartComponent) {
					((ChartComponent) comp).clearChart();
					chartsPanel.remove(comp);
				}
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
				ChartComponent cc = null;
				if (valuesCharts) {
					cc = new ModelChartComponent();
				} else {
					cc = new SegmentationChartComponent(segmentationConfPanel.getClustersAmount(), 
							segmentationConfPanel.getStepsAmount(), segmentationConfPanel.getPointsAmount());
				}
				if (singleModeCheckbox.isSelected()) {
					cc.drawChart(data, selectedJoints, false);
				} else {
					for (int i = 0; i < selectedJoints.size(); i++) {
						cc.drawChart(data, selectedJoints.get(i), false);
					}
				}
				chartsPanel.add(cc);
				chartsPanel.validate();
				chartsPanel.repaint();
			}
		}
	
	}

}
