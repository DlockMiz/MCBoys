package com.mcboys.mcboys;

import com.mcboys.mcboys.Models.McServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@SpringBootApplication
public class McboysApplication {

	public static void main(String[] args) {
		SpringApplication.run(McboysApplication.class, args);
	}

}
