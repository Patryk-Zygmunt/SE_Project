package com.softwareEngineering.server;

import com.softwareEngineering.server.model.entity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class TestDataCreator {
    
   public static Agent createAgent() {
       Agent agent =new Agent();
       String mac = "fe80::ac00:dec3:5e50:209d";
       agent.setMac(mac);
       agent.setName("TestServer");      
       return agent;
	}

      public static ServerInfo createServerInfo(Agent agent) {
		ServerInfo si = new ServerInfo();
		si.setAgent(agent);
		si.setInfoTime(LocalDateTime.of(LocalDate.of(1997,12,20),LocalTime.of(12,11,12,0)));
		si.setTemperature(66.6);
		si.setRam(createRam());
		si.setProcessor(createProcessor());
		si.setDiscs(createDiscs());
		si.setOperations(createOperations());
		si.setIoInterfaces(createIOInterface());
		si.setLogs(createLogs());
		return si;
	}

 public static Ram createRam() {
		Ram ram = new Ram();
		ram.setTotal(121.8);
		ram.setUsed(14.9);
		return ram;
	}

 public static Processor createProcessor() {
		Processor processor = new Processor();
		processor.setSystem(20.2);
		processor.setUnused(40.2);
		processor.setUser(4.2);
		return processor;
	}

 public static List<Disc> createDiscs() {
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

 public static List<Operation> createOperations() {
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

 public static List<IOInterface> createIOInterface() {
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

 public static List<Log> createLogs() {
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
     
}
