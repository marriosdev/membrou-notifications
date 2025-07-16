package com.membrou.notifications;

import io.sentry.Sentry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationsApplication {
	public static void main(String[] args) {
//		Sentry.init(options -> {
//			options.setDsn("https://<token>@o0.ingest.sentry.io/0");
//			options.setEnable(true);
//		});
		SpringApplication.run(NotificationsApplication.class, args);
	}
}
