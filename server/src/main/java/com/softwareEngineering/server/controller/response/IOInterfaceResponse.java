package com.softwareEngineering.server.controller.response;

import com.softwareEngineering.server.model.entity.IOInterface;

/**
 * Created by Linus on 30.11.2017.
 */
public class IOInterfaceResponse {
    private String name;
    private double rec;
    private double trans;

    public IOInterfaceResponse(IOInterface io) {
        this.name = io.getName();
        this.rec = io.getRec();
        this.trans = io.getTrans();
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
