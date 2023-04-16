package server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Logger;

import db.Data;
import db.SQLConnection;
import wse.WSE;
import wse.server.servlet.HttpServlet;
import wse.server.servlet.HttpServletRequest;
import wse.server.servlet.HttpServletResponse;
import wse.utils.http.StreamUtils;

public class DepthMeterServlet extends HttpServlet{
	static Logger logger = WSE.getLogger();

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		InputStream inputStream = request.getContent();
		byte[] byteArray = StreamUtils.readAll(inputStream);
		
		Scanner scanner = new Scanner(new ByteArrayInputStream(byteArray));
		scanner.useLocale(Locale.US);
		float sensorData = scanner.nextFloat();
		int sensorType = scanner.nextInt();
		int sensorID = scanner.nextInt();
		
		try {
			SQLConnection connection = ArduinoServer.getConnect();
			connection.insert(sensorData, sensorType, sensorID);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.writeHeader();

		scanner.close();

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.forceEnableMessageLogging();

		List<Data> dataList;
		String message = "Vattenniv�n i Karsj� \n";
		

		try {
			SQLConnection connection = ArduinoServer.getConnect();
			dataList = connection.get(7);
			connection.close();
			
			message += "Antal m�tpunkter: " + dataList.size() + "\n" + "------------------------------------------------------------------------\n"; 
			
			for (Data data : dataList) {
				
				message += data.value + "m vid tiden " + data.timeStamp + "\n";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		byte[] byteMessage = message.getBytes();
		response.setContentLength(byteMessage.length);
		response.write(byteMessage);
	}

	

}
