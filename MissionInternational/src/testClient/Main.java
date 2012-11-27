package testClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import missionintergroup.GPSCoordinate;
import missionintergroup.MissionID;
import missionintergroup.MissionIntergroup;

public class Main {
	private static String serverIP = null;
	private static int serverPort = 0;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean connected = false;
		
		MissionIntergroup testMisson = new MissionIntergroup(new MissionID('F', 1), new GPSCoordinate(10, 10), "Test misson", "this misson is testing", new Date());
		try {
			Socket socket = new Socket(serverIP, serverPort);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			connected = true;
		} catch (Exception e) {
			System.out.println("socket fail " + e.toString());
			
		}
		 
		while (connected){
			if(testMisson != null){
				
			}
			
		}
	}

}
