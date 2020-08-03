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

    //Exposing an API...  because why not.
    public void insert(Instruction instruction) {
        new InsertInstructionAsyncTask(instructionDao).execute(instruction);
    }

    public void update(Instruction instruction) {
        new UpdateInstructionAsyncTask(instructionDao).execute(instruction);
    }

    public void delete(Instruction instruction) {
        new DeleteInstructionAsyncTask(instructionDao).execute(instruction);
    }

    public void deleteAllInstructions() {
        new DeleteAllInstructionAsyncTask(instructionDao).execute();
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

    private static class UpdateInstructionAsyncTask extends AsyncTask<Instruction, Void, Void> {
        private InstructionDao instructionDao;

        private UpdateInstructionAsyncTask(InstructionDao instructionDao) {
            this.instructionDao = instructionDao;
        }

        @Override
        protected Void doInBackground(Instruction... instructions) {
            instructionDao.update(instructions[0]);
            return null;
        }
    }

    private static class DeleteInstructionAsyncTask extends AsyncTask<Instruction, Void, Void> {
        private InstructionDao instructionDao;

        private DeleteInstructionAsyncTask(InstructionDao instructionDao) {
            this.instructionDao = instructionDao;
        }

        @Override
        protected Void doInBackground(Instruction... instructions) {
            instructionDao.delete(instructions[0]);
            return null;
        }
    }

    private static class DeleteAllInstructionAsyncTask extends AsyncTask<Void, Void, Void> {
        private InstructionDao instructionDao;

        private DeleteAllInstructionAsyncTask(InstructionDao instructionDao) {
            this.instructionDao = instructionDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            instructionDao.deleteAllNotes();
            return null;
        }
    }
}
