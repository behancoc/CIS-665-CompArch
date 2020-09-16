package com.bhancock.pipelinedecoderapp;

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
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int OPEN_DOCUMENT_REQUEST_CODE = 1;

    private boolean forwardingEnabled = false;
    private Uri uri = null;

    InstructionCache instructionCache;
    ArrayList<ArrayList<Instruction.SEGMENT>> pipelineSequence =
            new ArrayList<ArrayList<Instruction.SEGMENT>>();

    Hashtable<Integer, DEPENDENCY> hazardTable = new Hashtable <>();
    Hashtable<Integer, List<Instruction.SEGMENT>> cycleSegments = new Hashtable <>();
    List<Instruction.SEGMENT> segmentListPerCycle = new ArrayList<>();

    public enum DEPENDENCY {
            READ_AFTER_WRITE,
            WRITE_AFTER_WRITE,
            WRITE_AFTER_READ,
            STRUCTURAL,
            NONE;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        instructionCache = InstructionCache.getInstance(getApplicationContext());
        Switch forwardingSwitch = findViewById(R.id.forwarding_switch);
        Button openFile = findViewById(R.id.open_file);
        Button runPipelineDecoder = findViewById(R.id.run_program);




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

        runPipelineDecoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Instruction firstInstruction = new Instruction(1, "ADD", "R1", "R2", "R3");
//                Instruction secondInstruction = new Instruction(2, "SUB", "R4", "R1", "R5");
//                Instruction thirdInstruction = new Instruction(3, "LW", "T0", 0, "S2");
//                Instruction fourthInstruction = new Instruction(4, "ADDI", "S2", "S2", 4);
//                Instruction fifthInstruction = new Instruction(5, "ADD", "T0", "S2", 4);
//                Instruction sixthInstruction = new Instruction(6, "SW", "T0", 0, "S4");
//                Instruction seventhInstruction = new Instruction(7, "ADD", "S4", "S4", 4);
//                Instruction eighthInstruction = new Instruction(8, "ADD", "S0", "S0", 1);

                Instruction firstInstruction = new Instruction(1, "ADDI", "S0", "0", "0");
                Instruction secondInstruction = new Instruction(2, "ADD", "S10", "S1", "S0");
                Instruction thirdInstruction = new Instruction(3, "LW", "T0", 0, "S2");
                Instruction fourthInstruction = new Instruction(4, "ADDI", "S2", "S2", 4);
                Instruction fifthInstruction = new Instruction(5, "ADDI", "T0", "T0", 5);
                Instruction sixthInstruction = new Instruction(6, "SW", "T0", 0, "S4");
                Instruction seventhInstruction = new Instruction(7, "ADDI", "S4", "S4", 4);
                Instruction eighthInstruction = new Instruction(8, "ADDI", "S0", "S0", 1);


                //TODO: Temporary, reading from file should write to cache directly!
                List<Instruction> instructionList = new ArrayList<>();


                instructionList.add(0, firstInstruction);
                instructionList.add(1, secondInstruction);
                instructionList.add(2, thirdInstruction);
                instructionList.add(3, fourthInstruction);
                instructionList.add(4, fifthInstruction);
                instructionList.add(5, sixthInstruction);
                instructionList.add(6, seventhInstruction);
                instructionList.add(7, eighthInstruction);

                initializeInstructionCache(instructionList);
                executePipeline();


                Toast.makeText(getApplicationContext(), "Running Program", Toast.LENGTH_SHORT).show();
            }
        });
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
            }
        }
    }


    private DEPENDENCY dataDependencyCheck(Instruction currentInstruction, Instruction previousInstruction) {

        String currentInstructionDestinationRegister = null;
        String currentInstructionSourceReg1 = null;
        String currentInstructionSourceReg2 = null;
        String currentInstructionBaseAddress = null;
        int currentInstructionOffset = -100;

        String previousInstructionDestinationRegister = null;
        String previousInstructionSourceReg1 = null;
        String previousInstructionSourceReg2 = null;
        String previousInstructionBaseAddress = null;
        int previousInstructionOffset = -100;


        //Handling R Type instructions
        if(currentInstruction.getOperand().equalsIgnoreCase("ADD") ||
                currentInstruction.getOperand().equalsIgnoreCase("SUB")) {

            currentInstructionDestinationRegister = currentInstruction.getDestinationRegister();
            currentInstructionSourceReg1 = currentInstruction.getSourceRegister1();
            currentInstructionSourceReg2 = currentInstruction.getSourceRegister2();
        }

        if (previousInstruction.getOperand().equalsIgnoreCase("ADD") ||
                previousInstruction.getOperand().equalsIgnoreCase("SUB")) {

            previousInstructionDestinationRegister = previousInstruction.getDestinationRegister();
            previousInstructionSourceReg1 = previousInstruction.getSourceRegister1();
            previousInstructionSourceReg2 = previousInstruction.getSourceRegister2();
        }


        //Handling I Type instructions
        if (currentInstruction.getOperand().equalsIgnoreCase("LW") ||
            currentInstruction.getOperand().equalsIgnoreCase("SW")) {

            currentInstructionDestinationRegister = currentInstruction.getDestinationRegister();
            currentInstructionBaseAddress = currentInstruction.getBaseAddress();
            currentInstructionOffset = currentInstruction.getOffset();
        }

        if (previousInstruction.getOperand().equalsIgnoreCase("LW") ||
                previousInstruction.getOperand().equalsIgnoreCase("SW")) {

            previousInstructionDestinationRegister = previousInstruction.getDestinationRegister();
            previousInstructionBaseAddress = previousInstruction.getBaseAddress();
            previousInstructionOffset = previousInstruction.getOffset();
        }

        if(currentInstruction.getOperand().equalsIgnoreCase("ADDI")) {
            currentInstructionDestinationRegister = currentInstruction.getDestinationRegister();
            currentInstructionBaseAddress = currentInstruction.getBaseAddress();
            currentInstructionOffset = currentInstruction.getOffset();
        }

        if(previousInstruction.getOperand().equalsIgnoreCase("ADDI")) {
            previousInstructionDestinationRegister = previousInstruction.getDestinationRegister();
            previousInstructionBaseAddress = previousInstruction.getBaseAddress();
            previousInstructionOffset = previousInstruction.getOffset();
        }



        //Handling R-type data hazards
        try {

            //Previous command was SW... this isn't technically a data hazard
            if((previousInstructionDestinationRegister.equalsIgnoreCase(currentInstructionSourceReg1) ||
               previousInstructionDestinationRegister.equalsIgnoreCase(currentInstructionSourceReg2)) &&
               previousInstruction.getOperand().equalsIgnoreCase("SW") &&
                    (currentInstruction.getOperand().equalsIgnoreCase("ADD")
                            || currentInstruction.getOperand().equalsIgnoreCase("SUB"))) {

                return DEPENDENCY.NONE;
            }

            if(currentInstructionSourceReg1.equalsIgnoreCase(previousInstructionDestinationRegister) ||
                    currentInstructionSourceReg2.equalsIgnoreCase(previousInstructionDestinationRegister)) {

                //THIS IS THE TRUE DEPENDENCE > FOCUS ON THIS SPECIFIC CASE FIRST!
                Log.d(TAG, "POSSIBLE READ AFTER WRITE (RAW) DATA DEPENDENCY!");
                hazardTable.put(currentInstruction.getInstructionNumber(), DEPENDENCY.READ_AFTER_WRITE);

                return DEPENDENCY.READ_AFTER_WRITE;

            } else if (currentInstructionDestinationRegister.equalsIgnoreCase(previousInstructionDestinationRegister)) {

                Log.d(TAG, "POSSIBLE WRITE AFTER WRITE (WAW) DATA DEPENDENCY!");
                hazardTable.put(currentInstruction.getInstructionNumber(), DEPENDENCY.WRITE_AFTER_WRITE);

                return DEPENDENCY.WRITE_AFTER_WRITE;

            } else if (currentInstructionDestinationRegister.equalsIgnoreCase(previousInstructionSourceReg1) ||
                    currentInstructionDestinationRegister.equalsIgnoreCase(previousInstructionSourceReg2)) {
                Log.d(TAG, "POSSIBLE WRITE AFTER READ (WAR) DATA DEPENDENCY!");
                hazardTable.put(currentInstruction.getInstructionNumber(), DEPENDENCY.WRITE_AFTER_READ);

                return DEPENDENCY.WRITE_AFTER_READ;
            }
            else {
                return DEPENDENCY.NONE;
            }


        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }

        //Handling I-type data hazards
        try {
            if(currentInstructionDestinationRegister.equalsIgnoreCase(previousInstructionDestinationRegister)) {
                Log.d(TAG, "POSSIBLE WRITE AFTER WRITE (WAW) DATA DEPENDENCY!");
                return DEPENDENCY.WRITE_AFTER_WRITE;
            }
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }

        return DEPENDENCY.NONE;
    }


    private int determineNumberOfStalls(Boolean forwarding, Instruction currentInstruction, Instruction previousInstruction) {


        DEPENDENCY dataDependency = dataDependencyCheck(currentInstruction, previousInstruction);

        if(dataDependency != DEPENDENCY.NONE) {
            //  1.)  When is the register available?
            Instruction.SEGMENT prevInstructionRegisterAvail = determineRegisterAvailability(forwarding, previousInstruction);

            //  2.)  When is the register needed?
            determineRegisterNeeded(forwarding, currentInstruction);
            Instruction.SEGMENT currentInstructionRegisterNeeded = determineRegisterNeeded(forwarding, currentInstruction);

            //  3.) Out of sequence?
            // Subtract 1 because pipeline sequence is 0 based but unfortunately our instructions numbers are not
            int cycleRegisterNeeded = pipelineSequence.get(currentInstruction.getInstructionNumber() - 1).indexOf(currentInstructionRegisterNeeded);
            int cycleRegisterAvail = pipelineSequence.get(previousInstruction.getInstructionNumber() - 1).indexOf(prevInstructionRegisterAvail);

            Log.d(TAG, "cycleRegisterNeeded: " + cycleRegisterNeeded);
            Log.d(TAG, "cycleRegisterAvail: " + cycleRegisterAvail);

            if(cycleRegisterNeeded < cycleRegisterAvail) {
                Log.d(TAG, "Yeah we should stall...");

                //Stall cycles detected
                return cycleRegisterAvail - cycleRegisterNeeded;
            }
        }

        return 0;
    }

    private Instruction.SEGMENT determineRegisterAvailability(boolean forwardingEnabled, Instruction instruction) {

        Instruction.SEGMENT stage = Instruction.SEGMENT.UNKNOWN;

        if(forwardingEnabled) {
            if (instruction.getOperand().equalsIgnoreCase("ADD")) {
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.EXECUTE);
                stage = Instruction.SEGMENT.EXECUTE;
            }

            if (instruction.getOperand().equalsIgnoreCase("ADDI")) {
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.EXECUTE);
                stage = Instruction.SEGMENT.EXECUTE;
            }

            if (instruction.getOperand().equalsIgnoreCase("SUB")) {
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.EXECUTE);
                stage = Instruction.SEGMENT.EXECUTE;
            }

            if (instruction.getOperand().equalsIgnoreCase("AND")) {
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.EXECUTE);
                stage = Instruction.SEGMENT.EXECUTE;
            }

            if (instruction.getOperand().equalsIgnoreCase("OR")) {
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.EXECUTE);
                stage = Instruction.SEGMENT.EXECUTE;
            }

            if(instruction.getOperand().equalsIgnoreCase("LW")) {

                //DOUBLE CHECK THIS......
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.MEMORY);
                stage = Instruction.SEGMENT.MEMORY;
            }

            if(instruction.getOperand().equalsIgnoreCase("SW")) {
                //DOUBLE CHECK THIS...... ALSO....
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.MEMORY);
                stage = Instruction.SEGMENT.MEMORY;
            }
        }

        if(!forwardingEnabled) {
            if (instruction.getOperand().equalsIgnoreCase("ADD")) {
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.WRITE_BACK);
                stage = Instruction.SEGMENT.WRITE_BACK;
            }

            if (instruction.getOperand().equalsIgnoreCase("ADDI")) {
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.WRITE_BACK);
                stage = Instruction.SEGMENT.WRITE_BACK;
            }

            if (instruction.getOperand().equalsIgnoreCase("SUB")) {
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.WRITE_BACK);
                stage = Instruction.SEGMENT.WRITE_BACK;
            }

            if (instruction.getOperand().equalsIgnoreCase("AND")) {

                //DOUBLE CHECK THIS......
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.WRITE_BACK);
                stage = Instruction.SEGMENT.EXECUTE;
            }

            if (instruction.getOperand().equalsIgnoreCase("OR")) {

                //DOUBLE CHECK THIS......
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.WRITE_BACK);
                stage = Instruction.SEGMENT.EXECUTE;
            }

            if(instruction.getOperand().equalsIgnoreCase("LW")) {
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.WRITE_BACK);
                stage = Instruction.SEGMENT.MEMORY;
            }

            if(instruction.getOperand().equalsIgnoreCase("SW")) {
                //DOUBLE CHECK THIS...... ALSO....
                instructionCache.getInstruction(instruction.getInstructionNumber()).setRegisterAvailability(Instruction.SEGMENT.MEMORY);
                stage = Instruction.SEGMENT.WRITE_BACK;
            }
        }
        return stage;
    }

    private Instruction.SEGMENT determineRegisterNeeded(boolean forwardingEnabled, Instruction instruction) {

        //TODO: DOUBLE CHECK THE RETURN SEGMENT FOR ALL SCENARIOS

        Instruction.SEGMENT stage = Instruction.SEGMENT.UNKNOWN;

        if(forwardingEnabled) {
            if(instruction.getOperand().equalsIgnoreCase("ADD")) {
                instructionCache.getInstruction(instruction.getInstructionNumber())
                        .setRegisterRequired(Instruction.SEGMENT.DECODE);

                stage = Instruction.SEGMENT.DECODE;
            }

            if(instruction.getOperand().equalsIgnoreCase("ADDI")) {
                instructionCache.getInstruction(instruction.getInstructionNumber())
                        .setRegisterRequired(Instruction.SEGMENT.DECODE);

                stage = Instruction.SEGMENT.DECODE;
            }

            if(instruction.getOperand().equalsIgnoreCase("SUB")) {
                instructionCache.getInstruction(instruction.getInstructionNumber())
                        .setRegisterRequired(Instruction.SEGMENT.DECODE);

                stage = Instruction.SEGMENT.DECODE;
            }

            if(instruction.getOperand().equalsIgnoreCase("AND")) {
                instructionCache.getInstruction(instruction.getInstructionNumber())
                        .setRegisterRequired(Instruction.SEGMENT.DECODE);

                stage = Instruction.SEGMENT.DECODE;
            }

            if(instruction.getOperand().equalsIgnoreCase("OR")) {
                instructionCache.getInstruction(instruction.getInstructionNumber())
                        .setRegisterRequired(Instruction.SEGMENT.DECODE);

                stage = Instruction.SEGMENT.DECODE;
            }

            if(instruction.getOperand().equalsIgnoreCase("LW")) {
                instructionCache.getInstruction(instruction.getInstructionNumber())
                        .setRegisterRequired(Instruction.SEGMENT.DECODE);

                stage = Instruction.SEGMENT.DECODE;
            }

            if(instruction.getOperand().equalsIgnoreCase("SW")) {
                instructionCache.getInstruction(instruction.getInstructionNumber())
                        .setRegisterRequired(Instruction.SEGMENT.DECODE);

                stage = Instruction.SEGMENT.DECODE;
            }
        }


        if (!forwardingEnabled) {
            if(instruction.getOperand().equalsIgnoreCase("ADD")) {
                instructionCache.getInstruction(instruction.getInstructionNumber())
                        .setRegisterRequired(Instruction.SEGMENT.DECODE);

                stage = Instruction.SEGMENT.DECODE;
            }

            if(instruction.getOperand().equalsIgnoreCase("ADDI")) {
                instructionCache.getInstruction(instruction.getInstructionNumber())
                        .setRegisterRequired(Instruction.SEGMENT.DECODE);

                stage = Instruction.SEGMENT.DECODE;
            }

            if(instruction.getOperand().equalsIgnoreCase("SUB")) {
                instructionCache.getInstruction(instruction.getInstructionNumber())
                        .setRegisterRequired(Instruction.SEGMENT.DECODE);

                stage = Instruction.SEGMENT.DECODE;
            }

            if(instruction.getOperand().equalsIgnoreCase("AND")) {
                instructionCache.getInstruction(instruction.getInstructionNumber())
                        .setRegisterRequired(Instruction.SEGMENT.DECODE);

                stage = Instruction.SEGMENT.DECODE;
            }

            if(instruction.getOperand().equalsIgnoreCase("OR")) {
                instructionCache.getInstruction(instruction.getInstructionNumber())
                        .setRegisterRequired(Instruction.SEGMENT.DECODE);

                stage = Instruction.SEGMENT.DECODE;
            }

            if(instruction.getOperand().equalsIgnoreCase("LW")) {
                instructionCache.getInstruction(instruction.getInstructionNumber())
                        .setRegisterRequired(Instruction.SEGMENT.DECODE);

                stage = Instruction.SEGMENT.DECODE;
            }

            if(instruction.getOperand().equalsIgnoreCase("SW")) {
                instructionCache.getInstruction(instruction.getInstructionNumber())
                        .setRegisterRequired(Instruction.SEGMENT.DECODE);

                stage = Instruction.SEGMENT.DECODE;
            }
        }
        return stage;
    }

    private void initializeInstructionCache(List<Instruction> instructions){
        for(Instruction instruction: instructions) {
            instructionCache.addInstruction(instruction.getInstructionNumber(), instruction);
        }
    }

    private void updateCache(Enumeration enumeration) {
        int cacheIndex = 1;
        while(enumeration.hasMoreElements()) {
            instructionCache.addInstruction(cacheIndex, (Instruction) enumeration.nextElement());
            cacheIndex ++;
        }
    }


    public void printToConsole() {
        StringBuilder stringBuilder = new StringBuilder();
        String result = "";
        stringBuilder.append(result);

        for(int i = 0; i < pipelineSequence.size(); i++){
            for(int j = 0; j < pipelineSequence.get(i).size(); j++){

                stringBuilder.append("|");
                stringBuilder.append(pipelineSequence.get(i).get(j));
                stringBuilder.append("|");
            }

            stringBuilder.append("\n");
        }
        Log.d(TAG, "PIPELINE_SEQUENCE: " + "\n" + stringBuilder.toString());

        Log.d(TAG, "Hazards: " +"\n");
        getStats();

    }

    public void constructPipelineSequence(Instruction instruction, int stallsRequired) {


        if (instruction.getInstructionNumber() == 1) {
            pipelineSequence.add((new ArrayList<Instruction.SEGMENT>(Arrays.asList(Instruction.SEGMENT.FETCH,
                    Instruction.SEGMENT.DECODE,
                    Instruction.SEGMENT.EXECUTE,
                    Instruction.SEGMENT.MEMORY,
                    Instruction.SEGMENT.WRITE_BACK))));
            return;
        }

        try {
            int index = instruction.getInstructionNumber() - 1;

            int stallCounter = stallsRequired;
            int cycleIndex = 1;


            if (stallsRequired == 0 && instruction.getInstructionNumber() != 1) {
                flushPreStallDetectionPipelineSequence(instruction);
                // Actually the instruction number dictates when the first FETCH is issued....
                // Staging of FETCH always occurs within a cycle number that matches the instruction number
                // Therefore...

                pipelineSequence.add(index, new ArrayList<Instruction.SEGMENT>(Arrays.asList(Instruction.SEGMENT.EMPTY)));
                cycleIndex ++;
                while(cycleIndex != instruction.getInstructionNumber()) {
                    pipelineSequence.get(index).add(cycleIndex - 1, Instruction.SEGMENT.EMPTY);
                    cycleIndex ++;
                }

                pipelineSequence.get(index).add(cycleIndex - 1, Instruction.SEGMENT.FETCH);

                for(int i = 1; i < 5; i ++) {
                    pipelineSequence.get(index).add(cycleIndex, Instruction.SEGMENT.valueOf(i));
                    cycleIndex++;
                }

            }

            if (stallsRequired != 0 && instruction.getInstructionNumber() != 1) {

                //Flush
                Log.d(TAG, "Instruction Number to be flushed: " + instruction.getInstructionNumber());
                flushPreStallDetectionPipelineSequence(instruction);


                // Actually the instruction number dictates when the first FETCH is issued....
                // Staging of FETCH always occurs within a cycle number that matches the instruction number
                // Therefore...

                pipelineSequence.add(index, new ArrayList<Instruction.SEGMENT>(Arrays.asList(Instruction.SEGMENT.EMPTY)));
                cycleIndex ++;
                while(cycleIndex != instruction.getInstructionNumber()) {
                    pipelineSequence.get(index).add(cycleIndex - 1, Instruction.SEGMENT.EMPTY);
                    cycleIndex ++;
                }

//                pipelineSequence.add(index, new ArrayList<Instruction.SEGMENT>(Arrays.asList(Instruction.SEGMENT.FETCH)));
                pipelineSequence.get(index).add(cycleIndex - 1, Instruction.SEGMENT.FETCH);
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


    private void constructPreStallDetectionPipelineSequence(Instruction instruction) {

        try {
            int index = instruction.getInstructionNumber() - 1;

            int cycleIndex = 1;


            if (instruction.getInstructionNumber() != 1) {

                // Actually the instruction number dictates when the first FETCH is issued....
                // Staging of FETCH always occurs within a cycle number that matches the instruction number
                // Therefore...

                pipelineSequence.add(index, new ArrayList<Instruction.SEGMENT>(Arrays.asList(Instruction.SEGMENT.EMPTY)));
                cycleIndex ++;
                while(cycleIndex != instruction.getInstructionNumber()) {
                    pipelineSequence.get(index).add(cycleIndex -1, Instruction.SEGMENT.EMPTY);
                    cycleIndex ++;
                }

                pipelineSequence.get(index).add(cycleIndex - 1, Instruction.SEGMENT.FETCH);

                for(int i = 1; i < 5; i ++) {
                    pipelineSequence.get(index).add(cycleIndex , Instruction.SEGMENT.valueOf(i));
                    cycleIndex++;
                }
            }

        } catch (Exception e) {
            e.getMessage();
            Log.e(TAG, "First sequence of pipeline has not been created...");
        }

    }

    private void flushPreStallDetectionPipelineSequence(Instruction instruction) {
        pipelineSequence.remove(instruction.getInstructionNumber() - 1);
    }

    private void executePipeline() {

        int instructionCounter = 1;
        Enumeration<Instruction> enumeration = instructionCache.getInstructions();
        int instructionCacheSize = instructionCache.getSize();

        constructPipelineSequence(instructionCache.getInstruction(instructionCounter), 0);

        while(enumeration.hasMoreElements()) {
            instructionCounter ++;

            if(instructionCounter > instructionCacheSize) {
                break;
            }

            constructPreStallDetectionPipelineSequence(instructionCache.getInstruction(instructionCounter));

            int stallsFromLastInstruction = determineNumberOfStalls(forwardingEnabled,
                    instructionCache.getInstruction(instructionCounter),
                    instructionCache.getInstruction(instructionCounter - 1));


            int stallsFromPreviousInstructions = 0;
//            try {
//                if(instructionCounter > 3) {
//                    //Check for addition reason to stall due to previous instructions
//                    stallsFromPreviousInstructions =  determineNumberOfStalls(forwardingEnabled,
//                            instructionCache.getInstruction(instructionCounter),
//                            instructionCache.getInstruction(instructionCounter - 2));
//                }
//            } catch (IndexOutOfBoundsException e) {
//                e.getMessage();
//            }

             int totalStalls = stallsFromLastInstruction + stallsFromPreviousInstructions;

            constructPipelineSequence(instructionCache.getInstruction(instructionCounter), totalStalls);
        }
        printToConsole();
        resetProgram();
    }

    private void resetProgram() {
        instructionCache.resetCache();
        pipelineSequence.clear();
        hazardTable.clear();
    };

    private void getStats() {
        Set<Integer> keys = hazardTable.keySet();
        for(Integer key: keys) {
            Log.d(TAG, "Hazard Detected at Instruction "+key+" is: "+hazardTable.get(key));
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        //Singleton instance for cache will be deleted by the OS
        Toast.makeText(this, "We're running out of memory!!!!", Toast.LENGTH_SHORT).show();
    }
}