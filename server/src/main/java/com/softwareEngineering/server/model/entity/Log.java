package com.softwareEngineering.server.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * part of server info data (logs)
 */
@Entity
public class Log {
	private Long Id;
//	private LocalDateTime date;
	private String process;
	private String errorDesc;

	@Id
	@GeneratedValue
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

//	public LocalDateTime getDate() {
//		return date;
//	}
//
//	public void setDate(LocalDateTime date) {
//		this.date = date;
//	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Log log = (Log) o;

		if (process != null ? !process.equals(log.process) : log.process != null) return false;
		return errorDesc != null ? errorDesc.equals(log.errorDesc) : log.errorDesc == null;
	}

	@Override
	public int hashCode() {
		int result = process != null ? process.hashCode() : 0;
		result = 31 * result + (errorDesc != null ? errorDesc.hashCode() : 0);
		return result;
	}
}
