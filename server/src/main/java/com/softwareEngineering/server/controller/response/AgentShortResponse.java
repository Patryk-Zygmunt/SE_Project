package com.softwareEngineering.server.controller.response;

import com.softwareEngineering.server.model.entity.Agent;
import com.softwareEngineering.server.model.entity.ServerInfo;

/**
 * Created by Linus on 24.11.2017.
 */
public class AgentShortResponse {
    private Long agentId;
    private String mac;
    private String name;

    public AgentShortResponse(Agent agent){
        this.agentId = agent.getAgentId();
        this.mac = agent.getMac();
        this.name = agent.getName();
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

    @Override
    public String toString() {
        return "AgentShortResponse{" +
                "agentId=" + agentId +
                ", mac='" + mac + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
