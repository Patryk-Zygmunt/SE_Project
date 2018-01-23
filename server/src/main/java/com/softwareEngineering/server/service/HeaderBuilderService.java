package com.softwareEngineering.server.service;

import com.softwareEngineering.server.model.entity.ServerInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
/**
 * Creates headers for responses
 *
 * @author Daniel Stefanik
 *
 */

@Service
public class HeaderBuilderService {
	/**
	 *
	 * @param ucBuilder UriComponentsBuilder from request
	 * @param serverInfo recived server information
	 * @return heders for server information response
	 */
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
