package com.softwareEngineering.server.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;

@Entity
public class ServerInfo {

	private Long InfoId;
	private Double ramTaken;
	private Agent agent;
	private LocalDateTime infoTime;

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
	@JsonBackReference
	public Agent getAgent() {
		return agent;
	}

	public LocalDateTime getInfoTime() {
		return infoTime;
	}

	public void setInfoTime(LocalDateTime infoTime) {
		this.infoTime = infoTime;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

}
