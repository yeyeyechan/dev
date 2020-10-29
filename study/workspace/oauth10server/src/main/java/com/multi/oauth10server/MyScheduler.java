package com.multi.oauth10server;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduler {
	//@Scheduled(cron = "0,10,20,30,40,50 * * * * *")
	public void MyJob() {
		System.out.println("## MyJob");
	}
}
