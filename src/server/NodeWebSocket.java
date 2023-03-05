package server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Scanner;

import db.SQLConnection;
import wse.server.servlet.ws.WebSocketServlet;
import wse.utils.http.HttpHeader;
import wse.utils.http.StreamUtils;

public class NodeWebSocket extends WebSocketServlet {
static NodeWebSocket socket;

	public NodeWebSocket(){
		NodeWebSocket.socket = this;
		System.out.println("=====================Socket variable initialized===========================");
	}
	
	@Override
	public void onClose(boolean arg0, String arg1) {
		System.out.println("WebSocket shut down");
	}

	@Override
	public void onInit(HttpHeader arg0) {
		System.out.println("WebSocket started");
	}

//	private void onPing() {
//		System.out.println("====================================Pong===================");
//
//	}
	@Override
	public void onMessage(InputStream inputStream) {

		SQLConnection connection = ArduinoServer.getConnect();

		byte[] byteArray;
		try {
			byteArray = StreamUtils.readAll(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}

		Scanner scanner = new Scanner(new ByteArrayInputStream(byteArray));
		scanner.useLocale(Locale.US);
		float sensorData = scanner.nextFloat();
		int sensorType = scanner.nextInt();
		int sensorID = scanner.nextInt();

		try {
			connection.insert(sensorData, sensorType, sensorID);
		} catch (Exception e) {
			e.printStackTrace();
		}

		scanner.close();

	}
	public static NodeWebSocket getSocket() {
		return socket;
	}

}
