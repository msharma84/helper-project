package com.myproject.env;

/**
*Import this dependency from maven central repository
*<dependency>
*	<groupId>uk.org.webcompere</groupId>
*	<artifactId>system-stubs-jupiter</artifactId>
*	<version>2.1.3</version>
*	<scope>test</scope>
*</dependency>
*
*/

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

@ExtendWith(SystemStubsExtension.class)
class EnvironmentVariablesUnitTest {

	@SystemStub
	private EnvironmentVariables environmentVariables;
	
	@BeforeEach
	void beforeEach() {
		environmentVariables.set("ENV1", "VALUE1");
	}
	@Test
	void envTest() {
		assertThat(System.getenv("ENV1")).isEqualTo("VALUE1");
	}
}
