package com.bhancock.bisc.emulator;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class InstructionRepository {

    private InstructionDao instructionDao;
    private LiveData<List<Instruction>> allInstructions;

    public InstructionRepository(Application application) {
        InstructionDatabase instructionDatabase = InstructionDatabase.getInstance(application);
        instructionDao = instructionDatabase.instructionDao();
        allInstructions = instructionDao.getAllInstructions();
    }

    public void insert(Instruction instruction) {

    }

    public void update(Instruction instruction) {

    }

    public void delete(Instruction instruction) {

    }

    public void deleteAllInstructions() {

    }

    public LiveData<List<Instruction>> getAllInstructions() {
        return allInstructions;
    }

    private static class InsertInstructionAsyncTask extends AsyncTask<Instruction, Void, Void> {
        private InstructionDao instructionDao;

        private InsertInstructionAsyncTask(InstructionDao instructionDao) {
            this.instructionDao = instructionDao;
        }

        @Override
        protected Void doInBackground(Instruction... instructions) {
            instructionDao.insert(instructions[0]);
            return null;
        }
    }
}
