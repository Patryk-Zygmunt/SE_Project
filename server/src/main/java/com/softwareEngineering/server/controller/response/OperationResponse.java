package com.softwareEngineering.server.controller.response;

import com.softwareEngineering.server.model.entity.Operation;

/**
 * Objects of this class are mapped to send to frontend.
 *
 * Created by Linus on 24.11.2017.
 */
public class OperationResponse {
    private String name;
    private double read;
    private double write;

    public OperationResponse(Operation operation) {
        this.name = operation.getName();
        this.read = operation.getRead();
        this.write = operation.getWrite();
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
