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
		serverInfo.setRamTaken(agentRequestInfo.getRamTaken());
		// TODO set other info
		serverInfo.setAgent(agent);

		cod.i("CREATE NEW SERVER INFO: ", serverInfo);
		serverInfoRepository.save(serverInfo);
		return serverInfo;
	}


}
