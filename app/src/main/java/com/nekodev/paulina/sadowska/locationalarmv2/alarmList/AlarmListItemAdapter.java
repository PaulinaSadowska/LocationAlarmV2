package com.nekodev.paulina.sadowska.locationalarmv2.alarmList;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.nekodev.paulina.sadowska.locationalarmv2.R;

import java.util.ArrayList;

/**
 * Created by Paulina Sadowska on 12.03.16.
 */
public class AlarmListItemAdapter extends RecyclerView.Adapter<AlarmViewHolder> {

    private ArrayList<AlarmDataItem> mAlarmDataItems;
    private AlarmViewHolder mAlarmViewHolder;
    private Activity mActivity;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AlarmListItemAdapter(ArrayList<AlarmDataItem> alarmData, Activity mActivity) {
        this.mActivity = mActivity;
        this.mAlarmDataItems = alarmData;
    }

    public void remove(int position) {
        mAlarmDataItems.remove(position);
        notifyItemRemoved(position);
    }

    public void add(AlarmDataItem item) {
        mAlarmDataItems.add(item);
        notifyDataSetChanged();
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        // create a new view
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_list_item_simple, parent, false);
            mAlarmViewHolder = new AlarmViewHolder(v);
        return mAlarmViewHolder;
    }

    @Override
    public void onBindViewHolder(final AlarmViewHolder holder, final int position) {
        holder.bindViewHolder(mAlarmDataItems.get(position));
        holder.setClickListener(new AlarmViewHolder.ClickListener() {
            @Override
            public void onClick(View v, int pos, boolean isLongClick) {
                //showAlarmDetails(pos);
                Toast.makeText(mActivity, "CLICKED", Toast.LENGTH_SHORT).show();
            }
        });

        Switch isActiveSwitch = holder.getIsActiveSwitch();
        if (isActiveSwitch != null) {
            isActiveSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mAlarmDataItems.get(position).setIsActive(isChecked);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mAlarmDataItems.size();
    }
}
