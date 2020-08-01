package com.bhancock.bisc.emulator.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.bhancock.bisc.emulator.models.Instruction;
import com.bhancock.bisc.emulator.R;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;

import java.util.HashMap;
import java.util.List;

public class RegisterSelectionStackAdapter extends StackAdapter<Integer> {

    private static final String TAG = RegisterSelectionStackAdapter.class.getSimpleName();

    private List<Instruction> mInstructionList;
    private Context mContext;
    public HashMap<Integer, String> instructionMapping = new HashMap<>();
    private int registerSelectionCount = 0;


    public RegisterSelectionStackAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void bindView(Integer data, int position, CardStackView.ViewHolder holder) {
        if (holder instanceof ColorItemViewHolder) {
            ColorItemViewHolder h = (ColorItemViewHolder) holder;
            h.onBind(data, position);
            h.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Confirm Selection");
                    builder.setMessage("Would you like to select this Register?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            registerSelectionCount ++;
//                            AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
//                            builder1.setTitle("Register Selection");
//                            builder.setMessage("Please Select another Register");
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                    return true;
                }
            });
        }
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view;
        view = getLayoutInflater().inflate(R.layout.list_item, parent, false);
        return new ColorItemViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.list_item;
    }

    private static class ColorItemViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mTextTitle;
        private HashMap<Integer, String> instructionMapping = new HashMap<>();


        public ColorItemViewHolder(View view) {
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
            instructionMapping.put(0, "R0");
            instructionMapping.put(1, "R1");
            instructionMapping.put(2, "R2");
            instructionMapping.put(3, "R3");
            instructionMapping.put(4, "R4");
            instructionMapping.put(5, "R5");
            instructionMapping.put(6, "R6");
            instructionMapping.put(7, "R7");
        }
    }
}
