package com.bhancock.pipelinedecoderapp.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InstructionCache {

    private static InstructionCache sInstructionCache = null;

    private List<Instruction> mInstructions;

    public static InstructionCache getInstance(Context context) {
        if (sInstructionCache == null) {
            sInstructionCache = new InstructionCache(context);
        }
        return sInstructionCache;
    }

    private InstructionCache(Context context) {
        mInstructions = new ArrayList<>();
    }

    public List<Instruction> getInstructions() {
        return mInstructions;
    }

    public Instruction getInstruction(Integer instructionNumber) {
        for (Instruction instruction: mInstructions) {
            if (instruction.getInstructionNumber().equals(instructionNumber)) {
                return instruction;
            }
        }
        return null;
    }
}
