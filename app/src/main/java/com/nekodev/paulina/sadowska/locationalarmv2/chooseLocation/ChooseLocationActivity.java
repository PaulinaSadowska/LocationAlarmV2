package com.nekodev.paulina.sadowska.locationalarmv2.chooseLocation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appyvet.rangebar.IRangeBarFormatter;
import com.appyvet.rangebar.RangeBar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nekodev.paulina.sadowska.locationalarmv2.Constants;
import com.nekodev.paulina.sadowska.locationalarmv2.Keys;
import com.nekodev.paulina.sadowska.locationalarmv2.R;
import com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails.AlarmDetailsActivity;
import com.nekodev.paulina.sadowska.locationalarmv2.data.AlarmDataItem;
import com.nekodev.paulina.sadowska.locationalarmv2.data.DataManager;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseLocationActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final float STROKE_WIDTH = (float) 1.5;
    private static final int ZOOM = 13;
    private static final int FILL_COLOR = Color.argb(60, 0, 0, 0);
    private static final int STROKE_COLOR = Color.argb(200, 0, 0, 0);
    private LatLng pinLocalization = new LatLng(52.4034891, 16.946969); //assigned when phone localization not found
    private String placeAddress;
    private int radius = 1000;
    private GoogleMap mMap;
    private PlaceAutocompleteFragment autocompleteFragment;
    private GoogleApiClient mGoogleApiClient;
    private DataManager manager;

    private int alarmId = -1;

    @Bind(R.id.choose_location_radius_label)
    TextView radiusLabel;
    @Bind(R.id.choose_location_radius_seekbar)
    RangeBar radiusSeekbar;
    @Bind(R.id.choose_location_save)
    Button saveButton;
    @Bind(R.id.choose_location_cancel)
    Button cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
        ButterKnife.bind(this);
        manager = DataManager.getInstance(getFilesDir().getPath(), Constants.FILE_NAME);
        if(getIntent().hasExtra(Keys.ALARM_ID)){
            alarmId = getIntent().getIntExtra(Keys.ALARM_ID, alarmId);
            AlarmDataItem alarm = manager.get(alarmId);
            if(alarm!=null){
                pinLocalization = alarm.getCoordinates();
                placeAddress = alarm.getAddress();
                radius = alarm.getRadiusInMeters();
            }
            else{
                alarmId = -1;
            }
        }

        Resources res = getResources();
        saveButton.setText(res.getString(R.string.save));
        cancelButton.setText(res.getString(R.string.cancel));

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.choose_location_map);
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.choose_location_place_autocomplete_fragment);

        mapFragment.getMapAsync(this);
        BuildGoogleApiClient();
        configureAutocompleteFragment();

        radiusLabel.setText(getString(R.string.radius_label));
        radiusSeekbar.setRangePinsByValue(0, radius);
        radiusSeekbar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                radius = Integer.parseInt(rightPinValue);
                updateMap(false);
            }
        });
        radiusSeekbar.setFormatter(new IRangeBarFormatter() {
            @Override
            public String format(String s) {
                // Transform the String s here then return s
                return s + getString(R.string.radius_unit_label);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //outState.put(Keys.LocationData.COORDINATES, locationData.getCoordinates());

        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void BuildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    private void configureAutocompleteFragment() {
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            private final static String TAG = "PLACE_PICKER";

            @Override
            public void onPlaceSelected(Place place) {
                placeAddress = place.getAddress().toString();
                pinLocalization = place.getLatLng();
                updateMap(true);
            }

            @Override
            public void onError(Status status) {
                Log.e(TAG, "An error occurred: " + status);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateMap(true);
    }

    private void updateMap(boolean animate) {
        if (mMap == null) {
            return;
        }
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(pinLocalization));
        if (radius > 0)
            addMarkerWithCircle();
        if (animate) {
            moveCameraToPosition();
        }
    }

    private void moveCameraToPosition() {
        CameraPosition c = CameraPosition.builder()
                .target(pinLocalization)
                .zoom(ZOOM)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(c), 3000, null);
    }

    private void addMarkerWithCircle() {
        if (radius > 0) {
            mMap.addCircle(new CircleOptions()
                    .center(pinLocalization)
                    .radius(radius)
                    .fillColor(FILL_COLOR)
                    .strokeColor(STROKE_COLOR)
                    .strokeWidth(STROKE_WIDTH));
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(placeAddress == null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //disconnect to GoogleApiClient
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(ChooseLocationActivity.class.getName(), "Google Api Client connection failed");
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng placeLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        pinLocalization = placeLatLng;
        updateMap(true);
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @OnClick(R.id.choose_location_cancel)
    public void cancelLocalization(View view) {
        onBackPressed();
    }

    @OnClick(R.id.choose_location_save)
    public void saveLocalization(View view) {
        if(placeAddress == null){
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(pinLocalization.latitude, pinLocalization.longitude, 1);
                if(null!=listAddresses&&listAddresses.size()>0){
                    Address address = listAddresses.get(0);
                    placeAddress = "";
                    for(int i=0; i<address.getMaxAddressLineIndex(); i++) {
                        placeAddress += address.getAddressLine(i) + ", ";
                    }
                    placeAddress = placeAddress.substring(0, placeAddress.length()-2);
                }
            } catch (IOException e) {
                e.printStackTrace();
                placeAddress = "";
            }
        }
        captureScreen();
    }

    public void captureScreen() {
        if(alarmId<0) {
            alarmId = manager.addAlarm(pinLocalization, placeAddress, radius);
        }
        else{
            manager.editAlarmLocation(alarmId, pinLocalization, placeAddress, radius);
        }
        GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {

            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                OutputStream fout;
                try {
                    fout = openFileOutput(manager.getImageId(alarmId),
                            MODE_WORLD_READABLE);

                    // Write the string to the file
                    snapshot.compress(Bitmap.CompressFormat.JPEG, 90, fout);
                    fout.flush();
                    fout.close();
                } catch (Exception e) {
                    Log.d("ImageCapture", e.getMessage());
                }
                startAlarmDetailsActivity(alarmId);
            }
        };
        mMap.snapshot(callback);
    }

    private void startAlarmDetailsActivity(int alarmId) {
        Intent intent = new Intent(this, AlarmDetailsActivity.class);
        intent.putExtra(Keys.ALARM_ID, alarmId);
        startActivity(intent);
    }
}
