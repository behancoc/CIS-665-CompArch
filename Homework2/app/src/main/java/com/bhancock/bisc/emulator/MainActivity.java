package com.bhancock.bisc.emulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.bhancock.bisc.emulator.activities.InstructionTypeActivity;
import com.loopeer.cardstack.CardStackView;


public class MainActivity extends AppCompatActivity implements CardStackView.ItemExpendListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("UserSelections", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("Hello", 12);
        editor.putInt("World", 14);
        editor.commit();


        Intent intent = new Intent(MainActivity.this, InstructionTypeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemExpend(boolean expend) {
        //mLinearLayout.setVisibility(expend ? View.VISIBLE : View.GONE);
    }
}