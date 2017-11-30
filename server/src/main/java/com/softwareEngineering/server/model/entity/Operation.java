package com.softwareEngineering.server.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Operation {
	private Long Id;
	private String name;
	private double read;
	private double write;

	@Id
	@GeneratedValue
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getRead() {
		return read;
	}

	public void setRead(double read) {
		this.read = read;
	}

	public double getWrite() {
		return write;
	}

	public void setWrite(double write) {
		this.write = write;
	}

}
