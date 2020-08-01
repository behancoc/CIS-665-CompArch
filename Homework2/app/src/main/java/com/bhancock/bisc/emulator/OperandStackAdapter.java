package com.bhancock.bisc.emulator;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;

import java.util.HashMap;
import java.util.List;

public class OperandStackAdapter extends StackAdapter<Integer> {

    private static final String TAG = OperandStackAdapter.class.getSimpleName();

    private List<Instruction> mInstructionList;
    private Context mContext;
    public HashMap<Integer, String> instructionMapping = new HashMap<>();


    public OperandStackAdapter(Context context) {
        super(context);
    }

    @Override
    public void bindView(Integer data, int position, CardStackView.ViewHolder holder) {
        if (holder instanceof OperandItemViewHolder) {
            OperandItemViewHolder h = (OperandItemViewHolder) holder;
            h.onBind(data, position);
        }
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view;
        view = getLayoutInflater().inflate(R.layout.list_item, parent, false);
        return new OperandItemViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.list_item;
    }

    private static class OperandItemViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mTextTitle;
        private HashMap<Integer, String> instructionMapping = new HashMap<>();


        public OperandItemViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
        }

        @Override
        public void onItemExpand(boolean b) {
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }


        public void onBind(Integer data, int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);
            setInstructionMapping();
            Log.d(TAG, "Instruction mapping value from key " + instructionMapping.get(position));
            mTextTitle.setText(instructionMapping.get(position));
        }

        public void setInstructionMapping() {
            instructionMapping.put(0, "add");
            instructionMapping.put(1, "addc");
            instructionMapping.put(2, "addw");
            instructionMapping.put(3, "sub");
            instructionMapping.put(4, "subc");
            instructionMapping.put(5, "subw");
            instructionMapping.put(6, "logl");
            instructionMapping.put(7, "logr");
            instructionMapping.put(8, "asr");
            instructionMapping.put(9, "cmp");
            instructionMapping.put(10, "cmpn");
            instructionMapping.put(11, "and");
            instructionMapping.put(12, "eor");
            instructionMapping.put(13, "or");
            instructionMapping.put(14, "nor");
            instructionMapping.put(15, "nand");
            instructionMapping.put(16, "sb");
            instructionMapping.put(17, "sw");
            instructionMapping.put(18, "lb");
            instructionMapping.put(19, "lw");
            instructionMapping.put(20, "jal");
            instructionMapping.put(21, "mult");
            instructionMapping.put(22, "div");
            instructionMapping.put(23, "beq");
            instructionMapping.put(24, "bne");
            instructionMapping.put(25, "flsh");

        }

    }
}