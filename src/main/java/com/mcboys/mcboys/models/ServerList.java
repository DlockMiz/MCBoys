package com.mcboys.mcboys.models;

public class ServerList {

    private String[] list;
    private String errorMessage;

    public void setList(String[] list) {
        this.list = list;
    }
    public String[] getList() {
        return list;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
