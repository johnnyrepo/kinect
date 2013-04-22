using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Input;
using System.Xml;
using System.Threading;

using Microsoft.Kinect;


namespace KinectSensorProxy {
    public interface IKinectSensorProxy {
        string initialize();
        string getFrames();
        string start();
        string stop();
    }

    public class KinectSensorProxy : IKinectSensorProxy {
        
		private KinectSensor kinectSensor;
        private List<string> skeletonQueue = new List<string>();
        
        private readonly object _lock = new object();

        public KinectSensorProxy() {
        }

        public string getFrames() {
			
			//syncronize modification        	
        	Monitor.Enter(_lock);
            List<string> skeletonList = new List<string>();
            while (skeletonQueue.Count > 0) {
                skeletonList.Add(skeletonQueue.ElementAt(0));
                skeletonQueue.RemoveAt(0);
            }
            Monitor.Exit(_lock);

            string listToString = null;
            if (skeletonList.Count > 0) {
                listToString = skeletonList.ElementAt(0);
                for (int i = 1; i < skeletonList.Count; i++) {
                    listToString += "*" + skeletonList.ElementAt(i);
                }
            }
            
            return listToString;
        }

        private void appendSkeletonToQueue(string skeleton) {
        	Monitor.Enter(_lock);
            skeletonQueue.Add(skeleton);
            Monitor.Exit(_lock);

        }

        private void skeletonFrameReady(object sender, SkeletonFrameReadyEventArgs e) {
			string frameId = null;
			string timestamp = null;
        	Skeleton[] skeletons = null;
            using (SkeletonFrame skeletonFrame = e.OpenSkeletonFrame()) {
				if (skeletonFrame != null) {
        			frameId = skeletonFrame.FrameNumber.ToString();
        			timestamp = skeletonFrame.Timestamp.ToString();
      				skeletons = new Skeleton[skeletonFrame.SkeletonArrayLength];
      				skeletonFrame.CopySkeletonDataTo(skeletons);
				} else {
                    // apps processing of skeleton data took too long; it got more than 2 frames behind.
                    // the data is no longer avabilable.
                    Console.Error.WriteLine("Processing of skeleton data took too long; it got more than 2 frames behind. The data is no longer avabilable.");
                }
        	}

			if(skeletons == null) {
				return;
			}

			XmlDocument doc = new XmlDocument();
			XmlNode xmlnode = doc.CreateNode(XmlNodeType.XmlDeclaration, "", "");
			
			doc.AppendChild(xmlnode);
			XmlNode root = doc.CreateElement("frame");
			
			doc.AppendChild(root);
			XmlNode frameNode = doc.CreateElement("frameId");
			frameNode.InnerText = frameId;
			
			root.AppendChild(frameNode);
			XmlNode timeStampNode = doc.CreateElement("timestamp");
			timeStampNode.InnerText = timestamp;
			
			root.AppendChild(timeStampNode);
			
			foreach (Skeleton skeleton in skeletons) {
			
			    if (skeleton.TrackingState == SkeletonTrackingState.Tracked) {
			        XmlNode skeletonData = doc.CreateElement("skeletonData");
			        root.AppendChild(skeletonData);
			
			        XmlNode trackingId = doc.CreateElement("trackingId");
			        trackingId.InnerText = skeleton.TrackingId.ToString();
			        skeletonData.AppendChild(trackingId);
			
			        JointCollection joints = skeleton.Joints;
			        foreach (Joint j in joints) {
			            XmlNode joint = doc.CreateElement("joint");
			/*
			            XmlNode jPosition = doc.CreateElement("position");
			            jPosition.InnerText = j.Position.X.ToString() + "#" + j.Position.Y.ToString() + "#" + j.Position.Z.ToString();
			            joint.AppendChild(jPosition);
			*/
								
			            XmlNode jPositionX = doc.CreateElement("positionX");
			            jPositionX.InnerText = j.Position.X.ToString();
			            joint.AppendChild(jPositionX);
			
			            XmlNode jPositionY = doc.CreateElement("positionY");
			            jPositionY.InnerText = j.Position.Y.ToString();
			            joint.AppendChild(jPositionY);
			
			            XmlNode jPositionZ = doc.CreateElement("positionZ");
			            jPositionZ.InnerText = j.Position.Z.ToString();
			            joint.AppendChild(jPositionZ);
			
			            XmlNode jointID = doc.CreateElement("jointId");
			            jointID.InnerText = j.JointType.ToString();
			            joint.AppendChild(jointID);
			
			            skeletonData.AppendChild(joint);
			        }
			  /*      
			        XmlNode positionX = doc.CreateElement("position");
			        positionX.InnerText = skeleton.Position.X.ToString() + "#" + skeleton.Position.Y.ToString() + "#" + skeleton.Position.Z.ToString();
			        skeletonData.AppendChild(positionX);
			*/
			                           
			        XmlNode positionX = doc.CreateElement("positionX");
			        positionX.InnerText = skeleton.Position.X.ToString();
			        skeletonData.AppendChild(positionX);
			
			        XmlNode positionY = doc.CreateElement("positionY");
			        positionY.InnerText = skeleton.Position.Y.ToString();
			        skeletonData.AppendChild(positionY);
			
			        XmlNode positionZ = doc.CreateElement("positionZ");
			        positionZ.InnerText = skeleton.Position.Z.ToString();
			        skeletonData.AppendChild(positionZ);
				}
			}
			appendSkeletonToQueue(doc.OuterXml);
        }

        public string start() {
        	if(kinectSensor == null) {
        		return "FAIL, Kinect not initialized";
        	}
			kinectSensor.Start();
			return "OK, start() done!";
        }
        
        public string initialize() {
        	if(KinectSensor.KinectSensors.Count == 0) {
        		return "FAIL, No Kinect sensors found!";
        	}
        	
            kinectSensor = KinectSensor.KinectSensors[0];
            
            try {
                kinectSensor.ColorStream.Enable();
                kinectSensor.DepthStream.Enable();
                kinectSensor.SkeletonStream.Enable();
            } catch (InvalidOperationException e) {
                return "FAIL, Runtime initialization failed. Please make sure Kinect device is plugged in. Msg:" + e;
            }

            kinectSensor.SkeletonFrameReady += new EventHandler<SkeletonFrameReadyEventArgs>(skeletonFrameReady);

            return "OK, initialize() done!";
        }

        public String stop() {
            this.kinectSensor.Stop();
            return "OK, stop() done!";
        }

        public String setSeatedTrackingMode() {
            this.kinectSensor.SkeletonStream.TrackingMode = SkeletonTrackingMode.Seated;
            return "Seated skeleton tracking mode set";
        }

        public String setDefaultTrackingMode() {
            this.kinectSensor.SkeletonStream.TrackingMode = SkeletonTrackingMode.Default;
            return "Default skeleton tracking mode set";
        }

    }
}
