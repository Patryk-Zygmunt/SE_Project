package com.softwareEngineering.server.service;

import com.softwareEngineering.server.TestDataCreator;
import com.softwareEngineering.server.model.entity.Agent;
import com.softwareEngineering.server.model.entity.Log;
import com.softwareEngineering.server.model.entity.ServerInfo;
import com.softwareEngineering.server.repositories.AgentRepository;
import com.softwareEngineering.server.repositories.ServerInfoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class) public class ServerInfoServiceTest {

    @TestConfiguration static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        @Primary
        public AgentService agentService() {
            return new AgentService(Mockito.mock(AgentRepository.class));
        }

        @Bean
        public ServerInfoService serverInfoService(AgentService agentService) {
            return new ServerInfoService(agentService);
        }
    }

    @Autowired ServerInfoService serverInfoService;

    @MockBean private ServerInfoRepository serverInfoRepository;

    @Before
    public void setUpAgentsLogsPage() {
        Agent agent = TestDataCreator.createAgent();
        agent.setAgentId(1L);
        List<ServerInfo> serverInfos = new LinkedList<>();
        serverInfos.add(TestDataCreator.createServerInfo(agent));
        Mockito.when(serverInfoRepository.getServerInfosByAgent_AgentIdOrderByInfoTimeDesc(1, new PageRequest(0, 4)))
                .thenReturn(serverInfos);
    }

    @Test
    public void getAgentLogsPage() {
        Agent agent = TestDataCreator.createAgent();
        agent.setAgentId(1L);
        List<ServerInfo> serverInfosTest = new LinkedList<>();
        serverInfosTest.add(TestDataCreator.createServerInfo(agent));
        List<Log> logs = serverInfoService.getAgentLogsPage(1, new PageRequest(0, 4));
        assertEquals(logs.get(0), serverInfosTest.get(0).getLogs().get(0));
        assertEquals(logs.get(1), serverInfosTest.get(0).getLogs().get(1));


    }

}
