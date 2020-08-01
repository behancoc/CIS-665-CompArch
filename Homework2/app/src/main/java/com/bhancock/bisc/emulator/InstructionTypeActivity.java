package com.bhancock.bisc.emulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

import com.loopeer.cardstack.CardStackView;

import java.util.Arrays;

public class InstructionTypeActivity extends AppCompatActivity implements CardStackView.ItemExpendListener {

    public static Integer[] TEST_DATAS = new Integer[]{
            R.color.color_1,
            R.color.color_2,
            R.color.color_3,
    };

    private CardStackView mInstructionTypeStackView;
    private LinearLayout mLinearLayout;
    private InstructionTypeStackAdapter mInstructionStackAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_type);

        mInstructionTypeStackView = findViewById(R.id.instruction_type_stack_view);
        mInstructionTypeStackView.setItemExpendListener(this);
        mInstructionStackAdapter = new InstructionTypeStackAdapter(this);
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

    }
}