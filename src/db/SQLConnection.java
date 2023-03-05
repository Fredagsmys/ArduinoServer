package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class SQLConnection {
	Connection connection;
	Statement statement;

	public SQLConnection() throws SQLException {
		String URI = "jdbc:mysql://localhost:3306/sensorData?user=root&password=Baggenq321&serverTimezone=GMT%2b2";
		connection = DriverManager.getConnection(URI);
		statement = connection.createStatement();

	}

	public int insert(double value, int sensorType, int sensorID) throws SQLException {

		String insertString = String.format(Locale.US,
				"INSERT INTO data (Value, SensorType, SensorID) VALUES (%.1f, %d, %d);",
				value, sensorType, sensorID);
		return statement.executeUpdate(insertString);
	}

	public List<Data> get(int sensorID) throws SQLException {
		String getString = String.format("SELECT * FROM data WHERE SensorID = %d", sensorID);
		ResultSet resultSet = statement.executeQuery(getString);
		List<Data> dataList = new LinkedList<>();
		while (resultSet.next()) {
			Data newData = new Data();
			read(resultSet, newData);
			dataList.add(newData);
		}
		return dataList;
	}

	public List<Data> get(int sensorID, String date) throws SQLException {
		
		String getString = String.format("SELECT * FROM data WHERE SensorID = %d AND DATE LIKE '%s%%'",
				sensorID, date);
		System.out.println(getString);
		ResultSet resultSet = statement.executeQuery(getString);
		List<Data> dataList = new LinkedList<>();
		while (resultSet.next()) {
			Data newData = new Data();
			read(resultSet, newData);
			dataList.add(newData);
		}

		return dataList;
	}
//	public List<Data> getBetween(int sensorID, String startDate, String stopDate) throws SQLException {
//		
//		String dateString = Utils.getDatesBetween(startDate, stopDate);
//		String getString = String.format("SELECT * FROM data WHERE SensorID = %d AND Timestamp REGEXP '%s'",
//				sensorID, dateString);
//		System.out.println(getString);
//		ResultSet resultSet = statement.executeQuery(getString);
//		List<Data> dataList = new LinkedList<>();
//		while (resultSet.next()) {
//			Data newData = new Data();
//			read(resultSet, newData);
//			dataList.add(newData);
//		}
//
//		return dataList;
//	}

	public List<db.DataPacket> getMultiple(List<Integer> sensorID) throws SQLException {
		List<db.DataPacket> dataPacketList = new ArrayList<>();
		for (int ID : sensorID) {
			db.DataPacket dataPacket = new db.DataPacket();
			String getString = String.format("SELECT * FROM data WHERE SensorID = %d", ID);
			ResultSet resultSet = statement.executeQuery(getString);
			while (resultSet.next()) {
				Data newData = new Data();
				read(resultSet, newData);
				dataPacket.dataList.add(newData);
			}
			dataPacketList.add(dataPacket);
		}
		return dataPacketList;

	}

	public List<Integer> getIDs() throws SQLException {
		String getString = "SELECT DISTINCT SensorID FROM data;";
		ResultSet resultSet = statement.executeQuery(getString);
		List<Integer> IDs = new ArrayList<>();
		while (resultSet.next()) {
			IDs.add(resultSet.getInt(1));
		}
		return IDs;

	}

	public List<Data> getAll() throws SQLException {
		String getString = "SELECT * FROM data";
		ResultSet resultSet = statement.executeQuery(getString);
		List<Data> dataList = new LinkedList<>();
		while (resultSet.next()) {
			Data newData = new Data();
			read(resultSet, newData);
			dataList.add(newData);
		}
		return dataList;
	}

	public static final void printAll(ResultSet resultSet) throws SQLException {
		ResultSetMetaData meta = resultSet.getMetaData();
		while (resultSet.next()) {
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				System.out.println(meta.getColumnName(i) + ": '" + resultSet.getObject(i) + "'");
			}
			System.out.println();
		}
	}

	public final void printAll() throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM data;");
		SQLConnection.printAll(resultSet);
	}

	public void read(ResultSet resultSet, Data data) throws SQLException {
		data.ID = resultSet.getInt(1);
		data.timeStamp = resultSet.getString(2);
		data.value = resultSet.getDouble(3);
		data.sensorType = resultSet.getInt(4);
		data.sensorID = resultSet.getInt(5);
	}



}
