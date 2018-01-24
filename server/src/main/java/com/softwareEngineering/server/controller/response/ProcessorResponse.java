package com.softwareEngineering.server.controller.response;

import com.softwareEngineering.server.model.entity.Processor;

/**
 * Objects of this class are mapped to send to frontend.
 *
 * Created by Linus on 24.11.2017.
 */
public class ProcessorResponse {
    private double system;
    private double unused;
    private double user;

    public ProcessorResponse(Processor processor) {
        this.system = processor.getSystem();
        this.user = processor.getUser();
        this.unused = processor.getUnused();
    }

    public double getSystem() {
        return system;
    }

    public void setSystem(double system) {
        this.system = system;
    }

    public double getUnused() {
        return unused;
    }

    public void setUnused(double unused) {
        this.unused = unused;
    }

    public double getUser() {
        return user;
    }

    public void setUser(double user) {
        this.user = user;
    }
}
