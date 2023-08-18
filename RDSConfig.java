package com;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.WebIdentityTokenCredentialsProvider;
import com.amazonaws.services.rds.auth.GetIamAuthTokenRequest;
import com.amazonaws.services.rds.auth.RdsIamAuthTokenGenerator;

public class RDSConfig {
	
	public String getToken(String host, Integer port, String username, String aws_region) {
		
		AWSCredentialsProvider cred = WebIdentityTokenCredentialsProvider.builder().build();
		RdsIamAuthTokenGenerator generator = RdsIamAuthTokenGenerator.builder().credentials(cred).region(aws_region).build();
		
		GetIamAuthTokenRequest request = GetIamAuthTokenRequest.builder()
					.hostname(host)
					.port(port)
					.userName(username).build();
		String authToken = generator.getAuthToken(request);
		return authToken;
	}
}
