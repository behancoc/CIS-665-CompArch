package com.bhancock.bisc.emulator;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Instruction.class}, version = 1)
public abstract class InstructionDatabase extends RoomDatabase {

    private static InstructionDatabase instance;

    public abstract InstructionDao instructionDao();

    public static synchronized InstructionDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    InstructionDatabase.class, "instruction_database")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
