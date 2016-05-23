package com.nekodev.paulina.sadowska.locationalarmv2.alarmList;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nekodev.paulina.sadowska.locationalarmv2.R;
import com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails.AlarmDetailsActivity;
import com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails.AlarmTypes;
import com.nekodev.paulina.sadowska.locationalarmv2.data.AlarmDataItem;

import java.util.ArrayList;

/**
 * Created by Paulina Sadowska on 12.03.16.
 */
public class AlarmListItemAdapter extends RecyclerView.Adapter<AlarmViewHolder> {

    private ArrayList<AlarmDataItem> mAlarmDataItems;
    private AlarmViewHolder mAlarmViewHolder;
    private Activity mActivity;
    private boolean onBind;

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
    public void onBindViewHolder(AlarmViewHolder holder, int position) {
        onBind = true;
        bindViewHolderData(holder, mAlarmDataItems.get(position));
        holder.setListItemListener(new ListItemListener() {
            @Override
            public void onCardClicked(int pos) {
                Intent intent = new Intent(mActivity, AlarmDetailsActivity.class);
                mActivity.startActivity(intent);
            }

            @Override
            public void onDeleteButtonClicked(int pos) {
                mAlarmDataItems.remove(pos);
                if(!onBind){
                    notifyItemChanged(pos);
                }
            }

            @Override
            public void onIsActiveChange(int pos, boolean isChecked) {
                mAlarmDataItems.get(pos).setIsActive(isChecked);
                if(!onBind) {
                    notifyItemChanged(pos);
                }
            }
        });
        onBind = false;
    }

    private void bindViewHolderData(AlarmViewHolder holder, AlarmDataItem alarmDataItem) {

        holder.alarmActiveSwitch.setChecked(alarmDataItem.getIsActive());
        holder.alarmLocalization.setText(alarmDataItem.getAddress());

        String radiusStr = " + " + alarmDataItem.getRadiusInKilometers() + mActivity.getString(R.string.km);
        holder.alarmRadius.setText(radiusStr);
       // holder.alarmDays.setText(Utilities.getActiveDaysString(mActivity.getResources(), alarmDataItem.getRepeatDays()));
        if(alarmDataItem.getAlarmType()== AlarmTypes.NOTIFICATION)
            holder.alarmNotificationType.setText(mActivity.getString(R.string.notifications));
        else
            holder.alarmNotificationType.setText(mActivity.getString(R.string.sound));

    }

    @Override
    public int getItemCount() {
        return mAlarmDataItems.size();
    }
}
