package com.softwareEngineering.server.service;

import com.app.COD;
import com.app.CODFactory;
import com.softwareEngineering.server.model.AgentRequestInfo;
import com.softwareEngineering.server.model.entity.Agent;
import com.softwareEngineering.server.model.entity.Log;
import com.softwareEngineering.server.model.entity.ServerInfo;
import com.softwareEngineering.server.repositories.ServerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServerInfoService {

	private COD cod = CODFactory.setLevelOfDepression(2);


	ServerInfoRepository serverInfoRepository;
	AgentService agentService;

    public ServerInfoService() {
    }

    @Autowired public ServerInfoService(ServerInfoRepository serverInfoRepository, AgentService agentService,
            ServerInfoService serverInfoService) {
        this.serverInfoRepository = serverInfoRepository;
        this.agentService = agentService;
    }

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

	public List<ServerInfo> getAgentHistory(long id){
		return serverInfoRepository.getServerInfosByAgent_AgentIdOrderByInfoTime(id);
	}
	public List<ServerInfo> getAgentHistoryPage(long id, Pageable pageable){
		return serverInfoRepository.getServerInfosByAgent_AgentIdOrderByInfoTime(id,pageable);
	}

	public List<Log> getAgentLogsPage(long id, Pageable pageable) {
		return serverInfoRepository.getServerInfosByAgent_AgentIdOrderByInfoTime(id, pageable)
				.parallelStream().map(i ->
						i.getLogs()).flatMap(List::stream).collect(Collectors.toList());
	}

    public List<ServerInfo> getAgentHistoryBetweenDate(long timeStart, long timeStop, long id) {
        return serverInfoRepository.getServerInfosByInfoTimeBetweenAndAgent_AgentId(
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStart), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStop), ZoneId.systemDefault()), id);
    }
}
