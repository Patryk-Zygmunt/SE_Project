package com.softwareEngineering.server.model.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * part of server info data
 */
@Embeddable
public class Processor {

	// private Long Id;
	private double system;
	private double unused;
	private double user;
	//
	// @Id
	// @GeneratedValue
	// public Long getId() {
	// return Id;
	// }
	//
	// public void setId(Long id) {
	// Id = id;
	// }

	@Column(name = "processor_system")
	public double getSystem() {
		return system;
	}

	public void setSystem(double system) {
		this.system = system;
	}

	@Column(name = "processor_unused")
	public double getUnused() {
		return unused;
	}

	public void setUnused(double unused) {
		this.unused = unused;
	}

	@Column(name = "processor_user")
	public double getUser() {
		return user;
	}

	public void setUser(double user) {
		this.user = user;
	}

}
