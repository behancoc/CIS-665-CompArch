package com.bhancock.bisc.emulator;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bhancock.bisc.emulator.models.Register;

@Entity(tableName = "instruction_table")
public class Instruction {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String format;

    private String opcode;

    public Instruction(String format, String opcode) {
        this.format = format;
        this.opcode = opcode;
    }

    public int getId() {
        return id;
    }

    public String getFormat() {
        return format;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setId(int id) {
        this.id = id;
    }
}
