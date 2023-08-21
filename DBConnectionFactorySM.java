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
	
	public static void main(String[] args) throws Exception {
		String password= getSecretPassword();
		
		System.out.println("Password retrieve from secret manager -> "+password);
		
		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		String url = "";
		String user = "";
		
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
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
