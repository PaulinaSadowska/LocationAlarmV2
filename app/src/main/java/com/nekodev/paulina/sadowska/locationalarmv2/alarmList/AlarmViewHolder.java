package com.nekodev.paulina.sadowska.locationalarmv2.alarmList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.nekodev.paulina.sadowska.locationalarmv2.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Paulina Sadowska on 12.03.16.
 */
public class AlarmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    @Bind(R.id.list_item_delete)
    ImageView deleteButton;
    @Bind(R.id.list_item_switch)
    Switch alarmActiveSwitch;
    @Bind(R.id.list_item_localization)
    TextView alarmLocalization;
    @Bind(R.id.list_item_notification_type)
    TextView alarmNotificationType;
    @Bind(R.id.list_item_radius)
    TextView alarmRadius;
    @Bind(R.id.list_item_active_days)
    TextView alarmDays;

    private ListItemListener listener;
    public void setListItemListener(ListItemListener listener){
        this.listener = listener;
    }

    public AlarmViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);
        alarmActiveSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(listener!=null)
                    listener.onIsActiveChange(getPosition(), isChecked);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)
                    listener.onDeleteButtonClicked(getPosition());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(listener!=null)
            listener.onCardClicked(getPosition());
    }
}

