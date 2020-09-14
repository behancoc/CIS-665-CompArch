package com.bhancock.pipelinedecoderapp.model;

import java.util.HashMap;
import java.util.Map;

public class Instruction {

    private Integer instructionNumber;
    private String operand;
    private String sourceRegister1;
    private String sourceRegister2;
    private String destinationRegister;
    private String baseAddress;
    private int immediate;
    private int offset;
    private SEGMENT registerAvailability;
    private SEGMENT registerRequired;

    public enum SEGMENT {
        FETCH(1),
        DECODE(2),
        EXECUTE(3),
        MEMORY(4),
        WRITE_BACK(5);

        private int value;
        private static Map map = new HashMap<>();

        private SEGMENT(int value) {
            this.value = value;
        }

        static {
            for (SEGMENT segment: SEGMENT.values()) {
                map.put(segment.value, segment);
            }
        }

        public static SEGMENT valueOf(int segment) {
            return (SEGMENT) map.get(segment);
        }

        public int getValue() {
            return value;
        }
    }


    /**
     * Empty Constructor
     */
    public Instruction() {

    }


    /**
     * Constructor for SW and LW
     * @param operand
     * @param offset
     * @param baseAddress
     */
    public Instruction(Integer instructionNumber, String operand, String destinationRegister, int offset, String baseAddress) {
        this.instructionNumber = instructionNumber;
        this.operand = operand;
        this.destinationRegister = destinationRegister;
        this.offset = offset;
        this.baseAddress = baseAddress;
    }

    /**
     * Constructor for ADD, SUB
     * @param operand
     * @param destinationRegister
     * @param sourceRegister1
     * @param sourceRegister2
     */
    public Instruction(Integer instructionNumber, String operand, String destinationRegister,
                       String sourceRegister1, String sourceRegister2) {

        this.instructionNumber = instructionNumber;
        this.operand = operand;
        this.destinationRegister = destinationRegister;
        this.sourceRegister1 = sourceRegister1;
        this.sourceRegister2 = sourceRegister2;
    }

    public Instruction(int instructionNumber, String operand, String destinationRegister, String sourceRegister1, int immediate) {
        this.instructionNumber = instructionNumber;
        this.operand = operand;
        this.destinationRegister = destinationRegister;
        this.sourceRegister1 = sourceRegister1;
        this.immediate = immediate;
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

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

    public SEGMENT getRegisterAvailability() {
        return registerAvailability;
    }

    public void setRegisterAvailability(SEGMENT registerAvailability) {
        this.registerAvailability = registerAvailability;
    }

    public SEGMENT getRegisterRequired() {
        return registerRequired;
    }

    public void setRegisterRequired(SEGMENT registerRequired) {
        this.registerRequired = registerRequired;
    }
}
