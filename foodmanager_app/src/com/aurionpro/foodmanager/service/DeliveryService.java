package com.aurionpro.foodmanager.service;

import com.aurionpro.foodmanager.model.DeliveryAgent;

import java.util.Random;
import java.util.List;

public class DeliveryService {

	public DeliveryAgent assignAgent(List<DeliveryAgent> agents) {
		Random random = new Random();
		int index = random.nextInt(agents.size());
		return agents.get(index);
	}

}
