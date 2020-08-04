package com.bhancock.bisc.emulator.models;



import android.util.Log;
import android.util.Pair;

import androidx.room.Entity;

import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

    @Ignore
    HashMap<Integer, Pair<String, String>> opcodeMapping = new HashMap<>();




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
        opcodeMapping.put(17, new Pair<>("inc", Integer.toBinaryString(18)));
        opcodeMapping.put(18, new Pair<>("jmp", Integer.toBinaryString(19)));
        opcodeMapping.put(19, new Pair<>("lw", Integer.toBinaryString(20)));
        opcodeMapping.put(20, new Pair<>("lbu", Integer.toBinaryString(21)));
        opcodeMapping.put(21, new Pair<>("logl", Integer.toBinaryString(22)));
        opcodeMapping.put(22, new Pair<>("logr", Integer.toBinaryString(23)));
        opcodeMapping.put(23, new Pair<>("mskla", Integer.toBinaryString(24)));
        opcodeMapping.put(24, new Pair<>("nmask", Integer.toBinaryString(25)));
        opcodeMapping.put(25, new Pair<>("mlogo", Integer.toBinaryString(26)));
        opcodeMapping.put(26, new Pair<>("nor", Integer.toBinaryString(27)));
        opcodeMapping.put(27, new Pair<>("or", Integer.toBinaryString(28)));
        opcodeMapping.put(28, new Pair<>("sb", Integer.toBinaryString(29)));
        opcodeMapping.put(29, new Pair<>("sw", Integer.toBinaryString(30)));
        opcodeMapping.put(30, new Pair<>("sub", Integer.toBinaryString(31)));
        opcodeMapping.put(31, new Pair<>("slt", Integer.toBinaryString(32)));
        opcodeMapping.put(32, new Pair<>("slti", Integer.toBinaryString(33)));
    }

    public String getOpCodeStringRepresentation(int opcode) {
        if (opcodeMapping.containsKey(opcode)) {
            return opcodeMapping.get(opcode).first;
        }
        return null;
    }
}
