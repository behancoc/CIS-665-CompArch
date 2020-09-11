package com.bhancock.pipelinedecoderapp.model;

public class Instruction {

    private int instructionNumber;
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
    public Instruction(int instructionNumber, String operation, int offset, String baseAddress) {
        this.instructionNumber = instructionNumber;
        this.operation = operation;
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
    public Instruction(int instructionNumber, String operation, String destinationRegister,
                       String sourceRegister1, String sourceRegister2) {

        this.instructionNumber = instructionNumber;
        this.operation = operation;
        this.destinationRegister = destinationRegister;
        this.sourceRegister1 = sourceRegister1;
        this.sourceRegister2 = sourceRegister2;
    }


    public int getInstructionNumber() {
        return instructionNumber;
    }

    public void setInstructionNumber(int instructionNumber) {
        this.instructionNumber = instructionNumber;
    }
}
