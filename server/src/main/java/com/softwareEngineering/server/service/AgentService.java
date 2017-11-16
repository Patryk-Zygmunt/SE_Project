package com.softwareEngineering.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softwareEngineering.server.model.entity.Agent;
import com.softwareEngineering.server.repositories.AgentRepository;

import java.util.List;

@Service
public class AgentService {

	@Autowired
	AgentRepository agentRepository;

	public List<Agent> getAgents() {
		return agentRepository.findAll();
	}

}
