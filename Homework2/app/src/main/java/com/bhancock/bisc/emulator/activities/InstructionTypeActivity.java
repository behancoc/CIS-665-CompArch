package com.bhancock.bisc.emulator.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.bhancock.bisc.emulator.adapters.InstructionFormatStackAdapter;
import com.bhancock.bisc.emulator.R;
import com.loopeer.cardstack.CardStackView;

import java.util.Arrays;

public class InstructionTypeActivity extends AppCompatActivity implements
        CardStackView.ItemExpendListener {

    private final String TAG = InstructionTypeActivity.class.getSimpleName();


    public static Integer[] TEST_DATAS = new Integer[]{
            R.color.color_1,
            R.color.color_2,
            R.color.color_3,
    };

    private CardStackView mInstructionTypeStackView;
    private LinearLayout mLinearLayout;
    private InstructionFormatStackAdapter mInstructionStackAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_type);

        mInstructionTypeStackView = findViewById(R.id.instruction_type_stack_view);
        mInstructionTypeStackView.setItemExpendListener(this);
        mInstructionStackAdapter = new InstructionFormatStackAdapter(this);
        mInstructionTypeStackView.setAdapter(mInstructionStackAdapter);



        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mInstructionStackAdapter.updateData(Arrays.asList(TEST_DATAS));
                    }
                }
                , 200
        );


    }


    @Override
    public void onItemExpend(boolean expend) {
        Log.d(TAG, "OnItemExpend");
    }

}