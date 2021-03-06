package com.softwareEngineering.server.controller.response;

import com.softwareEngineering.server.model.entity.Ram;

/**
 * Objects of this class are mapped to send to frontend.
 *
 * Created by Linus on 24.11.2017.
 */
public class RamResponse {
    private double total;
    private double used;

    public RamResponse(Ram ram) {
        this.total = ram.getTotal();
        this.used = ram.getUsed();
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
