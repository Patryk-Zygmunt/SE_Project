package com.softwareEngineering.server.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ServerInfo {

	private Long InfoId;
	private Agent agent;
	private LocalDateTime infoTime;
	private double temperature;
	private Ram ram;
	private Processor processor;
	private List<Disc> discs;
	private List<Operation> operations;
	private List<IOInterface> ioInterfaces;
	private List<Log> logs;

	@Id
	@GeneratedValue
	public Long getInfoId() {
		return InfoId;
	}

	public void setInfoId(Long infoId) {
		InfoId = infoId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "agent_id")
	@JsonBackReference
	public Agent getAgent() {
		return agent;
	}

	public LocalDateTime getInfoTime() {
		return infoTime;
	}

	public void setInfoTime(LocalDateTime infoTime) {
		this.infoTime = infoTime;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderColumn
	// @OneToMany(cascade = CascadeType.ALL)
	// @Fetch(FetchMode.SELECT)
	@ElementCollection
	public List<Disc> getDiscs() {
		return discs;
	}

	public void setDiscs(List<Disc> discs) {
		this.discs = discs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderColumn
	// @OneToMany(cascade = CascadeType.ALL)
	// @Fetch(FetchMode.SELECT)
	@ElementCollection
	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderColumn
	// @OneToMany(cascade = CascadeType.ALL)
	// @Fetch(FetchMode.SELECT)
	@ElementCollection
	public List<IOInterface> getIoInterfaces() {
		return ioInterfaces;
	}

	public void setIoInterfaces(List<IOInterface> ioInterfaces) {
		this.ioInterfaces = ioInterfaces;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderColumn
	// @OneToMany(cascade = CascadeType.ALL)
	// @Fetch(FetchMode.SELECT)
	@ElementCollection
	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

    public ServerInfo() {
    }

    public ServerInfo(Long infoId, Agent agent, LocalDateTime infoTime, double temperature, Ram ram,
            Processor processor, List<Disc> discs, List<Operation> operations, List<IOInterface> ioInterfaces,
            List<Log> logs) {
        InfoId = infoId;
        this.agent = agent;
        this.infoTime = infoTime;
        this.temperature = temperature;
        this.ram = ram;
        this.processor = processor;
        this.discs = discs;
        this.operations = operations;
        this.ioInterfaces = ioInterfaces;
        this.logs = logs;
    }

    @Override public String toString() {
        return "ServerInfo{" + "InfoId=" + InfoId + ", agent=" + agent + ", infoTime=" + infoTime + ", temperature="
                + temperature + ", ram=" + ram + ", processor=" + processor + ", discs=" + discs + ", operations="
                + operations + ", ioInterfaces=" + ioInterfaces + ", logs=" + logs + '}';
    }
}
