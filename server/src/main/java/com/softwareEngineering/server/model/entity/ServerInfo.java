package com.softwareEngineering.server.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ServerInfo {

	private Long InfoId;
	private Double ramTaken;
	private Agent agent;

	@Id
	@GeneratedValue
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
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "agent_id")
	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

}
