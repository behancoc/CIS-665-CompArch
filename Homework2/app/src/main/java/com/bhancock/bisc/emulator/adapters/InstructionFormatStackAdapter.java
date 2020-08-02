package com.bhancock.bisc.emulator.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import com.bhancock.bisc.emulator.R;
import com.bhancock.bisc.emulator.activities.OperandActivity;
import com.bhancock.bisc.emulator.models.InstructionFormat;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class InstructionFormatStackAdapter extends StackAdapter<Integer> {

    private static final String TAG = InstructionFormatStackAdapter.class.getSimpleName();
    private Context mContext;

    public InstructionFormatStackAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void bindView(final Integer data, final int position, CardStackView.ViewHolder holder) {
        if (holder instanceof InstructionFormatStackAdapter.InstructionTypeItemViewHolder) {
            InstructionFormatStackAdapter.InstructionTypeItemViewHolder h = (InstructionFormatStackAdapter.InstructionTypeItemViewHolder) holder;
            h.onBind(data, position);
            h.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Confirm Selection");
                    builder.setMessage("Would you like to select this Instruction?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Log.d(TAG, "Possible data? " + position);

                            SharedPreferences sharedPreferences = mContext.getSharedPreferences("UserSelections", MODE_PRIVATE);
                            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                            sharedPreferencesEditor.putInt("InstructionFormat", data);
                            sharedPreferencesEditor.commit();
                            Toast.makeText(v.getContext(), "Successful write to Shared Preferences", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(mContext, OperandActivity.class);
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
        return new InstructionFormatStackAdapter.InstructionTypeItemViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.list_item;
    }

    private static class InstructionTypeItemViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mTextTitle;
        private HashMap<Integer, String> instructionFormatMapping = new HashMap<>();

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
            setUserInterfaceInstructionFormatMapping();
            mTextTitle.setText(instructionFormatMapping.get(position));
        }

        public void setUserInterfaceInstructionFormatMapping() {
            InstructionFormat formatTypeRRR = new InstructionFormat();
            InstructionFormat formatTypeRRI = new InstructionFormat();
            InstructionFormat formatTypeRI = new InstructionFormat();

            formatTypeRRR.setInstructionFormatName("RRR-type");
            formatTypeRRI.setInstructionFormatName("RRI-type");
            formatTypeRI.setInstructionFormatName("RI-type");

            instructionFormatMapping.put(0, formatTypeRRR.getInstructionFormatName());
            instructionFormatMapping.put(1, formatTypeRRI.getInstructionFormatName());
            instructionFormatMapping.put(2, formatTypeRI.getInstructionFormatName());
        }
    }
}
