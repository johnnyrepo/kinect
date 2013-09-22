/*******************************************************************************
 * Copyright (c) 2012 jnect.org.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eugen Neufeld - initial API and implementation
 *******************************************************************************/
package ee.ttu.kinect.model.parser;

import java.io.StringReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import ee.ttu.kinect.model.Frame;
import ee.ttu.kinect.model.Joint;
import ee.ttu.kinect.model.JointType;

public class SkeletonParserKinect implements SkeletonParser {

	public static final String SKELETON_KEYWORD = "skeletonData";

	private XMLInputFactory inputFactory;
	

	//private int oldNumSkeletons = -1;
	private long frameNumber = -1;
	private long timestamp = -1;

	public void reset() {
		//this.oldNumSkeletons = -1;
		frameNumber = -1;
	}

	public SkeletonParserKinect() {
		inputFactory = XMLInputFactory.newInstance();
	}

	@Override
	public void parseSkeleton(String input, Frame frame) {
        XMLStreamReader streamReader;
		try {
			streamReader = inputFactory.createXMLStreamReader(new StringReader(input));
			streamReader.nextTag(); // advance to 'frame' tag
			while (streamReader.hasNext()) {
				if (streamReader.isStartElement()) {
					if (streamReader.getLocalName().equals("frameId")) {
						int currentFrame = Integer.parseInt(streamReader.getElementText());
						if (currentFrame > frameNumber) { // TODO I receive the same document multiple times
							frameNumber = currentFrame;
							streamReader.next();
							if (streamReader.getLocalName().equals("timestamp")) {
								timestamp = Long.parseLong(streamReader.getElementText());
								frame.setTimestamp(timestamp);
								frame.setFrameNumber(frameNumber);
								streamReader.nextTag(); // moving to 'skeletonData' tag
								if (streamReader.getLocalName().equals("skeletonData")) {
									//logger.info("Found skeleton");
									streamReader.nextTag(); // moving to 'trackingId' tag
									if (streamReader.getLocalName().equals("trackingId")) {
										//String trackingId = 
										streamReader.getElementText(); // trackingId
										while (streamReader.hasNext()) {
											streamReader.nextTag(); // moving to 'joint' tag
											if (streamReader.getLocalName().equals("joint")) {
												String positionX = null;
												String positionY = null;
												String positionZ = null;
												String jointId = null;
												
												streamReader.nextTag();
												if (streamReader.getLocalName().equals("positionX")) {
													positionX = streamReader.getElementText();
												}
												streamReader.nextTag();
												if (streamReader.getLocalName().equals("positionY")) {
													positionY = streamReader.getElementText();
												}
												streamReader.nextTag();
												if (streamReader.getLocalName().equals("positionZ")) {
													positionZ = streamReader.getElementText();
												}
												streamReader.nextTag();
												if (streamReader.getLocalName().equals("jointId")) {
													jointId = streamReader.getElementText();
												}
												
												updateBody(frame, parseJoint(jointId, positionX, positionY, positionZ));
												
												streamReader.next();
											} else {
												break;
											}
										}
									}
								}
							}
						}
					}
				}
				streamReader.next();
			}
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Joint parseJoint(String jointId, String positionX, String positionY, String positionZ) {
		Joint joint = new Joint();
		joint.setType(JointType.getValueOf(jointId));
		joint.setPositionX(parsePosition(positionX));
		joint.setPositionY(parsePosition(positionY));
		joint.setPositionZ(parsePosition(positionZ));
		
		return joint;
	}
	
	private double parsePosition(String position) {
		String pos = position.replace(',', '.');
		return Double.parseDouble(pos);
	}
	
	private void updateBody(Frame frame, Joint joint) {
		switch (joint.getType()) {
		case ANKLE_LEFT:
			frame.setAnkleLeft(joint);
			break;
		case ANKLE_RIGHT:
			frame.setAnkleRight(joint);
			break;
		case ELBOW_LEFT:
			frame.setElbowLeft(joint);
			break;
		case ELBOW_RIGHT:
			frame.setElbowRight(joint);
			break;
		case FOOT_LEFT:
			frame.setFootLeft(joint);
			break;
		case FOOT_RIGHT:
			frame.setFootRight(joint);
			break;
		case HAND_LEFT:
			frame.setHandLeft(joint);
			break;
		case HAND_RIGHT:
			frame.setHandRight(joint);
			break;
		case HEAD:
			frame.setHead(joint);
			break;
		case HIP_CENTER:
			frame.setHipCenter(joint);
			break;
		case HIP_LEFT:
			frame.setHipLeft(joint);
			break;
		case HIP_RIGHT:
			frame.setHipRight(joint);
			break;
		case KNEE_LEFT:
			frame.setKneeLeft(joint);
			break;
		case KNEE_RIGHT:
			frame.setKneeRight(joint);
			break;
		case SHOULDER_CENTER:
			frame.setShoulderCenter(joint);
			break;
		case SHOULDER_LEFT:
			frame.setShoulderLeft(joint);
			break;
		case SHOULDER_RIGHT:
			frame.setShoulderRight(joint);
			break;
		case SPINE:
			frame.setSpine(joint);
			break;
		case WRIST_LEFT:
			frame.setWristLeft(joint);
			break;
		case WRIST_RIGHT:
			frame.setWristRight(joint);
			break;
		}
	}

}
