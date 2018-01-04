package com.softwareEngineering.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softwareEngineering.server.model.entity.Agent;
import com.softwareEngineering.server.model.entity.ServerInfo;

@Repository
public interface ServerInfoRepository extends JpaRepository<ServerInfo, Long> {

}
