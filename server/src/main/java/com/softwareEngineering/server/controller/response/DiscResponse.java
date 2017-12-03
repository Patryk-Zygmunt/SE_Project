package com.softwareEngineering.server.controller.response;

import com.softwareEngineering.server.model.entity.Disc;

/**
 * Created by Linus on 30.11.2017.
 */
public class DiscResponse {
    private String name;
    private double total;
    private double used;

    public DiscResponse(Disc disc) {
        this.name = disc.getName();
        this.total = disc.getTotal();
        this.used = disc.getUsed();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getUsed() {
        return used;
    }

    public void setUsed(double used) {
        this.used = used;
    }
}
