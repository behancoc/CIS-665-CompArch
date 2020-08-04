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

import com.bhancock.bisc.emulator.R;
import com.bhancock.bisc.emulator.models.Instruction;

public class ResultAdapter extends ListAdapter<Instruction, ResultAdapter.ResultsHolder> {

    private final String TAG = ResultAdapter.class.getSimpleName();

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

    public ResultAdapter() {
        super(DIFF_UTIL_INSTRUCTION_CALLBACK);
    }

    @NonNull
    @Override
    public ResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, parent, false);

        return new ResultsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsHolder holder, int position) {
        Instruction currentInstruction = getItem(position);

        String placeholder = currentInstruction.getOpCodeStringRepresentation(Integer.valueOf(currentInstruction.getOpcode()));

        holder.textViewOpcodeResult.setText(currentInstruction.getInstructionFormat());


        holder.textViewDestRegResultValue.setText(currentInstruction.getDestinationRegister() + " 0x000");
        holder.textViewSrc1RegResultValue.setText(currentInstruction.getSourceRegister1() + " 0x001");
        holder.textViewSrc2RegResultValue.setText(currentInstruction.getSourceRegister2() + " 0x010");
        holder.textViewOpcodeResultValue.setText(currentInstruction.getOpCodeStringRepresentation(Integer.valueOf(currentInstruction.getOpcode())));

    }

    public Instruction getInstructionAt(int position) {
        return getItem(position);
    }

    class ResultsHolder extends RecyclerView.ViewHolder {
        private TextView textViewOpcodeResult;
        private TextView textViewDestRegResult;
        private TextView textViewSrc1RegResult;
        private TextView textViewSrc2RegResult;
        private TextView textViewExecutionResult;

        private TextView textViewOpcodeResultValue;
        private TextView textViewDestRegResultValue;
        private TextView textViewSrc1RegResultValue;
        private TextView textViewSrc2RegResultValue;
        private TextView textViewExecutionResultValue;



        public ResultsHolder(@NonNull View itemView) {
            super(itemView);
            textViewOpcodeResult = itemView.findViewById(R.id.text_view_opcode_result);
            textViewDestRegResult = itemView.findViewById(R.id.text_view_dest_register_result);
            textViewSrc1RegResult = itemView.findViewById(R.id.text_view_source_register1_result);
            textViewSrc2RegResult = itemView.findViewById(R.id.text_view_source_register2_result);
            textViewExecutionResult = itemView.findViewById(R.id.text_view_source_instruction_result);

            textViewOpcodeResultValue = itemView.findViewById(R.id.text_view_opcode_result_value);
            textViewDestRegResultValue = itemView.findViewById(R.id.text_view_dest_register_result_value);
            textViewSrc1RegResultValue = itemView.findViewById(R.id.text_view_source_register1_result_value);
            textViewSrc2RegResultValue = itemView.findViewById(R.id.text_view_source_register2_result_value);
            textViewExecutionResultValue = itemView.findViewById(R.id.text_view_source_instruction_result_value);

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
