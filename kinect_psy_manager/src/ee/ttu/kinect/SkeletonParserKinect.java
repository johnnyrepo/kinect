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
package ee.ttu.kinect;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.Position;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import ee.ttu.kinect.model.Body;
import ee.ttu.kinect.model.Joint;
import ee.ttu.kinect.model.JointType;

public class SkeletonParserKinect implements SkeletonParser {

	public static final String SKELETON_KEYWORD = "skeletonData";

	private XMLInputFactory inputFactory;
	
	private DocumentBuilder docBuilder;

	private int oldNumSkeletons = -1;
	private long frame = -1;
	private long timestamp = -1;

	public void reset() {
		this.oldNumSkeletons = -1;
		this.frame = -1;
	}

	public SkeletonParserKinect() {
		inputFactory = XMLInputFactory.newInstance();
        /*		
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		try {
			this.docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
		*/
	}

	@Override
	public void parseSkeleton(String input, Body body) {
        XMLStreamReader streamReader;
		try {
			streamReader = inputFactory.createXMLStreamReader(new StringReader(input));
			streamReader.nextTag(); // advance to 'frame' tag
			while (streamReader.hasNext()) {
				if (streamReader.isStartElement()) {
					if (streamReader.getLocalName().equals("frameId")) {
						int currentFrame = Integer.parseInt(streamReader.getElementText());
						if (currentFrame > this.frame) { // TODO I receive the same document multiple times
							this.frame = currentFrame;
							streamReader.next();
							if (streamReader.getLocalName().equals("timestamp")) {
								this.timestamp = Long.parseLong(streamReader.getElementText());
								body.setTimestamp(timestamp);
								body.setFrameNumber(frame);
								streamReader.nextTag(); // moving to 'skeletonData' tag
								if (streamReader.getLocalName().equals("skeletonData")) {
									//logger.info("Found skeleton");
									streamReader.nextTag(); // moving to 'trackingId' tag
									if (streamReader.getLocalName().equals("trackingId")) {
										String trackingId = streamReader.getElementText();
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
												
												updateBody(body, parseJoint(jointId, positionX, positionY, positionZ));
												
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
	/*
	@Override
	public void parseSkeleton(String input, Body body) {
		try {
			Document doc = docBuilder.parse(new InputSource(new StringReader(
					input)));

			NodeList frameNodes = doc.getElementsByTagName("frameId");
			// TODO Possible NPE if document is invalid
			Node frameNode = frameNodes.item(0);
			int currentFrame = Integer.parseInt(frameNode.getTextContent());
			if (currentFrame > this.frame) { // TODO I receive the same document
												// multiple times
				this.frame = currentFrame;

				NodeList timeStampNode = doc.getElementsByTagName("timestamp");
				Node timestampNode = timeStampNode.item(0);
				this.timeStamp = Long.parseLong(timestampNode.getTextContent());
				body.setTimestamp(timeStamp);
				body.setFrameNumber(frame);

				NodeList skeletons = doc.getElementsByTagName(SKELETON_KEYWORD);

				// Check whether number of skeletons has changed
				if (skeletons.getLength() != oldNumSkeletons) {
					oldNumSkeletons = skeletons.getLength();
					logger.info("Found " + skeletons.getLength() + " skeletons");
				}

				for (int i = 0; i < skeletons.getLength(); i++) {
					Node skeleton = skeletons.item(i);
					NodeList skeletonData = skeleton.getChildNodes();
					for (int j = 0; j < skeletonData.getLength(); j++) {
						Node data = skeletonData.item(j);

						if (data.getNodeName().equals("joint")) {
							Joint joint = parseJoint(data);
							updateBody(body, joint);
						}
					}
				}
			}
		} catch (SAXException e) {
			logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}
	*/
	private Joint parseJoint(Node jointNode) {
		Joint joint = new Joint();

		NodeList data = jointNode.getChildNodes();
		for (int k = 0; k < data.getLength(); k++) {
			Node jointData = data.item(k);
			if (jointData.getNodeName().equals("jointId")) {
				String jointId = jointData.getTextContent();
				joint.setType(JointType.getValueOf(jointId));
			} else if (jointData.getNodeName().equals("positionX")) {
				String posX = jointData.getTextContent().replace(',', '.');
				joint.setPositionX(Float.parseFloat(posX));
			} else if (jointData.getNodeName().equals("positionY")) {
				String posY = jointData.getTextContent().replace(',', '.');
				joint.setPositionY(Float.parseFloat(posY));
			} else if (jointData.getNodeName().equals("positionZ")) {
				String posZ = jointData.getTextContent().replace(',', '.');
				joint.setPositionZ(Float.parseFloat(posZ));
			}
		}

		return joint;
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
	
	private void updateBody(Body body, Joint joint) {
		switch (joint.getType()) {
		case ANKLE_LEFT:
			body.setAnkleLeft(joint);
			break;
		case ANKLE_RIGHT:
			body.setAnkleRight(joint);
			break;
		case ELBOW_LEFT:
			body.setElbowLeft(joint);
			break;
		case ELBOW_RIGHT:
			body.setElbowRight(joint);
			break;
		case FOOT_LEFT:
			body.setFootLeft(joint);
			break;
		case FOOT_RIGHT:
			body.setFootRight(joint);
			break;
		case HAND_LEFT:
			body.setHandLeft(joint);
			break;
		case HAND_RIGHT:
			body.setHandRight(joint);
			break;
		case HEAD:
			body.setHead(joint);
			break;
		case HIP_CENTER:
			body.setHipCenter(joint);
			break;
		case HIP_LEFT:
			body.setHipLeft(joint);
			break;
		case HIP_RIGHT:
			body.setHipRight(joint);
			break;
		case KNEE_LEFT:
			body.setKneeLeft(joint);
			break;
		case KNEE_RIGHT:
			body.setKneeRight(joint);
			break;
		case SHOULDER_CENTER:
			body.setShoulderCenter(joint);
			break;
		case SHOULDER_LEFT:
			body.setShoulderLeft(joint);
			break;
		case SHOULDER_RIGHT:
			body.setShoulderRight(joint);
			break;
		case SPINE:
			body.setSpine(joint);
			break;
		case WRIST_LEFT:
			body.setWristLeft(joint);
			break;
		case WRIST_RIGHT:
			body.setWristRight(joint);
			break;
		}
	}

}
