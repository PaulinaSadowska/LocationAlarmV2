package com.nekodev.paulina.sadowska.locationalarmv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Paulina Sadowska on 14.05.2016.
 */
public class AlarmDetailsActivity extends AppCompatActivity {

    @Bind(R.id.alarm_details_title)
    TextView alarmTitle;
    @Bind(R.id.alarm_details_map_preview)
    ImageView mapPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_details_activity);
        ButterKnife.bind(this);

        alarmTitle.setText("Piotrowo 2, Poznań");
        mapPreview.setImageResource(R.drawable.maps_dummy);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.alarm_details_fragment, new AlarmDetailsFragment());

        transaction.commit();

    }

    @OnClick (R.id.alarm_details_edit_location_button)
    public void onEditLocationButtonClick() {
        Intent intent = new Intent(this, ChooseLocationActivity.class);
        startActivity(intent);
    }
}
