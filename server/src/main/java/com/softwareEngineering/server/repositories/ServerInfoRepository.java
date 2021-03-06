package com.softwareEngineering.server.repositories;

import com.softwareEngineering.server.model.entity.ServerInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ServerInfoRepository extends JpaRepository<ServerInfo, Long> {


    List<ServerInfo> getServerInfosByAgent_AgentIdOrderByInfoTimeDesc(@Param("id") long id);

    List<ServerInfo> getServerInfosByAgent_AgentIdOrderByInfoTimeDesc(@Param("id") long id, Pageable pageable);

    ServerInfo findFirstByAgent_AgentIdOrderByInfoTimeDesc(@Param("id") long id);

    List<ServerInfo> getServerInfosByInfoTimeBetweenAndAgent_AgentIdOrderByInfoTimeDesc(LocalDateTime start, LocalDateTime stop, long agent_id);


}
