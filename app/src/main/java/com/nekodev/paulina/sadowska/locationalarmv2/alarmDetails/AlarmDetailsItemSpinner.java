package com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.nekodev.paulina.sadowska.locationalarmv2.Keys;
import com.nekodev.paulina.sadowska.locationalarmv2.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Paulina Sadowska on 15.05.2016.
 */
public class AlarmDetailsItemSpinner extends Fragment {

    @Bind(R.id.alarm_details_item_title)
    TextView title;
    @Bind(R.id.alarm_details_item_option)
    Spinner option;
    private int itemSelected = -1;

    private String itemTitle;
    private ArrayList<String> itemOptions = new ArrayList<>();
    private SpinnerSelectionChangedListener listener;
    public void setOnSpinnerSelectionChangedListener(SpinnerSelectionChangedListener listener){
        this.listener = listener;
    }

    public interface SpinnerSelectionChangedListener{
        void onSpinnerSelectionChanged(int position);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        itemTitle = args.getString(Keys.AlarmDetailsItemKeys.ITEM_TITLE_KEY);
        String[] optionsTemp = (String[]) args.getSerializable(Keys.AlarmDetailsItemKeys.ITEM_OPTION_KEY);
        if(optionsTemp!=null){
            for (String s : optionsTemp) {
                itemOptions.add(s);
            }
        }
    }

    public void setItemSelected(int itemNumber){
        this.itemSelected = itemNumber;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarm_details_item_spinner, container, false);
        ButterKnife.bind(this, view);
        this.title.setText(itemTitle);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_list_item, itemOptions);
        adapter.setDropDownViewResource(R.layout.spinner_list_item);
        this.option.setAdapter(adapter);
        if(itemSelected>-1){
            this.option.setSelection(itemSelected);
        }
        this.option.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=itemSelected){
                    itemSelected = position;
                    listener.onSpinnerSelectionChanged(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }
}
