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
import com.bhancock.bisc.emulator.R;
import com.bhancock.bisc.emulator.activities.OperandActivity;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;

import java.util.HashMap;

public class InstructionTypeStackAdapter extends StackAdapter<Integer> {

    private static final String TAG = InstructionTypeStackAdapter.class.getSimpleName();
    private Context mContext;

    public InstructionTypeStackAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void bindView(Integer data, int position, CardStackView.ViewHolder holder) {
        if (holder instanceof InstructionTypeStackAdapter.InstructionTypeItemViewHolder) {
            InstructionTypeStackAdapter.InstructionTypeItemViewHolder h = (InstructionTypeStackAdapter.InstructionTypeItemViewHolder) holder;
            h.onBind(data, position);
            h.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Confirm Selection");
                    builder.setMessage("Would you like to select this Instruction?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(mContext,
                                    OperandActivity.class);
                            mContext.startActivity(intent);

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
        return new InstructionTypeStackAdapter.InstructionTypeItemViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.list_item;
    }

    private static class InstructionTypeItemViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mTextTitle;
        private HashMap<Integer, String> instructionMapping = new HashMap<>();

        public InstructionTypeItemViewHolder(View view) {
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
            instructionMapping.put(0, "i-type");
            instructionMapping.put(1, "r-type");
            instructionMapping.put(2, "j-type");
        }
    }
}
