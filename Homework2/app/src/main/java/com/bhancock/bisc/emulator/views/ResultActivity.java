package com.bhancock.bisc.emulator.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;

import com.bhancock.bisc.emulator.models.Instruction;
import com.bhancock.bisc.emulator.viewmodels.InstructionViewModel;
import com.bhancock.bisc.emulator.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private final String TAG = ResultActivity.class.getSimpleName();

    private InstructionViewModel instructionViewModel;
    private LiveData<List<Instruction>> instructionList;
    private HashMap<String, ArrayList<Object> > instructionMapping = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        instructionViewModel = ViewModelProviders.of(this).get(InstructionViewModel.class);

        createMapping();
        readInstruction();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void readInstruction() {
       instructionList = instructionViewModel.getAllInstructions();

       Log.d(TAG, "Value Value list: " + instructionList.getValue());

        instructionViewModel.getAllInstructions().observe(this, new Observer<List<Instruction>>() {
            @Override
            public void onChanged(List<Instruction> instructions) {
                if (instructions != null) {
                    Log.d(TAG, "Happy Dance!");

                    Log.d(TAG, "Grabbing some formats!: " +instructions.get(0).getInstructionFormat());
                    //instructions.get(1).getDestinationRegister()


                } else {
                    Log.d(TAG, "List is null");
                }
            }
        });
    }


    private void computeInstructionResult(String opcode) {
    }

    private void createMapping() {



        ArrayList<Object> addInstructionAssemblyFormat = new ArrayList<>();
        addInstructionAssemblyFormat.add(0, "rA");
        addInstructionAssemblyFormat.add(1, "rB");
        addInstructionAssemblyFormat.add(2, "rC");


        ArrayList<Object> addiInstructionAssemblyFormat = new ArrayList<>();
        addiInstructionAssemblyFormat.add(0, "rA");
        addiInstructionAssemblyFormat.add(1, "rB");
        addiInstructionAssemblyFormat.add(2, "imm");


        ArrayList<Object> nandInstructionAssemblyFormat = new ArrayList<>();
        nandInstructionAssemblyFormat.add(0, "rA");
        nandInstructionAssemblyFormat.add(1, "rB");
        nandInstructionAssemblyFormat.add(2, "rC");

        ArrayList<Object> luiInstructionAssemblyFormat = new ArrayList<>();
        nandInstructionAssemblyFormat.add(0, "rA");
        nandInstructionAssemblyFormat.add(1, "imm");

        ArrayList<Object> swInstructionAssemblyFormat = new ArrayList<>();
        nandInstructionAssemblyFormat.add(0, "rA");
        nandInstructionAssemblyFormat.add(1, "rB");
        nandInstructionAssemblyFormat.add(2, "imm");

        ArrayList<Object> lwInstructionAssemblyFormat = new ArrayList<>();
        nandInstructionAssemblyFormat.add(0, "rA");
        nandInstructionAssemblyFormat.add(1, "rB");
        nandInstructionAssemblyFormat.add(2, "imm");

        ArrayList<Object> beqInstructionAssemblyFormat = new ArrayList<>();
        nandInstructionAssemblyFormat.add(0, "rA");
        nandInstructionAssemblyFormat.add(1, "rB");
        nandInstructionAssemblyFormat.add(2, "imm");

        ArrayList<Object> jalrInstructionAssemblyFormat = new ArrayList<>();
        nandInstructionAssemblyFormat.add(0, "rA");
        nandInstructionAssemblyFormat.add(1, "rB");





        instructionMapping.put("add", addInstructionAssemblyFormat);
        instructionMapping.put("addi", addiInstructionAssemblyFormat);

        Log.d(TAG, "Hey " + instructionMapping.get("addi"));

    }


}