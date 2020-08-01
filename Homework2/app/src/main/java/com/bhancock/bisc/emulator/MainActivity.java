package com.bhancock.bisc.emulator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import com.loopeer.cardstack.CardStackView;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements CardStackView.ItemExpendListener {
    private static final String TAG = MainActivity.class.getSimpleName();

//    public static Integer[] TEST_DATAS = new Integer[]{
//            R.color.color_1,
//            R.color.color_2,
//            R.color.color_3,
//            R.color.color_4,
//            R.color.color_5,
//            R.color.color_6,
//            R.color.color_7,
//            R.color.color_8,
//            R.color.color_9,
//            R.color.color_10,
//            R.color.color_11,
//            R.color.color_12,
//            R.color.color_13,
//            R.color.color_14,
//            R.color.color_15,
//            R.color.color_16,
//            R.color.color_17,
//            R.color.color_18,
//            R.color.color_19,
//            R.color.color_20,
//            R.color.color_21,
//            R.color.color_22,
//            R.color.color_23,
//            R.color.color_24,
//            R.color.color_25,
//            R.color.color_26
//    };

//    private CardStackView mStackView;
//    private LinearLayout mLinearLayout;
//    private CardStackAdapter mCardStackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, InstructionTypeActivity.class);
        startActivity(intent);

//        mStackView = findViewById(R.id.stackview_main);
//        mStackView.setItemExpendListener(this);
//        mCardStackAdapter = new CardStackAdapter(this);
//        mStackView.setAdapter(mCardStackAdapter);
//
//        new Handler().postDelayed(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        mCardStackAdapter.updateData(Arrays.asList(TEST_DATAS));
//                    }
//                }
//                , 200
//        );


    }

    @Override
    public void onItemExpend(boolean expend) {
        //mLinearLayout.setVisibility(expend ? View.VISIBLE : View.GONE);
    }
}