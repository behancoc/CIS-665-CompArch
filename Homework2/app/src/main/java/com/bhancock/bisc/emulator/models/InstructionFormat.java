package com.bhancock.bisc.emulator.models;

public class InstructionFormat {

    /**
     * Modeling this off RiSC-16 to start...
     * modify to my own Instruction-Set Arch eventually
     */

    private String opcode;
    private Register registerA;
    private Register registerB;
    private Register registerC;
    private int immediate; //Type of this needs to be changed
    private int signedImmediate; //Type of this needs to be changed

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


}
