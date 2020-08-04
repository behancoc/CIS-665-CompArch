package com.bhancock.bisc.emulator.models;



import android.util.Pair;

import androidx.room.Entity;

import androidx.room.PrimaryKey;

import java.util.HashMap;

@Entity(tableName = "instruction_table")
public class Instruction {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String instructionFormat;
    private String opcode;
    private int instructionNumber;
    private String destinationRegister;
    private String sourceRegister1;
    private String sourceRegister2;
    private int signedImmediate;
    private int immediate;




    public Instruction(String instructionFormat, String opcode, int instructionNumber,
                       String destinationRegister, String sourceRegister1, String sourceRegister2,
                       int signedImmediate, int immediate) {

        this.instructionFormat = instructionFormat;
        this.opcode = opcode;
        this.instructionNumber = instructionNumber;
        this.destinationRegister = destinationRegister;
        this.sourceRegister1 = sourceRegister1;
        this.sourceRegister2 = sourceRegister2;
        this.signedImmediate = signedImmediate;
        this.immediate = immediate;

        populateMapping();
    }


    public int getId() {
        return id;
    }

    public String getInstructionFormat() {
        return instructionFormat;
    }

    public String getOpcode() {
        return opcode;
    }

    public int getInstructionNumber() {
        return instructionNumber;
    }

    public String getDestinationRegister() {
        return destinationRegister;
    }

    public String getSourceRegister1() {
        return sourceRegister1;
    }

    public String getSourceRegister2() {
        return sourceRegister2;
    }

    public int getSignedImmediate() {
        return signedImmediate;
    }

    public int getImmediate() {
        return immediate;
    }

    public void setImmediate(int immediate) {
        this.immediate = immediate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBinaryRepresentation(String instructionFormat) {

        if(instructionFormat.equalsIgnoreCase("r-type")) {
            return 0b0000001;
        }

        if(instructionFormat.equalsIgnoreCase("i-type")) {
            return 0b0000011;
        }

        if(instructionFormat.equalsIgnoreCase("s-type")) {
            return 0b0000111;
        }

        if(instructionFormat.equalsIgnoreCase("b-type")) {
            return 0b0001111;
        }

        if(instructionFormat.equalsIgnoreCase("j-type")) {
            return 0b0011111;
        }

        return -1;
    }

    private void populateMapping() {
        HashMap<Integer, Pair<String, String>> opcodeMapping = new HashMap<>();
        opcodeMapping.put(0, new Pair<>("add", Integer.toBinaryString(1)));
        opcodeMapping.put(1, new Pair<>("addi", Integer.toBinaryString(2)));
        opcodeMapping.put(2, new Pair<>("addiu", Integer.toBinaryString(3)));
        opcodeMapping.put(3, new Pair<>("and", Integer.toBinaryString(4)));
        opcodeMapping.put(4, new Pair<>("and", Integer.toBinaryString(5)));
        opcodeMapping.put(5, new Pair<>("and", Integer.toBinaryString(6)));
        opcodeMapping.put(6, new Pair<>("andn", Integer.toBinaryString(7)));
        opcodeMapping.put(7, new Pair<>("beq", Integer.toBinaryString(8)));
        opcodeMapping.put(8, new Pair<>("blt", Integer.toBinaryString(9)));
        opcodeMapping.put(9, new Pair<>("bgt", Integer.toBinaryString(10)));
        opcodeMapping.put(10, new Pair<>("blsr", Integer.toBinaryString(11)));
        opcodeMapping.put(11, new Pair<>("bswap", Integer.toBinaryString(12)));
        opcodeMapping.put(12, new Pair<>("dec", Integer.toBinaryString(13)));
        opcodeMapping.put(13, new Pair<>("csign", Integer.toBinaryString(14)));
        opcodeMapping.put(14, new Pair<>("div", Integer.toBinaryString(15)));
        opcodeMapping.put(15, new Pair<>("divu", Integer.toBinaryString(16)));
        opcodeMapping.put(16, new Pair<>("flash", Integer.toBinaryString(17)));

    }
}
