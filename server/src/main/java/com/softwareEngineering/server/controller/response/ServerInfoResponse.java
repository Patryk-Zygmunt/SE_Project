package com.softwareEngineering.server.controller.response;

import com.softwareEngineering.server.model.entity.Agent;
import com.softwareEngineering.server.model.entity.Disc;
import com.softwareEngineering.server.model.entity.IOInterface;
import com.softwareEngineering.server.model.entity.Log;
import com.softwareEngineering.server.model.entity.Operation;
import com.softwareEngineering.server.model.entity.Processor;
import com.softwareEngineering.server.model.entity.Ram;
import com.softwareEngineering.server.model.entity.ServerInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Linus on 24.11.2017.
 */
public class ServerInfoResponse {

	private Long InfoId;
	private String infoTime;
	private String mac;
	private String name;
	private double temperature;
	private RamResponse ram;
	private ProcessorResponse processor;
	private List<DiscResponse> discs;
	private List<OperationResponse> operations;
	private List<IOInterfaceResponse> ioInterfaces;
	private List<LogResponse> logs;

	public ServerInfoResponse(ServerInfo serverInfo) {
		this.InfoId = serverInfo.getInfoId();
		this.infoTime = serverInfo.getInfoTime().toString();
		this.temperature = serverInfo.getTemperature();
		this.ram =  new RamResponse(serverInfo.getRam());
		this.processor = new ProcessorResponse(serverInfo.getProcessor());
		this.discs = serverInfo.getDiscs().parallelStream().map(DiscResponse::new).collect(Collectors.toList());
		this.operations = serverInfo.getOperations().parallelStream().map(OperationResponse::new).collect(Collectors.toList());
		this.ioInterfaces = serverInfo.getIoInterfaces().parallelStream().map(IOInterfaceResponse::new).collect(Collectors.toList());
		this.logs = serverInfo.getLogs().parallelStream().map(LogResponse::new).collect(Collectors.toList());
	}

	public Long getInfoId() {
		return InfoId;
	}

	public void setInfoId(Long infoId) {
		InfoId = infoId;
	}

	public String getInfoTime() {
		return infoTime;
	}

	public void setInfoTime(String infoTime) {
		this.infoTime = infoTime;
	}

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

	public RamResponse getRam() {
		return ram;
	}

	public void setRam(RamResponse ram) {
		this.ram = ram;
	}

	public ProcessorResponse getProcessor() {
		return processor;
	}

	public void setProcessor(ProcessorResponse processor) {
		this.processor = processor;
	}

	public List<DiscResponse> getDiscs() {
		return discs;
	}

	public void setDiscs(List<DiscResponse> discs) {
		this.discs = discs;
	}

	public List<OperationResponse> getOperations() {
		return operations;
	}

	public void setOperations(List<OperationResponse> operations) {
		this.operations = operations;
	}

	public List<IOInterfaceResponse> getIoInterfaces() {
		return ioInterfaces;
	}

	public void setIoInterfaces(List<IOInterfaceResponse> ioInterfaces) {
		this.ioInterfaces = ioInterfaces;
	}

	public List<LogResponse> getLogs() {
		return logs;
	}

	public void setLogs(List<LogResponse> logs) {
		this.logs = logs;
	}
}
