package com.softwareEngineering.server.repositories;

import com.softwareEngineering.server.TestDataCreator;

import com.softwareEngineering.server.model.entity.Agent;
import com.softwareEngineering.server.model.entity.ServerInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) @DataJpaTest @TestPropertySource(locations = "classpath:application-integrationtest.properties") public class ServerInfoRepositoryTest {

    @Autowired private TestEntityManager entityManager;

    @Autowired private ServerInfoRepository serverInfoRepository;

    @Test public void getServerInfosByAgent_AgentIdOrderByInfoTimeTest() {
        Agent agent = TestDataCreator.createAgent();
        long agentId = (long) entityManager.persistAndGetId(agent);
        agent.setAgentId(agentId);
        ServerInfo serverInfoOlder = TestDataCreator.createServerInfo(agent);
        ServerInfo serverInfoNew = TestDataCreator.createServerInfo(agent);
        serverInfoNew.setInfoTime(LocalDateTime.of(LocalDate.of(2005, 11, 12), LocalTime.of(12, 11, 12, 0)));
        List<ServerInfo> testServerInfo = new LinkedList<>();
        testServerInfo.add(serverInfoNew);
        testServerInfo.add(serverInfoOlder);
        agent.setServerInfos(new HashSet<>(testServerInfo));
        entityManager.persist(serverInfoNew);
        entityManager.persist(serverInfoOlder);
        entityManager.flush();
        List<ServerInfo> serverInfo = serverInfoRepository
                .getServerInfosByAgent_AgentIdOrderByInfoTime((Long) entityManager.getId(agent));
        assertEquals(2, serverInfo.size());
        assertEquals(LocalDateTime.of(LocalDate.of(2005, 11, 12), LocalTime.of(12, 11, 12, 0)),
                serverInfo.get(1).getInfoTime());
    }

}
