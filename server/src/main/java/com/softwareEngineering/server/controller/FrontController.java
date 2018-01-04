package com.softwareEngineering.server.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.COD;
import com.app.CODFactory;
import com.softwareEngineering.server.controller.response.AgentResponse;
import com.softwareEngineering.server.controller.response.AgentShortResponse;
import com.softwareEngineering.server.controller.response.ServerInfoResponse;
import com.softwareEngineering.server.model.entity.Agent;
import com.softwareEngineering.server.repositories.AgentRepository;
import com.softwareEngineering.server.repositories.ServerInfoRepository;
import com.softwareEngineering.server.service.AgentService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class FrontController {

	private final COD cod = CODFactory.setLevelOfDepression(2);

	private AgentService agentService;
	private final AgentRepository agentRepository;
	private final ServerInfoRepository serverInfoRepository;

	@Autowired
	public FrontController(AgentService agentService, AgentRepository agentRepository,
			ServerInfoRepository serverInfoRepository) {
		this.agentService = agentService;
		this.agentRepository = agentRepository;
		this.serverInfoRepository = serverInfoRepository;
	}

	@RequestMapping(value = "/api/front/agents", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<AgentShortResponse> getAgents() {
//		cod.i("AGENTS: ", agentService.getAgents());//uncomment to show agents :)
		return agentService.getAgents().stream().map(AgentShortResponse::new).collect(Collectors.toList());
	}

	@RequestMapping(value = "/api/front/agent/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public AgentResponse getAgentInfo(@PathVariable(value = "id") int id) {
		Agent agent = agentRepository.findByAgentId(id);
		return new AgentResponse(agent, agent.getServerInfos().parallelStream().findFirst().get());
	}

	@RequestMapping(value = "/api/front/history/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<ServerInfoResponse> getAgentHistory(@PathVariable(value = "id") int id) {
		Agent agent = agentRepository.findByAgentId(id);
		return agent.getServerInfos().stream().map(ServerInfoResponse::new).collect(Collectors.toList());
	}

}
