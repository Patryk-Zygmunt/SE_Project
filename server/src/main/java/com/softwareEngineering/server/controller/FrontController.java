package com.softwareEngineering.server.controller;

import com.app.COD;
import com.app.CODFactory;
import com.softwareEngineering.server.controller.response.AgentResponse;
import com.softwareEngineering.server.controller.response.AgentShortResponse;
import com.softwareEngineering.server.controller.response.LogResponse;
import com.softwareEngineering.server.controller.response.ServerInfoResponse;
import com.softwareEngineering.server.model.entity.Agent;
import com.softwareEngineering.server.service.AgentService;
import com.softwareEngineering.server.service.ServerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller responsible for frontend requests
 */

@CrossOrigin(origins = "*")
@RestController
public class FrontController {

    private final COD cod = CODFactory.setLevelOfDepression(2);

    private AgentService agentService;
    private ServerInfoService serverInfoService;

    @Autowired
    public FrontController(AgentService agentService, ServerInfoService serverInfoService) {
        this.agentService = agentService;
        this.serverInfoService = serverInfoService;
    }

    /**
     * Get all agents informations
     *
     * @return agents informations
     */
    @RequestMapping(value = "/api/front/agents", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<AgentShortResponse> getAgents() {
        return agentService.getAgents().stream().map(AgentShortResponse::new).collect(Collectors.toList());
    }

    /**
     * Get agent informations
     *
     * @param id agent id
     * @return agent informations
     */
    @RequestMapping(value = "/api/front/agent/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AgentResponse getAgentInfo(@PathVariable(value = "id") int id) {
        Agent agent = agentService.findByAgentId(id);
        return new AgentResponse(agent, agent.getServerInfos().parallelStream().findFirst().get());
    }

    /**
     * Get server informations
     *
     * @param id agent id
     * @return all server informations from agent
     */
    @RequestMapping(value = "/api/front/history/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ServerInfoResponse> getAgentHistory(@PathVariable(value = "id") long id) {

        return serverInfoService.getAgentHistory(id).stream().map(ServerInfoResponse::new).collect(Collectors.toList());
    }

    /**
     * Get server informations
     *
     * @param id   agent id
     * @param page page number
     * @return page of server informations from agent
     */
    @RequestMapping(value = "/api/front/history/{id}/{page}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ServerInfoResponse> getAgentHistoryPage(
            @PathVariable(value = "id") int id, @PathVariable(value = "page") int page) {
        return serverInfoService.getAgentHistoryPage(id, new PageRequest(page, 4)).stream().map(ServerInfoResponse::new).collect(Collectors.toList());
    }

    /**
     * Get server logs
     *
     * @param id   agent id
     * @param page page number
     * @return page of server logs from agent
     */
    @RequestMapping(value = "/api/front/logs/{id}/{page}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<LogResponse> getAgentLogsPage(
            @PathVariable(value = "id") int id, @PathVariable(value = "page") int page) {
        return serverInfoService.getAgentLogsPage(id, new PageRequest(page, 4)).stream().map(LogResponse::new).collect(Collectors.toList());
    }

    /**
     * Get server informations between two dates
     *
     * @param id        agent id
     * @param timeStart from date
     * @param timeStop  to date
     * @return server informations between two dates
     */
    @RequestMapping(value = "/api/front/history/date/{id}/{timestart}/{timestop}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ServerInfoResponse> getAgentHistoryByDate(@PathVariable(value = "id") long id,
                                                          @PathVariable(value = "timestart") long timeStart, @PathVariable(value = "timestop") long timeStop) {
        return serverInfoService.getAgentHistoryBetweenDate(timeStart, timeStop, id).stream().map(ServerInfoResponse::new).collect(Collectors.toList());
    }
}
