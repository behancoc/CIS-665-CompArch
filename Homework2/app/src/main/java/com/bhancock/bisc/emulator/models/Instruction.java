package com.bhancock.bisc.emulator.models;

import java.util.HashMap;

public class Instruction {

    private String instructionName;
    private HashMap<String, Byte> instructionMapping = new HashMap<>();

    public Instruction() {
        generateInstructionMapping();
    }

    public Instruction(String name) {
        generateInstructionMapping();
        this.setInstructionName(name);
    }

    public String getInstructionName() {
        return instructionName;
    }

    public void setInstructionName(String instructionName) {
        this.instructionName = instructionName;
    }

    public void generateInstructionMapping() {
        instructionMapping.put("add", (byte) 0);
        instructionMapping.put("addi", (byte) 1);
        instructionMapping.put("nand", (byte) 2);
        instructionMapping.put("lui", (byte) 3);
        instructionMapping.put("sw", (byte) 4);
        instructionMapping.put("lw", (byte) 5);
        instructionMapping.put("beq", (byte) 6);
        instructionMapping.put("jalr", (byte) 7);
    }


}
