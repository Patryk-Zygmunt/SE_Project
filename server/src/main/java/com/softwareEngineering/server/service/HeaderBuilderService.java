package com.softwareEngineering.server.service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.softwareEngineering.server.model.entity.ServerInfo;

@Service
public class HeaderBuilderService {

	public HttpHeaders getServerInfoHeader(UriComponentsBuilder ucBuilder, ServerInfo serverInfo) {
		HttpHeaders headers = new HttpHeaders();

		Map<String, Object> uriVariables = new HashMap<>();
		uriVariables.put("agentId", serverInfo.getAgent().getAgentId());
		uriVariables.put("serverInfoId", serverInfo.getInfoId());

		URI uri = ucBuilder.buildAndExpand(uriVariables).toUri();

		headers.setLocation(uri);
		return headers;
	}

}
