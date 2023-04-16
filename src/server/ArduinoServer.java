package server;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.logging.Level;

import db.SQLConnection;
import wse.WSE;
import wse.server.ServiceManager;
import wse.server.WSEServer;
import wse.utils.log.LogPrintStream;

public class ArduinoServer extends WSEServer {
	static SQLConnection sqlConnection;
	static Boolean waterStatus = false;

	public static void main(String[] args) throws SecurityException, FileNotFoundException {
		WSE.setLogLevel(Level.FINEST);
		WSE.initDefaultStandaloneLogging();
		WSE.initFileLogging();
		System.setErr(new LogPrintStream(WSE.getLogger(), Level.SEVERE));
		System.setOut(new LogPrintStream(WSE.getLogger(), Level.INFO));
		
		new ArduinoServer().start();
		

	}

	public ArduinoServer() {

		ServiceManager manager = getServiceManager();
		manager.register("/SendData", ArduinoSensorDataServlet.class);
		manager.register("/SendDataSocket", NodeWebSocket.class);
		manager.register("/SendVoltage", SolarPanelServlet.class);
		manager.register("/SendDepth", DepthMeterServlet.class);
		manager.register("/SendMessage",MessageTestServlet.class);
		manager.register("/GetWaterStatus",GetWaterStatusServlet.class);
		
		addHttp(1303);
	}

	public static SQLConnection getConnect() throws SQLException {
		sqlConnection = new SQLConnection();
		return sqlConnection;
	}
	
	public static void setWaterStatus(Boolean state) {
		waterStatus = state;
	}
}
