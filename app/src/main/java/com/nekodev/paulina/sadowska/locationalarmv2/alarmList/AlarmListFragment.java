package com.nekodev.paulina.sadowska.locationalarmv2.alarmList;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nekodev.paulina.sadowska.locationalarmv2.R;
import com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails.AlarmTypes;

import java.util.ArrayList;

/**
 * Created by Paulina Sadowska on 08.05.2016.
 */
public class AlarmListFragment extends Fragment {

    private RecyclerView mAlarmRecyclerView;
    private AlarmListItemAdapter mAlarmListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alarm_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAlarmRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_alarm_list_recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAlarmRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<AlarmDataItem> mAlarmDataItems;
        mAlarmDataItems = new ArrayList<>();

        AlarmDataItem item = new AlarmDataItem();
        item.setIsActive(true);
        item.setRadiusInMeters(3000);
        item.setAlarmType(AlarmTypes.NOTIFICATION);
        item.setLocation("Piotrowo 2");
        item.setRepeatDays(new boolean[]{true, false, false, true, false, true, false});
        mAlarmDataItems.add(item);

        AlarmDataItem item2 = new AlarmDataItem();
        item2.setIsActive(false);
        item2.setRadiusInMeters(1500);
        item2.setAlarmType(AlarmTypes.SOUND);
        item2.setLocation("Warszawa Centralna");
        item2.setRepeatDays(new boolean[]{true, true, true, true, true, true, true});
        mAlarmDataItems.add(item2);

        mAlarmListAdapter = new AlarmListItemAdapter(mAlarmDataItems, getActivity());
        mAlarmRecyclerView.setAdapter(mAlarmListAdapter);
    }
}
