package com.bhancock.bisc.emulator.models;

import java.io.Serializable;

public class Register implements Serializable {

    private String registerName;
    private int registerValue;
    private int registerSize;

    public Register(int registerSize) {
        this.registerSize = registerSize;
    }


    public String getRegisterName() {
        return registerName;
    }

    public int getRegisterValue() {
        return registerValue;
    }

    public int getRegisterSize() {
        return registerSize;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    public void setRegisterValue(int registerValue) {
        this.registerValue = registerValue;
    }

    public void setRegisterSize(int registerSize) {
        this.registerSize = registerSize;
    }
}
