package com.nekodev.paulina.sadowska.locationalarmv2.alarmList;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nekodev.paulina.sadowska.locationalarmv2.Keys;
import com.nekodev.paulina.sadowska.locationalarmv2.R;
import com.nekodev.paulina.sadowska.locationalarmv2.Utilities;
import com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails.AlarmDetailsActivity;
import com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails.AlarmTypes;
import com.nekodev.paulina.sadowska.locationalarmv2.data.AlarmDataItem;
import com.nekodev.paulina.sadowska.locationalarmv2.data.DataManager;

import java.util.HashMap;

/**
 * Created by Paulina Sadowska on 12.03.16.
 */
public class AlarmListItemAdapter extends RecyclerView.Adapter<AlarmViewHolder> {

    private DataManager manager;
    private AlarmViewHolder mAlarmViewHolder;
    private Activity mActivity;
    private boolean onBind;

    private HashMap<Integer, Integer> alarmIds = new HashMap<>();
    private OnEmptyAdapterListener listener;
    public void setOnEmptyAdapterListener(OnEmptyAdapterListener listener){
        this.listener = listener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AlarmListItemAdapter(String path, String fileName, Activity mActivity) {
        this.mActivity = mActivity;
        manager = DataManager.getInstance(path, fileName);
        //initMockData();
        updateKeyMap();
    }

    private void updateKeyMap(){
        int position = 0;
        for (Integer alarmId : manager.getKeys()) {
            alarmIds.put(position, alarmId);
            position++;
        }
    }

    private void initMockData(){
        if(manager!=null) {
            manager.resetMockAlarmData();
        }
    }

    private int getAlarmId(int position){
        return alarmIds.get(position);
    }

    public void remove(int position) {
        manager.remove(getAlarmId(position));
        updateKeyMap();
        if(manager.numberOfAlarms()==0 && listener != null){
            listener.adapterBecomeEmpty();
        }
        if(!onBind){
            notifyDataSetChanged();
        }
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
        bindViewHolderData(holder, manager.get(getAlarmId(position)));
        holder.setListItemListener(new ListItemListener() {
            @Override
            public void onCardClicked(int pos) {
                Intent intent = new Intent(mActivity, AlarmDetailsActivity.class);
                intent.putExtra(Keys.ALARM_ID, getAlarmId(pos));
                mActivity.startActivity(intent);
            }

            @Override
            public void onDeleteButtonClicked(int position) {
                remove(position);
            }

            @Override
            public void onIsActiveChange(int position, boolean isChecked) {
                manager.editAlarmIsActive(getAlarmId(position), isChecked);
                if(!onBind) {
                    notifyItemChanged(position);
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
        holder.alarmDays.setText(Utilities.getActiveDaysString(mActivity.getResources(), alarmDataItem.getRepeatDays()));
        if(alarmDataItem.getAlarmType()== AlarmTypes.NOTIFICATION)
            holder.alarmNotificationType.setText(mActivity.getString(R.string.notifications));
        else
            holder.alarmNotificationType.setText(mActivity.getString(R.string.sound));

    }

    @Override
    public int getItemCount() {
        return manager.numberOfAlarms();
    }

    public boolean onBind() {
        return onBind;
    }
}
