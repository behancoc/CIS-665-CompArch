package com.bhancock.bisc.emulator.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bhancock.bisc.emulator.R;

public class AddOrEditInstructionActivity extends AppCompatActivity {

    public static final String TAG = AddOrEditInstructionActivity.class.getSimpleName();
    public static final String EXTRA_ID = "com.bhancock.bisc.emulator.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.bhancock.bisc.emulator.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.bhancock.bisc.emulator.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.bhancock.bisc.emulator.EXTRA_PRIORITY";

    private TextView instructionFormatTextView;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private EditText editTextDescription;
    private NumberPicker numberPicker;
    private NumberPicker opCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instruction);

        instructionFormatTextView = findViewById(R.id.instruction_format_selection_text_view);
        editTextDescription = (EditText) findViewById(R.id.edit_text_description);
        numberPicker = findViewById(R.id.number_picker_priority);
        opCodePicker = findViewById(R.id.opcode_picker);

        radioGroup = findViewById(R.id.radioGroup);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        opCodePicker.setMinValue(0);
        opCodePicker.setMaxValue(20);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Instruction");
            instructionFormatTextView.setText(intent.getStringExtra(EXTRA_TITLE));
            opCodePicker.setValue(Integer.parseInt(intent.getStringExtra(EXTRA_DESCRIPTION)));
            numberPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        } else {
            setTitle("Add Instruction");
        }
    }

    public void checkButton(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        instructionFormatTextView.setText(radioButton.getText());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_instruction_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_instruction:
                saveInstruction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveInstruction() {
        String title = instructionFormatTextView.getText().toString();
        String description = String.valueOf(opCodePicker.getValue());

        Log.d(TAG, "Description: " + description);
        int priority = numberPicker.getValue();

        if (title.trim().isEmpty()) {
            Toast.makeText(this, "Do something", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }
}