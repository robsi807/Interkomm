package testClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Scanner;
import com.google.gson.Gson;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import authentication.LoginObject;

import missionintergroup.GPSCoordinate;
import missionintergroup.MissionID;
import missionintergroup.MissionIntergroup;
import missionintergroup.MissionIntergroupUpdate;
import missionintergroup.MissionIntergroupUpdate.UpdateContent;

public class TestClient {
	private static String serverIP = "130.236.77.94";
	private static int serverPort = 3802;
	
	/**
	 * @param args
	 */
	public TestClient() {
		
	}
		
		public void TestClientStart(){
			
		System.setProperty("javax.net.ssl.keyStore" , getClass().getClassLoader().getResource("testClient/masterserver.jks").getPath());
		System.setProperty("javax.net.ssl.keyStorePassword","password");
		SSLContext SSLC = null;
		
		boolean connected = false;
		Object incomeing = null;
		Scanner in = new Scanner(System.in);
		System.out.println("Select faction char");
		char faction = in.nextLine().charAt(0);
		LoginObject login = new LoginObject(faction);
		MissionIntergroup testMisson = new MissionIntergroup(new MissionID(faction, 1), new GPSCoordinate(10, 10), "Test misson", "this misson is testing", new Date());
		MissionIntergroupUpdate testUpdate = new MissionIntergroupUpdate(new MissionID(faction, 1), UpdateContent.TITLE, "New name");
		LoginObject testlogin = new LoginObject('f');
		System.out.println(testMisson.getId().idToString());
		ObjectOutputStream output = null;
		ObjectInputStream input = null;
		Socket socket = null;
		boolean test = true;
		Gson gson = new Gson();
		
		String missonTester = gson.toJson(testMisson);
		String updateTester = gson.toJson(testUpdate);
		String loginTester = gson.toJson(testlogin);
		if(missonTester.contains("\"identifier\":\"@Missonintergroup@\"")){
			System.out.println("its alive");
			MissionIntergroup con = gson.fromJson(missonTester, MissionIntergroup.class);
			System.out.println(con.getTitle());
		}
		if(updateTester.contains("\"identifier\":\"@MissonUpdateInter@\"")){
			System.out.println("update is alive");
			MissionIntergroupUpdate up = gson.fromJson(updateTester, MissionIntergroupUpdate.class);
			System.out.println(up.getNewValue());
		}
		if(loginTester.contains("\"identifier\":\"@login@\"")){
			System.out.println("login is alive");
			System.out.println(gson.fromJson(loginTester, LoginObject.class));
		}
		
		/*
		try {
//			SSLSocketFactory sslsocketfactory = SSLC.getSocketFactory();
//			socket = (SSLSocket) sslsocketfactory.createSocket(serverIP,serverPort);
			socket = new Socket(serverIP,serverPort);
			input = new ObjectInputStream (socket.getInputStream());
			output = new ObjectOutputStream (socket.getOutputStream());
			connected = true;
		} catch (Exception e) {
			System.out.println("socket fail " + e.toString());
		}
		
		while (connected){
				try {
					output.writeObject(login);
//					testMisson = null;
				} catch (IOException e) {
					System.out.println("output error: " + e.toString());
				}
			while(true){
				if(test){
					try {
						output.writeObject(testMisson);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					test = false;
				}
				try {
					incomeing = input.readObject();
				} catch (Exception e) {
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
		}*/ 
	}

}
