package com.softwareEngineering.server.controller.response;

import com.softwareEngineering.server.model.entity.Agent;
import com.softwareEngineering.server.model.entity.ServerInfo;

import java.time.LocalDateTime;

/**
 * Created by Linus on 24.11.2017.
 */
public class ServerInfoResponse {

    private Long InfoId;
    private Double ramTaken;
    private Agent agent;
    private LocalDateTime infoTime;

    public Long getInfoId() {
        return InfoId;
    }

    public void setInfoId(Long infoId) {
        InfoId = infoId;
    }

    public Double getRamTaken() {
        return ramTaken;
    }

    public void setRamTaken(Double ramTaken) {
        this.ramTaken = ramTaken;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public LocalDateTime getInfoTime() {
        return infoTime;
    }

    public void setInfoTime(LocalDateTime infoTime) {
        this.infoTime = infoTime;
    }

    public ServerInfoResponse(ServerInfo serverInfo) {

        this.infoTime = serverInfo.getInfoTime();
        this.ramTaken = serverInfo.getRamTaken();
        this.InfoId =serverInfo.getInfoId();

    }
}
