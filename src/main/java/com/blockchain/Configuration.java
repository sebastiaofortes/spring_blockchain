package com.blockchain;

import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

	public Configuration() {
		// TODO Auto-generated constructor stub
	}
	
	@Bean
	public P2P p2p () {
		P2P myp2p = new P2P();
		return myp2p;
	}

}
