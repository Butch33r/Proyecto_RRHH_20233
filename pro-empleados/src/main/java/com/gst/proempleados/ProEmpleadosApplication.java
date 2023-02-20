package com.gst.proempleados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients

@SpringBootApplication
public class ProEmpleadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProEmpleadosApplication.class, args);
	}

}
