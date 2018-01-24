package com.softwareEngineering.server.controller;

import com.app.COD;
import com.app.CODFactory;
import com.softwareEngineering.server.model.AgentRequestInfo;
import com.softwareEngineering.server.model.entity.ServerInfo;
import com.softwareEngineering.server.service.HeaderBuilderService;
import com.softwareEngineering.server.service.ServerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * Controller responsible for agent requests
 */

@Controller
@RequestMapping("/api/agent")
public class AgentController {
	private final COD cod = CODFactory.setLevelOfDepression(2);

    @Autowired
    public AgentController(ServerInfoService serverInfoService, HeaderBuilderService headerBuilderService) {
        this.serverInfoService = serverInfoService;
        this.headerBuilderService = headerBuilderService;
    }

	ServerInfoService serverInfoService;


    HeaderBuilderService headerBuilderService;

	/**
	 * Controller responsible for agent requests
	 * @param agentRequestInfo all informations of server and agent
	 * @param ucBuilder Creates headers for responses
	 *
	 * @return response with headers
	 */
	@RequestMapping(value = "/addInfo", method = RequestMethod.POST)
	public ResponseEntity<?> addInfo(@RequestBody AgentRequestInfo agentRequestInfo, UriComponentsBuilder ucBuilder) {
		cod.i(".../api/agent/addInfo {AGENT_REQUEST_INFO}: ", agentRequestInfo);
		ServerInfo serverInfo = serverInfoService.saveServerInfo(agentRequestInfo);
		HttpHeaders headers = headerBuilderService.getServerInfoHeader(ucBuilder, serverInfo);
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

}