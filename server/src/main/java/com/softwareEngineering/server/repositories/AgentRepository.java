package com.softwareEngineering.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softwareEngineering.server.model.entity.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, String> {
	Agent findByMac(String mac);

	Agent findByName(String name);
}
