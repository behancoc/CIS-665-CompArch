package com.bhancock.bisc.emulator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.InstructionHolder> {

    private List<Instruction> instructions = new ArrayList<>();

    @NonNull
    @Override
    public InstructionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.instruction_item, parent, false);

        return new InstructionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionHolder holder, int position) {
        Instruction currentInstruction = instructions.get(position);
        holder.textViewTitle.setText(currentInstruction.getFormat());
        holder.textViewDescription.setText(currentInstruction.getOpcode());
        holder.textViewPriority.setText(String.valueOf(3));
    }

    @Override
    public int getItemCount() {
        return instructions.size();
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
        notifyDataSetChanged();
    }

    class InstructionHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public InstructionHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
        }
    }
}
