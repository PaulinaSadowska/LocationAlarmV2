package com.nekodev.paulina.sadowska.locationalarmv2.alarmList;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nekodev.paulina.sadowska.locationalarmv2.Constants;
import com.nekodev.paulina.sadowska.locationalarmv2.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Paulina Sadowska on 08.05.2016.
 */
public class AlarmListFragment extends Fragment {

    @Bind(R.id.fragment_alarm_list_recycler_view)
    RecyclerView mAlarmRecyclerView;
    @Bind(R.id.fragment_alarm_list_empty)
    View emptyView;

    private AlarmListItemAdapter mAlarmListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alarm_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAlarmRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAlarmListAdapter = new AlarmListItemAdapter(getActivity().getFilesDir().getPath(), Constants.FILE_NAME, getActivity());
        if(mAlarmListAdapter.getItemCount()==0){
            showEmptyView();
        }
        else{
            hideEmptyView();
        }
        mAlarmListAdapter.setOnEmptyAdapterListener(new OnEmptyAdapterListener() {
            @Override
            public void adapterBecomeEmpty() {
                showEmptyView();
            }
        });
        mAlarmRecyclerView.setAdapter(mAlarmListAdapter);
    }

    public void hideEmptyView(){
        emptyView.setVisibility(View.GONE);
        mAlarmRecyclerView.setVisibility(View.VISIBLE);
    }

    public void showEmptyView(){
        emptyView.setVisibility(View.VISIBLE);
        mAlarmRecyclerView.setVisibility(View.GONE);
    }
}
