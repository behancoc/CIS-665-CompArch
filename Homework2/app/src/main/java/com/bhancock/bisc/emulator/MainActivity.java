package com.bhancock.bisc.emulator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import com.bhancock.bisc.emulator.activities.InstructionTypeActivity;
import com.loopeer.cardstack.CardStackView;


public class MainActivity extends AppCompatActivity implements CardStackView.ItemExpendListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, InstructionTypeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemExpend(boolean expend) {
        //mLinearLayout.setVisibility(expend ? View.VISIBLE : View.GONE);
    }
}