package com.bhancock.pipelinedecoderapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.bhancock.pipelinedecoderapp.model.Instruction;
import com.bhancock.pipelinedecoderapp.model.InstructionCache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int OPEN_DOCUMENT_REQUEST_CODE = 1;
    private boolean forwardingEnabled = false;
    private int cycle = 0;

    InstructionCache instructionCache = InstructionCache.getInstance(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch forwardingSwitch = findViewById(R.id.forwarding_switch);
        Button openFile = findViewById(R.id.open_file);
        Button run = findViewById(R.id.open_file);


        forwardingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    Log.d(TAG, "Switch is on!");
                    forwardingEnabled = true;
                } else {
                    Log.d(TAG, "Switch is off!");
                    forwardingEnabled = false;
                }
            }
        });


        openFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Open File button Clicked");
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("text/plain");
                startActivityForResult(intent, OPEN_DOCUMENT_REQUEST_CODE);
            }
        });

        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: RUN PROGRAM HERE
            }
        });
    }



    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == OPEN_DOCUMENT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();

                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder total = new StringBuilder();
                    for (String line; (line = bufferedReader.readLine()) != null; ) {
                        total.append(line).append('\n');
                    }

                    String content = total.toString();

                    Log.d(TAG, "Content: " +  "\n" + content);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Log.d(TAG, "Path: " +uri.getEncodedPath());

                //TODO: READ DATA FROM FILE AND POPULATE INSTRUCTIONS:

                Instruction firstInstruction = new Instruction(1, "LW", "S0", 0, "R2");
                Instruction secondInstruction = new Instruction(2, "SUB", "R4", "R1", "R5");
                Instruction thirdInstruction = new Instruction(3, "LW", "T0", 0, "S2");
                Instruction fourthInstruction = new Instruction(4, "ADD", "S2", "S2", 4);
                Instruction fifthInstruction = new Instruction(5, "ADD", "T0", "S2", 4);
                Instruction sixthInstruction = new Instruction(6, "SW", "T0", 0, "S4");
                Instruction seventhInstruction = new Instruction(7, "ADD", "S4", "S4", 4);
                Instruction eighthInstruction = new Instruction(8, "ADD", "S0", "S0", 1);



                instructionCache.addInstruction(1, firstInstruction);
                instructionCache.addInstruction(2, secondInstruction);
                instructionCache.addInstruction(3, thirdInstruction);
                instructionCache.addInstruction(4, fourthInstruction);
                instructionCache.addInstruction(5, fifthInstruction);
                instructionCache.addInstruction(6, sixthInstruction);
                instructionCache.addInstruction(7, seventhInstruction);
                instructionCache.addInstruction(8, eighthInstruction);



            }
        }
    }


    public void executePipeline(@NonNull Instruction instruction) {

        if (forwardingEnabled) {
            //TODO: Handle forwarding pipelining scenario
        }

        if (!forwardingEnabled) {
            //TODO: Handle not forwarding pipelining scenario

            if (instruction.getInstructionNumber() == 1) {
                cycle = 1;
                //TODO:  We know this is first instruction.... display output to screen


            } else {

                //Current Instruction
                int currentInstructionNumber = instruction.getInstructionNumber();
                String operand = instruction.getOperation();
                String destReg = instruction.getDestinationRegister();


                //Previous Instruction
                int previousInstructionNumber = instructionCache.getInstruction(currentInstructionNumber - 1).getInstructionNumber();
                String prevOperand = instructionCache.getInstruction(previousInstructionNumber).getOperation();
                String prevDestReg = instructionCache.getInstruction(previousInstructionNumber).getDestinationRegister();
                String prevSource1Reg = instructionCache.getInstruction(previousInstructionNumber).getSourceRegister1();
                String prevSource2Reg = instructionCache.getInstruction(previousInstructionNumber).getSourceRegister2();

                //Check for Data Hazards now that we have current instruction and previous instruction
                //Remember... need to check instructions prior to prev instructions as cycle count increases
                //TODO: Create method to check for data hazards

                //TODO: Create method to check for structural hazards
                //TODO: Create method to check for control hazards (if we decide to do branch)


            }
        }
    }

    /**
     *
     * @return number of stalls
     */
    public int detectDataHazards() {
        return 0;
    }


    public void printToConsole() {

    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();

        //Singleton instance for cache will be deleted by the OS
        Toast.makeText(this, "We're running out of memory!!!!", Toast.LENGTH_SHORT).show();
    }
}