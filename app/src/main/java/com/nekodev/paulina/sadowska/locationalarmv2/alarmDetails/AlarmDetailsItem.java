package com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nekodev.paulina.sadowska.locationalarmv2.Keys;
import com.nekodev.paulina.sadowska.locationalarmv2.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Paulina Sadowska on 15.05.2016.
 */
public class AlarmDetailsItem extends Fragment{

    @Bind(R.id.alarm_details_item_title)
    TextView title;
    @Bind(R.id.alarm_details_item_option)
    TextView option;

    private String itemTitle;
    private String itemOption;
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
        itemOption = args.getString(Keys.AlarmDetailsItemKeys.ITEM_OPTION_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarm_details_item, container, false);
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
        this.option.setText(itemOption);
        return view;
    }
}
