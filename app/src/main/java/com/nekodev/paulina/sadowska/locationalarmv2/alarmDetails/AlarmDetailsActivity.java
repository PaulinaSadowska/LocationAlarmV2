package com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nekodev.paulina.sadowska.locationalarmv2.Constants;
import com.nekodev.paulina.sadowska.locationalarmv2.Keys;
import com.nekodev.paulina.sadowska.locationalarmv2.R;
import com.nekodev.paulina.sadowska.locationalarmv2.alarmList.AlarmListActivity;
import com.nekodev.paulina.sadowska.locationalarmv2.chooseLocation.ChooseLocationActivity;
import com.nekodev.paulina.sadowska.locationalarmv2.data.AlarmDataItem;
import com.nekodev.paulina.sadowska.locationalarmv2.data.DataManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Paulina Sadowska on 14.05.2016.
 */
public class AlarmDetailsActivity extends AppCompatActivity {

    @Bind(R.id.alarm_details_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.alarm_details_title)
    TextView alarmTitle;
    @Bind(R.id.alarm_details_map_preview)
    ImageView mapPreview;
    @Bind(R.id.alarm_details_save)
    Button saveButton;
    @Bind(R.id.alarm_details_cancel)
    Button cancelButton;

    private AlarmDetailsFragment alarmDetailsFragment = new AlarmDetailsFragment();
    private AlarmDataItem alarmData;
    private DataManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_details_activity);
        ButterKnife.bind(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.alarm_details_fragment, alarmDetailsFragment);
        transaction.commit();

        manager = DataManager.getInstance(getFilesDir().getPath(), Constants.FILE_NAME);
        int alarmId = getIntent().getIntExtra(Keys.ALARM_ID, 0);
        alarmData = manager.get(alarmId);
        if(alarmData != null){
            alarmTitle.setText(alarmData.getAddress());
            Bitmap iconBitmap = BitmapFactory.decodeFile(getFilesDir().getPath()+"/"+alarmData.getImageName());
            if(iconBitmap!=null) {
                mapPreview.setImageBitmap(iconBitmap);
            }
        }
        Resources res = getResources();
        saveButton.setText(res.getString(R.string.save));
        cancelButton.setText(res.getString(R.string.cancel));
    }

    @OnClick(R.id.alarm_details_save)
    public void saveLocalization(View view) {
        Intent intent = new Intent(this, AlarmListActivity.class);
        manager.editAlarmDetails(alarmData.getAlarmId(), alarmDetailsFragment.getAlarmType(),
                alarmDetailsFragment.getAlarmSound(), alarmDetailsFragment.getRepeatDays());
        //save data in manager
        startActivity(intent);
    }

    @OnClick(R.id.alarm_details_cancel)
    public void cancelLocalization(View view) {
        Intent intent = new Intent(this, AlarmListActivity.class);
        manager.remove(alarmData.getAlarmId()); //TODO - FIX what when alarm is edited not created, then changes should be removed, not the whole alarm
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick (R.id.alarm_details_edit_location_button)
    public void onEditLocationButtonClick() {
        Intent intent = new Intent(this, ChooseLocationActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        alarmDetailsFragment.onActivityResult(requestCode, resultCode, data);
    }
}
