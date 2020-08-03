package com.bhancock.bisc.emulator.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bhancock.bisc.emulator.models.Instruction;
import com.bhancock.bisc.emulator.viewmodels.InstructionViewModel;
import com.bhancock.bisc.emulator.R;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private final String TAG = ResultActivity.class.getSimpleName();

    private InstructionViewModel instructionViewModel;
    private LiveData<List<Instruction>> instructionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        instructionViewModel = ViewModelProviders.of(this).get(InstructionViewModel.class);
        readInstruction();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void readInstruction() {
       instructionList = instructionViewModel.getAllInstructions();

       Log.d(TAG, "Value in list: " + instructionList.getValue());
    }
}