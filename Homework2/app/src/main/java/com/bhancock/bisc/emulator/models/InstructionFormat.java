package com.bhancock.bisc.emulator.models;

public class InstructionFormat {

    /**
     * Modeling this off RiSC-16 to start...
     * modify to my own Instruction-Set Arch eventually
     */

    private String instructionFormatName;
    private String opcode;
    private Register registerA;
    private Register registerB;
    private Register registerC;
    private int immediate; //Type of this needs to be changed
    private int signedImmediate; //Type of this needs to be changed

    public InstructionFormat() {

    }

    //Empty constructor
    public InstructionFormat(String opcode) {

    }

    //RRR-type constructor
    public InstructionFormat(String opcode, Register registerA,
                             Register registerB, Register registerC) {
        this.opcode = opcode;
        this.registerA = registerA;
        this.registerB = registerB;
        this.registerC = registerC;
    }

    //RRI-type constructor
    public InstructionFormat(String opcode, Register registerA,
                             Register registerB, int signedImmediate) {
        this.opcode = opcode;
        this.registerA = registerA;
        this.registerB = registerB;
        this.signedImmediate = signedImmediate;
    }

    //RI-type constructor
    public InstructionFormat(String opcode, Register registerA, int immediate) {
        this.opcode = opcode;
        this.registerA = registerA;
        this.immediate = immediate;
    }

    public String getInstructionFormatName() {
        return instructionFormatName;
    }

    public String getOpcode() {
        return opcode;
    }

    public Register getRegisterA() {
        return registerA;
    }

    public Register getRegisterB() {
        return registerB;
    }

    public Register getRegisterC() {
        return registerC;
    }

    public int getImmediate() {
        return immediate;
    }

    public int getSignedImmediate() {
        return signedImmediate;
    }

    //Setters for class

    public void setInstructionFormatName(String instructionFormatName) {
        this.instructionFormatName = instructionFormatName;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public void setRegisterA(Register registerA) {
        this.registerA = registerA;
    }

    public void setRegisterB(Register registerB) {
        this.registerB = registerB;
    }

    public void setRegisterC(Register registerC) {
        this.registerC = registerC;
    }

    public void setImmediate(int immediate) {
        this.immediate = immediate;
    }

    public void setSignedImmediate(int signedImmediate) {
        this.signedImmediate = signedImmediate;
    }
}
