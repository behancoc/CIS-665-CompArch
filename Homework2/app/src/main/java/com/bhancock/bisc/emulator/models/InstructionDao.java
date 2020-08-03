package com.bhancock.bisc.emulator.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface InstructionDao {

    @Insert
    void insert(Instruction instruction);

    @Update
    void update(Instruction instruction);

    @Delete
    void delete(Instruction instruction);

    @Query("DELETE FROM instruction_table")
    void deleteAllNotes();

//    @Query("SELECT * FROM instruction_table ORDER BY format DESC")
//    List<Instruction> getAllInstructions();

    @Query("SELECT * FROM instruction_table ORDER BY instructionFormat DESC")
    LiveData<List<Instruction>> getAllInstructions();
}
