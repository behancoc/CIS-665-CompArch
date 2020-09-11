package com.bhancock.pipelinedecoderapp.model;

import android.content.Context;

import java.util.Enumeration;
import java.util.Hashtable;


public class InstructionCache {

    private static InstructionCache sInstructionCache = null;

    private Hashtable<Integer, Instruction> mInstructionsHashTable;

    public static InstructionCache getInstance(Context context) {
        if (sInstructionCache == null) {
            sInstructionCache = new InstructionCache(context);
        }
        return sInstructionCache;
    }

    private InstructionCache(Context context) {
        mInstructionsHashTable = new Hashtable<Integer, Instruction>();
    }

    public Enumeration<Instruction> getInstructions() {
        Enumeration<Instruction> values = mInstructionsHashTable.elements();
        return values;
    }

    public Instruction getInstruction(Integer instructionNumber) {
        return mInstructionsHashTable.get(instructionNumber);

    }

    public void addInstruction(Integer key, Instruction instruction) {
        mInstructionsHashTable.put(key, instruction);
    }
}
