package com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private String itemTitle;
    private ArrayList<String> itemOptions = new ArrayList<>();
    private ItemClickedListener listener;
    public void setOnItemClickedListener(ItemClickedListener listener){
        this.listener = listener;
    }

    public interface ItemClickedListener{
        void onItemClicked();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarm_details_item_spinner, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null) {
                    listener.onItemClicked();
                }
            }
        });
        ButterKnife.bind(this, view);
        this.title.setText(itemTitle);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_list_item, itemOptions);
        adapter.setDropDownViewResource(R.layout.spinner_list_item);
        this.option.setAdapter(adapter);
        return view;
    }
}
