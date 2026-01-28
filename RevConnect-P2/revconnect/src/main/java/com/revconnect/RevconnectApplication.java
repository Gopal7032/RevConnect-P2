package com.revconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RevconnectApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context =
				SpringApplication.run(RevconnectApplication.class, args);

		// Keep application running until user exits
		synchronized (context) {
			try {
				context.wait();
			} catch (InterruptedException ex) {
				System.out.println("Application stopped.");
				Thread.currentThread().interrupt();
			}
		}
	}
}
