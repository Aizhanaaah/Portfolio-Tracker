package com.aizhan.Portfolio_Tracker;

import org.springframework.boot.SpringApplication;

public class TestPortfolioTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.from(PortfolioTrackerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
