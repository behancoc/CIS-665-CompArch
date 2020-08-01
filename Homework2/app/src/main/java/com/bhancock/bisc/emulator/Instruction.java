package com.bhancock.bisc.emulator;

public class Instruction {

    public String instructionName;

    public Instruction() {

    }

    public Instruction(String name) {
        instructionName = name;
    }

    public boolean isValid() {
        return true;
    }

    public String getInstructionName() {
        return instructionName;
    }

    public void setInstructionName(String instructionName) {
        this.instructionName = instructionName;
    }
}
