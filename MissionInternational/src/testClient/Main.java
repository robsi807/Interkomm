package testClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

import authentication.LoginObject;

import missionintergroup.GPSCoordinate;
import missionintergroup.MissionID;
import missionintergroup.MissionIntergroup;
import missionintergroup.MissionIntergroupUpdate;

public class Main {
	private static String serverIP = null;
	private static int serverPort = 0;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean connected = false;
		Object incomeing = null;
		Scanner in = new Scanner(System.in);
		System.out.println("Select faction char");
		char faction = in.nextLine().charAt(0);
		LoginObject login = new LoginObject(faction);
		MissionIntergroup testMisson = new MissionIntergroup(new MissionID(faction, 1), new GPSCoordinate(10, 10), "Test misson", "this misson is testing", new Date());
		ObjectOutputStream output = null;
		ObjectInputStream input = null;
		try {
			Socket socket = new Socket(serverIP, serverPort);
			input = (ObjectInputStream) socket.getInputStream();
			output = (ObjectOutputStream) socket.getOutputStream();
			connected = true;
		} catch (Exception e) {
			System.out.println("socket fail " + e.toString());
		}
		 
		while (connected){
			if(testMisson != null){
				try {
					output.writeObject(login);
					output.writeObject(testMisson);
				} catch (IOException e) {
					System.out.println("output error: " + e.toString());
				}
			}
			while(true){
				try {
					incomeing = input.readObject();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(incomeing != null){
					if(incomeing instanceof MissionIntergroup){
						System.out.println("A misson has arrived, it was called: " + ((MissionIntergroup)incomeing).getTitle());
						incomeing = null;
					}else if(incomeing instanceof MissionIntergroupUpdate){
						System.out.println("A misson uppdate has arrived");
						incomeing = null;
					}
				}
			}
		}
	}

}
