package com.kedacom.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {
	//Access the url after starting the service to check the remote repository configuration info
	// http://localhost:1201/category-service/default/master
	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}
}
