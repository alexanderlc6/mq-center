package com.sp.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.sp.mq"})
@Slf4j
public class MqCenterApplication {
	public static void main(String[] args) {
		SpringApplication.run(MqCenterApplication.class, args);
		log.info("JVM initialized memory, usedMemory:{}MB, totalMemory:{}MB, maxMemory:{}MB",
				(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024,
				Runtime.getRuntime().totalMemory() / 1024 / 1024, Runtime.getRuntime().maxMemory() / 1024 / 1024);
	}
}
