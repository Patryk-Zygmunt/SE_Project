package com.softwareEngineering.server.controller;

import com.app.COD;
import com.app.CODFactory;
import com.softwareEngineering.server.controller.response.AgentResponse;
import com.softwareEngineering.server.controller.response.AgentShortResponse;
import com.softwareEngineering.server.controller.response.ServerInfoResponse;
import com.softwareEngineering.server.model.entity.Agent;
import com.softwareEngineering.server.repositories.AgentRepository;
import com.softwareEngineering.server.repositories.ServerInfoRepository;
import com.softwareEngineering.server.service.AgentService;
import com.softwareEngineering.server.service.ServerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class FrontController {

	private final COD cod = CODFactory.setLevelOfDepression(2);

	private AgentService agentService;
	private final AgentRepository agentRepository;
	private ServerInfoService serverInfoService;

	@Autowired
	public FrontController(AgentService agentService, AgentRepository agentRepository, ServerInfoRepository serverInfoRepository, ServerInfoService serverInfoService) {
		this.agentService = agentService;
		this.agentRepository = agentRepository;
		this.serverInfoService = serverInfoService;
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
	public List<ServerInfoResponse> getAgentHistory(@PathVariable(value = "id") long id) {
		return serverInfoService.getAgentHistory(id).stream().map(ServerInfoResponse::new).collect(Collectors.toList());
	}

	@RequestMapping(value = "/api/front/history/{id}/{page}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<ServerInfoResponse> getAgentHistoryPage(
			@PathVariable(value = "id") int id,@PathVariable(value = "page") int page) {
		return serverInfoService.getAgentHistoryPage(id,new PageRequest(page, 3)).stream().map(ServerInfoResponse::new).collect(Collectors.toList());
	}
	@RequestMapping(value = "/api/front/history/date/{id}/{timestart}/{timestop}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<ServerInfoResponse> getAgentHistoryByDate(@PathVariable(value = "id") long id,
														  @PathVariable(value = "timestart") long timeStart, @PathVariable(value = "timestop") long timeStop) {
		return serverInfoService.getAgentHistoryBetweenDate(timeStart, timeStop, id).stream().map(ServerInfoResponse::new).collect(Collectors.toList());
	}
}
