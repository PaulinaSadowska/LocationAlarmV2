package com.nekodev.paulina.sadowska.locationalarmv2.alarmList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
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
    @Bind(R.id.list_item_localization)  TextView alarmLocalization;
    @Bind(R.id.list_item_notification_type)  TextView alarmNotificationType;
    @Bind(R.id.list_item_radius)        TextView alarmRadius;
    @Bind(R.id.list_item_active_days)   TextView alarmDays;

    private ClickListener clickListener;

    public AlarmViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);
    }

    public Switch getIsActiveSwitch() {
        return alarmActiveSwitch;
    }

    public ImageView getDeleteButton() {
        return deleteButton;
    }

    /* Interface for handling clicks - both normal and long ones. */
    public interface ClickListener {
        void onClick(View v, int position, boolean isLongClick);
    }

    /* Setter for listener. */
    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        clickListener.onClick(v, getPosition(), false);
    }
}

