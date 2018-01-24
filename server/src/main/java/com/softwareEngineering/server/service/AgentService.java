package com.softwareEngineering.server.service;

import com.app.COD;
import com.app.CODFactory;
import com.softwareEngineering.server.model.AgentRequestInfo;
import com.softwareEngineering.server.model.entity.Agent;
import com.softwareEngineering.server.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class responsible for operations on Agents.
 */
@Service
public class AgentService {
    private COD cod = CODFactory.setLevelOfDepression(2);

    //@Autowired
    AgentRepository agentRepository;

    public AgentService() {

    }

    @Autowired
    public AgentService(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    public List<Agent> getAgents() {
        return agentRepository.findAll();
    }

    /**
     * Creates new agent if agent with mac number does not exists
     *
     * @param agentRequestInfo contains agent informations (mac number)
     * @return created agent or existing one
     */
    public Agent createAgentIfNotExists(AgentRequestInfo agentRequestInfo) {
        Agent agent = agentRepository.findByMac(agentRequestInfo.getMac());
        if (agent == null) {
            agent = createNewAgent(agentRequestInfo);
        }
        return agent;
    }

    /**
     * Creates new agent
     *
     * @param agentRequestInfo contains agent informations
     * @return created agent
     */
    private Agent createNewAgent(AgentRequestInfo agentRequestInfo) {
        Agent agent = new Agent();
        agent.setMac(agentRequestInfo.getMac());
        agent.setName(agentRequestInfo.getName());
        agentRepository.save(agent);
//		cod.i("CREATED NEW AGENT: ", agent);
        return agent;
    }

    /**
     * get agent by id
     *
     * @param id agent id
     * @return agent or null if not exists
     */
    public Agent findByAgentId(int id) {
        return agentRepository.findByAgentId(id);
    }
}
