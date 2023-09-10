package com.fbaron;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@SpringBootApplication
@RequiredArgsConstructor
public class Application {

	private final Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/getHostIP")
	public String getHostIP() throws UnknownHostException {
		InetAddress localhost = InetAddress.getLocalHost();
		String hostIp = localhost.getHostAddress();
		return "Host IP: " + hostIp;
	}

	@GetMapping("/getActiveProfile")
	public String getActiveProfile() {
		return "Active Profile: " + environment.getActiveProfiles()[0];
	}

}
