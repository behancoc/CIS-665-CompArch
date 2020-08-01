package com.bhancock.bisc.emulator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.main_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(mLayoutManager);
        populateRecyclerView();

    }

    private void populateRecyclerView() {

        Instruction addInstruction = new Instruction("add");
        Instruction addiInstruction = new Instruction("addi");
        Instruction subInstruction = new Instruction("sub");

        ArrayList<Instruction> instructions = new ArrayList<Instruction>();

        instructions.add(addInstruction);
        instructions.add(addiInstruction);
        instructions.add(subInstruction);

        Log.d(TAG, "The size of the list is:  " + instructions.size());


        mAdapter = new MyRecyclerAdapter(instructions, this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }
}