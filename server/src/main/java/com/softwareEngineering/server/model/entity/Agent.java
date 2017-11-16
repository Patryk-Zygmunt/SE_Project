package com.softwareEngineering.server.model.entity;

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

	private String mac;
	private Long agentId;
	private String name;
	// private Date add_date;
	// private String agentIp;
	// private Set<String> excludedLogs;

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
	public Set<ServerInfo> getServerInfos() {
		return serverInfos;
	}

	public void setServerInfos(Set<ServerInfo> serverInfos) {
		this.serverInfos = serverInfos;
	}

	// public Date getAdd_date() {
	// return add_date;
	// }
	//
	// public void setAdd_date(Date add_date) {
	// this.add_date = add_date;
	// }
	//
	// public String getAgentIp() {
	// return agentIp;
	// }
	//
	// public void setAgentIp(String agentIp) {
	// this.agentIp = agentIp;
	// }
	//
	// public Set<String> getExcludedLogs() {
	// return excludedLogs;
	// }
	//
	// public void setExcludedLogs(Set<String> excludedLogs) {
	// this.excludedLogs = excludedLogs;
	// }

}
