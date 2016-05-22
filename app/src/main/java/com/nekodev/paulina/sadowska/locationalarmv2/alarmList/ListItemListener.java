package com.nekodev.paulina.sadowska.locationalarmv2.alarmList;

/**
 * Created by Paulina Sadowska on 22.05.2016.
 */
public interface ListItemListener {
    void onCardClicked(int pos);
    void onDeleteButtonClicked(int pos);
    void onIsActiveChange(int pos, boolean isChecked);
}
