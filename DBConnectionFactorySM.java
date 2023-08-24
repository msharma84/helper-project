package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DBConnectionFactorySM {
	
	private static String DRIVERNAME = "org.postgresql.Driver";

	 /**
    	 * The PostgreSQL error code for when a user logs in using an invalid password.
    	 *
     	* See <a href="https://www.postgresql.org/docs/9.6/static/errcodes-appendix.html">PostgreSQL documentation</a>.
     	*/
    	private static final String ACCESS_DENIED_FOR_USER_USING_PASSWORD_TO_DATABASE = "28P01";
    
    	private static final Integer RETRY_COUNT = 3;
	
	private static String getSecretPassword() throws Exception{
		
		AWSCredentialsProvider credentialsProvider = new AWSCredentialsProvider() {
			
			private String accessKey = "";
            		private String secretAccessKey = "";
            
			public AWSCredentials getCredentials() {
				return new BasicAWSCredentials(accessKey, secretAccessKey);
			}

			public void refresh() {
				 this.accessKey = null;
			}
			
		};
		
		JsonNode secretsJson = null;
	    ObjectMapper objectMapper = new ObjectMapper();
	    AWSSecretsManager  client  =  AWSSecretsManagerClientBuilder.standard()
	    		.withCredentials(credentialsProvider)
	    		.withRegion("ap-south-1")
	    		.build();
		GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId("DBCred");
	    GetSecretValueResult getSecretValueResponse = null;
	    try {
	        getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
	    } catch (Exception e) {
	        // For a list of exceptions thrown, see
	        // https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
	        throw e;
	    }

	    String secret = getSecretValueResponse.getSecretString();
	    if (secret != null) 
	    {
	    	try{
	    		secretsJson = objectMapper.readTree(secret);
	    	} catch (Exception e) {
		        // For a list of exceptions thrown, see
		        // https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
		        try {
					throw e;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
	    }
	    return secretsJson.get("password").textValue();
	}

	private static String refreshPasswordFromSecret() throws Exception {
		System.out.println("Getting new Password from Secret Manager");
		String newPassword = getSecretPassword();
		System.out.println("New Password fetched..."+newPassword);
		return newPassword;
	}

	public static boolean isExceptionDueToAuthenticationError(Exception e) {
        if (e instanceof SQLException) {
            SQLException sqle = (SQLException) e;
            String sqlState = sqle.getSQLState();
            return sqlState.equals(ACCESS_DENIED_FOR_USER_USING_PASSWORD_TO_DATABASE);
		}
           return false;
    	}
	
	public static void main(String[] args) throws Exception {
		
		String password= getSecretPassword();
		System.out.println("Password retrieve from secret manager -> "+password);
		
		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		String url = "jdbc:postgresql://mydb.ap-south-1.rds.amazonaws.com:5432/db";
		String user = "admin";
		Integer attempt_count = 0;
		while(con == null && attempt_count < RETRY_COUNT) {
			try {
				Class.forName(DRIVERNAME);
				con = DriverManager.getConnection(url,user,password);
				stmt= con.createStatement();
				rs = stmt.executeQuery("SELECT CURRENT_TIMESTAMP");
				while(rs.next()){
					// if we get Timestamp as return type - valid query is successfully run in the database
					Timestamp t = rs.getTimestamp(1);
					System.out.println("TimeStamp - >"+t);
				}
			} catch (SQLException e) {
				 attempt_count = attempt_count+1;
				 if (isExceptionDueToAuthenticationError(e)) {
					 password = refreshPasswordFromSecret();
				 }else {
					 System.err.println(e.getMessage());
				 }
			} catch (ClassNotFoundException e) {
				System.err.println(e.getMessage());
			}
		}
		attempt_count=0;
	}
}
