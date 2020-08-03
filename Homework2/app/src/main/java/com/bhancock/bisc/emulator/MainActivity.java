package com.bhancock.bisc.emulator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopeer.cardstack.CardStackView;

import java.util.List;


public class MainActivity extends AppCompatActivity implements CardStackView.ItemExpendListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int ADD_INSTRUCTION_REQUEST = 1;
    public static final int EDIT_INSTRUCTION_REQUEST = 1;

    private InstructionViewModel instructionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addInstuctionButton = findViewById(R.id.add_instruction_button);
        addInstuctionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddOrEditInstructionActivity.class);
                startActivityForResult(intent, ADD_INSTRUCTION_REQUEST);
            }
        });

        FloatingActionButton runInstructionButton = findViewById(R.id.execute_instruction_button);
        runInstructionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        });

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

//                adapter.setInstructions(instructions);
                adapter.submitList(instructions);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                instructionViewModel.delete(adapter.getInstructionAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Instruction deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new InstructionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Instruction instruction) {
                Intent intent = new Intent(MainActivity.this, AddOrEditInstructionActivity.class);
                intent.putExtra(AddOrEditInstructionActivity.EXTRA_ID, instruction.getId());
                intent.putExtra(AddOrEditInstructionActivity.EXTRA_TITLE, instruction.getInstructionFormat());
                intent.putExtra(AddOrEditInstructionActivity.EXTRA_DESCRIPTION, instruction.getOpcode());
                startActivityForResult(intent, EDIT_INSTRUCTION_REQUEST);

            }
        });


//        Intent intent = new Intent(MainActivity.this, InstructionTypeActivity.class);
//        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_INSTRUCTION_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddOrEditInstructionActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddOrEditInstructionActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddOrEditInstructionActivity.EXTRA_PRIORITY, 1);

            Instruction instruction = new Instruction(title, description);
            instructionViewModel.insert(instruction);

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == ADD_INSTRUCTION_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddOrEditInstructionActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Instruction not updated!", Toast.LENGTH_SHORT).show();
            }

            String title = data.getStringExtra(AddOrEditInstructionActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddOrEditInstructionActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddOrEditInstructionActivity.EXTRA_PRIORITY, 1);

            Instruction instruction = new Instruction(title, description);
            instruction.setId(id);
            instructionViewModel.update(instruction);

            Toast.makeText(MainActivity.this, "Instruction Updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Instruction discarded", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_instuctions:
                instructionViewModel.deleteAllInstructions();
                Toast.makeText(this, "Deleted all instuctions in list!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemExpend(boolean expend) {

    }
}