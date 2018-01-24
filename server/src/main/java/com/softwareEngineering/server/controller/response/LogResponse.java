package com.softwareEngineering.server.controller.response;

import com.softwareEngineering.server.model.entity.Log;

/**
 * Objects of this class are mapped to send to frontend.
 *
 * Created by Linus on 24.11.2017.
 */
public class LogResponse {
    private String process;
    private String errorDesc;

    public LogResponse(Log log) {
        this.process = log.getProcess();
        this.errorDesc = log.getErrorDesc();
    }

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
}
