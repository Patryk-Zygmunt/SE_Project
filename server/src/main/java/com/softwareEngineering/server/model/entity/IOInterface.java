package com.softwareEngineering.server.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * part of server info data
 */
@Entity
public class IOInterface {
	private Long Id;
	private String name;
	private double rec;
	private double trans;

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

	public double getRec() {
		return rec;
	}

	public void setRec(double rec) {
		this.rec = rec;
	}

	public double getTrans() {
		return trans;
	}

	public void setTrans(double trans) {
		this.trans = trans;
	}

}
