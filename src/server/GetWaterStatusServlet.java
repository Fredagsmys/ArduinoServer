package server;

import java.io.IOException;

import wse.server.servlet.HttpServlet;
import wse.server.servlet.HttpServletRequest;
import wse.server.servlet.HttpServletResponse;

public class GetWaterStatusServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		super.doGet(request, response);
		String waterState = String.valueOf(ArduinoServer.waterStatus ? 1 : 0);
		response.setContentLength(waterState.length());
		response.write(waterState.getBytes());
		ArduinoServer.setWaterStatus(false);
	}
}