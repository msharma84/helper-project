package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionFactory {

	private static RDSConfig rdsConfig = new RDSConfig();

	private static String DRIVERNAME = "org.postgresql.Driver";
	private static String VALID_QUERY = "SELECT CURRENT_TIMESTAMP";

	/**
	 * Getting the Database connection of IBM DB2
	 * 
	 * @param url      url of the database
	 * @param userName username of the db
	 * @param password password of the db
	 * @return Connection returns DB Connection object
	 */
	public static Connection getConnection(final String url, final String tokenUser,
			final String region) {

		Connection con = null;
		
		int slashing = url.indexOf("//") + 2;
		String sub = url.substring(slashing, url.indexOf("/", slashing));
		String [] splitted = sub.split(":");
		
		String host = splitted[0];
		Integer port = Integer.parseInt(splitted[1]) ;
		
		String token = rdsConfig.getToken(host, port, tokenUser, region);
		try {
			Class.forName(DRIVERNAME);
			con = DriverManager.getConnection(url, tokenUser, token);

			// testing for the valid connection
			if(testConnection(con)){
				logger.info("Valid DB Connection # Connection Tested...");
			}else{
				logger.info("In-Valid DB Connection...");
				con = null;
			}

		} catch (ClassNotFoundException cla) {
			System.out.println("Class Not found exception...{}",ExceptionUtils.getStackTrace(cla));
		} catch (SQLException sqe) {
			System.out.println("SQL Exception...{}",ExceptionUtils.getStackTrace(sqe));
		} catch (Exception exe) {
			System.out.println("Exception occured while making Connection Object...{}",ExceptionUtils.getStackTrace(exe));
		}
		return con;
	}

	/**
	 *  Closing the java.sql.Connection class object 
	 *  
	 *  @param	con		Connection object which need to be closed
	 *  
	 * */
	public static void close(Connection con){
		
		logger.info("Closing the connection object...");
		try {
			if(con != null ){
				con.close();
			}
		} catch (SQLException e) {
			logger.error("Exception occured while closing Connection Object...{}",ExceptionUtils.getStackTrace(e));
		}
	}

	/**
	 * This method will test connection if proper or not
	 * @param	con		Connection object which need to be checked
	 * @return	flag	true if connection is fine, false in case of connection is wrong
	 * @author  mohit
	 * */
	public static synchronized boolean testConnection(Connection con){
		
		boolean flag = false;
		logger.info("Testing the connection before providing to another process...");
		ResultSet rs = null;
		Statement stmt = null;
		try{
			stmt= con.createStatement();
			
			// running the query for validation of the database
			rs = stmt.executeQuery(VALID_QUERY);
			while(rs.next()){
				// if we get Timestamp as return type - valid query is successfully run in the database
				Timestamp t = rs.getTimestamp(1);
				if( t != null){
					flag =true;
				}
			}
		}catch(SQLException e){
			logger.error("SQL Exception error...{}",ExceptionUtils.getStackTrace(e));
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("Error in closing Result Set...{}",ExceptionUtils.getStackTrace(e));
				}
			}
			if( stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.error("Error in closing Statement...{}",ExceptionUtils.getStackTrace(e));
				}
			}
		}
		return flag;
	}
	
	public static void main(String args[]) {
		
		Connection con = getConnection("jdbc:postgresql://XXXXXX-pgsql.cftakoabckjk.ap-south-1.rds.amazonaws.com:5432/bludb?currentSchema=bluadmin",
				"rds","ap-south-1");
		System.out.println("Connection Object...{}",con);
	}
}
