package com.softwareEngineering.server.controller;


import com.softwareEngineering.server.controller.response.AgentResponse;
import com.softwareEngineering.server.controller.response.AgentShortResponse;
import com.softwareEngineering.server.controller.response.ServerInfoResponse;
import com.softwareEngineering.server.model.entity.Agent;
import com.softwareEngineering.server.repositories.AgentRepository;
import com.softwareEngineering.server.repositories.ServerInfoRepository;
import com.softwareEngineering.server.service.AgentService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.sun.corba.se.spi.activation.IIOP_CLEAR_TEXT.value;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class FrontController {

    private AgentService agentService  ;
    private final AgentRepository agentRepository;
    private final ServerInfoRepository serverInfoRepository;

    @Autowired
    public FrontController(AgentService agentService, AgentRepository agentRepository, ServerInfoRepository serverInfoRepository) {
        this.agentService = agentService;
        this.agentRepository = agentRepository;
        this.serverInfoRepository = serverInfoRepository;
    }


    @RequestMapping(value = "/api/front/agents", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<AgentShortResponse> getAgents(){
        System.out.print( agentService.getAgents()
                .stream().map(AgentShortResponse::new).collect(Collectors.toList()));
        return  agentService.getAgents()
                .stream().map(AgentShortResponse::new).collect(Collectors.toList());
    }


    @RequestMapping(value = "/api/front/agent/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AgentResponse getAgentInfo(@PathVariable(value="id") int id){
        Agent agent = agentRepository.findByAgentId(id);
            return new AgentResponse(agent,agent.getServerInfos().parallelStream().findFirst().get());
    }

    @RequestMapping(value = "/api/front/history/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ServerInfoResponse> getAgentHistory(@PathVariable(value="id") int id){
        Agent agent = agentRepository.findByAgentId(id);
        return agent.getServerInfos()
                .stream().map(ServerInfoResponse::new).collect(Collectors.toList());
    }




}
