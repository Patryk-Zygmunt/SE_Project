package com.softwareEngineering.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.COD;
import com.app.CODFactory;
import com.softwareEngineering.server.model.AgentRequestInfo;
import com.softwareEngineering.server.model.entity.Agent;
import com.softwareEngineering.server.model.entity.ServerInfo;
import com.softwareEngineering.server.repositories.AgentRepository;
import com.softwareEngineering.server.repositories.ServerInfoRepository;

import java.util.List;

@Service
public class AgentService {
	private COD cod = CODFactory.setLevelOfDepression(2);

	@Autowired
	AgentRepository agentRepository;

	public List<Agent> getAgents() {
		return agentRepository.findAll();
	}

	public Agent saveAgentIfExists(AgentRequestInfo agentRequestInfo) {
		Agent agent = agentRepository.findByMac(agentRequestInfo.getMac());
		if (agent == null) {
			agent = createNewAgent(agentRequestInfo);
		}
		return agent;
	}

	private Agent createNewAgent(AgentRequestInfo agentRequestInfo) {
		Agent agent = new Agent();
		agent.setMac(agentRequestInfo.getMac());
		agent.setName(agentRequestInfo.getName());
		// TODO set other info
		cod.i("CREATE NEW AGENT: ", agent);
		agentRepository.save(agent);
		return agent;
	}

}
