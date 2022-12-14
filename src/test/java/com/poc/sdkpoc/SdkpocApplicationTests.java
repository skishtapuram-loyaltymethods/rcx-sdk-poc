package com.poc.sdkpoc;

import static groovy.json.JsonOutput.toJson;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rcx.client.model.RCXToken;
import com.rcx.client.model.RcxAuth;
import com.rcx.client.property.RcxConnectionProperties;
import com.rcx.client.service.AuthHandler;
import com.rcx.client.service.webclient.RCXHttpClient;
import com.rcx.client.service.webclient.util.ExceptionMapper;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

@SpringBootTest
class SdkpocApplicationTests {

	private AuthHandler authHandler;

	private static MockWebServer mockWebServer;

	private WebClient activityWebClient;

	private ExceptionMapper exceptionMapper;

	private RcxAuth rcxAuth;

	private ObjectMapper objectMapper;

	private RCXHttpClient rcxHttpClient;

	@BeforeAll
	static void StartServer() throws IOException {
		mockWebServer = new MockWebServer();
//		mockWebServer.start();
		mockWebServer.start(3000);
	}

	@AfterAll
	static void ShutdownServer() throws IOException {
		mockWebServer.shutdown();
	}
	@BeforeEach
	void setupMockWebServer() {
		mockWebServer = new MockWebServer();

		RcxConnectionProperties properties = new RcxConnectionProperties();
//		properties.setBaseUrl(mockWebServer.url("/").url().toString());
		properties.setBaseUrl("http://crud:3000");
//		properties.setPath("/api/v1/");
		properties.setUsername("test/admin");
		properties.setPassword("wint00l$");

		objectMapper = new ObjectMapper();
		activityWebClient = WebClient.create("http://localhost:3000/api/v1/");
		exceptionMapper = new ExceptionMapper(objectMapper);
		rcxAuth = new RcxAuth();

		rcxHttpClient = new RCXHttpClient(activityWebClient, exceptionMapper, rcxAuth);

		authHandler = new AuthHandler(properties, rcxHttpClient, rcxAuth);
	}

	@Test
	void demo() throws InterruptedException {

		RCXToken rcxToken  = new RCXToken();
		rcxToken.setToken("asfdasdfasdfasdjasbhdfgwyerq3e219374213g2bfhe");

		MockResponse mockResponse = new MockResponse();

		mockResponse.setResponseCode(200)
				.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.setBody(toJson(rcxToken));

		mockWebServer.enqueue(mockResponse);

		authHandler.login();

		RecordedRequest request = mockWebServer.takeRequest();

		assertThat(request.getMethod()).isEqualTo("POST");

	}

}
