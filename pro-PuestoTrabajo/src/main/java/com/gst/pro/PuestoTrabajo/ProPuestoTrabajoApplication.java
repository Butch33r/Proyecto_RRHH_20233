package com.gst.pro.PuestoTrabajo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ProPuestoTrabajoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProPuestoTrabajoApplication.class, args);
	}

}
