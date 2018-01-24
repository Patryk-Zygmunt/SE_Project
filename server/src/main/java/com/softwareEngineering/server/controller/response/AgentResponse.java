package com.softwareEngineering.server.controller.response;

import com.softwareEngineering.server.model.entity.Agent;
import com.softwareEngineering.server.model.entity.ServerInfo;

/**
 * Objects of this class are mapped to send to frontend.
 *
 * Created by Linus on 24.11.2017.
 *
 */

    public class AgentResponse {
        private Long agentId;
        private String mac;
        private String name;
    private ServerInfoResponse serverInfoResponse;

    public AgentResponse(Agent agent, ServerInfo serverInfo){
        this.agentId = agent.getAgentId();
        this.mac = agent.getMac();
        this.name = agent.getName();
        this.serverInfoResponse = new ServerInfoResponse(serverInfo);
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServerInfoResponse getServerInfoResponse() {
        return serverInfoResponse;
    }

    public void setServerInfoResponse(ServerInfoResponse serverInfoResponse) {
        this.serverInfoResponse = serverInfoResponse;
    }


    
    
}
