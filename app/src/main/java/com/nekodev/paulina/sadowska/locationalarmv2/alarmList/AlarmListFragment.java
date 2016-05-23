package com.nekodev.paulina.sadowska.locationalarmv2.alarmList;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nekodev.paulina.sadowska.locationalarmv2.R;
import com.nekodev.paulina.sadowska.locationalarmv2.data.DataManager;

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
        DataManager manager = new DataManager(getActivity().getFilesDir().getPath(), "alarms");
        mAlarmListAdapter = new AlarmListItemAdapter(manager.getAlarmData(), getActivity());
        mAlarmRecyclerView.setAdapter(mAlarmListAdapter);
    }
}
