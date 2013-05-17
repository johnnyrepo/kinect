package ee.ttu.kinect.view.chart;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SegmentationChartConfPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel clustersLabel;
	
	private JLabel stepLabel;
	
	private JLabel pointsLabel;
	
	private JComboBox<Integer> clustersCombo;
	
	private JComboBox<Integer> stepCombo;
	
	private JComboBox<Integer> pointsCombo;
	
	public SegmentationChartConfPanel() {
		setSize(200, 200);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		clustersLabel = new JLabel("Clusters amount");
		stepLabel = new JLabel("Step between points");
		pointsLabel = new JLabel("Points amount");
		clustersCombo = new JComboBox<Integer>(new Integer[] {8, 7, 6, 5, 4, 3, 2});
		clustersCombo.setSize(100, 100);
		stepCombo = new JComboBox<Integer>(new Integer[] {7, 6, 5, 4, 3, 2, 1});
		pointsCombo = new JComboBox<Integer>(new Integer[] {7, 6, 5, 4, 3, 2, 1});
		
		clustersCombo.setMaximumSize(new Dimension(100, 100));
		stepCombo.setMaximumSize(new Dimension(100, 100));
		pointsCombo.setMaximumSize(new Dimension(100, 100));
		
		clustersCombo.setSelectedIndex(0);
		stepCombo.setSelectedIndex(0);
		pointsCombo.setSelectedIndex(0);
		
		add(clustersLabel);
		add(clustersCombo);
		add(stepLabel);
		add(stepCombo);
		add(pointsLabel);
		add(pointsCombo);
	}
	
	public int getClustersAmount() {
		return (Integer) clustersCombo.getSelectedItem();
	}
	
	public int getStepsAmount() {
		return (Integer) stepCombo.getSelectedItem();
	}
	
	public int getPointsAmount() {
		return (Integer) pointsCombo.getSelectedItem();
	}

}
