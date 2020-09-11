package com.bhancock.pipelinedecoderapp.model;

import java.util.UUID;

public class Instruction {

    private Integer instructionNumber;
    private String operation;
    private String sourceRegister1;
    private String sourceRegister2;
    private String destinationRegister;
    private String baseAddress;
    private int immediate;
    private int offset;



    /**
     * Empty Constructor
     */
    public Instruction() {

    }


    /**
     * Constructor for SW and LW
     * @param operation
     * @param offset
     * @param baseAddress
     */
    public Instruction(Integer instructionNumber, String operation, String destinationRegister, int offset, String baseAddress) {
        this.instructionNumber = instructionNumber;
        this.operation = operation;
        this.destinationRegister = destinationRegister;
        this.offset = offset;
        this.baseAddress = baseAddress;
    }

    /**
     * Constructor for ADD, SUB
     * @param operation
     * @param destinationRegister
     * @param sourceRegister1
     * @param sourceRegister2
     */
    public Instruction(Integer instructionNumber, String operation, String destinationRegister,
                       String sourceRegister1, String sourceRegister2) {

        this.instructionNumber = instructionNumber;
        this.operation = operation;
        this.destinationRegister = destinationRegister;
        this.sourceRegister1 = sourceRegister1;
        this.sourceRegister2 = sourceRegister2;
    }


    public Integer getInstructionNumber() {
        return instructionNumber;
    }

    public void setInstructionNumber(Integer instructionNumber) {
        this.instructionNumber = instructionNumber;
    }

    public int getImmediate() {
        return immediate;
    }

    public void setImmediate(int immediate) {
        this.immediate = immediate;
    }

    public String getSourceRegister1() {
        return sourceRegister1;
    }

    public void setSourceRegister1(String sourceRegister1) {
        this.sourceRegister1 = sourceRegister1;
    }

    public String getSourceRegister2() {
        return sourceRegister2;
    }

    public void setSourceRegister2(String sourceRegister2) {
        this.sourceRegister2 = sourceRegister2;
    }

    public String getDestinationRegister() {
        return destinationRegister;
    }

    public void setDestinationRegister(String destinationRegister) {
        this.destinationRegister = destinationRegister;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getBaseAddress() {
        return baseAddress;
    }

    public void setBaseAddress(String baseAddress) {
        this.baseAddress = baseAddress;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
