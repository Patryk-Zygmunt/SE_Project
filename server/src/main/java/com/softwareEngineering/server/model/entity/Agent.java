package com.softwareEngineering.server.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Agent {

	private Long agentId;
	private String mac;
	private String name;
//	private String agentIp;
	// private Date add_date;
	// private Set<String> excludedLogs;
//	@OneToMany(fetch = FetchType.EAGER, mappedBy = "agent")
//	@JsonManagedReference
	private Set<ServerInfo> serverInfos;

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Id
	@GeneratedValue
	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "agent")
	@JsonManagedReference
	public Set<ServerInfo> getServerInfos() {
		return serverInfos;
	}

	public void setServerInfos(Set<ServerInfo> serverInfos) {
		this.serverInfos = serverInfos;
	}

//	public String getAgentIp() {
//		return agentIp;
//	}
//
//	public void setAgentIp(String agentIp) {
//		this.agentIp = agentIp;
//	}
	// public Date getAdd_date() {
	// return add_date;
	// }
	//
	// public void setAdd_date(Date add_date) {
	// this.add_date = add_date;
	// }
	//
	
	//
	// public Set<String> getExcludedLogs() {
	// return excludedLogs;
	// }
	//
	// public void setExcludedLogs(Set<String> excludedLogs) {
	// this.excludedLogs = excludedLogs;
	// }

}
