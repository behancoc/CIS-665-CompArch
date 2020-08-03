package com.bhancock.bisc.emulator.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bhancock.bisc.emulator.models.Instruction;
import com.bhancock.bisc.emulator.R;

public class InstructionAdapter extends ListAdapter<Instruction, InstructionAdapter.InstructionHolder> {

    private final String TAG = InstructionAdapter.class.getSimpleName();
    private static final DiffUtil.ItemCallback<Instruction> DIFF_UTIL_INSTRUCTION_CALLBACK = new DiffUtil.ItemCallback<Instruction>() {
        @Override
        public boolean areItemsTheSame(@NonNull Instruction oldItem, @NonNull Instruction newItem) {
            if (oldItem.getId() == newItem.getId()) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public boolean areContentsTheSame(@NonNull Instruction oldItem, @NonNull Instruction newItem) {
            if (oldItem.getInstructionFormat().equals(newItem.getInstructionFormat()) &&
                oldItem.getOpcode().equals(newItem.getOpcode())) {
                return true;
            } else {
                return false;
            }
        }
    };

    private OnItemClickListener onItemClickListener;

    public InstructionAdapter() {
        super(DIFF_UTIL_INSTRUCTION_CALLBACK);
    }

    @NonNull
    @Override
    public InstructionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.instruction_item, parent, false);

        return new InstructionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionHolder holder, int position) {
        Instruction currentInstruction = getItem(position);
        holder.textViewTitle.setText(currentInstruction.getInstructionFormat());
        holder.textViewDescription.setText(currentInstruction.getOpcode());
        holder.textViewPriority.setText(String.valueOf(3));
    }

    public Instruction getInstructionAt(int position) {
        return getItem(position);
    }

    class InstructionHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public InstructionHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_instruction_type);
            textViewDescription = itemView.findViewById(R.id.text_view_opcode);
            textViewPriority = itemView.findViewById(R.id.text_view_instruction_number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    try {
                        onItemClickListener.onItemClick(getItem(position));
                    } catch (NullPointerException e) {
                        Log.e(TAG, "Null Pointer just occurred " + e.getMessage());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Instruction instruction);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
