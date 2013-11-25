package ee.ttu.kinect.model.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import ee.ttu.kinect.model.Markers;

public class MarkersServer implements Runnable {

	private ServerSocket serverSocket;

	private Markers markers;

	public MarkersServer(Markers markers) throws IOException {
		this.markers = markers;
		serverSocket = new ServerSocket(1111, 0, InetAddress.getByName("localhost"));
	}

	public void run() {
		try {
			while(true) {
				Socket clientSocket = null;
				try {
					clientSocket = serverSocket.accept();
					System.out.println("listening.. " + serverSocket.getLocalSocketAddress() + " " + serverSocket.getLocalPort());
					BufferedReader input = new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));
					String answer;
					while((answer = input.readLine()) != null) {
						System.out.println("incoming... " + answer);
						markers.setState(parseMarkers(answer));
					}
				} catch(Exception e) {
					e.printStackTrace();
				} finally {
					if (clientSocket != null) {
						clientSocket.close();
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean[] parseMarkers(String markersAsStr) {
		boolean[] state = new boolean[markersAsStr.length()];
		for (int i = 0; i < markersAsStr.length(); i++) {
			state[i] = markersAsStr.charAt(i) == '1' ? true : false;
		}

		return state;
	}

}