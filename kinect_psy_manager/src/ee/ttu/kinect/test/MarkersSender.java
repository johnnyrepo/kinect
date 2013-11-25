package ee.ttu.kinect.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class MarkersSender implements Runnable {

	private Random rnd = new Random();
		
	public static void main(String[] args) throws IOException {
		new MarkersSender().run();
	}

	public void run() {
		Socket socket = null;
		try {
			socket = new Socket("localhost", 1111);
			while (true) {
				PrintWriter out =
				        new PrintWriter(socket.getOutputStream(), true);
				try {
					if (rnd.nextInt(100000) == 9) {
						int amount = rnd.nextInt(17);
						int rand = rnd.nextInt(amount);
						for (int i = 0; i < amount; i++) {
							if (rand == i) {
								out.print('1');
							} else {
								out.print('0');
							}
						}
						out.println();
						//System.out.println(res);
						//out.flush();
					}
				} finally {
					//System.out.println("finally");
					//socket.close();
				}
			}
		} catch (Exception e) {
			System.out.println("exc " + e);
		} finally {
			//System.out.println("finally");
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}