package com.softwareEngineering.server.repositories;

import com.softwareEngineering.server.model.entity.ServerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.softwareEngineering.server.model.entity.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, String> {
	Agent findByMac(String mac);
	Agent findByName(String name);
	Agent findByAgentId(long id);






	//@Query("select s from Agent a join ServerInfo on a.serverInfos=")

}

