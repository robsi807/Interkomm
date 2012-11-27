package testClient;

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
		new MissionIntergroup(new MissionID('F', 1), new GPSCoordinate(10, 10), "Test misson", "this misson is testing", new Date());
		try {
			Socket socket = new Socket(serverIP, serverPort);	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		while (true){
			
		}
	}

}
