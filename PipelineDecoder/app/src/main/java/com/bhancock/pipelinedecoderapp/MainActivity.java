package com.bhancock.pipelinedecoderapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.bhancock.pipelinedecoderapp.model.Instruction;
import com.bhancock.pipelinedecoderapp.model.InstructionCache;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int OPEN_DOCUMENT_REQUEST_CODE = 1;

    private boolean forwardingEnabled = false;
    private int cycle = 0;
    private int instructionFeed = 1;
    private Uri uri = null;

    InstructionCache instructionCache;
    ArrayList<ArrayList<Instruction.SEGMENT>> pipelineSequence =
            new ArrayList<ArrayList<Instruction.SEGMENT>>();


    public enum DATA_DEPENDENCY  {
            READ_AFTER_WRITE,
            WRITE_AFTER_WRITE,
            WRITE_AFTER_READ,
            NONE;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        instructionCache = InstructionCache.getInstance(getApplicationContext());
        Switch forwardingSwitch = findViewById(R.id.forwarding_switch);
        Button openFile = findViewById(R.id.open_file);
        Button run = findViewById(R.id.open_file);




        forwardingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    Log.d(TAG, "Switch is on!");
                    forwardingEnabled = true;


                    Instruction firstInstruction = new Instruction(1, "LW", "S0", 0, "R2");
                    Instruction secondInstruction = new Instruction(2, "SUB", "R4", "R1", "R5");
                    Instruction thirdInstruction = new Instruction(3, "LW", "T0", 0, "S2");
                    Instruction fourthInstruction = new Instruction(4, "ADD", "S2", "S2", 4);
                    Instruction fifthInstruction = new Instruction(5, "ADD", "T0", "S2", 4);
                    Instruction sixthInstruction = new Instruction(6, "SW", "T0", 0, "S4");
                    Instruction seventhInstruction = new Instruction(7, "ADD", "S4", "S4", 4);
                    Instruction eighthInstruction = new Instruction(8, "ADD", "S0", "S0", 1);

                    constructPipelineSequence(secondInstruction, 2);


//                    ArrayList<ArrayList<Instruction.SEGMENT>> pipelineSequence = new ArrayList<ArrayList<Instruction.SEGMENT>>();
//
//                    pipelineSequence.add((new ArrayList<Instruction.SEGMENT>(Arrays.asList(Instruction.SEGMENT.FETCH,
//                            Instruction.SEGMENT.DECODE,
//                            Instruction.SEGMENT.EXECUTE,
//                            Instruction.SEGMENT.MEMORY,
//                            Instruction.SEGMENT.WRITE_BACK))));
//
//                    pipelineSequence.add(1, new ArrayList<Instruction.SEGMENT>(Arrays.asList(Instruction.SEGMENT.FETCH,
//                            Instruction.SEGMENT.STALL,
//                            Instruction.SEGMENT.STALL,
//                            Instruction.SEGMENT.DECODE,
//                            Instruction.SEGMENT.MEMORY,
//                            Instruction.SEGMENT.WRITE_BACK)));
//
//                    Log.d(TAG, pipelineSequence.toString());
//                    ArrayList<Instruction.SEGMENT> test = pipelineSequence.get(1);
//                    Log.d(TAG, test.toString());


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

//        run.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                if(uri == null) {
////                    Toast.makeText(getApplicationContext(),"Unable to find text file", Toast.LENGTH_SHORT).show();
////                }
//            }
//        });


    }




    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == OPEN_DOCUMENT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
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

                    Log.d(TAG, "Content: " + "\n" + content);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Log.d(TAG, "Path: " + uri.getEncodedPath());

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


                Enumeration<Instruction> enumeration = instructionCache.getInstructions();

