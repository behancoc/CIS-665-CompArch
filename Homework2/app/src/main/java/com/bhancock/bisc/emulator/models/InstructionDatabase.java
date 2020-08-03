package com.bhancock.bisc.emulator.models;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Instruction.class}, version = 1)
public abstract class InstructionDatabase extends RoomDatabase {

    private static InstructionDatabase instance;

    public abstract InstructionDao instructionDao();

    public static synchronized InstructionDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    InstructionDatabase.class, "instruction_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){

        //So I'm just doing this to get some throw away data into database for testing purposes
        //I need to delete this before submission...
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();

        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private InstructionDao instructionDao;

        private PopulateDBAsyncTask(InstructionDatabase database) {
            instructionDao = database.instructionDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            instructionDao.insert(new Instruction("RRR-type", "add"));
            instructionDao.insert(new Instruction("RRI-type", "sw"));
            instructionDao.insert(new Instruction("RI-type", "jalr"));

//            instructionDao.insert(new Instruction("RRR-type","add", "R1", "R2", "R3"));
//            instructionDao.insert(new Instruction("RRI-type","addi", "R1", "R2", 5));

            return null;
        }
    }
}
