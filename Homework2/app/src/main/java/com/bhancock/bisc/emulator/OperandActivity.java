package com.bhancock.bisc.emulator;

import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.loopeer.cardstack.CardStackView;

import java.util.Arrays;

public class OperandActivity extends AppCompatActivity implements CardStackView.ItemExpendListener {

    public static Integer[] TEST_DATAS = new Integer[]{
            R.color.color_1,
            R.color.color_2,
            R.color.color_3,
            R.color.color_4,
            R.color.color_5,
            R.color.color_6,
            R.color.color_7,
            R.color.color_8,
            R.color.color_9,
            R.color.color_10,
            R.color.color_11,
            R.color.color_12,
            R.color.color_13,
            R.color.color_14,
            R.color.color_15,
            R.color.color_16,
            R.color.color_17,
            R.color.color_18,
            R.color.color_19,
            R.color.color_20,
            R.color.color_21,
            R.color.color_22,
            R.color.color_23,
            R.color.color_24,
            R.color.color_25,
            R.color.color_26
    };

    private CardStackView mOperandStackView;
    private LinearLayout mLinearLayout;
    private CardStackAdapter mCardStackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operand);

        mOperandStackView = findViewById(R.id.operand_stack_view);
        mOperandStackView.setItemExpendListener(this);
        mCardStackAdapter = new CardStackAdapter(this);
        mOperandStackView.setAdapter(mCardStackAdapter);

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mCardStackAdapter.updateData(Arrays.asList(TEST_DATAS));
                    }
                }
                , 200
        );
    }

    @Override
    public void onItemExpend(boolean expend) {

    }
}