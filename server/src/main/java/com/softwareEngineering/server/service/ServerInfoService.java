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

/**
 * Class responsible for operations on Server infos.
 */
@Service
public class ServerInfoService {

    private COD cod = CODFactory.setLevelOfDepression(2);

    @Autowired
    ServerInfoRepository serverInfoRepository;

    @Autowired
    AgentService agentService;

    public ServerInfoService() {
    }


    public ServerInfoService(AgentService agentService) {
        this.agentService = agentService;
    }

    /**
     * Get all server informations from all servers
     * @return all server informations from all servers
     */
    public List<ServerInfo> getServerInfos() {
        return serverInfoRepository.findAll();
    }

    /**
     * Creates new serwer informations - if agent does not exists this method creates it
     *
     * @param agentRequestInfo agent and server info informations
     *
     * @return created server informations
     */
    public ServerInfo saveServerInfo(AgentRequestInfo agentRequestInfo) {
        Agent agent = agentService.createAgentIfNotExists(agentRequestInfo);
        ServerInfo serverInfo = createNewServerInfo(agentRequestInfo, agent);
        return serverInfo;
    }


    /**
     * Get all server informations from agent
     *
     * @param id agent identificator
     *
     * @return all server informations from one server
     */
    public List<ServerInfo> getAgentHistory(long id) {
        agentService.getAgents();
        List<ServerInfo> history = serverInfoRepository.getServerInfosByAgent_AgentIdOrderByInfoTimeDesc(id);
        return history;
    }

    /**
     * Get page of server informations from agent
     *
     * @param id agent identificator
     * @param pageable page informations
     *
     * @return page of server informations from one server
     */
    public List<ServerInfo> getAgentHistoryPage(long id, Pageable pageable) {
        return serverInfoRepository.getServerInfosByAgent_AgentIdOrderByInfoTimeDesc(id, pageable);
    }

    /**
     * Get page of server logs
     *
     * @param id agent identificator
     * @param pageable page informations
     *
     * @return page of server logs from one server
     */
    public List<Log> getAgentLogsPage(long id, Pageable pageable) {
        return serverInfoRepository.getServerInfosByAgent_AgentIdOrderByInfoTimeDesc(id, pageable)
                .parallelStream().map(i ->
                        i.getLogs()).flatMap(List::stream).collect(Collectors.toList());
    }

    /**
     * Get server informations between two dates
     *
     * @param id        agent identificator
     * @param timeStart start date
     * @param timeStop  end date
     *
     * @return server informations between two dates
     */
    public List<ServerInfo> getAgentHistoryBetweenDate(long timeStart, long timeStop, long id) {
        return serverInfoRepository.getServerInfosByInfoTimeBetweenAndAgent_AgentIdOrderByInfoTimeDesc(
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStart), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStop), ZoneId.systemDefault()), id);
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
