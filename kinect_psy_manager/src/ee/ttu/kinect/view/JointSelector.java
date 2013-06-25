package ee.ttu.kinect.view;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import ee.ttu.kinect.model.JointType;

public class JointSelector {

	private JointSelectorFrame selectorFrame = new JointSelectorFrame();
	
	public void open() {
		selectorFrame.setVisible(true);
	}
	
	public List<JointType> getSelectedJoints() {
		return selectorFrame.getSelectedJoints();
	}
	
	private class JointSelectorFrame extends JFrame {
	
		private static final long serialVersionUID = 1L;
		
		private JList<JointType> jointList;
		
		JointSelectorFrame() {
			jointList = new JList<JointType>(JointType.values());
			JScrollPane scrollPane = new JScrollPane(jointList);
			add(scrollPane);
			
			setTitle("Select joint for analysis");
			setSize(150, 400);
			setVisible(false);
		}

		List<JointType> getSelectedJoints() {
			return jointList.getSelectedValuesList();
		}
		
	}
}
