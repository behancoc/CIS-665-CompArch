package com.bhancock.pipelinedecoderapp.model;

public class Instruction {

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
    public Instruction(String operation, int offset, String baseAddress) {
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
    public Instruction(String operation, String destinationRegister,
                       String sourceRegister1, String sourceRegister2) {

        this.operation = operation;
        this.destinationRegister = destinationRegister;
        this.sourceRegister1 = sourceRegister1;
        this.sourceRegister2 = sourceRegister2;
    }
}
