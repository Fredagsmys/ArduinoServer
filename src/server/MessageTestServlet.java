package server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Scanner;

import wse.server.servlet.HttpServlet;
import wse.server.servlet.HttpServletRequest;
import wse.server.servlet.HttpServletResponse;
import wse.utils.http.StreamUtils;

public class MessageTestServlet extends HttpServlet{
	
	String message = "";
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		InputStream inputStream = request.getContent();
		byte[] byteArray = StreamUtils.readAll(inputStream);

		Scanner scanner = new Scanner(new ByteArrayInputStream(byteArray));
		scanner.useLocale(Locale.US);
		message = scanner.next();
	}
	
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.forceEnableMessageLogging();
		response.setContentLength(message.length());
		response.write(message.getBytes());
	}

}
