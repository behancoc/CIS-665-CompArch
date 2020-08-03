package com.bhancock.bisc.emulator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bhancock.bisc.emulator.activities.InstructionTypeActivity;
import com.loopeer.cardstack.CardStackView;

import java.util.List;


public class MainActivity extends AppCompatActivity implements CardStackView.ItemExpendListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private InstructionViewModel instructionViewModel;


//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final InstructionAdapter adapter = new InstructionAdapter();
        recyclerView.setAdapter(adapter);


        instructionViewModel = ViewModelProviders.of(this).get(InstructionViewModel.class);
        instructionViewModel.getAllInstructions().observe(this, new Observer<List<Instruction>>() {
            @Override
            public void onChanged(List<Instruction> instructions) {
                //Update CardView... possibly.  Not really sure if this is a great approach yet.
//                Toast.makeText(getApplicationContext(), "onChanged", Toast.LENGTH_SHORT).show();

                adapter.setInstructions(instructions);
            }
        });


//        sharedPreferences = getSharedPreferences("UserSelections", Context.MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//        editor.putInt("Hello", 12);
//        editor.putInt("World", 14);
//        editor.apply();
//
//        String test = sharedPreferences.getString("InstructionFun", "");

//        Intent intent = new Intent(MainActivity.this, InstructionTypeActivity.class);
//        startActivity(intent);
    }

    @Override
    public void onItemExpend(boolean expend) {

    }
}