package com.softwareEngineering.server.service;

import java.util.Arrays;
import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.app.COD;
import com.app.CODFactory;
import com.softwareEngineering.server.model.entity.Agent;
import com.softwareEngineering.server.model.entity.ServerInfo;
import com.softwareEngineering.server.repositories.AgentRepository;
import com.softwareEngineering.server.repositories.ServerInfoRepository;

@Component
public class Init {
	private static final COD cod = CODFactory.setLevelOfDepression(2);

	private final AgentRepository agentRepository;
	private final ServerInfoRepository serverInfoRepository;

	@Autowired
	public Init(AgentRepository agentRepository, ServerInfoRepository serverInfoRepository) {
		this.agentRepository = agentRepository;
		this.serverInfoRepository = serverInfoRepository;
	}

	@Transactional
	@PostConstruct
	public void init() {
		Agent agent = createAgent();
		createServerInfo(agent);
		createServerInfo(agent);
		cod.i("INIT AGENTS: ", agentRepository.findAll());
		cod.i("INIT SERVER_INFOS: ", serverInfoRepository.findAll());
	}

	@Transactional
	private ServerInfo createServerInfo(Agent agent) {
		ServerInfo si = new ServerInfo();
		si.setAgent(agent);
		si.setRamTaken(3.5);
		serverInfoRepository.save(si);
		return si;
	}

	@Transactional
	private Agent createAgent() {
		String mac = "fe80::ac00:dec3:5e50:209d";
		Agent agent = agentRepository.findByMac(mac);
		if (agent == null) {
			agent = new Agent();
			agent.setMac(mac);
			agent.setName("Server");

			agentRepository.save(agent);
		}
		return agent;
	}

}