package com.softwareEngineering.server.model;

import java.util.List;

import com.softwareEngineering.server.model.entity.Disc;
import com.softwareEngineering.server.model.entity.IOInterface;
import com.softwareEngineering.server.model.entity.Log;
import com.softwareEngineering.server.model.entity.Operation;
import com.softwareEngineering.server.model.entity.Processor;
import com.softwareEngineering.server.model.entity.Ram;



public class AgentRequestInfo {
	private String mac;
	private String name;
	private double temperature;
	private Ram ram;
	private Processor processor;
	private List<Disc> discs;
	private List<Operation> operations;
	private List<IOInterface> ioInterfaces;
	private List<Log> logs;

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public Ram getRam() {
		return ram;
	}

	public void setRam(Ram ram) {
		this.ram = ram;
	}

	public Processor getProcessor() {
		return processor;
	}

	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	public List<Disc> getDiscs() {
		return discs;
	}

	public void setDiscs(List<Disc> discs) {
		this.discs = discs;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public List<IOInterface> getIoInterfaces() {
		return ioInterfaces;
	}

	public void setIoInterfaces(List<IOInterface> ioInterfaces) {
		this.ioInterfaces = ioInterfaces;
	}

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}
}