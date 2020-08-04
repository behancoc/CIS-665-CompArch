package com.bhancock.bisc.emulator.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;

import com.bhancock.bisc.emulator.adapters.InstructionAdapter;
import com.bhancock.bisc.emulator.adapters.ResultAdapter;
import com.bhancock.bisc.emulator.models.Instruction;
import com.bhancock.bisc.emulator.viewmodels.InstructionViewModel;
import com.bhancock.bisc.emulator.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    private final String TAG = ResultActivity.class.getSimpleName();

    private InstructionViewModel instructionViewModel;
    private LiveData<List<Instruction>> instructionList;
    private HashMap<String, ArrayList<Object> > instructionMapping = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        RecyclerView recyclerView = findViewById(R.id.results_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        final ResultAdapter adapter = new ResultAdapter();
        recyclerView.setAdapter(adapter);

        instructionViewModel = ViewModelProviders.of(this).get(InstructionViewModel.class);
        instructionViewModel.getAllInstructions().observe(this, new Observer<List<Instruction>>() {
            @Override
            public void onChanged(List<Instruction> instructions) {
                adapter.submitList(instructions);
            }
        });



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

                    byte[] byteArray = new byte[4];

                    for(int i = 0; i < instructions.size(); i ++) {

                        Log.d(TAG, "Grabbing some formats!: " +instructions.get(i).getInstructionFormat());

                        String opcode = instructions.get(i).getOpcode().toLowerCase();
                        String formatCheck = instructions.get(i).getInstructionFormat().toLowerCase();

                        int binaryOpcode = instructions.get(i).getBinaryRepresentation(formatCheck);


                        if(instructionMapping.containsKey(opcode)) {
                            continue;
                        } else {

                            if(formatCheck.equalsIgnoreCase("r-type")) {                         //7 bits
                                ArrayList<Object> assemblyFormat = new ArrayList<>();
                                assemblyFormat.add(0, instructions.get(i).getDestinationRegister());    //5 bits
                                assemblyFormat.add(1, instructions.get(i).getSourceRegister1());        //5 bits
                                assemblyFormat.add(2, instructions.get(i).getSourceRegister2());        //5 bits

                                //Need to figure out what goes in last 10 bits
                                instructionMapping.put(opcode, assemblyFormat);

                            } else if (formatCheck.equalsIgnoreCase("i-type")) {                 //7 bits
                                ArrayList<Object> assemblyFormat = new ArrayList<>();
                                assemblyFormat.add(0, instructions.get(i).getDestinationRegister());    // 5 bits
                                assemblyFormat.add(1, instructions.get(i).getSourceRegister1());        // 5 bits
                                assemblyFormat.add(2, instructions.get(i).getSignedImmediate());        //15 bits
                                instructionMapping.put(opcode, assemblyFormat);

                            } else if (formatCheck.equalsIgnoreCase("s-type")) {                    //7 bits
                                ArrayList<Object> assemblyFormat = new ArrayList<>();
                                assemblyFormat.add(0, instructions.get(i).getDestinationRegister());        //5 bits
                                assemblyFormat.add(1, instructions.get(i).getSourceRegister1());            //5 bits
                                assemblyFormat.add(2, instructions.get(i).getSignedImmediate());
                                instructionMapping.put(opcode, assemblyFormat);

                            } else if (formatCheck.equalsIgnoreCase("b-type")) {
                                ArrayList<Object> assemblyFormat = new ArrayList<>();
                                assemblyFormat.add(0, instructions.get(i).getDestinationRegister());
                                assemblyFormat.add(1, instructions.get(i).getSourceRegister1());
                                assemblyFormat.add(2, instructions.get(i).getSignedImmediate());
                                instructionMapping.put(opcode, assemblyFormat);

                            } else {
                                ArrayList<Object> assemblyFormat = new ArrayList<>();
                                assemblyFormat.add(0, instructions.get(i).getDestinationRegister());
                                assemblyFormat.add(1, instructions.get(i).getImmediate());
                            }
                        }
                    }

                } else {
                    Log.d(TAG, "List is null");
                }
            }
        });
    }


    private void computeInstructionResult(String opcode) {

    }
}