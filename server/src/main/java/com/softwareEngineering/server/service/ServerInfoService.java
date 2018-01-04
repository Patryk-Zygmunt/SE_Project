package com.softwareEngineering.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.COD;
import com.app.CODFactory;
import com.softwareEngineering.server.model.AgentRequestInfo;
import com.softwareEngineering.server.model.entity.Agent;
import com.softwareEngineering.server.model.entity.Disc;
import com.softwareEngineering.server.model.entity.IOInterface;
import com.softwareEngineering.server.model.entity.Log;
import com.softwareEngineering.server.model.entity.Operation;
import com.softwareEngineering.server.model.entity.Processor;
import com.softwareEngineering.server.model.entity.Ram;
import com.softwareEngineering.server.model.entity.ServerInfo;
import com.softwareEngineering.server.repositories.AgentRepository;
import com.softwareEngineering.server.repositories.ServerInfoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServerInfoService {

	private COD cod = CODFactory.setLevelOfDepression(2);

	@Autowired
	ServerInfoRepository serverInfoRepository;

	@Autowired
	AgentService agentService;

	public List<ServerInfo> getServerInfos() {
		return serverInfoRepository.findAll();
	}

	public ServerInfo saveServerInfo(AgentRequestInfo agentRequestInfo) {
		Agent agent = agentService.saveAgentIfExists(agentRequestInfo);
		ServerInfo serverInfo = createNewServerInfo(agentRequestInfo, agent);
		return serverInfo;
	}

	private ServerInfo createNewServerInfo(AgentRequestInfo agentRequestInfo, Agent agent) {
		ServerInfo serverInfo = new ServerInfo();
		serverInfo.setAgent(agent);
		serverInfo.setInfoTime(LocalDateTime.now());
		serverInfo.setTemperature(agentRequestInfo.getTemperature());
		serverInfo.setRam(agentRequestInfo.getRam());
		serverInfo.setProcessor(agentRequestInfo.getProcessor());
		serverInfo.setDiscs(agentRequestInfo.getDiscs());
		serverInfo.setOperations(agentRequestInfo.getOperations());
		serverInfo.setIoInterfaces(agentRequestInfo.getIoInterfaces());
		serverInfo.setLogs(agentRequestInfo.getLogs());
		serverInfoRepository.save(serverInfo);
		cod.i("CREATED NEW SERVER INFO: ", serverInfo);
		return serverInfo;
	}

}
