package com.bhancock.bisc.emulator.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "instruction_table")
public class Instruction {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String instructionFormat;
    private String opcode;
//    private String destinationRegister;
//    private String sourceRegister1;
//    private String sourceRegister2;
//    @ColumnInfo
//    private int signedImmediate;
//    @ColumnInfo
//    private int immediate;



    public Instruction(String instructionFormat, String opcode) {
        this.instructionFormat = instructionFormat;
        this.opcode = opcode;
    }

//    @Ignore
//    public Instruction(String instructionFormat, String opcode,
//                              String sourceRegister1, String sourceRegister2,
//                              String destinationRegister) {
//
//        this.instructionFormat = instructionFormat;
//        this.opcode = opcode;
//        this.destinationRegister = destinationRegister;
//        this.sourceRegister1 = sourceRegister1;
//        this.sourceRegister2 = sourceRegister2;
//
//    }
//
//    @Ignore
//    public Instruction(String instructionFormat, String opcode,
//                              String destinationRegister, String sourceRegister1,
//                              int signedImmediate) {
//
//        this.instructionFormat = instructionFormat;
//        this.opcode = opcode;
//        this.destinationRegister = destinationRegister;
//        this.sourceRegister1 = sourceRegister1;
//        this.signedImmediate = signedImmediate;
//
//    }
//
//    @Ignore
//    public Instruction(String instructionFormat, String opcode,
//                              String destinationRegister, int immediate) {
//
//        this.instructionFormat = instructionFormat;
//        this.opcode = opcode;
//        this.destinationRegister = destinationRegister;
//        this.immediate = immediate;
//    }

    public int getId() {
        return id;
    }

    public String getInstructionFormat() {
        return instructionFormat;
    }

    public String getOpcode() {
        return opcode;
    }

//    public String getDestinationRegister() {
//        return destinationRegister;
//    }
//
//    public String getSourceRegister1() {
//        return sourceRegister1;
//    }
//
//    public String getSourceRegister2() {
//        return sourceRegister2;
//    }

//    public int getSignedImmediate() {
//        return signedImmediate;
//    }
//
//    public int getImmediate() {
//        return immediate;
//    }

//    public void setImmediate(int immediate) {
//        this.immediate = immediate;
//    }

    public void setId(int id) {
        this.id = id;
    }
}
