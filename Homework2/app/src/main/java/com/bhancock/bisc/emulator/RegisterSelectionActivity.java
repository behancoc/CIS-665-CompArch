package com.bhancock.bisc.emulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

import com.loopeer.cardstack.CardStackView;

import java.util.Arrays;

public class RegisterSelectionActivity extends AppCompatActivity implements CardStackView.ItemExpendListener {

    public static Integer[] TEST_DATAS = new Integer[]{
            R.color.color_1,
            R.color.color_2,
            R.color.color_3,
            R.color.color_4,
            R.color.color_5,
            R.color.color_6,
            R.color.color_7,
            R.color.color_8,
    };

    private CardStackView mRegisterSelectionCardStackView;
    private LinearLayout mLinearLayout;
    private RegisterSelectionStackAdapter mRegisterSelectionStackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_selection);

        mRegisterSelectionCardStackView = findViewById(R.id.register_selection_stack_view);
        mRegisterSelectionCardStackView.setItemExpendListener(this);
        mRegisterSelectionStackAdapter = new RegisterSelectionStackAdapter(this);
        mRegisterSelectionCardStackView.setAdapter(mRegisterSelectionStackAdapter);

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mRegisterSelectionStackAdapter.updateData(Arrays.asList(TEST_DATAS));
                    }
                }
                , 200
        );


    }

    @Override
    public void onItemExpend(boolean expend) {

    }
}