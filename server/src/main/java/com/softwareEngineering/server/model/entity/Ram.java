package com.softwareEngineering.server.model.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Embeddable
public class Ram {
//	private Long Id;
	private double total;
	private double used;

//	@Id
//	@GeneratedValue
//	public Long getId() {
//		return Id;
//	}
//
//	public void setId(Long id) {
//		Id = id;
//	}
	@Column(name = "ram_total")
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Column(name = "ram_used")
	public double getUsed() {
		return used;
	}

	public void setUsed(double used) {
		this.used = used;
	}

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Ram ram = (Ram) o;

        if (Double.compare(ram.total, total) != 0)
            return false;
        return Double.compare(ram.used, used) == 0;
    }

    @Override public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(total);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(used);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
