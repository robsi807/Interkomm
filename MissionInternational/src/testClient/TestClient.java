package testClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

import com.google.gson.Gson;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import authentication.LoginObject;

import missionintergroup.GPSCoordinate;
import missionintergroup.MissionID;
import missionintergroup.MissionIntergroup;
import missionintergroup.MissionIntergroupUpdate;
import missionintergroup.MissionIntergroupUpdate.UpdateContent;

public class TestClient implements HandshakeCompletedListener {
	private static String serverIP = "130.236.225.148";
	private static int serverPort = 3802;
	private char password[] = "password".toCharArray();
	private boolean connected = false;
	private BufferedReader input;
	private PrintWriter output;
	private boolean test;
	private char faction;
	SSLSocket sslsocket;
	boolean connect = false;

	/**
	 * @param args
	 */
	public TestClient() {

	}

	public void TestClientStart() {
		KeyStore keystore;
		KeyManagerFactory keymangamentfactory = null;
		SSLContext sslcontext = null;
		TrustManagerFactory trustmanagerfactory = null;
		try {
			keystore = KeyStore.getInstance("JKS");
			keystore.load(
					new FileInputStream(
							new File(
									"/home/eric/Dropbox/GitTDDD36/InterkommServer/src/interserver/masterserver.jks")),
					password);
			keymangamentfactory = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			keymangamentfactory.init(keystore, password);
			trustmanagerfactory = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustmanagerfactory.init(keystore);
			sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(keymangamentfactory.getKeyManagers(),
					trustmanagerfactory.getTrustManagers(), null);
		} catch (Exception e1) {
			System.out.println("error with keystore and shit" + e1.toString());
		}

		connected = false;

		Scanner in = new Scanner(System.in);
		System.out.println("Select faction char");
		faction = in.nextLine().charAt(0);

		output = null;
		input = null;
		test = true;

		System.setProperty(
				"javax.net.ssl,keyStore",
				"/home/eric/Dropbox/GitTDDD36/Interkomm/MissionInternational/src/testClient/masterserver");
		System.setProperty("javax.net.ssl.keyStorePassword", "password");
		SSLSocketFactory sslsocketfactory = null;
		sslsocket = null;
		try {
			sslsocketfactory = sslcontext.getSocketFactory();
			sslsocket = (SSLSocket) sslsocketfactory.createSocket(serverIP,
					serverPort);
			sslsocket.addHandshakeCompletedListener(this);
			sslsocket.startHandshake();

		} catch (Exception e) {
			System.out.println("SSL error " + e.toString());
		}
		while(true){
			if(!connect){
				System.out.println("not");
			}
			if(connect){
				try {
					input = new BufferedReader(new InputStreamReader(
							sslsocket.getInputStream()));
					output = new PrintWriter(sslsocket.getOutputStream(), true);
					connected = true;
					System.out.println("connected is alive");
				} catch (Exception e) {
					System.out.println("socket fail " + e.toString());
				}
				Gson gson = new Gson();
				LoginObject login = new LoginObject(faction);
				MissionIntergroup testMisson = new MissionIntergroup(new MissionID(
						faction, 1), new GPSCoordinate(10, 10), "Test misson",
						"this misson is testing", new Date());
				String incomeingString = null;
				while (connected) {
					try {
						output.println(gson.toJson(login));
						System.out.println("just wrote " + login);
					} catch (Exception e) {
						System.out.println("output error: " + e.toString());
					}
					boolean loop = true;
					while (loop) {
						if (test) {
							try {
								output.println(gson.toJson(testMisson));
								System.out.println("just wrote " + testMisson);
							} catch (Exception e) {
								e.printStackTrace();
							}
							test = false;
						}
						
						try {
							incomeingString = input.readLine();
							if (!incomeingString.equals("&")) {
								System.out.println("Incomeing string is: "
										+ incomeingString);
							}
						} catch (Exception e) {
							System.out.println("error " + e.toString());
							System.out.println("disconnected");
							loop = false;
						}
					}
				}
			}
		}
	}

		

	

	@Override
	public void handshakeCompleted(HandshakeCompletedEvent arg0) {
		connect = true;
		System.out.println("handshake completed");

}
}
