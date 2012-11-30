package testClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.util.Date;
import java.util.Scanner;
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

/**
 * A very simple client that has been used to debugg the interkomm server.
 * This client is to be used as a example for how to write a client module for
 * your servers, NOT TO BE COPIED, IF YOU DO YOU'RE GOING TO HAVE A BAD TIME.
 * @author eric (the pretty one)
 *
 */ 
public class TestClient implements HandshakeCompletedListener {
	private static String serverIP = "130.236.71.15";
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
	 * A empty constructor
	 * @param args
	 */
	public TestClient() {

	}
	/**
	 * Runs the test client
	 */
	public void TestClientStart() {
	/*====This part is used to establish the SSL encryption====*/
		KeyStore keystore;
		KeyManagerFactory keymangamentfactory = null;
		SSLContext sslcontext = null;
		TrustManagerFactory trustmanagerfactory = null;
		try {
			keystore = KeyStore.getInstance("JKS");
			keystore.load(new FileInputStream(new File(getClass().getClassLoader().getResource("testClient/masterserver.jks").getPath())),password);
			keymangamentfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			keymangamentfactory.init(keystore, password);
			trustmanagerfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustmanagerfactory.init(keystore);
			sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(keymangamentfactory.getKeyManagers(),trustmanagerfactory.getTrustManagers(), null);
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
		SSLSocketFactory sslsocketfactory = null;
		sslsocket = null;
		try {
			sslsocketfactory = sslcontext.getSocketFactory();
			sslsocket = (SSLSocket) sslsocketfactory.createSocket(serverIP,serverPort);
			sslsocket.addHandshakeCompletedListener(this);
			sslsocket.startHandshake();

		} catch (Exception e) {
			System.out.println("SSL error " + e.toString());
		}
		/*====SSL encryption end====*/
		// the loop
		while(true){
			if(connect){
				try {
					input = new BufferedReader(new InputStreamReader(sslsocket.getInputStream()));
					output = new PrintWriter(sslsocket.getOutputStream(), true);
					connected = true;
					System.out.println("connected is alive");
				} catch (Exception e) {
					System.out.println("socket fail " + e.toString());
				}
				Gson gson = new Gson();
				LoginObject login = new LoginObject(faction);
				MissionIntergroup testMisson = new MissionIntergroup(new MissionID(faction, 1), new GPSCoordinate(10, 10), "Test misson","this misson is testing", new Date());
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
								System.out.println("Incomming string is: "+ incomeingString);
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
	
	/**
	 * a listener that prevents the client to start listening for incoming data before the handshake is done. 
	 */
	@Override
	public void handshakeCompleted(HandshakeCompletedEvent arg0) {
		connect = true;
		System.out.println("handshake completed");
	}
}
