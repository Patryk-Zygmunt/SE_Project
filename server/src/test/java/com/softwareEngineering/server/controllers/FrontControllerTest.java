package com.softwareEngineering.server.controllers;

import com.softwareEngineering.server.TestDataCreator;
import com.softwareEngineering.server.controller.FrontController;
import com.softwareEngineering.server.model.entity.Agent;
import com.softwareEngineering.server.model.entity.ServerInfo;
import com.softwareEngineering.server.service.AgentService;
import com.softwareEngineering.server.service.ServerInfoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.LinkedList;
import java.util.List;


import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) @WebMvcTest(FrontController.class) public class FrontControllerTest {
    @Autowired private MockMvc mvc;

    @MockBean private ServerInfoService serverInfoService;
    @MockBean private AgentService agentService;

    @Before public void setUpHistory() {
        Agent agent = TestDataCreator.createAgent();
        agent.setAgentId(1L);
        List<ServerInfo> serverInfos = new LinkedList<>();
        serverInfos.add(TestDataCreator.createServerInfo(agent));
        given(serverInfoService.getAgentHistory(1)).willReturn(serverInfos);
    }

    @Test public void getAgentHistoryTest() throws Exception {
        mvc.perform(get("/api/front/history/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())               
                .andExpect(jsonPath("$[0].temperature").value(66.6))
               .andExpect( jsonPath("$", hasSize(1)))             ;

    }

}
