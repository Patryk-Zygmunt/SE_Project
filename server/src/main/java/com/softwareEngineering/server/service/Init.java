package com.softwareEngineering.server.service;

import com.app.COD;
import com.app.CODFactory;
import com.softwareEngineering.server.model.entity.*;
import com.softwareEngineering.server.repositories.AgentRepository;
import com.softwareEngineering.server.repositories.ServerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class Init {
	private static final COD cod = CODFactory.setLevelOfDepression(2);

	private final AgentRepository agentRepository;
	private final ServerInfoRepository serverInfoRepository;

	@Autowired
	public Init(AgentRepository agentRepository, ServerInfoRepository serverInfoRepository) {
		this.agentRepository = agentRepository;
		this.serverInfoRepository = serverInfoRepository;
	}

	@Transactional
	@PostConstruct
	public void init() {
		serverInfoRepository.deleteAll();
		agentRepository.deleteAll();

		Agent agent = createAgent();
		createServerInfo(agent);
		createServerInfo(agent);
		cod.i("INIT AGENTS: ", agentRepository.findAll());
		cod.i("INIT SERVER_INFOS: ", serverInfoRepository.findAll());
	}

	@Transactional
	private ServerInfo createServerInfo(Agent agent) {
		ServerInfo si = new ServerInfo();
		si.setAgent(agent);
		si.setInfoTime(LocalDateTime.now());
		si.setTemperature(66.6);
		si.setRam(createRam());
		si.setProcessor(createProcessor());
		si.setDiscs(createDiscs());
		si.setOperations(createOperations());
		si.setIoInterfaces(createIOInterface());
		si.setLogs(createLogs());
		serverInfoRepository.save(si);
		return si;
	}

	private Ram createRam() {
		Ram ram = new Ram();
		ram.setTotal(121.8);
		ram.setUsed(14.9);
		return ram;
	}

	private Processor createProcessor() {
		Processor processor = new Processor();
		processor.setSystem(20.2);
		processor.setUnused(40.2);
		processor.setUser(4.2);
		return processor;
	}

	private List<Disc> createDiscs() {
		Disc disc1 = new Disc();
		disc1.setName("disc1");
		disc1.setTotal(800.2);
		disc1.setUsed(192.9);
		Disc disc2 = new Disc();
		disc2.setName("disc2");
		disc2.setTotal(800.2);
		disc2.setUsed(192.9);
		return Arrays.asList(disc1, disc2);
	}

	private List<Operation> createOperations() {
		Operation o1 = new Operation();
		o1.setName("o1");
		o1.setRead(80.0);
		o1.setWrite(30.0);
		Operation o2 = new Operation();
		o2.setName("o2");
		o2.setRead(20.0);
		o2.setWrite(10.0);
		return Arrays.asList(o1, o2);
	}

	private List<IOInterface> createIOInterface() {
		IOInterface o1 = new IOInterface();
		o1.setName("io1");
		o1.setRec(80.0);
		o1.setTrans(30.0);
		IOInterface o2 = new IOInterface();
		o2.setName("io2");
		o2.setRec(80.0);
		o2.setTrans(30.0);
		return Arrays.asList(o1, o2);
	}

	private List<Log> createLogs() {
		Log log1 = new Log();
//		log1.setDate(LocalDateTime.now());
		log1.setProcess("Process[666]");
		log1.setErrorDesc("Error Description");
		Log log2 = new Log();
//		log2.setDate(LocalDateTime.now());
		log2.setProcess("Process[667]");
		log2.setErrorDesc("Error Description2");
		return Arrays.asList(log1, log2);
	}

	@Transactional
	private Agent createAgent() {
		String mac = "fe80::ac00:dec3:5e50:209d";
		Agent agent = agentRepository.findByMac(mac);
		if (agent == null) {
			agent = new Agent();
			agent.setMac(mac);
			agent.setName("Server");

			agentRepository.save(agent);
		}
		return agent;
	}

}