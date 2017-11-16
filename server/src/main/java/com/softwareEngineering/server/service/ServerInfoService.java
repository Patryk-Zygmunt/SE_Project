package com.softwareEngineering.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softwareEngineering.server.model.entity.Agent;
import com.softwareEngineering.server.model.entity.ServerInfo;
import com.softwareEngineering.server.repositories.AgentRepository;
import com.softwareEngineering.server.repositories.ServerInfoRepository;

import java.util.List;

@Service
public class ServerInfoService {

	@Autowired
	ServerInfoRepository  serverInfoRepository;

	public List<ServerInfo> getServerInfos() {
		return serverInfoRepository.findAll();
	}

}