//                while(enumeration.hasMoreElements()) {
//                    executePipeline(enumeration.nextElement());
//                }

                executePipeline(firstInstruction);

            }
        }
    }


    public void executePipeline(@NonNull Instruction instruction) {

        ArrayList<ArrayList<Instruction.SEGMENT>> pipelineSequence = new ArrayList<ArrayList<Instruction.SEGMENT>>();

        pipelineSequence.add((new ArrayList<Instruction.SEGMENT>(Arrays.asList(Instruction.SEGMENT.FETCH,
                Instruction.SEGMENT.DECODE,
                Instruction.SEGMENT.EXECUTE,
                Instruction.SEGMENT.MEMORY,
                Instruction.SEGMENT.WRITE_BACK))));

//        ArrayList<Instruction.SEGMENT> instructionPipeline = new ArrayList<>();
//        StringBuilder stringBuilder = new StringBuilder();
//
//        stringBuilder.append(instruction.getOperand());
//        stringBuilder.append(" ");
//        stringBuilder.append(instruction.getOffset());

//        for(int i = 0; i < 5; i++) {
//
//
//            stringBuilder.append("|");
//            stringBuilder.append(Instruction.SEGMENT.valueOf(i).toString());
//            stringBuilder.append("|");
//            Log.d(TAG ,  Instruction.SEGMENT.valueOf(i).toString());
//
//            instructionPipeline.add(i, Instruction.SEGMENT.valueOf(i));
//        }
//
//        Log.d(TAG, stringBuilder.toString());










        if (forwardingEnabled) {
            //TODO: Handle forwarding pipelining scenario
        }

//        if (!forwardingEnabled) {
//            if (instruction.getInstructionNumber() == 1) {
//
//
//
//
//            } else {
//
//                //We're no longer on the first instruction here...
//                //Current Instruction
//                int currentInstructionNumber = instruction.getInstructionNumber();
//
//                //Previous Instruction
//                int previousInstructionNumber = instructionCache.getInstruction(currentInstructionNumber - 1).getInstructionNumber();
//                Instruction previousInstruction = instructionCache.getInstruction(currentInstructionNumber - 1);
//
//
//                if (dataDependencyCheck(instruction, previousInstruction) != DATA_DEPENDENCY.NONE) {
//                    //REPLACE WITH SWITCH STATEMENT EVENTUALLY?
//
//                    if (dataDependencyCheck(instruction, previousInstruction) == DATA_DEPENDENCY.READ_AFTER_WRITE) {
//                        determineNumberOfStalls(forwardingEnabled, instruction, previousInstruction);
//                    }
//                }
//
//
//
//
//                //TODO: Create method to check for structural hazards
//                //TODO: Create method to check for control hazards (if we decide to do branch)
//
//            }
//        }
    }

    private DATA_DEPENDENCY dataDependencyCheck(Instruction currentInstruction, Instruction previousInstruction) {
        String currentInstructionDestinationRegister = currentInstruction.getDestinationRegister();
        String currentInstructionSourceReg1 = currentInstruction.getSourceRegister1();
        String currentInstructionSourceReg2 = currentInstruction.getSourceRegister2();


        String previousInstructionDestinationRegister = previousInstruction.getDestinationRegister();
        String previousInstructionSourceReg1 = previousInstruction.getSourceRegister1();
        String previousInstructionSourceReg2 = previousInstruction.getSourceRegister2();

        if(currentInstructionSourceReg1.equalsIgnoreCase(previousInstructionDestinationRegister) ||
            currentInstructionSourceReg2.equalsIgnoreCase(previousInstructionDestinationRegister)) {


            //THIS IS THE TRUE DEPENDENCE > FOCUS ON THIS SPECIFIC CASE FIRST!
            Log.d(TAG, "POSSIBLE READ AFTER WRITE (RAW) DATA DEPENDENCY!");

            return DATA_DEPENDENCY.READ_AFTER_WRITE;

        } else if (currentInstructionDestinationRegister.equalsIgnoreCase(previousInstructionDestinationRegister)) {

            Log.d(TAG, "POSSIBLE WRITE AFTER WRITE (WAW) DATA DEPENDENCY!");
            return DATA_DEPENDENCY.WRITE_AFTER_WRITE;

        } else if (currentInstructionDestinationRegister.equalsIgnoreCase(previousInstructionSourceReg1) ||
                   currentInstructionDestinationRegister.equalsIgnoreCase(previousInstructionSourceReg2)) {
            Log.d(TAG, "POSSIBLE WRITE AFTER READ (WAR) DATA DEPENDENCY!");
            return DATA_DEPENDENCY.WRITE_AFTER_READ;

        }
        else {
            return DATA_DEPENDENCY.NONE;
        }
    }


    private int determineNumberOfStalls(Boolean forwarding, Instruction currentInstruction, Instruction previousInstruction) {

        ArrayList<Instruction.SEGMENT> instructionPipeline = new ArrayList<>();

        currentInstruction = instructionCache.getInstruction(2);

        for(int i = 0; i < 5; i++) {
            Log.d(TAG ,  "Sanity check: " + Instruction.SEGMENT.valueOf(i));
            instructionPipeline.add(i, Instruction.SEGMENT.valueOf(i));
        }



        Instruction.SEGMENT stagePrevInstRegAvailable = previousInstruction.getRegisterAvailability();
        Instruction.SEGMENT stageCurInstructionRegRequired = currentInstruction.getRegisterRequired();


        if (forwarding) {

        }

        if (!forwarding) {

        }

        return 0;
    }

    private void determineRegisterAvailability(boolean forwardingEnabled, Instruction instruction) {
        if(forwardingEnabled) {
            if (instruction.getOperand().equalsIgnoreCase("ADD")) {
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.EXECUTE);
            }

            if (instruction.getOperand().equalsIgnoreCase("SUB")) {
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.EXECUTE);
            }

            if (instruction.getOperand().equalsIgnoreCase("AND")) {
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.EXECUTE);
            }

            if (instruction.getOperand().equalsIgnoreCase("OR")) {
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.EXECUTE);
            }

            if(instruction.getOperand().equalsIgnoreCase("LW")) {

                //DOUBLE CHECK THIS......
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.MEMORY);
            }

            if(instruction.getOperand().equalsIgnoreCase("SW")) {
                //DOUBLE CHECK THIS...... ALSO....
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.MEMORY);
            }


        }

        if(!forwardingEnabled) {
            if (instruction.getOperand().equalsIgnoreCase("ADD")) {
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.WRITE_BACK);
            }

            if (instruction.getOperand().equalsIgnoreCase("SUB")) {
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.WRITE_BACK);
            }

            if (instruction.getOperand().equalsIgnoreCase("AND")) {

                //DOUBLE CHECK THIS......
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.EXECUTE);
            }

            if (instruction.getOperand().equalsIgnoreCase("OR")) {

                //DOUBLE CHECK THIS......
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.EXECUTE);
            }

            if(instruction.getOperand().equalsIgnoreCase("LW")) {

                //DOUBLE CHECK THIS......
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.MEMORY);
            }

            if(instruction.getOperand().equalsIgnoreCase("SW")) {
                //DOUBLE CHECK THIS...... ALSO....
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.WRITE_BACK);
            }
        }

    }


    public void printToConsole(Instruction instruction) {


    }

    public void constructPipelineSequence(Instruction instruction, int stallsRequired) {

        if (instruction.getInstructionNumber() == 1) {
            pipelineSequence.add((new ArrayList<Instruction.SEGMENT>(Arrays.asList(Instruction.SEGMENT.FETCH,
                    Instruction.SEGMENT.DECODE,
                    Instruction.SEGMENT.EXECUTE,
                    Instruction.SEGMENT.MEMORY,
                    Instruction.SEGMENT.WRITE_BACK))));
        }

        try {
            int index = instruction.getInstructionNumber() - 1;

            int stallCounter = stallsRequired;
            int cycleIndex = 1;


            if (stallsRequired == 0) {
                pipelineSequence.add(index, new ArrayList<Instruction.SEGMENT>(Arrays.asList(Instruction.SEGMENT.FETCH,
                        Instruction.SEGMENT.DECODE,
                        Instruction.SEGMENT.EXECUTE,
                        Instruction.SEGMENT.MEMORY,
                        Instruction.SEGMENT.WRITE_BACK)));
            }

            if (stallsRequired != 0) {
                pipelineSequence.add(index, new ArrayList<Instruction.SEGMENT>(Arrays.asList(Instruction.SEGMENT.FETCH)));
                while (stallCounter != 0) {
                    pipelineSequence.get(index).add(cycleIndex, Instruction.SEGMENT.STALL);
                    stallCounter --;
                    cycleIndex ++;
                }


                for(int i = 1; i < 5; i ++) {
                    pipelineSequence.get(index).add(cycleIndex, Instruction.SEGMENT.valueOf(i));
                    cycleIndex++;
                }
            }

        } catch (Exception e) {
            e.getMessage();
            Log.e(TAG, "First sequence of pipeline has not been created...");
        }
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();

        //Singleton instance for cache will be deleted by the OS
        Toast.makeText(this, "We're running out of memory!!!!", Toast.LENGTH_SHORT).show();
    }
}