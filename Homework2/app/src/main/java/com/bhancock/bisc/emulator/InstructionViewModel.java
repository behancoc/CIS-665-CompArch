package com.bhancock.bisc.emulator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.List;

public class InstructionViewModel extends AndroidViewModel {

    private InstructionRepository repository;
    private LiveData<List<Instruction>> allInstructions;

    public InstructionViewModel(@NonNull Application application) {
        super(application);
        repository = new InstructionRepository(application);
        allInstructions = repository.getAllInstructions();
    }

    public void insert(Instruction instruction) {
        repository.insert(instruction);
    }

    public void update(Instruction instruction){
        repository.update(instruction);
    }

    public void delete(Instruction instruction) {
        repository.delete(instruction);
    }

    public void deleteAllInstructions() {
        repository.deleteAllInstructions();
    }

    public LiveData<List<Instruction>> getAllInstructions() {
        return allInstructions;
    }
}
