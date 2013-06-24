package ee.ttu.kinect.view.chart;

import java.awt.BorderLayout;
import java.awt.Component;
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
import ee.ttu.kinect.view.ChartType;

public class ChartSelector {
	
	public void open(List<Body> data, ChartType type) {
		new ChartSelectorFrame(data, type);
		//clearCharts();
	}
	
	private class ChartSelectorFrame extends JFrame {
		
		private static final long serialVersionUID = 1L;
	
		private List<Body> data;
		
		private ChartType type;
	
		private JList<JointType> jointsList;
	
		private SegmentationChartConfPanel segmentationConfPanel;
		
		private JCheckBox singleModeCheckbox;
	
		private JButton drawButton;
	
		private JPanel controlPanel;
	
		private JPanel chartsPanel;
	
		public ChartSelectorFrame(List<Body> data, ChartType type) {
			this.data = data;
			this.type = type;
			
			setSize(1600, 800);
			setLayout(new BorderLayout());
			
			switch (type) {
				case VALUES:
					setTitle("Analysis with velocities/accelerations VALUES");
					break;
				case SEGMENTATION:
					setTitle("Analysis with velocities/accelerations SEGMENTATION");
					break;
				case MOTION:
					setTitle("Analysis with motion trajectories");
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
			if (type == ChartType.SEGMENTATION) {
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
				if (comp instanceof Chart) {
					((Chart) comp).clearChart();
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
				if (singleModeCheckbox.isSelected()) {
					Chart cc = null;
					if (type == ChartType.VALUES) {
						cc = new ModelChart();
					} else if (type == ChartType.SEGMENTATION) {
						cc = new SegmentationChart(segmentationConfPanel.getClustersAmount(), 
								segmentationConfPanel.getStepsAmount(), segmentationConfPanel.getPointsAmount());
					} else if (type == ChartType.MOTION) {
						cc =  new MotionChart();
					}
					cc.drawChart(data, selectedJoints, false);
					chartsPanel.add(cc);
				} else {
					for (JointType selectedType : selectedJoints) {
						Chart cc = null;
						if (type == ChartType.VALUES) {
							cc = new ModelChart();
						} else if (type == ChartType.SEGMENTATION) {
							cc = new SegmentationChart(segmentationConfPanel.getClustersAmount(), 
									segmentationConfPanel.getStepsAmount(), segmentationConfPanel.getPointsAmount());
						} else if (type == ChartType.MOTION) {
							cc =  new MotionChart();
						}
						List<JointType> arg = new ArrayList<JointType>();
						arg.add(selectedType);
						cc.drawChart(data, arg, false);
						chartsPanel.add(cc);
					}
				}
				
				chartsPanel.validate();
				chartsPanel.repaint();
			}
		}
	
	}

}
