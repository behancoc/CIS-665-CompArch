package com.bhancock.pipelinedecoderapp.model;

import android.content.Context;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;


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

    public int getSize() {
        Collection<Instruction> collection = mInstructionsHashTable.values();
        return collection.size();
    }
    public Collection<Instruction> getInstructionsAsCollection() {
        return mInstructionsHashTable.values();
    }
}
